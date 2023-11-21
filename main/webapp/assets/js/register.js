var $inputPwd = document.getElementById("password");
var $inputPwdConfirm = document.getElementById("ConfirmPassword");
var $btnSubimit = document.getElementById("btnSubmit");

$inputPwdConfirm.addEventListener('change', e => {
	e.preventDefault();
	
	if ($inputPwdConfirm.value == "") return;
	
	console.log($inputPwd.value);
	console.log($inputPwdConfirm.value);
	
	if ($inputPwdConfirm.value == $inputPwd.value){
		$btnSubimit.removeAttribute("disabled");
	} else {
		$btnSubimit.setAttribute("disabled","disabled");
	}
	
});

document.addEventListener('DOMContentLoaded', e => {
	var $form = document.getElementById('register-form');
	
	$form.addEventListener('submit', e => {
		e.preventDefault();
				
		 var formData = new FormData($form);
		 
		 var nome = formData.get("completeName");
         var email = formData.get("email");
         var pwd = formData.get("pwd");
         var birth = formData.get("date_birth");
         var occupation = formData.get("occupation");
         
         var body = {
			 nome,
			 email,
			 pwd,
			 birth,
			 occupation
		 }
		 
		fetch("register", {
            method: "POST",
            body: JSON.stringify(body)
        })
        .then(function(response) {
            // Aqui você obtém o status code da resposta
            var statusCode = response.status;
            if (statusCode == 202){
				Swal.fire({
					position: "center",
					icon: "warning",
					title: "Usuário já existente.",
					text: "Estamos direcionando para a pagina de login.",
					showConfirmButton: false,
					timer: 3000
				}).then(()=>{
					 window.location.href = "login.jsp";
				});
			} else {
				window.location.href = "goal";
			}


            // Se desejar, você pode processar o corpo da resposta aqui
            return response.text(); // ou response.json() se a resposta for em JSON
        })
        .then(function(data) {
            // Faça algo com os dados da resposta (se necessário)
            console.log("Resposta do servidor:", data);
        })
        .catch(function(error) {
            console.error("Erro durante a requisição:", error);
        });
         
		 
		
	})	
});