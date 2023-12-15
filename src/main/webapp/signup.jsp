<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>

<head>
<title>Registration Form</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://unpkg.com/dropzone@5/dist/min/dropzone.min.css"
	type="text/css" />

<meta content='<%=request.getAttribute("token")%>' name="-gg-token" />



</head>
<!-- class="dropzone" -->
<!-- <div class="previews"></div> -->

<body>
	<div class="container">
		<h2>Registration Form</h2>

		<form id="upload-form" class="dropzone" action="upload-avatar">
			<!-- this is were the previews should be shown. -->
			<div class="previews"></div>

			<button type="submit">Submit data and files!</button>


		</form>

		<form action="${pageContext.request.contextPath}/signup-action"
			method="post" id="signupForm">
			<!-- enctype="multipart/form-data" id="signupForm" -->
			<!--                 <div class="form-group"> -->
			<!--                     <label for="avatar">Select an avatar:</label> -->
			<!--                     <input type="file" class="form-control" id="avatar" name="avatar" required> -->
			<!--                 </div> -->
			<div class="form-group">
				<label for="fullName">Full Name:</label> <input type="text"
					class="form-control" id="fullName" name="fullName" required>
				<div id="fullNameError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="email">Email:</label> <input type="email"
					class="form-control" id="email" name="email" required>
				<div id="emailError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="phone">Phone:</label> <input type="text"
					class="form-control" id="phone" name="phone" required>
				<div id="phoneError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					class="form-control" id="password" name="password" required>
				<div id="passwordError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="confirmPassword">Confirm Password:</label> <input
					type="password" class="form-control" id="confirmPassword"
					name="confirmPassword" required>
				<div id="confirmPasswordError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="nickname">Nickname:</label> <input type="text"
					class="form-control" id="nickname" name="nickname" required>
				<div id="nicknameError" class="error" style="color: red;"></div>
			</div>
			
			<p id="MessageValidation" class="d-none" style="color: green"></p>
			
			<input type="hidden" name="-gg-token" id="-gg-token" /> <br>
			
			<button type="submit" class="btn btn-primary" id="register">Register</button>

			<script type="text/javascript">
                window.onload = function (event) {
                    //Get the token and insert to tname="-gg-token"")
                    let token = document.querySelector('[name="-gg-token"]').content;
                    document.getElementById('-gg-token').value = token;
                        //Configuration dropzon         
                        Dropzone.options.uploadForm = { // The camelized version of the ID of the form element

                            // The configuration we've talked about above
                            autoProcessQueue: false,
                            uploadMultiple: false,
                            parallelUploads: 1,
                            maxFiles: 1,
                            paramName: avatar,

                            // The setting up of the dropzone
                            init: function () {
                                var myDropzone = this;
// First change the button to actually tell Dropzone to process the queue.
                                this.element.querySelector("button[type=submit]").addEventListener("click", function (e) {
                                    // Make sure that the form isn't actually being sent.
                                    e.preventDefault();
                                    e.stopPropagation();
                                    myDropzone.processQueue();
                                });

                                // Listen to the sendingmultiple event. In this case, it's the sendingmultiple event instead
                                // of the sending event because uploadMultiple is set to true.
                                this.on("sendingmultiple", function () {
                                    // Gets triggered when the form is actually being sent.
                                    // Hide the success button or the complete form.
                                });
                                this.on("successmultiple", function (files, response) {
                                    // Gets triggered when the files have successfully been sent.
                                    // Redirect user or notify of success.
                                });
                                this.on("errormultiple", function (files, response) {
                                    // Gets triggered when there was an error sending the files.
                                    // Maybe show form again, and notify user of error
                                });
                            }

                        }
                    }

                    //Form Handler
                    const form =  document.querySelector('#signupForm');
                        form.addEventListener('submit', async (event) => {
                            //disable the default form action
                            event.preventDefault();
                            //collect data from the form
                            const formData = new FormData(form);
                            
                            
                            //send the data using fetch
                            let response = await fetch(form.action, {
                                headers: {
                                    'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
                                },
                                method: form.method,
                                body: new URLSearchParams(formData).toString()
                            })
                            let json = await response.json();
                            console.log(json);

                            Object.keys(json).forEach(key => {
                                console.log(key, " = ", json[key]);
                                if (key != null && key != "mail-send") {
                                	document.getElementById(key + "Error").innerHTML = json[key];
                                	
                                } else {
                                    // No errors, registration is successful
  //                                  alert('Registration is successful.');
                                    alert(json["mail-send"]);
                                    
                                    // Reload the page after a short delay (you can adjust the delay as needed)
                                    setTimeout(function() {
                                        location.reload();
                                    }, 1000);
                                    window.location.href = "${pageContext.request.contextPath}/";
                                }
                            });

//                             window.location.href = "${pageContext.request.contextPath}/";
                            
                        });
                </script>

		</form>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL"
		crossorigin="anonymous"></script>
	<script src="https://unpkg.com/dropzone@5/dist/min/dropzone.min.js"></script>
</body>

</html>