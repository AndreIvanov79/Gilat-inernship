<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Calendar" %>
<!doctype html>
<html lang="en">


<head>
<title>Sign In Form</title>
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

<body class="d-flex flex-column min-vh-100 px-5 pt-5">
    <h1>Welcome to GODZILA GAME</h1>
    
    <div class="container">
		<h2>Sign In Form</h2>
    
	    <form action="${pageContext.request.contextPath}/signin"
			method="post" id="signinForm">
			
			<div class="form-group">
				<label for="email">Email:</label> <input type="email"
					class="form-control" id="email" name="email" required>
				<div id="emailError" class="error" style="color: red;"></div>
			</div>
			<div class="form-group">
				<label for="password">Password:</label> <input type="password"
					class="form-control" id="password" name="password" required>
				<div id="passwordError" class="error" style="color: red;"></div>
			</div>
			
			<button type="submit" class="btn btn-primary" style="margin-top:20px;">Sign In</button>
    	</form>
	</div>
	
	<footer class="footer mt-auto py-3 bg-light">
	    <div class="container text-center">
	        <span class="text-muted">Student &copy; <%= Calendar.getInstance().get(Calendar.YEAR) %> </span>
	    </div>
	</footer>
	
	<script type="text/javascript">
	
	//Form Handler
    const form =  document.querySelector('#signinForm');
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
                if (key != null) {
                	document.getElementById(key + "Error").innerHTML = json[key];
//                 	setTimeout(function() {
//                         location.reload();
//                     }, 1000);
                } else {
                	alert('Successful sign in.');
                    
                    // Reload the page after a short delay (you can adjust the delay as needed)
                    setTimeout(function() {
                        location.reload();
                    }, 1000);
                	window.location.href = "${pageContext.request.contextPath}/download";
                }
            });

 //             window.location.href = "${pageContext.request.contextPath}/download";
            
        });
	</script>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>

</html>