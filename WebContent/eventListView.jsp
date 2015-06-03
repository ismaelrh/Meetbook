
<%@page import="negocio.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@page import="infraestructura.MenuGenerator"%>
<head>
<meta charset="UTF-8">
<title>Eventos || MeetBook</title>
<meta name="keywords" content="Meetbook, eventos">
<meta name="description" content="Lista de eventos">
<link rel="stylesheet" type="text/css" href="style.css" />
<!-- Bootstrap  -->
<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
<link rel="stylesheet" href="/MeetBook/css/evento.css">

<!-- Javascripts necesarios -->
<script src="/MeetBook/resource/js/bootstrap.js"></script>
<script src="/MeetBook/resource/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/MeetBook/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="/MeetBook/resource/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="/MeetBook/resource/js/search-evento.js"></script>
<!-- <script src="/MeetBook/resource/js/chat.js"></script> -->

</head>
<body background="images/wallpaper.jpg">

	<%
		//Cargamos la sesión, no omitir.
			UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");
		
			int modo_int = (Integer) request.getAttribute("modo");
			String tituloSeccion = (String) request.getAttribute("tituloSeccion");
			List<EventoDTO> eventos = (List<EventoDTO>) request.getAttribute("eventosMostrados");
			List<InvitacionDTO> invitacionesPendientes = (List<InvitacionDTO>) request.getAttribute("invitacionesPendientes");
			String eventSuccessMessage = (String) request.getAttribute("eventSuccessMessage");
			String invitationSuccessMessage = (String) request.getAttribute("invitationSuccessMessage");
	%>
	<script>
		var nick =
	<%=user.getNick()%>
		
	</script>
	<!--MENÚ DE ARRIBA -->
	<%
		out.println(MenuGenerator.generateMenu(user, modo_int));
	%>
	<!-- FIN MENU DE ARRIBA -->
	<!-- CONTENIDO -->
	<div class=contenidoo>


		<div class="tablaIzq">


			<div class="panel panel-primary columna-principal" align="center">
			
			<div class="input-group">
  <span class="input-group-addon" id="basic-addon1">Buscar evento</span>
  <input type="text" placeholder="Introduzca un texto..."
												name="currency" class="form-control" id="autocomplete">
</div>
<br/>


				<div class="panel-heading"><%=tituloSeccion%></div>

				<br />

				<%
					if (eventSuccessMessage != null && eventSuccessMessage != "") {
						out.println("<div class=\"alert alert-success\">");
						out.println(eventSuccessMessage);
						out.println("</div>");
					}
				%>



				<%-- Este estilo para los creados por mi. --%>
				<%-- <div class="rectangleEvent"><strong><% %></strong></div> --%>
				<%
					out.println("<div class=\"row\">");
					out.println("<div class=\"[ col-xs-12 col-sm-offset-0 col-sm-12 ]\">");
					out.println("<ul class=\"event-list\">");

					for (EventoDTO ev : eventos) {
						Calendar c = Calendar.getInstance();
						c.setTime(ev.getFecha());
						out.println("<li>");
						out.println("<time datetime=\"2014-07-20 0000\">");
						out.println("<span class=\"day\">"
								+ c.get(Calendar.DAY_OF_MONTH) + "</span>");
						out.println("<span class=\"month\">"
								+ new SimpleDateFormat("MMM").format(c.getTime())
								+ "</span>");
						out.println("<span class=\"year\">" + c.get(Calendar.YEAR)
								+ "</span>");
						out.println("<span class=\"time\">"
								+ c.get(Calendar.HOUR_OF_DAY) + ":"
								+ c.get(Calendar.MINUTE) + "</span>");
						out.println("</time>");
						out.println("<div class=\"info\">");
						out.println("<h2 class=\"title\">" + ev.getTitulo() + "</h2>");
						out.println("<p class=\"desc\">Creado por: "
								+ ev.getCreador().getNick() + "</p>");
						out.println("<p class=\"desc\">Lugar: " + ev.getLugar()
								+ "</p>");
						out.println("<ul>");
						if (ev.getCreador().getMail().equalsIgnoreCase(user.getMail())) {

							out.println("<form id=\"formBorrar\" style=\"display: none;\"action=\"/MeetBook/EventList.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"borrarEvento\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ ev.getId() + "\"></br>");
							out.println("</form>");

							out.println("<li style=\"width:33%;\"><a onclick=\"document.getElementById('formBorrar').submit(); return false;\"><span class=\"fa fa-times\"></span>Borrar</a></li>");
							out.println("<li style=\"width:33%;\"><a href=#><span class=\"fa fa-pencil\"></span>Editar</a></li>");

						}
						out.println("<li style=\"width:34%;\"><a href=\"/MeetBook/EventDetail.do"
								+ "?id="
								+ ev.getId()
								+ "\"><span class=\"fa fa-plus-square\"></span>Ver Detalles</a></li>");
						out.println("</ul>");
						out.println("</div>");

						out.println("</li>");
					}

					out.println("</ul>");
					out.println("</div>");
				%>

			</div>
			<div></div>
		</div>
	</div>
	<div class=tablaDcha>
		<div class="panel panel-primary columna-lateral" align="center">

			<div class="panel-heading">Invitaciones pendientes</div>
			<br />
			<%
				if (invitationSuccessMessage != null
						&& invitationSuccessMessage != "") {
					out.println("<div class=\"alert alert-success\">");
					out.println(invitationSuccessMessage);
					out.println("</div>");
				}
			%>

			<%
				if (invitacionesPendientes.size() == 0) {
					out.println("No hay invitaciones pendientes<br/><br/>");
				}

				out.println("<div class=\"row\">");
				out.println("<div class=\"[ col-xs-12 col-sm-offset-0 col-sm-12 ]\">");
				out.println("<ul class=\"event-list\">");

				for (InvitacionDTO invit : invitacionesPendientes) {

					EventoDTO eve = invit.getEvento();
					UsuarioDTO creador = eve.getCreador();
					Calendar c = Calendar.getInstance();
					c.setTime(eve.getFecha());
					out.println("<li>");
					out.println("<time datetime=\"2014-07-20 0000\">");
					out.println("<span class=\"day\">"
							+ c.get(Calendar.DAY_OF_MONTH) + "</span>");
					out.println("<span class=\"month\">"
							+ new SimpleDateFormat("MMM").format(c.getTime())
							+ "</span>");
					out.println("<span class=\"year\">" + c.get(Calendar.YEAR)
							+ "</span>");
					out.println("<span class=\"time\">"
							+ c.get(Calendar.HOUR_OF_DAY) + ":"
							+ c.get(Calendar.MINUTE) + "</span>");
					out.println("</time>");
					out.println("<div class=\"info\">");
					out.println("<h2 class=\"titleMini\">" + eve.getTitulo()
							+ "</h2>");
					out.println("<p class=\"desc\">Creado por: "
							+ eve.getCreador().getNick() + "</p>");
					out.println("<p class=\"desc\">Lugar: " + eve.getLugar()
							+ "</p>");
					out.println("<ul>");

					out.println("<form id=\"formAceptar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
					out.println("<input type=\"hidden\" name=\"action\" value=\"aceptarInvitacion\"><br>");
					out.println("<input type=\"hidden\" name=\"id\" value=\""
							+ invit.getIdInvitacion() + "\"></br>");
					out.println("</form>");

					out.println("<form id=\"formRechazar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
					out.println("<input type=\"hidden\" name=\"action\" value=\"rechazarInvitacion\"><br>");
					out.println("<input type=\"hidden\" name=\"id\" value=\""
							+ invit.getIdInvitacion() + "\"></br>");
					out.println("</form>");

					out.println("<li style=\"width:34%;\"><a href=\"#"
							+ "\" onclick=\"document.getElementById('formAceptar').submit(); return false;\"><span class=\"fa fa-check\"></span>Aceptar</a></li>");
					out.println("<li style=\"width:34%;\"><a href=\"#"
							+ "\" onclick=\"document.getElementById('formRechazar').submit(); return false;\"><span class=\"fa fa-times\"></span>Rechazar</a></li>");

					out.println("</ul>");
					out.println("</div>");

					out.println("</li>");
				}

				out.println("</ul>");
				out.println("</div>");
			%>



		</div>
<!-- CHAT 
		<div class="panel-heading">Chat</div>
			<div style="height: 200px; overflow-y: scroll;">
				<table id="response" class="table table-bordered"></table>
			</div>
			<form id="do-chat">
				<input type="text" class="input-block-level col-xs-12 col-sm-12"
					placeholder="Your message..." id="message" style="height: 60px" /> 
				<input
					type="submit" class="btn btn-large btn-block btn-danger"
					value="Send message" />
			</form> -->
	</div>




	</div>