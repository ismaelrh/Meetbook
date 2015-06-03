package controlador;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet que implementa el logout.
 * Controlador de logout.
 */
@WebServlet("/Logout.do")
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	public LogoutController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * En caso de GET destruimos la sesión actual y enviamos a página de login.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		try {
			request.getSession().invalidate();
			response.sendRedirect("Login.do");
		} catch (Exception ex) {
			request.setAttribute("error",
					"Se ha producido un error interno al cerrar la sesión.");
			request.getRequestDispatcher("errorView.jsp").forward(request,
					response);
		}
	}

	/**
	 * En caso de POST no se hace nada.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
