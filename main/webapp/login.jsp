<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Login</title>		
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous"/>
		<link rel="stylesheet" href="./assets/css/login.css">

		<script defer src="./assets/js/login.js"></script>
	</head>
	<body>
		<main class="container-fluid main-container">
			<img alt="" src="./assets/icons/fintech_logo.svg" class="mb-4">
			<div class="form">
				<h1 class="login-title">Faça seu login</h1>
				<form action="login" method="POST">
					<div class="mb-1 form-field-wrapper">
						<label for="email" class="form-label">
							E-mail
						</label>
						<input type="email" class="form-control" id="email" name="email" placeholder="name@example.com">
					</div>
					<div>
						<label for="password" class="form-label">Senha</label>
						<input type="password" class="form-control" name="pwd" id="password" placeholder="*********">
					</div>
					<input type="submit" value="Login" class="btn btn-success mt-3 mx-4">
				</form>
			</div>
			<a href="" class="forgot-password-btn">Esqueci a senha</a>
			<a href="register.jsp" class="sign-up-btn mt-3">Não tem uma conta? <br/>Registrar</a>
		</main>	
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
	</body>
</html>