<%-- JSP que muestra formulario de login. --%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="java.util.Map"%>
<html>

<%
	Map<String, String> e = (Map<String, String>) request
			.getAttribute("errores");
	String loginError = "";
	

	//Valor conservado de email
	String emailValor = "";

	if (e != null) {
		if (e.containsKey("loginError"))
			loginError =  e.get("loginError");
	}

	emailValor = (String) request.getAttribute("email");
	if (emailValor == null)
		emailValor = "";
%>
<head>
<meta charset="UTF-8">
<title>Entrar en tu sesión || MeetBook</title>
<meta name="keywords" content="Entrar, eventos">
<meta name="description" content="Entrar en tu sesión">
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
				<h4 class="modal-title" id="myModalLabel">Entrar en MeetBook</h4>
			</div>
			<div class="modal-body">
			<%
			if(loginError != ""){
				out.println("<div class=\"alert alert-danger\">" + loginError  +"</div>");
			}
			%>
			
				<div class="row">
					<div class="col-xs-6">
						<div class="well">
						
							<form id="loginForm" method="POST" action="/MeetBook/Login.do"
								novalidate="novalidate">
								<div class="form-group">
									<label for="username" class="control-label">Mail</label> <input
										type="text" class="form-control" id="username" name="email"
										value="" required="" title="Please enter you username"
										placeholder="example@gmail.com"> <span
										class="help-block"></span>
								</div>
								<div class="form-group">
									<label for="password" class="control-label">Contraseña</label>
									<input type="password" class="form-control" id="password"
										name="password" value="" required=""
										title="Please enter your password"> <span
										class="help-block"></span>
								</div>
								<!--   <div id="loginErrorMsg" class="alert alert-error"><%=loginError%></div> -->
								<button type="submit" class="btn btn-success btn-block">Login</button>

							</form>
						</div>
					</div>
					<div class="col-xs-6">
						<p class="lead">
							Regístrate ahora <span class="text-success">GRATIS</span>
						</p>
						<ul class="list-unstyled" style="line-height: 2">
							<li><span class="fa fa-check text-success"></span> Crea
								nuevos eventos</li>
							<li><span class="fa fa-check text-success"></span> Únete a
								eventos interesantes</li>
							<li><span class="fa fa-check text-success"></span> Pásalo
								bien</li>

						</ul>
						<p>
							<a href="/MeetBook/Registro.do" class="btn btn-info btn-block">Registrarse</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
