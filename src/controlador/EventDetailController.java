package controlador;

import infraestructura.EventoDTO;
import infraestructura.InvitacionDTO;
import infraestructura.MensajeDTO;
import infraestructura.UsuarioDTO;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.EventoServices;
import negocio.InvitacionServices;
import negocio.MensajeServices;

/**
 * Servlet que controla la visualización de un evento en detalle.
 */
@WebServlet("/EventDetail.do")
public class EventDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public EventDetailController() {
		super();

	}

	/** En caso de GET mostramos si es posible el evento requerido*/
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
		if (usuarioLogueado == null) {
			response.sendRedirect("Login.do");
			return;
		}
		
		
			
			mostrarDetallesEvento(request,response,usuarioLogueado);
			
		
			
		

	}
	
	private void mostrarDetallesEvento(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado) throws ServletException, IOException {
		String idI = request.getParameter("id");
		String invitationSuccessMessage = request.getParameter("invitationSuccessMessage");
		int id = Integer.parseInt(idI);

		try {
			// Obtenemos los datos
			EventoServices e = new EventoServices();
			InvitacionServices i = new InvitacionServices();
			MensajeServices m = new MensajeServices();

			// Obtenemos el evento
			EventoDTO evento = e.obtenerEvento(id);

			// Obtenemos los que han aceptado
			List<UsuarioDTO> asistentes = i.obtenerAsistentes(evento);

			// Obtenemos el estado de invitacion del usuario con la sesión
			// iniciada
			InvitacionDTO  invitacion = i.findInvitacion(usuarioLogueado, evento);
			String estadoInvitacion = "NO INVITADO";
			if(invitacion!=null){
				estadoInvitacion=invitacion.getEstado();
			}
			
			//Obtenemos las invitaciones pendientes futuras
			List<InvitacionDTO> invitacionesPendientes = i
					.obtenerInvitacionesPendientesFuturas(usuarioLogueado);
			
			
			//Obtenemos los 10 ultimos mensajes de chat
			List<MensajeDTO> ultimosMensajes =  m.lastTenMessages(evento.getId());
			
			request.setAttribute("evento", evento);
			request.setAttribute("asistentes", asistentes);
			request.setAttribute("estadoInvitacion", estadoInvitacion);
			request.setAttribute("invitacionUsuario", invitacion);
			request.setAttribute("invitacionesPendientes",invitacionesPendientes);
			request.setAttribute("invitationSuccessMessage",invitationSuccessMessage);
			request.setAttribute("lastMessages",ultimosMensajes);
			request.setAttribute("id", id);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("eventDetailView.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception ex) {
			request.setAttribute("error", "Se ha producido un error al abrir el evento");
			request.getRequestDispatcher("errorView.jsp").forward(request, response);
		}
	}

	/**
	 * En caso de POST no se hace nada.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		UsuarioDTO usuarioLogueado = (UsuarioDTO) request.getSession().getAttribute("usuario");
		if (usuarioLogueado == null) {
			response.sendRedirect("Login.do");
			return;
		}
		
		String accion = request.getParameter("action");
		if(accion==null || accion == ""){
			request.setAttribute("error",
					"Accion desconocida");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);
			
		}
		else if(accion.equalsIgnoreCase("borrarEvento")){
			//Accion de borrar evento especificada
			AccionesComunes.borrarEvento(request,response,usuarioLogueado);
		}
		else if(accion.equalsIgnoreCase("aceptarInvitacion")){
			//Accion de aceptar invitacion especificada
			AccionesComunes.aceptarInvitacion(request,response,usuarioLogueado);
			
			
		}
		else if(accion.equalsIgnoreCase("rechazarInvitacion")){
			//Accion de rechazar invitacion especificada
			AccionesComunes.rechazarInvitacion(request,response,usuarioLogueado);
			
		}
		else{
			//Accion desconocida especificada
			request.setAttribute("error",
					"Accion desconocida");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);
		
	}
	}

}
