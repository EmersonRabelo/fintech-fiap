<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Entradas</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous"/>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
	<link rel="stylesheet" href="./assets/css/income.css">
	<link rel="stylesheet" href="./assets/css/global.css">
</head>
<body>
	<header style="width: 100%;">
		<%@ include file="header.jsp" %>
	</header>


	<main class="result-list">
		<c:if test="${!empty incomes}">
			<div class="result">
				<table class="result-table table table-hover ">
					<thead class="table-dark">
						<tr>
			      			<th scope="col" colspan="2">Título</th>
			      			<th scope="col">Valor</th>
			      			<th scope="col">&nbsp;</th>
			    		</tr>
					</thead>
					<tbody>
					<c:forEach items="${incomes}" var="incomes">
			            <tr scope="row">
			                <td colspan="2">${incomes.name}</td>
			                <td><fmt:formatNumber value = "${incomes.value}" type = "currency"/>  </td>
			                <td>
			                	<div class="dropdown">
			                	<span class="btn material-symbols-outlined" type="button" id="tableMore" data-bs-toggle="dropdown" aria-expanded="false">
									more_vert
								</span>
								  <ul class="dropdown-menu" aria-labelledby="tableMore">
								    <li>
					                	<button class="dropdown-item" onclick="alterarItem(${incomes.id})">
					                		Alterar
					                	</button>
								    </li>
								    <li>
					                	<button class="dropdown-item" onclick="excluirItem(${incomes.id})">
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
		<c:if test="${empty incomes}">
			<h3 style="margin-top: 20px; class="no-result">Nenhum item encontrado!</h3>
		</c:if>
		<c:if test="${empty incomes}">
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
	        <h5 class="modal-title" id="staticBackdropLabel">Alterar entrada</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	        <div class="input-group mb-3">
			  <input type="text" class="form-control" id="changeIncomeTitleINPUT" placeholder="Título" aria-label="Título da entrada" aria-describedby="changeIncomeTitle">
			  <span class="input-group-text" id="changeIncomeTitle">Título</span>
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text">R$</span>
			  <input type="number" class="form-control" aria-label="Valor (em reais)" id="changeIncomeValue">
			  <span class="input-group-text">Valor</span>
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
			  <input type="text" class="form-control" id="addIncomeTitleINPUT" aria-label="Título da entrada" aria-describedby="changeIncomeTitle">
			  <span class="input-group-text" id="addIncomeTitle">Título</span>
			</div>
			<div class="input-group mb-3">
			  <span class="input-group-text">R$</span>
			  <input type="number" class="form-control" aria-label="Valor (em reais)" id="addIncomeValue">
			  <span class="input-group-text">Valor</span>
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
	<script defer src="./assets/js/income.js"></script>
</body>
</html>