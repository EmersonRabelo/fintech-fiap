function excluirItem(id) {
    var location = "goal";
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

var $goalCompletChange = document.getElementById('changeGoalCompletion');
var $goalDeadLineChange = document.getElementById('changeGoalDeadline');
var $goalTitleChange = document.getElementById('changeGoalTitle');

var $goalCompletAdd = document.getElementById('addGoalCompletion');
var $goalDeadLineAdd = document.getElementById('addGoalDeadline');
var $goalTitleAdd = document.getElementById('addGoalTitle');


function alterarItem(id) {
	
	logId = id;
	
    var location = "goal";
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
		
		$goalCompletChange.value = data.completion * 100;
		$goalDeadLineChange.value = data.deadLine;
		$goalTitleChange.value = data.name;
		
		myModal.show()
	})
	.catch(error => console.error('Erro:', error));
}



document.getElementById('submit-changes').addEventListener('click', e => {
	e.preventDefault();
	
	var goalCompletChange = $goalCompletChange.value;
	var goalDeadLineChange = $goalDeadLineChange.value;
	var goalTitleChange = $goalTitleChange.value;
	
	var body = {
		"id" : logId,
		"completion" : goalCompletChange,
		"deadLine" : goalDeadLineChange,
		"name" : goalTitleChange
	}
	
	console.log('teste',body);
	
	fetch("goal", {
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
	
	var goalCompletAdd = $goalCompletAdd.value;
	var goalDeadLineAdd = $goalDeadLineAdd.value;
	var goalTitleAdd = $goalTitleAdd.value;
	
	var body = {
		"completion" : goalCompletAdd,
		"deadLine" : goalDeadLineAdd,
		"name" : goalTitleAdd
	}
	
	console.log('teste',body);
	
	fetch("goal", {
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


function validarNumeroInteiro(input) {
    input.value = input.value.replace(/[\.,]/g, '');
    input.value = input.value.replace(/[^\d]/g, '');

    var numeroInteiro = parseInt(input.value, 10);

    if (isNaN(numeroInteiro)) {
        input.value = '';
    }
}

