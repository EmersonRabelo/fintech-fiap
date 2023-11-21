<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Relatório mensal</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
		<link rel="stylesheet" href="./assets/css/global.css">
		<link rel="stylesheet" href="./assets/css/report.css">
	</head>
	<body>
		<header style="width: 100%;">
			<%@ include file="header.jsp" %>
		</header>

		<main class="result-list">
		<c:if test="${!empty reports}">
			<div class="result">
				<table class="result-table table table-hover ">
					<thead class="table-dark">
						<tr>
			      			<th scope="col" colspan="2">Mês/Ano</th>
			      			<th scope="col">Entradas</th>
			      			<th scope="col">Saídas</th>
			    		</tr>
					</thead>
					<tbody>
						<c:forEach items="${reports}" var="report">
							<tr scope="row">
				                <td colspan="2">${report.month}</td>
				                <td><fmt:formatNumber value = "${report.incomesValue}" type = "currency"/> </td>
				                <td><fmt:formatNumber value = "${report.expensesValue}" type = "currency"/> </td>
				            </tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</c:if>
	<c:if test="${empty reports}">
		<h3 style="margin-top: 20px; class="no-result">Nenhum item encontrado!</h3>
	</c:if>
	</main>
	
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script defer src="./assets/js/report.js"></script>
	</body>
</html>