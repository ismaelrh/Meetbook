package controlador;

import infraestructura.*;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.EventoServices;
import negocio.InvitacionServices;

/**
 * Servlet que implementa la creacion de un evento. Controlador de la vista de
 * creacion de Evento.
 */
@WebServlet("/CrearEvento.do")
public class CrearEventoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/** En caso de GET servimos la página de crear evento (si está registrado) */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UsuarioDTO user = (UsuarioDTO) request.getSession().getAttribute(
				"usuario");
		if (user == null) {
			response.sendRedirect("Login.do");
			return;
		}
		
		RequestDispatcher rd = request
				.getRequestDispatcher("crearEventoView.jsp");
		rd.forward(request, response);
	}

	/**
	 * En caso de POST procesamos los datos de creacion de evento y mostramos
	 * errores si procede.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(
				"usuario");
		
		if (usuario == null) {
			response.sendRedirect("Login.do");
			return;
		}
		
		//Comienza el procesamiento de errores.
		Map<String, String> errores = new HashMap<String, String>();

		String titulo = request.getParameter("titulo"); // Titulo del evento

		String descripcion = request.getParameter("descripcion"); // Descripcion

		String lugar = request.getParameter("lugar"); // Lugar del evento

		String fecha = request.getParameter("fecha"); // Fecha del evento

		String hora = request.getParameter("hora"); // Hora del evento

		String invitadosNick = request.getParameter("invitadosNick");
		

		if ((titulo == null) || (titulo.equals(""))) {
			errores.put("titulo", "Campo obligatorio");
		}
		if ((descripcion == null) || (descripcion.equals(""))) {
			errores.put("descripcion", "Campo obligatorio");
		}
		if ((lugar == null) || (lugar.equals(""))) {
			errores.put("lugar", "Campo obligatorio");
		}
		if ((fecha == null) || (fecha.equals(""))) {
			errores.put("fecha", "Campo obligatorio");
		}
		if ((hora == null) || (hora.equals(""))) {
			errores.put("hora", "Campo obligatorio");
		}

		if (errores.isEmpty()) {
			// No hay errores
			EventoServices eventoServices = new EventoServices();
			EventoDTO eventoDTO = new EventoDTO();
			eventoDTO.setTitulo(titulo);
			eventoDTO.setDescripcion(descripcion);
			eventoDTO.setLugar(lugar);

			DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm",
					Locale.ENGLISH);
			Date date;
			try {
				date = format.parse(fecha + " " + hora);
			} catch (Exception ex) {
				ex.printStackTrace();
				date = Calendar.getInstance().getTime();
			}

			eventoDTO.setFecha(date);
			eventoDTO.setCreador(usuario);
			eventoDTO.setId(0);

			String[] invitadosNickArray = invitadosNick.split("\\r?\\n");
			
			InvitacionServices invitacionServices = new InvitacionServices();
			// Añadimos el evento
			int exito_ev = eventoServices.addEvento(eventoDTO);
			int exito_inv = Cons.SUCCESS;
			
			UsuarioDAO usuarioDAO = new UsuarioDAO();	
			//Añadimos invitados por nick
			for (String nick : invitadosNickArray) {
				UsuarioDTO u = usuarioDAO.findByNick(nick);
				if (u != null) {
					int code = invitacionServices.invitarUsuario(eventoDTO, u, usuario);
					if(code == Cons.ERR_PERMISSION_DENIED || code == Cons.ERR_DATABASE_PROBLEM){
						exito_inv = code;
					}
				}
			}
			
			

			if (exito_ev == Cons.SUCCESS && exito_inv == Cons.SUCCESS) {
				// Se ha tenido éxito
				response.sendRedirect("EventList.do?eventSuccessMessage=El evento se ha creado correctamente");

			} else {
				// No se ha tenido éxito
				request.setAttribute("error", "Se ha producido un error al añadir el evento.");
				request.getRequestDispatcher("errorView.jsp").forward(request, response);
			}

		} else {
			/* Ha habido errores en la introduccion de parametros, los mostramos */
			request.setAttribute("errores", errores);
			request.setAttribute("titulo", titulo);
			request.setAttribute("descripcion", descripcion);
			request.setAttribute("lugar", lugar);
			request.setAttribute("fecha", fecha);
			request.setAttribute("hora", hora);
			request.setAttribute("invitadosNick", invitadosNick);

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("crearEventoView.jsp");
			dispatcher.forward(request, response);

		}
	}

}