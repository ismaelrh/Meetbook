package negocio;

import infraestructura.EventoDTO;
import infraestructura.UsuarioDTO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JSON_API
 */
@WebServlet("/JSON_API.do")
public class JSON_API extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private static final String GET_USERS = "getUsers";
	private static final String GET_EVENTS = "getEvents";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JSON_API() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String param = request.getParameter("method");
		PrintWriter out = response.getWriter();
		if(param!=null && param.length()>0){
			switch(param){
			
			case GET_USERS:
				//Devuelve lista para autocompletado de usuarios
				
				//String busqueda = request.getParameter("term");
				//busqueda = busqueda.replace("+", " ");
				UsuarioServices us = new UsuarioServices();
				List<UsuarioDTO> lista = us.findAllUsers();
				String json = "[" ;
				for(UsuarioDTO usuario: lista){
					String nick = usuario.getNick();
					String mail = usuario.getMail();
					String objeto = "{ value: \"" + nick + " - " + mail + "\", data: \"" + nick + "\"}, ";
					json += objeto;
				}
				json = json + "]";
				out.println(json);
				response.setStatus(200);
				
				break;
			case GET_EVENTS:
				//Devuelve lista para autocompletado de eventos
				EventoServices ev = new EventoServices();
				List<EventoDTO> list = ev.obtenerTodosLosEventos();
				String json2 = "[";
				for(EventoDTO evento: list){
					String nombre = evento.getTitulo();
					String hora = evento.getFecha().toGMTString();
					String lugar = evento.getLugar();
					String objeto = "{ value: \"" + nombre + " - " + hora + " en " + lugar + "\", data: \"" + evento.getId() + "\"}, ";
					json2+=objeto;
				}
				json2 = json2 + "]";
				out.println(json2);
				response.setStatus(200);
				break;
			
			}
		}
		else{
			out.println("ESPECIFIQUE UN METODO POR FAVOR");
			response.setStatus(404);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
