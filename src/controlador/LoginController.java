package controlador;
import infraestructura.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import negocio.UsuarioServices;

/**
 * Servlet que implementa el login de un usuario.
 * Controlador de la página de login.
 */
@WebServlet("/Login.do")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * En caso de GET, se sirve la vista de login.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		 
		
		RequestDispatcher rd = request.getRequestDispatcher("loginView.jsp");
		rd.forward(request, response);
	}

	/**
	 * En caso de POST procesamos los datos de login y mostramos errores si procede.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		Map<String, String> errores = new HashMap<String, String>();
		
		//Obtenemos datos
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
	
		if ((email == null) || (email.equals(""))) {
			errores.put("loginError", "Rellene todos los campos");
		}
		if ((pw == null) || (pw.equals(""))) {
			errores.put("loginError", "Rellene todos los campos");
		}
		
		if (errores.isEmpty()) {	//No hay errores
			UsuarioServices manager = new UsuarioServices();
			int exito = 0;
			try {
				exito = manager.validarUsuario(email, pw);

				if (exito!=Cons.SUCCESS) { /* Datos incorrectos por cualquier razon*/
					errores.put("loginError", "Email o contraseña no válidos");
					request.setAttribute("errores", errores);
					request.setAttribute("email", email);
					//Redirigimos con errores
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("loginView.jsp");
					dispatcher.forward(request, response);
					
				} else {
					/* Se ha tenido exito */
					
					/* Registro correcto, logueamos */
					UsuarioDTO user = manager.getUsuario(email);
					HttpSession session = request.getSession();
					session.setAttribute("usuario", user); //Atributo usuario en session
					
					//Enviamos a lista de eventos
					response.sendRedirect("EventList.do"); 
					
						
				}
			} catch (Exception ex) {
				//Error interno
				request.setAttribute("error", "Se ha producido un error interno al hacer login.");
				request.getRequestDispatcher("errorView.jsp").forward(request, response);
			}

			
		} else {
			/* Ha habido errores, los mostramos */
			request.setAttribute("email",email);
			request.setAttribute("errores", errores);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("loginView.jsp");
			
			dispatcher.forward(request, response);

		}

	}

}
