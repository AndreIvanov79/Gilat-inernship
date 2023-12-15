<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.Calendar" %>
<!doctype html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>

<body class="d-flex flex-column min-vh-100 px-5 pt-5">
    <h1>Welcome to GODZILA GAME</h1>
    
    <div class="container my-auto mx-0">
    
	    <h2>Sign In here</h2>
	    <a class = "btn btn-warning btn-lg rounded-pill" href = "${pageContext.request.contextPath}/signin">Sign In</a>
	    <br>
	    <br>
	    <h2>Register here</h2>
	    <a class = "btn btn-warning btn-lg rounded-pill" href = "signup">Register</a>
	    <br>
	    <br>
<!-- 	    <h2>Download the game here</h2> -->
<%-- 	    <a class = "btn btn-warning btn-lg rounded-pill" href = "${pageContext.request.contextPath}/download">Download</a> --%>
<!-- 	    <br> -->
<!-- 	    <br> -->
    
	</div>
	
	<footer class="footer mt-auto py-3 bg-light">
	    <div class="container text-center">
	        <span class="text-muted">Student &copy; <%= Calendar.getInstance().get(Calendar.YEAR) %> </span>
	    </div>
	</footer>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
        crossorigin="anonymous"></script>
</body>

</html>