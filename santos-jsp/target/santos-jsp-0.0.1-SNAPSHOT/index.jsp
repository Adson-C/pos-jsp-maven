<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">

<title>ADS Jsp</title>
<style type="text/css">

body {
background-color: #F5F5DC;

}

form {
	position: absolute;
	top: 40%;
	left: 33%;
	right: 33%;
	
}
h4 {
	position: absolute;
	top: 30%;
	left: 33%;
	color: green;
	font-style: oblique;
	font-family: monospace;
}

h5 {
	position: absolute;
	top: 10%;
	left: 33%;
	font-size: 15px;
	font-family: sans-serif;
	font-style: italic;
	color: #FF4200;
    background-color: #fff3cd;
    border-color: #ffecb5;
}
.btn {
box-shadow: 5px 10px 10px #000;
} 

label {
color: #556B2F;
box-shadow: 1px 2px 2px #000;
font-family: sans-serif;
font-style: italic;
}



</style>

</head>
<body>
	<h4>Bem vindo ao ADS JSP</h4>


	<form action="<%= request.getContextPath() %>/ServletLogin" method="post" class="row g-3 needs-validation" novalidate>
	
		<input type="hidden" value="<%=request.getParameter("url")%>"name="url"> 
		
		<div class="mb-3">
			<label class="form-label">Login</label>
			 <input class="form-control" name="login" type="text" required="required"> 
			 <div class="valid-feedback">
      		OK
    </div>
     <div class="invalid-feedback">
      		Preenchimento Obrigadório
    </div>
		</div>	 
		
		<div class="mb-3">
			 <label class="form-label">Senha</label>
			  <input class="form-control" name="senha" type="password" required="required"> 
			  <div class="valid-feedback">
      		OK
    </div>
    <div class="invalid-feedback">
      		Preenchimento Obrigadório
    </div>
			 
		</div>
		
			<input id="btn" type="submit" value="Acessar" class="btn btn btn-info">

	</form>

	<h5>${msg}</h5>
	<!-- Option 1: Bootstrap Bundle with Popper -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
		crossorigin="anonymous"></script>
		
		<script type="text/javascript">
		
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function () {
		  'use strict'

		  // Fetch all the forms we want to apply custom Bootstrap validation styles to
		  var forms = document.querySelectorAll('.needs-validation')

		  // Loop over them and prevent submission
		  Array.prototype.slice.call(forms)
		    .forEach(function (form) {
		      form.addEventListener('submit', function (event) {
		        if (!form.checkValidity()) {
		          event.preventDefault()
		          event.stopPropagation()
		        }

		        form.classList.add('was-validated')
		      }, false)
		    })
		})()
		
		</script>
</body>
</html>