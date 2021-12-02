(function(){

	document.getElementById("loginbutton").addEventListener('click', (e) => {
	  
		var formlogin = e.target.closest("form");
		
		if (formlogin.checkValidity()) {
			httpRequest('CheckLogin', new FormData(formlogin),
				function(xhr) {
					let ret = JSON.parse(xhr.responseText);
					
					console.log("ESITO CHECK LOGIN");
					console.log(ret);
					
					document.getElementById("errormess1").textContent = ret.exitMess;
					if(ret.exitCode==0){
						sessionStorage.setItem('userid', ret.userId);
						window.location.href = ret.pageTarget;
					}
				}
			);
		} else {
			formlogin.reportValidity();
		}
	});

	document.getElementById("registerbutton").addEventListener('click', (e) => {
		
		let pwd1 = document.getElementById("pwd1");
		let pwd2 = document.getElementById("pwd2");
		let email = document.getElementById("email");
		
		if(pwd1.value==pwd2.value){
			
			if(/^[A-Z0-9-+_\.]+\@[A-Z0-9-+_\.]+$/i.test(email.value)){
				
				var objForm = e.target.closest("form");
				
				if (objForm.checkValidity()) {
					httpRequest('RegisterUser', new FormData(objForm),
						function(xhr) {
							let ret = JSON.parse(xhr.responseText);
							if(ret.exitCode==0)
								document.getElementById("errormess2").style.color = "green";
							else
								document.getElementById("errormess2").style.color = "red";
								
							document.getElementById("errormess2").textContent = ret.exitMess;
							
							if(ret.exitCode==0)
								objForm.reset();
						}
					);
				} else {
					objForm.reportValidity();
				}
			}
			else{
				document.getElementById("errormess2").style.color = "red";
				document.getElementById("errormess2").textContent = "Indirizzo email non corretto";
			}
		}
		else{
			document.getElementById("errormess2").style.color = "red";
			document.getElementById("errormess2").textContent = "Le password sono digitate in modo differente";
		}
	});

})();