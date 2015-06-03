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
import negocio.UsuarioServices;

/**
 * Servlet que implementa la creacion de un evento. Controlador de la vista de
 * creacion de Evento.
 */
@WebServlet("/BuscarUsuario.do")
public class BuscarUsuarioController extends HttpServlet {
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
				.getRequestDispatcher("buscarUsuarioView.jsp");
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
		
		String busqueda = request.getParameter("busqueda");
		String busquedaNick = request.getParameter("busquedaNick");
		
		UsuarioServices eventoServices = new UsuarioServices();
		List<UsuarioDTO> lista = null;

		if ((busqueda != null) && (!busqueda.equals(""))) {
			// No hay errores
			lista = eventoServices.searchUsuarioByMail(busqueda);
			
				// No se ha tenido éxito
				request.setAttribute("resultadoBusqueda", lista);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("buscarUsuarioView.jsp");
				dispatcher.forward(request, response);

		} 
		else if ((busquedaNick != null) && (!busquedaNick.equals(""))){
			// No hay errores
				lista = eventoServices.searchUsuarioByNick(busquedaNick);
			
				// No se ha tenido éxito
				request.setAttribute("resultadoBusquedaNick", lista);
				RequestDispatcher dispatcher = request
						.getRequestDispatcher("buscarUsuarioView.jsp");
				dispatcher.forward(request, response);
		}
		else {
			/* Ha habido errores en la introduccion de parametros, los mostramos */

			RequestDispatcher dispatcher = request
					.getRequestDispatcher("buscarUsuarioView.jsp");
			dispatcher.forward(request, response);

		}
	}
}