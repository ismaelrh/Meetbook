<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="infraestructura.*"%>
<%@page import="infraestructura.MenuGenerator"%>
<html>
<%
	Map<String, String> e = (Map<String, String>) request.getAttribute("errores");

	 	/* Errores */
	 	String tituloError = "";
	 	String descripcionError = "";
	 	String lugarError = "";
	 	String fechaError = "";
	 	String horaError = "";
	 	String invitadosNickError = "";
	 	String invitadosMailError = "";
	 	
	 	/* Valores previamente introducidos */
	 	String tituloValor = "";
	 	String descripcionValor = "";
	 	String lugarValor = "";
	 	String fechaValor = "";
	 	String horaValor = "";
	 	String invitadosNickValor = "";
	 	String invitadosMailValor = "";
	
	 	
	 	if (e != null) {
	 		//Hay errores
	 		String cabeceraError = "<span style=\"color:red\">";
	 		String finalError = "</span>";
	 		if(e.containsKey("titulo")) 
	 			tituloError = cabeceraError + e.get("titulo") + finalError;
	if(e.containsKey("descripcion")) 
		descripcionError = cabeceraError + e.get("descripcion") + finalError;
	if(e.containsKey("lugar")) 
		lugarError = cabeceraError + e.get("lugar") + finalError;
	if(e.containsKey("fecha")) 
		fechaError = cabeceraError + e.get("fecha") + finalError;
	if(e.containsKey("hora")) 
		horaError = cabeceraError + e.get("hora") + finalError;
	if(e.containsKey("invitadosNick")) 
		invitadosNickError = cabeceraError + e.get("invitadosNick") + finalError;
	
	 	}
	 	//Rellenamos valores previos
		tituloValor = (String) request.getAttribute("titulo");
		descripcionValor =  (String) request.getAttribute("descripcion");
		lugarValor =  (String) request.getAttribute("lugar");
		fechaValor = (String) request.getAttribute("fecha");
		horaValor = (String) request.getAttribute("hora");
		invitadosMailValor = (String) request.getAttribute("invitadosMail");
		invitadosNickValor = (String) request.getAttribute("invitadosNick");
		
		if(tituloValor == null) tituloValor = "";
		if(descripcionValor == null) descripcionValor = "";
		if(lugarValor == null) lugarValor = "";
		if(fechaValor == null) fechaValor = "";
		if(horaValor == null) horaValor = "";
		if(invitadosMailValor == null) invitadosMailValor = "";
		if(invitadosNickValor == null) invitadosNickValor = "";
%>
<head>
<meta charset="UTF-8">
<title>Crear evento || MeetBook</title>
<meta name="keywords" content="Crear, evento">
<meta name="description" content="Creación de evento">
<link rel="stylesheet" type="text/css" href="style.css" />
<!-- Bootstrap -->
<link rel="stylesheet" href="/MeetBook/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="style.css" />
<link rel="stylesheet" href="/MeetBook/css/evento.css">
<script src="/MeetBook/resource/js/bootstrap.js"></script>
<script src="/MeetBook/resource/js/bootstrap.min.js"></script>
<script src="/MeetBook/resource/js/jquery-1.10.2.min.js"></script>
<script src="/MeetBook/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="/MeetBook/resource/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript"
	src="/MeetBook/resource/js/jquery.autocomplete.min.js"></script>
<script type="text/javascript" src="/MeetBook/resource/js/search-usuario.js"></script>
</head>
<body background="images/wallpaper.jpg">

	<%
		//Cargamos la sesión, no omitir.
		UsuarioDTO usuario = (UsuarioDTO) session.getAttribute("usuario");
	%>
	<!--MENÚ DE ARRIBA -->
	<%
		out.println(MenuGenerator.generateMenu(usuario,0));
	%>
	<!-- FIN MENU DE ARRIBA -->
	<!-- CONTENIDO -->



	<div>
		<div>

			<div class="row">
				<div class="col-md-6 col-md-offset-3">
					<div class="well well-sm">
						<form class="form-horizontal" action="/MeetBook/CrearEvento.do"
							method="post">
							<fieldset>
								<legend class="text-center">Crear un nuevo evento</legend>

								<!-- Name input-->
								<div class="form-group">
									<label class="col-md-3 control-label" for="titulo">Título*</label>
									<div class="col-md-9">
										<input id="titulo" name="titulo" type="text"
											value="<%=tituloValor%>" placeholder="Título del evento"
											class="form-control">
									</div>
									<div class="col-md-9">
										<span class="errorText" id="tituloError"><%=tituloError%></span>
										<br /> <br />
									</div>
								</div>


								<!-- Email input-->
								<div class="form-group">
									<label class="col-md-3 control-label" for="lugar">Lugar*</label>
									<div class="col-md-9">
										<input id="lugar" name="lugar" type="text"
											value="<%=lugarValor%>" placeholder="Playa, piscina,..."
											class="form-control">
									</div>
									<div class="col-md-9">
										<span class="errorText" id="lugarError"><%=lugarError%></span>
										<br /> <br />
									</div>
								</div>

								<!-- Fecha input-->
								<div class="form-group">
									<label class="col-md-3 control-label" for="fecha">Fecha*</label>
									<div class="col-md-9">
										<input id="fecha" name="fecha" type="date"
											value="<%=fechaValor%>" class="form-control">
									</div>
									<div class="col-md-9">
										<span class="errorText" id="fechaError"><%=fechaError%></span>
										<br /> <br />
									</div>
								</div>

								<!-- Fecha input-->
								<div class="form-group">
									<label class="col-md-3 control-label" for="hora">Hora*</label>
									<div class="col-md-9">
										<input id="hora" name="hora" type="time"
											value="<%=horaValor%>" class="form-control">
									</div>
									<div class="col-md-9">
										<span class="errorText" id="horaError"><%=horaError%></span> <br />
										<br />
									</div>
								</div>


								<!-- Message body -->
								<div class="form-group">
									<label class="col-md-3 control-label" for="descripcion">Descripción*</label>
									<div class="col-md-9">
										<textarea class="form-control" id="descripcion"
											value="<%=descripcionValor%>" name="descripcion"
											placeholder="Explícate aquí" rows="5"></textarea>
									</div>
									<div class="col-md-9">
										<span class="errorText" id="descripcionError"><%=descripcionError%></span>
										<br /> <br />
									</div>
								</div>

								<!-- Invitados -->
								<div class="form-group">
									<label class="col-md-3 control-label" for="descripcion">Invitados*</label>
									<div class="col-md-9">
										<div class="input-group">
											<input type="text" placeholder="Buscar por mail o nick..."
												name="currency" class="form-control" id="autocomplete"
												onkeydown="searchChanged()" onchange="searchChanged()"
												onpaste="searchChanged()" oninput="searchChanged()">
											<span class="input-group-btn">
												<button class="btn btn-danger" type="button"
													id="botonBuscar" onclick='seleccionarUsuario();'>
													<i class="glyphicon glyphicon-plus"></i> Invitar
												</button> <input type="hidden" id="nickInvitado"> <input
												type="hidden" id="nick_mail_invitado">
										</div>
										<!-- /input-group -->


									</div>

								</div>

								<div>
									<label class="col-md-3 control-label" for="invitadosNick">
									</label>
									<div class="col-md-9">
										<ul class="list-group" id="listaInvitados">


										</ul>
									</div>


								</div>
								<!-- @end #searchfield -->



								<div class="form-group">
									<label class="col-md-3 control-label" for="invitadosNick">
									</label>
									<div class="col-md-9">
										<textarea class="form-control" id="invitadosNick"
											name="invitadosNick" placeholder="Uno por línea" rows="5"
											readonly style="display: none;""><%=invitadosNickValor%></textarea>
									</div>
									<div class="col-md-9">
										<span class="errorText" id="invitadosError"><%=invitadosNickError%></span>
										<br /> <br />
									</div>
								</div>


								<!-- Form actions -->
								<div class="form-group">
									<div class="col-md-12 text-right">
										<button type="submit" class="btn btn-primary btn-lg">Crear</button>
										<input type="reset" class="btn btn-primary btn-lg"
											value="Reestablecer" onClick="clearErrors()">
									</div>
								</div>
							</fieldset>
						</form>
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
			document.getElementById('tituloError').innerHTML = "";
			document.getElementById('descripcionError').innerHTML = "";
			document.getElementById('lugarError').innerHTML = "";
			document.getElementById('fechaError').innerHTML = "";
			document.getElementById('horaError').innerHTML = "";
			document.getElementById('invitadosError').innerHTML = "";
			document.getElementById('idError').innerHTML = "";
		}
	</script>
</body>
</html>
