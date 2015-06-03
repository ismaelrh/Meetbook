
<%-- JSP que muestra información sobre un error. --%>
<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="infraestructura.MenuGenerator"%>
<html>
<head>
<meta charset="UTF-8">
<title>Error || MeetBook</title>
<meta name="keywords" content="Error, películas, series">
<meta name="description" content="Error">
<link rel="stylesheet" type="text/css" href="style.css" />
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
<!-- Latest compiled JavaScript -->
<script src="/MeetBook/js/bootstrap.min.js"></script>
<script src="/MeetBook/resource/js/bootstrap.js"></script>
<script src="/MeetBook/resource/js/bootstrap.min.js"></script>
<script src="/MeetBook/resource/js/jquery-1.10.2.min.js"></script>
</head>



<body background="images/wallpaper.jpg">

	<!--MENÚ DE ARRIBA Y ATRIBUTOS-->
	<%
		UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");
		List<UsuarioDTO> resultadoBusqueda = (List<UsuarioDTO>) request
				.getAttribute("resultadoBusqueda");
		
		List<UsuarioDTO> resultadoBusquedaNick = (List<UsuarioDTO>) request
				.getAttribute("resultadoBusquedaNick");
		
		out.println(MenuGenerator.generateMenu(user, 0));
	%>
	<div id="login-overlay" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">Buscar por mail:</h4>
			</div>
			<div class="modal-body">
				<!-- BUSCADOR MALO -->
				<form class="form-horizontal" action="/MeetBook/BuscarUsuario.do"
					method="post">
					<div class="row">
						<div class="col-lg-6">
							<div class="input-group">
								<input id="busqueda" name="busqueda" type="text"
									class="form-control" placeholder="Email..."> <span
									class="input-group-btn">
									<button class="btn btn-default" type="submit">Buscar</button>
								</span>
							</div>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
					</div>
					<!-- /.row -->
				</form>
				<!-- LISTA DE USUARIOS -->
				<%
				if (resultadoBusqueda != null) {
						out.println("<ul class=\"list-group\">");
						for (UsuarioDTO user2 : resultadoBusqueda) {
							out.println("<li class=\"list-group-item\">"
									+ user2.getMail() + "</li>");
						}
						out.println("</ul>");
				}
				else{
					out.println("<ul class=\"list-group\">");
						out.println("<li class=\"list-group-item\">"
								+ "No se han encontrado resultados." + "</li>");
					out.println("</ul>");
				}
				%>
			</div>
		</div>
	</div>

	
		<div id="login-overlay" class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<h4 class="modal-title" id="myModalLabel">Buscar por nick:</h4>
			</div>
			<div class="modal-body">
				<!-- BUSCADOR MALO -->
				<form class="form-horizontal" action="/MeetBook/BuscarUsuario.do"
					method="post">
					<div class="row">
						<div class="col-lg-6">
							<div class="input-group">
								<input id="busquedaNick" name="busquedaNick" type="text"
									class="form-control" placeholder="Nick..."> <span
									class="input-group-btn">
									<button class="btn btn-default" type="submit">Buscar</button>
								</span>
							</div>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
					</div>
					<!-- /.row -->
				</form>
				
				<!-- LISTA DE USUARIOS -->
				<%
				if (resultadoBusquedaNick != null) {
						out.println("<ul class=\"list-group\">");
						for (UsuarioDTO user3 : resultadoBusquedaNick) {
							out.println("<li class=\"list-group-item\">"
									+ user3.getNick() + "</li>");
						}
						out.println("</ul>");
				}
				else{
					out.println("<ul class=\"list-group\">");
						out.println("<li class=\"list-group-item\">"
								+ "No se han encontrado resultados." + "</li>");
					out.println("</ul>");
				}
				%>
			</div>
		</div>
	</div>
	
	
</body>

</html>
