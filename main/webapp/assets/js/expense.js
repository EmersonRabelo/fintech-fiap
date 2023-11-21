function excluirItem(id) {
    var location = "expense";
    var params = "?id=" + id;
    var url = location + params;

    Swal.fire({
        title: "Tem certeza?",
        text: "Você não poderá reverter isto!",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#3085d6",
        cancelButtonColor: "#d33",
        cancelButtonText: "Cancelar",
        confirmButtonText: "Sim, exclua!",
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(url, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
            }).then((response) => {
                    if (!response.ok) {
	                    Swal.fire({
						  position: "top-end",
						  icon: "error",
						  title: "Não foi possivel excluir.",
						  text: "Por favor, ente novamente mais tarde!",
						  showConfirmButton: false,
						  timer: 1500
						});
						throw new Error("Erro ao excluir o item");
                    }
                    Swal.fire({
                        title: "Excluido!",
                        text: "Excluido com sucesso!",
                        icon: "success",
                    }).then(() => {
                        window.location.href = window.location.href;
                    });
                })
                .catch((error) => {
					Swal.fire({
					  position: "top-end",
					  icon: "error",
					  title: "Não foi possivel excluir.",
					  text: "Por favor, ente novamente mais tarde!",
					  showConfirmButton: false,
					  timer: 1500
					});
					
                    console.error("Erro ao excluir o item", error);
                });
        }
    });
}

var logId;

var $expenseTitleInputChange = document.getElementById('changeExpenseTitleINPUT');
var $expenseValueInputChange = document.getElementById('changeExpenseValue');
var $expenseTitleInputAdd = document.getElementById('addExpenseTitleINPUT');
var $expenseValueInputAdd = document.getElementById('addExpenseValue');

function alterarItem(id) {
	
	logId = id;
	
    var location = "expense";
    var params = "?id=" + id;
    var url = location + params;
    
    fetch(url, {
		method: "GET",
		headers: {
			"Content-Type": "application/json",
		}
	})
	.then(response => response.json())
	.then(data => {
		var myModal = new bootstrap.Modal(document.getElementById('changeItemModal'), '');
		
		$expenseTitleInputChange.value = data.name;
		$expenseValueInputChange.value = data.value;
		
		myModal.show()
	})
	.catch(error => console.error('Erro:', error));
}



document.getElementById('submit-changes').addEventListener('click', e => {
	e.preventDefault();
	
	var incomeTitle = $expenseTitleInputChange.value;
	var incomeValue = $expenseValueInputChange.value;
	
	var body = {
		"id" : logId,
		"title" : incomeTitle,
		"value" : incomeValue
	}
	
	fetch("expense", {
		method: "PUT",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(body)
	}).then(res => {
		
		if (res.status == 201){
			Swal.fire({
			    title: "Alterado!",
			    text: "Alterado com sucesso!",
			    icon: "success",
		    }).then(() => {
		    	window.location.href = window.location.href;
		    });	
		} else {
			console.error('Falha ao editar')
		}

	}).catch(error => console.error('Erro:', error));
	
});


document.getElementById('submit-add').addEventListener('click', e => {
	e.preventDefault();
	
	var expenseTitleAdd = $expenseTitleInputAdd.value;
	var expenseValueAdd = $expenseValueInputAdd.value;
	
	var body = {
		"title" : expenseTitleAdd,
		"value" : expenseValueAdd
	}
	
	fetch("expense", {
		method: "POST",
		headers: {
			"Content-Type": "application/json",
		},
		body: JSON.stringify(body)
	}).then(res => {
		
		if (res.status == 201){
			Swal.fire({
			    title: "Adicionado!",
			    text: "Adicionado com sucesso!",
			    icon: "success",
		    }).then(() => {
		    	window.location.href = window.location.href;
		    });	
		} else {
			console.error('Falha ao Adicionar')
		}

	}).catch(error => console.error('Erro:', error));
	
	
})

