<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<link rel="stylesheet" href="./assets/css/header.css">
	</head>
	<body>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <div class="container-fluid">
		    <a class="navbar-brand" href="#">
			    <span class="material-symbols-outlined">
					home
				</span>
		    </a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarNav">
		      <ul class="navbar-nav">
		        <li class="nav-item">
		          <a class="nav-link active" aria-current="page" href="MonthlyIncome">Entradas</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="goal">Objetivos</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="expense">Despesas</a>
		        </li>
		        <li class="nav-item">
		          <a class="nav-link" href="report">Relatório</a>
		        </li>
		      </ul>
		    </div>
		  </div>
			<a style="cursor: pointer; " href="logout">
				<span class="material-symbols-outlined" style="color: #FFF; font-size: 32px; margin-right: 20px;">
					logout
				</span>
			</a>
		</nav>
		<script src="./assets/js/header.js"></script>
	</body>
</html>