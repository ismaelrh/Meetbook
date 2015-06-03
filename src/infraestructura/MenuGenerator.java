package infraestructura;



public class MenuGenerator {

	/**
	 * Devuelve una String con el código HTML del menú superior de navegación que se muestra
	 * cuando el usuario está logueado.
	 */
	public static String generateMenu(UsuarioDTO usuario, int modo_int){
		String menu  ="";
		
		menu += "<nav class=\"navbar navbar-inverse\">";
		menu += "<div class=\"container-fluid\">";
		menu += "<div class=\"navbar-header\">";
		menu += "<a class=\"navbar-brand\" href=\"/MeetBook/EventList.do\">MeetBook</a>";
		menu += "</div>";
		menu += "<div class=\"collapse navbar-collapse\" id=\"bs-example-navbar-collapse-1\">";
		

		
		String active_all = "";
		String active_own = "";
		String active_past = "";
		String active_future = "";
		String active_declined = "";
		switch (modo_int) {
		case Cons.MODE_VIEW_ALL_EVENTS:
			active_all = "active";
			break;
		case Cons.MODE_VIEW_OWN_EVENTS:
			active_own = "active";
			break;
		case Cons.MODE_VIEW_FUTURE_EVENTS:
			active_future = "active";
			break;
		case Cons.MODE_VIEW_PAST_EVENTS:
			active_past = "active";
			break;
		case Cons.MODE_VIEW_DECLINED_EVENTS:
			active_declined = "active";
			break;
		default:
			active_all = "active";
			break;

		}
			
		menu += "<ul class=\"nav navbar-nav\">";
		menu += "<li class=\"" + active_all + "\"><a href=\"/MeetBook/EventList.do?modo="+ Cons.MODE_VIEW_ALL_EVENTS +"\">Todos";
		menu += "</a></li>";
		
		menu += "<li class=\"" + active_own + "\"><a href=\"/MeetBook/EventList.do?modo="+ Cons.MODE_VIEW_OWN_EVENTS +"\">Creados por mí";
		menu += "</a></li>";
					
		menu += "<li class=\"" + active_past + "\"><a href=\"/MeetBook/EventList.do?modo="+ Cons.MODE_VIEW_PAST_EVENTS +"\">Pasados";
		menu += "</a></li>";
		
		menu += "<li class=\"" + active_future + "\"><a href=\"/MeetBook/EventList.do?modo="+ Cons.MODE_VIEW_FUTURE_EVENTS +"\">Futuros";
		menu += "</a></li>";
		
		menu += "<li class=\"" + active_declined + "\"><a href=\"/MeetBook/EventList.do?modo="+ Cons.MODE_VIEW_DECLINED_EVENTS +"\">Rechazados";
		menu += "</a></li>";
						
						
		menu += "</ul>";
		
		menu += "<ul class=\"nav navbar-nav navbar-right\">";
		
		menu +=  "<form class=\"navbar-form navbar-left\" role=\"search\" action=\"/MeetBook/CrearEvento.do\" method=\"get\">";
		
		menu += "<div class=\"form-group\"></div>";
		
		menu += "<button type=\"submit\" class=\"btn btn-default\">Crear evento</button>";
		
		menu+= "</form>";
		
		menu +=  "<form class=\"navbar-form navbar-left\" role=\"search\" action=\"/MeetBook/BuscarUsuario.do\" method=\"post\">";
		
		menu += "<div class=\"form-group\"></div>";
		
		menu += "<button type=\"submit\" class=\"btn btn-info\">Buscador</button>";
		
		menu+= "</form>";	
		
//		menu+= "<li><a  href=\"#\">" + usuario.getNick() + "</a></li>";
//		
		menu+= "<input type=\"hidden\" id=\"nickname\" value=\"" + usuario.getNick() + "\">";
		
		menu += "<form class=\"navbar-form navbar-left\" role=\"search\"  action=\"/MeetBook/Perfil.do\" method=\"get\">";
		
		menu += "<div class=\"form-group\"></div>";
		
		menu += "<button type=\"submit\" class=\"btn btn-default\">" + usuario.getNick() + "</button>";
		
		menu += "</form>";

		menu+= "<li><a href=\"/MeetBook/Logout.do\">Salir</a></li>";
						
		menu +="</ul></div></div></nav>";

		return menu;
	}
}
