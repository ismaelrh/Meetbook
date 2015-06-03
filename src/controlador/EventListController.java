package controlador;

import infraestructura.Cons;
import infraestructura.EventoDTO;
import infraestructura.InvitacionDTO;
import infraestructura.UsuarioDTO;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import negocio.EventoServices;
import negocio.InvitacionServices;

/**
 * Servlet que controla la lista de eventos principal (o muro)
 */
@WebServlet("/EventList.do")
public class EventListController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public EventListController() {
		super();

	}

	/**
	 * En caso de GET servimos la lista de eventos (preparamos datos y se los
	 * pasamos a la vista correspondiente).
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	
			HttpSession session = request.getSession();
			UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("usuario");
			if (usuarioLogueado == null) {
				// Redirección en caso de no logueado
				response.sendRedirect("loginView.jsp");
				return;
			}
			
			listarEventos(request,response,usuarioLogueado);
				

			
	}

	private void listarEventos(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado)throws ServletException, IOException{
		try {
			String tituloSeccion;
			String modo = request.getParameter("modo");
			String eventSuccessMessage = request
					.getParameter("eventSuccessMessage");
			String invitationSuccessMessage = request
					.getParameter("invitationSuccessMessage");

			int modo_int;
			if (modo != null && modo != "") {
				modo_int = Integer.parseInt(modo);
			} else {
				modo_int = Cons.MODE_VIEW_ALL_EVENTS;
			}

			EventoServices el = new EventoServices();
			InvitacionServices il = new InvitacionServices();
			List<EventoDTO> eventos;
			// Se obtienen las invitaciones pendientes del usuario
			List<InvitacionDTO> invitacionesPendientes = il
					.obtenerInvitacionesPendientesFuturas(usuarioLogueado);

			switch (modo_int) {
			// Se establece el titulo de la seccion y los eventos a mostrar
			case Cons.MODE_VIEW_ALL_EVENTS:
				tituloSeccion = "TODOS LOS EVENTOS";
				eventos = el.obtenerTodosLosEventos();
				break;
			case Cons.MODE_VIEW_OWN_EVENTS:
				tituloSeccion = "EVENTOS CREADOS POR MI";
				eventos = el.obtenerEventosCreador(usuarioLogueado);
				break;
			case Cons.MODE_VIEW_FUTURE_EVENTS:
				tituloSeccion = "EVENTOS A LOS QUE VOY A ASISTIR";
				eventos = il.obtenerEventosAsistidosFuturos(usuarioLogueado);
				break;
			case Cons.MODE_VIEW_PAST_EVENTS:
				tituloSeccion = "EVENTOS YA ASISTIDOS";
				eventos = il.obtenerEventosAsistidosPasados(usuarioLogueado);
				break;
			case Cons.MODE_VIEW_DECLINED_EVENTS:
				tituloSeccion = "EVENTOS FUTUROS RECHAZADOS";
				eventos = il.obtenerEventosRechazadosFuturos(usuarioLogueado);
				break;
			default:
				tituloSeccion = "TODOS LOS EVENTOS";
				eventos = el.obtenerTodosLosEventos();
				break;

			}

			// Preparamos parámetros para la vista
			request.setAttribute("invitacionesPendientes",
					invitacionesPendientes);
			request.setAttribute("eventosMostrados", eventos);
			request.setAttribute("tituloSeccion", tituloSeccion);
			request.setAttribute("modo", modo_int);
			request.setAttribute("eventSuccessMessage", eventSuccessMessage);
			request.setAttribute("invitationSuccessMessage",
					invitationSuccessMessage);

			// Llamamos a la vista
			RequestDispatcher rd = request
					.getRequestDispatcher("eventListView.jsp");
			rd.forward(request, response);
		} catch (Exception ex) {
			request.setAttribute("error",
					"Se ha producido un error al cargar el muro.");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);
		}
	}

	
	
		
		
	
	/**
	 * En caso de POST no se hace nada.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session = request.getSession();
		UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("usuario");
		if (usuarioLogueado == null) {
			// Redirección en caso de no logueado
			response.sendRedirect("loginView.jsp");
			return;
		}
		
		String accion = request.getParameter("action");
		if(accion==null || accion == ""){
			//Accion desconocida especificada
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
