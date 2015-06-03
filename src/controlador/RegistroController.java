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

import negocio.UsuarioServices;

/**
 * Servlet que implementa el registro de un nuevo usuario.
 * Controlador de la página de registro.
 */
@WebServlet("/Registro.do")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	/**
	 * En caso de GET servimos la página de registro.
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		    
		
			RequestDispatcher rd = request.getRequestDispatcher("registroView.jsp");
			rd.forward(request, response);
	}

	/**
	 * En caso de POST procesamos los datos de registro y mostramos errores si procede.
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		

		Map<String, String> errores = new HashMap<String, String>();
		
		//Obtenemos datos pasados
		String email = request.getParameter("email");
		String pw = request.getParameter("password");
		String repw = request.getParameter("repassword");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String nick = request.getParameter("nick");
		
		
		if ((email == null) || (email.equals(""))) {
			errores.put("email", "Campo obligatorio");
		}
		if ((pw == null) || (pw.equals(""))) {
			errores.put("pw", "Campo obligatorio");
		}
		if ((repw == null) || (repw.equals(""))) {
			errores.put("repw", "Campo obligatorio");
		}
		if (!(repw.equals(pw))) {
			errores.put("repw", "Las contraseñas no coinciden");
		}
		if ((name == null) || (name.equals(""))) {
			errores.put("name", "Campo obligatorio");
		}
		if ((surname == null) || (surname.equals(""))) {
			errores.put("surname", "Campo obligatorio");
		}
		if ((nick == null) || (nick.equals(""))) {
			errores.put("nick", "Campo obligatorio");
		}
		

		
		if (errores.isEmpty()) {
			//No hay errores
			
			UsuarioServices manager = new UsuarioServices();
			UsuarioDTO user = new UsuarioDTO();
			user.setMail(email);
			user.setContrasegna(pw);
			user.setNombre(name);
			user.setApellidos(surname);
			user.setNick(nick);
			
			int exito = 0;
			try {
		
				//exito = Facade.SUCCESS si se ha añadido, ERROR_USED_MAIL si mail repetido,
				// ERROR_USED_NICK si nick repetido
				exito = manager.addUsuario(user); /* Añadimos el usuario */

				if(exito==Cons.SUCCESS){
					//Se ha tenido éxito
					//Lo logueamos y mandamos a página de inicio
					request.getSession().setAttribute("usuario",user);
					response.sendRedirect("EventList.do");
						
				}
				else{
					//No se ha tenido éxito
					
					if(exito==Cons.ERR_MAIL_REPEATED){
						errores.put("email", "El email ya está en uso");
					}
					if(exito==Cons.ERR_NICK_REPEATED){
						errores.put("nick", "El nick ya está en uso");
					}
					if(exito==Cons.ERR_NICK_MAIL_REPEATED){
						errores.put("email", "El email ya está en uso");
						errores.put("nick", "El nick ya está en uso");
					}
						
					
					//Hacemos forward con error de ya en uso.
					request.setAttribute("errores", errores);
					request.setAttribute("email",email);
					request.setAttribute("name",name);
					request.setAttribute("surname",surname);
					request.setAttribute("nick", nick);
					
					RequestDispatcher dispatcher = request
							.getRequestDispatcher("registroView.jsp");
					dispatcher.forward(request, response);
				}
				
			} catch (Exception ex) {
				//Error al registrar
				request.setAttribute("error", "Se ha producido un error interno durante el registro.");
				request.getRequestDispatcher("errorView.jsp").forward(request, response);
			}

			
		} else {
			/* Ha habido errores, los mostramos */
			request.setAttribute("errores", errores);
			request.setAttribute("email",email);
			request.setAttribute("name",name);
			request.setAttribute("surname",surname);
			request.setAttribute("nick", nick);
			RequestDispatcher dispatcher = request
					.getRequestDispatcher("registroView.jsp");
			dispatcher.forward(request, response);

		}

	}

}
