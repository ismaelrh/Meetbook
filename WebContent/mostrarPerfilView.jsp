<%@page import="negocio.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="infraestructura.MenuGenerator"%>
<html>

<%
	//Cargamos la sesión, no omitir.
	UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");
	String email = (String) request.getAttribute("mailUser");
	String name = (String) request.getAttribute("nameUser");
	String apellidos = (String) request.getAttribute("apellidosUser");
	String nick = (String) request.getAttribute("nickUser");
	String numEventosCreadosSTRING = (String) request.getAttribute("numCreados");
	String numEventosAceptadosSTRING = (String) request.getAttribute("numAsistira");
	String numEventosInvitacionesPendientesSTRING = (String) request
			.getAttribute("numInvPendientes");
	String numTotalAsistidosSTRING = (String) request
			.getAttribute("numTotalAsistidos");
	
	//int numEventosCreados = Integer.parseInt(numEventosCreadosSTRING);
	//int numEventosAceptados = Integer.parseInt(numEventosAceptadosSTRING);
	//int numEventosInvitacionesPendientes = Integer.parseInt(numEventosInvitacionesPendientesSTRING);
	//int numTotalAsistidos = Integer.parseInt(numTotalAsistidosSTRING);
%>

<head>
<meta charset="UTF-8">
<title>Perfil || MeetBook</title>
<meta name="keywords" content="Meetbook, eventos">
<meta name="description" content="Informacion de usuario">
<link rel="stylesheet" type="text/css" href="style.css" />
<!-- Bootstrap -->
<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
<script src="/MeetBook/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/MeetBook/css/evento.css">

<script src="/MeetBook/resource/js/bootstrap.js"></script>
<script src="/MeetBook/resource/js/jquery-1.10.2.min.js"></script>
</head>

<body background="images/wallpaper.jpg">
	<!--MENÚ DE ARRIBA -->
	<%
		out.println(MenuGenerator.generateMenu(user, 0));
	%>
	<!-- FIN MENU DE ARRIBA -->

	<!-- CONTENIDO -->
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="well well-sm">
					<form class="form-horizontal" action="/MeetBook/Perfil.do"
						method="post">
						<fieldset>
							<legend class="text-center">Información del usuario</legend>
							<div class="panel panel-info text-left ">
								<div class="panel-heading">Datos personales</div>
								<ul>
									<br>
									<li><span style="font-size: 14px;"><strong>NOMBRE:
										</strong><%=name%></span></li>
									<li><span style="font-size: 14px;"><strong>APELLIDOS:
										</strong><%=apellidos%></span></li>
									<li><span style="font-size: 14px;"><strong>EMAIL:
										</strong><%=email%></span></li>
									<li><span style="font-size: 14px;"><strong>NICK:
										</strong><%=nick%></span></li>
									<br>
								</ul>
							</div>
							<div class="panel panel-info text-left ">
								<div class="panel-heading">Datos respectivos a eventos</div>
								<ul>
									<br>
									<li><span style="font-size: 14px;"><strong>Nº
												EVENTOS CREADOS: </strong><%=numEventosCreadosSTRING%></span></li>
									<li><span style="font-size: 14px;"><strong>Nº
												TOTAL ASISTIDOS: </strong><%=numTotalAsistidosSTRING%></span></li>
									<li><span style="font-size: 14px;"><strong>Nº
												EVENTOS ACEPTADOS: </strong><%=numEventosAceptadosSTRING%></span></li>
									<li><span style="font-size: 14px;"><strong>Nº
												INVITACIONES PENDIENTES: </strong><%=numEventosInvitacionesPendientesSTRING%></span></li>
									<br>
								</ul>
							</div>

							
							
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>