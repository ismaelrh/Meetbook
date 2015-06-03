package controlador;

import infraestructura.Cons;
import infraestructura.EventoDTO;
import infraestructura.InvitacionDAO;
import infraestructura.InvitacionDTO;
import infraestructura.UsuarioDTO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import negocio.EventoServices;
import negocio.InvitacionServices;

/**
 * Esta clase reune acciones comunes a varios controladores, como puede ser
 * borrar un evento, aceptar o rechazar una invitación. Esta clase NO es un
 * controlador.
 */
public class AccionesComunes {

	/**
	 * Procesa una petición HTTP de borrado de evento.
	 * @param request
	 * @param response
	 * @param usuarioLogueado
	 * @throws ServletException
	 * @throws IOException
	 */
	protected static void borrarEvento(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado)
			throws ServletException, IOException {

		String idS = request.getParameter("id");
		int id = Integer.parseInt(idS);

		EventoServices eventoServices = new EventoServices();
		int exito = 0;

		try {
			EventoDTO event = eventoServices.obtenerEvento(id);

			HttpSession session = request.getSession();

			// Intentamos borrar el evento con el usuario logueado
			exito = eventoServices.borrarEvento(event, usuarioLogueado);

			if (exito != Cons.SUCCESS) {
				// Error de permisos
				request.setAttribute("error",
						"Error de permisos. No puedes borrar este evento.");
				request.getRequestDispatcher("errorView.jsp").forward(request,
						response);

			} else {
				// Se redirige a pagina de vista de eventos
				response.sendRedirect("EventList.do?eventSuccessMessage=El evento se ha borrado correctamente");
			}

		}

		catch (Exception e) {
			// Excepción al borrar
			request.setAttribute("error",
					"Se ha producido una excepción al borrar el evento.");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);

		}
	}

	/**
	 * Procesa una petición de aceptar invitación.
	 * @param request
	 * @param response
	 * @param usuarioLogueado
	 * @throws ServletException
	 * @throws IOException
	 */
	protected static void aceptarInvitacion(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado)
			throws ServletException, IOException {
		// Recibe el ID de la invitación
		String idI = request.getParameter("id");
		int id = Integer.parseInt(idI);

		InvitacionServices invitacionServices = new InvitacionServices();
		InvitacionDAO invitacionDAO = new InvitacionDAO();
		int exito = 0;

		try {
			InvitacionDTO invitacion = invitacionDAO.find(id);
			// Se acepta la invitación con el usuario logueado
			exito = invitacionServices.aceptarInvitacion(invitacion,
					usuarioLogueado);

		} catch (Exception e) {
			// Excepción al aceptar
			request.setAttribute("error",
					"Se ha producido una excepción al aceptar la invitación");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);

		}

		if (exito != Cons.SUCCESS) {
			// Error de permisos
			request.setAttribute("error",
					"Error de permisos. No puedes aceptar esta invitacion.");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);

		} else {

			// Vamos a página que nos ha traído aquí
			String referer = request.getHeader("referer");
			if (referer.contains("?")) {
				response.sendRedirect(request.getHeader("referer")
						+ "&invitationSuccessMessage=La invitación se ha aceptado correctamente");
			} else {
				response.sendRedirect(request.getHeader("referer")
						+ "?invitationSuccessMessage=La invitación se ha aceptado correctamente");
			}
		}

	}

	/**
	 * Procesa una petición de rechazar invitación.
	 * @param request
	 * @param response
	 * @param usuarioLogueado
	 * @throws ServletException
	 * @throws IOException
	 */
	protected static void rechazarInvitacion(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado)
			throws ServletException, IOException {
		String idI = request.getParameter("id");
		int id = Integer.parseInt(idI);

		InvitacionServices invitacionServices = new InvitacionServices();
		InvitacionDAO invitacionDAO = new InvitacionDAO();

		int exito = 0;

		try {

			InvitacionDTO invitacion = invitacionDAO.find(id);

			// Rechazamos la invitacion con el usuario logueado
			exito = invitacionServices.rechazarInvitacion(invitacion,
					usuarioLogueado);

		} catch (Exception e) {
			// Excepción al rechazar
			request.setAttribute("error",
					"Se ha producido un error al declinar la invitación.");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);

		}

		if (exito != Cons.SUCCESS) {
			// Error de permisos
			request.setAttribute("error",
					"Error de permisos. No puedes declinar esta invitacíon..");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);

		} else {

			// Vamos a página que nos ha traído aquí
			String referer = request.getHeader("referer");
			if (referer.contains("?")) {
				response.sendRedirect(request.getHeader("referer")
						+ "&invitationSuccessMessage=La invitación se ha rechazado correctamente");
			} else {
				response.sendRedirect(request.getHeader("referer")
						+ "?invitationSuccessMessage=La invitación se ha rechazado correctamente");
			}
		}

	}
}
