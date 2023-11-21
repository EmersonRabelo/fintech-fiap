<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Registro</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
		<link rel="stylesheet" href="./assets/css/register.css">
		<link rel="stylesheet" href="./assets/css/global.css">
	</head>
	<body>
	
		
		<main>
			<div class="welcome">
				<h2 class="register-title">Bem-vindo!</h2>
				<img alt="" src="./assets/icons/fintech_logo.svg" class="mb-4">
			</div>
			<div class="register-form">
				<form id="register-form">
					<div class="mb-3">
					  <label for="nameComplete" class="form-label">Nome</label>
					  <input type="text" class="form-control" name="completeName" id="completeName" placeholder="Nome completo">
					</div>
					<div class="mb-3">
					  <label for="occupation" class="form-label">Cargo</label>
					  <input type="text" class="form-control" name="occupation" id="occupation" placeholder="Cargo">
					</div>
					<div class="mb-3">
					  <label for="occupation" class="form-label">Data de nascimento</label>
					  <input type="date" class="form-control" name="date_birth" id="date_birth" placeholder="Cargo">
					</div>
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
					<div>
						<label for="ConfirmPassword" class="form-label">Confirmar senha</label>
						<input type="password" class="form-control" id="ConfirmPassword" placeholder="*********">
					</div>
					<input disabled="disabled" type="submit" value="Cadastrar" id="btnSubmit" class="btn btn-success mt-3">
				</form>
			</div>
		</main>
	
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script defer src="./assets/js/register.js"></script>
	</body>
</html>