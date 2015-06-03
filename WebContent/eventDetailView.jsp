<%@page import="infraestructura.MenuGenerator"%>
<%@page import="negocio.*"%>
<%@ page import="infraestructura.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="java.sql.Timestamp" %>
<html>

<%
	//Cargamos la sesión, no omitir.	
	UsuarioDTO user = (UsuarioDTO) session.getAttribute("usuario");

	EventoDTO evento = (EventoDTO) request.getAttribute("evento");

	List<UsuarioDTO> asistentes = (List<UsuarioDTO>) request
	.getAttribute("asistentes");

	List<InvitacionDTO> invitacionesPendientes = (List<InvitacionDTO>) request
	.getAttribute("invitacionesPendientes");

	
	List<MensajeDTO> ultimosMensajes = (List<MensajeDTO>) request.getAttribute("lastMessages");
	String estadoInvitacion = (String) request
	.getAttribute("estadoInvitacion");

	InvitacionDTO invitacion = null;
	if (!estadoInvitacion.equalsIgnoreCase("NO INVITADO")) {
		invitacion = (InvitacionDTO) request
		.getAttribute("invitacionUsuario");
	}

	String invitationSuccessMessage = (String) request
	.getAttribute("invitationSuccessMessage");
%>

<script>
	var nick =
<%=user.getNick()%>
	;
</script>

<head>
<meta charset="UTF-8">
<title>Evento || MeetBook</title>
<meta name="keywords" content="Meetbook, evento">
<meta name="description" content="Detalles de un evento">
<link rel="stylesheet" type="text/css" href="style.css" />
<!-- Bootstrap -->
<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
<script src="/MeetBook/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="/MeetBook/css/evento.css">

<script src="/MeetBook/resource/js/bootstrap.js"></script>
<script src="/MeetBook/resource/js/jquery-1.10.2.min.js"></script>
<script src="/MeetBook/resource/js/chat.js"></script>

</head>

<body background="images/wallpaper.jpg">

	<!--MENÚ DE ARRIBA -->
	<%
		out.println(MenuGenerator.generateMenu(user, 0));
	%>
	<!-- FIN MENU DE ARRIBA -->

	<!-- CONTENIDO -->
	<div class=contenidoo>
		<%
			if (invitationSuccessMessage != null
					&& invitationSuccessMessage != "") {
				out.println("<div class=\"alert alert-success\">");
				out.println(invitationSuccessMessage);
				out.println("</div>");
			}
		%>

		<div class=tablaIzq>

			<div class="panel panel-primary columna-principal" align="center">

				<div class="panel-heading"><%=evento.getTitulo()%>
					<%
						if (evento.getCreador().getMail().equalsIgnoreCase(user.getMail())) {
							//Es el creador

							out.print("<a class=\"btn btn-warning btn-sm\" style=\"float: right;\""
									+ "href=#> <font face=\"verdana\"" +

									"color=\"white\"> <span class=\"fa fa-pencil\"></span>Editar</font></a>");
							out.println("<form id=\"formBorrar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"borrarEvento\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ evento.getId() + "\"></br>");
							out.println("</form>");
							out.print("<a class=\"btn btn-danger btn-sm\" onclick=\"document.getElementById('formBorrar').submit(); return false;\" style=\"float: right; margin-right:2px;\""
									+ "href=\"#\"> <font face=\"verdana\""
									+ "color=\"white\"><span class=\"fa fa-times\"></span>Borrar</font></a>");

						}
					%>
				</div>


				<br /> <br />


				<div class=cuerpotab3>

					<div class="panel panel-info text-left ">
						<div class="panel-heading">Datos del evento</div>
						<ul>
							<br />
							<li><span style="font-size: 14px;"><strong>CREADOR:
								</strong><%=evento.getCreador().getNick()%></span></li>
							<li><span style="font-size: 14px;"><strong>LUGAR:
								</strong><%=evento.getLugar()%></span></li>
							<li><span style="font-size: 14px;"><strong>FECHA:
								</strong><%=evento.getFecha()%></span></li>
							<li><span style="font-size: 14px;"><strong>ESTADO
										DE MI ASISTENCIA: </strong><%=estadoInvitacion%></span></li>
							<br>
						</ul>
					</div>
					<div class="panel panel-info text-left ">
						<div class="panel-heading">Descripción</div>
						<br /> <span style="font-size: 14px;"><%=evento.getDescripcion()%></span>
						<br />
					</div>
					<div class="panel panel-info text-left ">

						<div class="panel-heading">Asistentes</div>
						<br />
						<%
							if (asistentes.size() > 0) {
								out.println("<ul>");
								for (UsuarioDTO asistente : asistentes) {
									out.println("<li>" + asistente.getNick() + " - "
											+ asistente.getMail() + "</li>");
								}
								out.println("</ul>");

							} else {
								out.println("No hay asistentes por el momento.");
							}
						%>
						<br />
					</div>
					<%
						//Mostramos opciones de aceptar invitación si la tiene pendiente
						if (estadoInvitacion.equalsIgnoreCase("PENDIENTE")) {
							out.println("<form id=\"formRechazar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"rechazarInvitacion\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ invitacion.getIdInvitacion() + "\"></br>");
							out.println("</form>");

							out.println("<a class=\"btn btn-danger\" style=\"float: right; margin-left: 4px;\""
									+ "href=\"#\"  onclick=\"document.getElementById('formRechazar').submit(); return false;\"><font face=\"verdana\""
									+ "color=\"white\">RECHAZAR ASISTENCIA</font></a>");

							out.println("<form id=\"formAceptar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"aceptarInvitacion\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ invitacion.getIdInvitacion() + "\"></br>");
							out.println("</form>");

							out.println("<a class=\"btn btn-success\" style=\"float: right; margin-left: 4px;\""
									+ "href=\"#\"  onclick=\"document.getElementById('formAceptar').submit(); return false;\" ><font face=\"verdana\""
									+ "color=\"white\">CONFIRMAR ASISTENCIA</font></a>");

						} else if (estadoInvitacion.equalsIgnoreCase("ACEPTADA")) {
							out.println("<form id=\"formRechazar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"rechazarInvitacion\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ invitacion.getIdInvitacion() + "\"></br>");
							out.println("</form>");

							out.println("<a class=\"btn btn-danger\" style=\"float: right; margin-left: 4px;\""
									+ "href=\"#\"  onclick=\"document.getElementById('formRechazar').submit(); return false;\" ><font face=\"verdana\""
									+ "color=\"white\">RECHAZAR ASISTENCIA</font></a>");
						} else if (estadoInvitacion.equalsIgnoreCase("RECHAZADA")) {
							out.println("<form id=\"formAceptar\" style=\"display: none;\"action=\"/MeetBook/EventDetail.do\" method=\"post\">");
							out.println("<input type=\"hidden\" name=\"action\" value=\"aceptarInvitacion\"><br>");
							out.println("<input type=\"hidden\" name=\"id\" value=\""
									+ invitacion.getIdInvitacion() + "\"></br>");
							out.println("</form>");

							out.println("<a class=\"btn btn-success\" style=\"float: right; margin-left: 4px;\""
									+ "href=\"#\"  onclick=\"document.getElementById('formAceptar').submit(); return false;\" ><font face=\"verdana\""
									+ "color=\"white\">CONFIRMAR ASISTENCIA</font></a>");

						}
					%>

				</div>
				<!-- CHAT -->
				<div class="panel panel-default" style="display:none;">
					<div class="panel-heading">Chat</div>
					<form id="do-chat">
					

						<!--  <input type="text" class="input-block-level col-xs-12 col-sm-12"
					placeholder="Your message..." id="message" style="height: 60px" /> 
				<input
					type="submit" class="btn btn-large btn-block btn-danger"
					value="Send message" />	-->

						<input type="hidden" id="evId" name="id"
							value="<%=evento.getId()%>" />

					</form>

				</div>
			</div>

		</div>

		<div class=tablaDcha>
			<div class="panel panel-primary columna-lateral" align="center">

				<div class="panel-heading">Invitaciones pendientes</div>
				<br /> <br />


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

<%

if (!estadoInvitacion.equalsIgnoreCase("NO INVITADO")) {
	
	out.println("<div class=\"container\"");
}
else{
	out.println("<div class=\"container\" style=\" display: none;\"");
}
%>
			
				<div class="row">
					<div class="col-md-5">
						<div class="panel panel-primary">
							<div class="panel-heading" id="accordion">
								<span class="glyphicon glyphicon-comment"></span> Chat
								<div class="btn-group pull-right">
									<a type="button" class="btn btn-default btn-xs"
										data-toggle="collapse" data-parent="#accordion"
										href="#collapseOne"> <span
										class="glyphicon glyphicon-chevron-down"></span>
									</a>
								</div>
							</div>
							<div class="panel-collapse" id="collapseOne">
								<div class="panel-body">
									<ul class="chat" id="response">
										<%
											for (MensajeDTO m : ultimosMensajes) {
												String remitente = m.getRemitente();
												String contenido = m.getContenido();
												Timestamp fecha = m.getFecha();
												

												String dia = "" +(fecha.getYear() +1900) + "-" + String.format("%02d",(fecha.getMonth() 
														+1)) + "-" +  String.format("%02d",fecha.getDate());
												String hora =  String.format("%02d",fecha.getHours()) + ":" +  String.format("%02d",fecha.getMinutes());
												out.println("<li class=\"left clearfix\"><span class=\"chat-img pull-left\">");
												out.println("</span>");
												out.println("<div class=\"chat-body clearfix\">");
												out.println("<div class=\"header\">");
												out.println("<small class=\"pull-right text-muted\">");
												out.println("<span class=\"glyphicon glyphicon-time\"></span>" + 
												dia + " a las "+ hora
														+ "</small>");
												out.println("</div>");
												out.println("<strong class=\"primary-font pull-left\">"
														+ remitente + ":<br/>  </strong>");
												out.println("<div><p class=\"pull-left\">");
												out.println("<br/>" + contenido + "</p></div>");
												out.println("</div></li>");
												
												

											}
										%>




									</ul>
								</div>
								<div class="panel-footer">
									<form id="do-chat">
										<div class="input-group">
											<input type="text" class="form-control input-sm" id="message"
												placeholder="Type your message here..." /> <span
												class="input-group-btn">
											<input type="hidden" id="evId" name="id"
												value="<%=evento.getId()%>" />
												<button class="btn btn-warning btn-sm" id="btn-chat">
													Send</button>
											</span>
										</div>
									</form>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


		</div>
</body>
</html>