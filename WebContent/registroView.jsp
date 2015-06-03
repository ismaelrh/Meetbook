<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="infraestructura.*"%>
<html>
<%
		Map <String,String> e = (Map<String,String>) request.getAttribute("errores");

			 /* Errores */
			 String emailError ="";
			 String pwError ="";
			 String repwError ="";
			 String nameError ="";
			 String surnameError ="";
			 String nickError = "";

		   /* Valores previamente introducidos */
			 String emailValor = "";
			 String nameValor = "";
			 String surnameValor = "";
			 String nickValor = "";

			 if(e!=null){

		/* Hay errores*/

		 String cabeceraError = "<span style=\"color:red\">";
		 String finalError = "</span>";
		 if(e.containsKey("email")) emailError = cabeceraError + e.get("email") + finalError;
		 if(e.containsKey("pw")) pwError = cabeceraError + e.get("pw") + finalError;
		 if(e.containsKey("repw")) repwError = cabeceraError + e.get("repw") + finalError;
		 if(e.containsKey("name")) nameError = cabeceraError + e.get("name") + finalError;
		 if(e.containsKey("surname")) surnameError = cabeceraError + e.get("surname") + finalError;
		 if(e.containsKey("nick")) nickError = cabeceraError + e.get("nick") + finalError;
			 }

			//Rellenamos valores previos
			 emailValor = (String) request.getAttribute("email");
			 nameValor =  (String)request.getAttribute("name");
			 surnameValor =  (String)request.getAttribute("surname");
			 nickValor = (String) request.getAttribute("nick");
		
			 if(emailValor == null) emailValor = "";
			 if(nameValor == null) nameValor = "";
			 if(surnameValor == null) surnameValor = "";
			 if(nickValor == null) nickValor = "";
	%>
<head>
	<meta charset="UTF-8">
	<title>Registrar usuario || MeetBook</title>
	<meta name="keywords" content="Registrar, eventos">
	<meta name="description" content="Registrar nuevo usuario en la web">
	<link rel="stylesheet" type="text/css" href="style.css" />
	<!-- Bootstrap  -->
	<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
	<script src="/MeetBook/js/bootstrap.min.js"></script>

</head>
<body background="images/wallpaper.jpg">

	



	<!-- CONTENIDO -->

	<div id="login-overlay" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<a href="/MeetBook/loginView.jsp"><button type="button"
						class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span><span class="sr-only">Close</span>
					</button></a>
				<h4 class="modal-title" id="myModalLabel">Registrarse en
					MeetBook</h4>
			</div>
			<div class="modal-body">
				<div class="row">
					<div>
						<div>
							<form id="loginForm" method="POST" action="/MeetBook/Registro.do"
								novalidate="novalidate">
								<div class="col-xs-12">
									<div class="form-group col-xs-6">
										<label for="email" class="control-label">Mail</label> 
										<input
											type="mail" class="form-control" id="idEmail" name="email"
											required="true"
											value="<%=emailValor%>"> <span
											class="errorText" id="mailError"><%=emailError%></span>
									</div>
									<div class="form-group col-xs-6">
										<label for="name" class="control-label">Nombre</label> <input
											type="text" class="form-control" id="idName" name="name"
											value="<%=nameValor %>" required="">
										<span class="errorText" id="nameError"><%=nameError%></span>
									</div>
									<div class="form-group col-xs-6">
										<label for="password" class="control-label">Contraseña</label>
										<input type="password" class="form-control" id="idPassword"
											name="password" value="" required=""
											onkeyup="comprobarPass(); return false;"> <span
											class="errorText" id="passwordError"><%=pwError%></span>
									</div>
									<div class="form-group col-xs-6">
										<label for="surname" class="control-label">Apellido</label> <input
											type="text" class="form-control" id="idSurname" name="surname"
											value="<%= surnameValor %>" required=""> <span
											class="errorText" id="surnameError"><%=surnameError%></span>
									</div>
									<div class="form-group col-xs-6">
										<label for="repassword" class="control-label">Repita
											contraseña</label> <input type="password" class="form-control"
											id="idRepassword" name="repassword" value="" required=""
											onkeyup="comprobarPass(); return false;"> <span
											class="errorText" id="repasswordError"><%=repwError%></span>
									</div>
									<div class="form-group col-xs-6">
										<label for="nick" class="control-label">Nick</label> <input
											type="text" class="form-control" id="idNick" name="nick"
											value="<%=nickValor %>" required="" title="Please enter your password"
											> <span
											class="errorText" id="nickError"><%=nickError%></span>
									</div>
									<!--   <div id="loginErrorMsg" class="alert alert-error"></div> -->
									<button type="submit" class="btn btn-success btn-block">Registrarse</button>
									
								</div>

							</form>
						</div>
					</div>

				</div>
			</div>
		</div>
	</div>
	<!--Funciones Javascript, ejecutadas algunas al modificar campos y otra al pulsar botón de borrar.
					Se usan para escribir en los avisos de contraseña mal repetida, campo necesario...
					No obstante el formulario puede enviarse con campos en mal estado-->
	<script>
		/*
		 * Borra todos campos de error
		 */
		function clearErrors() {
			document.getElementById('emailError').innerHTML = "";
			document.getElementById('passwordError').innerHTML = "";
			document.getElementById('repasswordError').innerHTML = "";
			document.getElementById('nameError').innerHTML = "";
			document.getElementById('surnameError').innerHTML = "";
			document.getElementById('yearError').innerHTML = "";
		}

		

		/*
		 * Muestra errores de passwords no coincidentes, y de campo necesario para password.
		 */
		function comprobarPass() {
			//Campos de texto
			var password = document.getElementById('idPassword');
			var repassword = document.getElementById('idRepassword');
			//Mensajes de error
			var error = document.getElementById('passwordError');
			var re_error = document.getElementById('repasswordError');
			//Colores
			var verde = "#04B404"
			var rojo = "#FF0000"

			error.innerHTML = "";
			if (password.value == repassword.value) { //Coinciden contraseñas
				if (password.value.length != 0) {
					re_error.innerHTML = "Las contraseñas coinciden"
					re_error.style.color = verde;
				} else { //No ha puesto nada
					re_error.innerHTML = "";
				}
			} else { //No coinciden contraseñas
				re_error.style.color = rojo;
				re_error.innerHTML = "Las contraseñas no coinciden"
				error.style.color = rojo;
			}

		}
	</script>
</body>

</html>
