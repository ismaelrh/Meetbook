package controlador;

import infraestructura.UsuarioDTO;

import java.io.IOException;

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
@WebServlet("/Perfil.do")
public class PerfilController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PerfilController() {
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
				response.sendRedirect("Login.do");
				return;
			}		
			mostrarDatosPerfil(request,response,usuarioLogueado);					
	}

	private void mostrarDatosPerfil(HttpServletRequest request,
			HttpServletResponse response, UsuarioDTO usuarioLogueado)throws ServletException, IOException{
		try {
			String nombre = usuarioLogueado.getNombre();
			String apellidos = usuarioLogueado.getApellidos();
			String nick = usuarioLogueado.getNick();
			String mail = usuarioLogueado.getMail();
			
			InvitacionServices iServices = new InvitacionServices();
			EventoServices eServices = new EventoServices();
			
			// Numero de eventos totales creados por el usuario
			int numEventosCreados = eServices.obtenerEventosCreador(usuarioLogueado).size();
			// Numero de eventos a los que el usuario ha asistido y ya han pasado
			int numAsistidosTotales = iServices.obtenerEventosAsistidosPasados(usuarioLogueado).size();
			// Numero de eventos a los que el usuario va a asistir
			int eventosAsistira= iServices.obtenerEventosAsistidosFuturos(usuarioLogueado).size();
			//Numero de invitaciones pendientes del usuario
			int invitacionesPendientes = iServices.obtenerInvitacionesPendientesFuturas(usuarioLogueado).size();
			
			request.setAttribute("nameUser", nombre);
			request.setAttribute("mailUser", mail);
			request.setAttribute("nickUser", nick);
			request.setAttribute("apellidosUser", apellidos);
			request.setAttribute("numCreados", Integer.toString(numEventosCreados));
			request.setAttribute("numTotalAsistidos", Integer.toString(numAsistidosTotales));
			request.setAttribute("numAsistira", Integer.toString(eventosAsistira));
			request.setAttribute("numInvPendientes", Integer.toString(invitacionesPendientes));
			
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("mostrarPerfilView.jsp");
			dispatcher.forward(request, response);
			
		} catch (Exception ex) {
			request.setAttribute("error", "Se ha producido un error al intentar modificar el usuario");
			request.getRequestDispatcher("errorView.jsp").forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		HttpSession session = request.getSession();
		UsuarioDTO usuarioLogueado = (UsuarioDTO) session.getAttribute("usuario");
		if (usuarioLogueado == null) {
			// Redirección en caso de no logueado
			response.sendRedirect("Login.do");
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
		
		 // falta a�adir boton para modificar perfil desde aqui
		
		else{
			//Accion desconocida especificada
			request.setAttribute("error",
					"Accion desconocida");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);
		}		
	}
}
