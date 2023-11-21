<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Metas</title>
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous"/>
		<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
		<link rel="stylesheet" href="./assets/css/goal.css">
		<link rel="stylesheet" href="./assets/css/global.css">
	</head>
	<body>
		<header style="width: 100%;">
			<%@ include file="header.jsp" %>
		</header>
		
			<main class="result-list">
		<c:if test="${!empty goals}">
			<div class="result">
				<table class="result-table table table-hover ">
					<thead class="table-dark">
						<tr>
			      			<th scope="col" colspan="2">Título</th>
			      			<th scope="col">Data limite</th>
			      			<th scope="col">Completo</th>
			      			<th scope="col">&nbsp;</th>
			    		</tr>
					</thead>
					<tbody>
					<c:forEach items="${goals}" var="goal">
			            <tr scope="row">
			                <td colspan="2">${goal.name}</td>
			                
			                <fmt:setLocale value="pt_BR" />
							<fmt:parseDate value="${goal.deadline}" pattern="yyyy-MM-dd" var="parsedDate" />
							<fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy" var="formattedDate" />
							<td>${formattedDate}</td>
							
			                <fmt:formatNumber value="${goal.completion * 100}" pattern="#,##0.00" type="number" var="formattedCompletion" />
							<td>${formattedCompletion}%</td>
							
			                <td>
			                	<div class="dropdown">
			                	<span class="btn material-symbols-outlined" type="button" id="tableMore" data-bs-toggle="dropdown" aria-expanded="false">
									more_vert
								</span>
								  <ul class="dropdown-menu" aria-labelledby="tableMore">
								    <li>
					                	<button class="dropdown-item" onclick="alterarItem(${goal.id})">
					                		Alterar
					                	</button>
								    </li>
								    <li>
					                	<button class="dropdown-item" onclick="excluirItem(${goal.id})">
					                		Excluir
					                	</button>
								    </li>
								  </ul>
								</div>
			                </td>
			            </tr>
		        	</c:forEach> 
					</tbody>
				</table>
			</div>
				<div>
					<button class="add_income" data-bs-toggle="modal" data-bs-target="#addItemModal">
						<span class="material-symbols-outlined">
							add_circle
						</span>
					</button>
				</div>
		</c:if>
		<c:if test="${empty goals}">
			<h3 style="margin-top: 20px;" class="no-result">Nenhum item encontrado!</h3>
		</c:if>
		<c:if test="${empty goals}">
			<div style="margin-top: 20px;">
				<button class="add_income" data-bs-toggle="modal" data-bs-target="#addItemModal">
					<span class="material-symbols-outlined">
						add_circle
					</span>
				</button>
			</div>
		</c:if>
	</main>
	
	<div class="modal fade" id="changeItemModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="staticBackdropLabel">Alterar Meta</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <div class="input-group mb-3">
			  <input type="text" class="form-control" id="changeGoalTitle" placeholder="Título" aria-label="Título da entrada" aria-describedby="goalTitleLabel">
			  <span class="input-group-text" id="goalTitleLabel">Título</span>
			</div>
	        <div class="input-group mb-3">
			  <input type="date" class="form-control" id="changeGoalDeadline" placeholder="Data limite" aria-label="Título da entrada" aria-describedby="goalDeadlineLabel">
			  <span class="input-group-text" id="goalDeadlineLabel">Data limite</span>
			</div>
			<div class="input-group mb-3">
			  <input type="number" onchange="validarNumeroInteiro(this)" class="form-control" id="changeGoalCompletion" placeholder="Realizado" aria-label="Título da entrada" aria-describedby="goalCompletionLabel">
			  <span class="input-group-text" id="goalCompletionLabel">(%) Realizado</span>
			</div>
			  <div class="col-auto">
			    <button class="btn btn-primary mb-3" id="submit-changes">Confirmar</button>
			  </div>
	      </div>
	    </div>
	  </div>
	</div>
	
	<div class="modal fade" id="addItemModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
	  <div class="modal-dialog modal-dialog-centered">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="staticBackdropLabel">Adicionar entrada</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <div class="input-group mb-3">
			  <input type="text" class="form-control" id="addGoalTitle" placeholder="Título" aria-label="Título da entrada" aria-describedby="goalTitleLabeladd">
			  <span class="input-group-text" id="goalTitleLabeladd">Título</span>
			</div>
	        <div class="input-group mb-3">
			  <input type="date" class="form-control" id="addGoalDeadline" placeholder="Data limite" aria-label="Título da entrada" aria-describedby="goalDeadlineLabeladd">
			  <span class="input-group-text" id="goalDeadlineLabeladd">Data de limite</span>
			</div>
			<div class="input-group mb-3">
			  <input type="number" onchange="validarNumeroInteiro(this)" class="form-control" id="addGoalCompletion" placeholder="Realizado (%)" aria-label="Título da entrada" aria-describedby="goalCompletionLabeladd">
			  <span class="input-group-text" id="goalCompletionLabeladd">(%) Realizado</span>
			</div>
			  <div class="col-auto">
			    <button class="btn btn-primary mb-3" id="submit-add">Confirmar</button>
			  </div>
	      </div>
	    </div>
	  </div>
	</div>
	
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
		<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
		<script defer src="./assets/js/goal.js"></script>
	</body>
</html>