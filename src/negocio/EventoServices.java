package negocio;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import infraestructura.EventoDAO;
import infraestructura.EventoDTO;
import infraestructura.UsuarioDTO;
import infraestructura.Cons;

/** 
 * Clase que implementa los metodos de logica de negocio de eventos.
 */
public class EventoServices {

	//Clase de persistencia de Evento
	private EventoDAO eventoDAO;
	
	public EventoServices(){
		eventoDAO = new EventoDAO();
	}
	
	

/**
 * Inserta en la base de datos un Evento
 * @param evento
 * @return 	Cons.SUCCESS en caso de exito
 * 			Cons.ERR_DATABASE_PROBLEM en caso de fallo con la base de datos
 */
	public int addEvento (EventoDTO evento){
		
		try{
			//Creamos evento
			eventoDAO.insert(evento);
			return Cons.SUCCESS;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return Cons.ERR_DATABASE_PROBLEM;
		}

	}
	
	/**
	 * Obtiene de la base de datos un evento segun el identificador id
	 * @param id
	 * @return  El EventoDTO correspondiente al id pasado por parametro
	 * 			null en caso de que error
	 */
	public EventoDTO obtenerEvento (int id){
		try{
		EventoDTO evento = eventoDAO.findById(id);
			return evento;
		}
		catch(Exception e){
			return null;
		}
	}
	
	/**
	 * Modifica de la base de datos el evento pasado por parametro
	 * @param evento El evento a modificar
	 * @param usuario El usuario que lo modificar
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de fallo en la base de datos
	 * 			Cons.ERR_INVALID_USER en caso de que el evento no haya sido creador por el usuario
	 * 								  pasado por parametro
	 * 			
	 */
	public int modificarEvento(EventoDTO evento, UsuarioDTO usuario) {
		if(evento.getCreador().equals(usuario)){
			//El usuario actual quiere modificar su evento
			try{
				eventoDAO.update(evento);
				return Cons.SUCCESS;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return Cons.ERR_DATABASE_PROBLEM;
			}
		}
		else{
			return Cons.ERR_INVALID_USER;
		}
		
	}
	
	
	/**
	 * Borra de la base de datos el evento pasado por parametro
	 * @param evento El evento a borrar
	 * @param usuario El usuario que lo borra
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de fallo en la base de datos
	 * 			Cons.ERR_INVALID_USER en caso de que el evento no haya sido creador por el usuario
	 * 								  pasado por parametro
	 * 			
	 */
	public int borrarEvento (EventoDTO evento, UsuarioDTO usuario){
		if(evento.getCreador().equals(usuario)){
			//El usuario actual quiere modificar su evento
			try{
				//Primero tendremos que borrar las invitaciones a dicho evento
				InvitacionServices in = new InvitacionServices();
				in.borrarInvitaciones(evento);
				eventoDAO.delete(evento);
				return Cons.SUCCESS;
			}
			catch(Exception ex){
				ex.printStackTrace();
				return Cons.ERR_DATABASE_PROBLEM;
			}
		}
		else{
			return Cons.ERR_INVALID_USER;
		}
	}
	
	/**
	 * Devuelve una lista de todos los eventos en la base de Datos
	 * @return	Lista de eventos pedida
	 */
	public List<EventoDTO> obtenerTodosLosEventos(){
		List<EventoDTO> lista = new ArrayList<>();
		try{
			 lista = eventoDAO.findAll();
			Collections.sort(lista);	
		}
		catch(Exception ex){
			ex.printStackTrace();
			
		}
		return lista;
	}

	/**
	 * Devuelve una lista de todos los eventos creados por el usuario pasado por parametro
	 * @param user
	 * @return	Lista de eventos pedida
	 */
	public List<EventoDTO> obtenerEventosCreador(UsuarioDTO user){
		
		List<EventoDTO> lista = new ArrayList<>();
		try{
			lista = eventoDAO.findByCreator(user);
			Collections.sort(lista);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return lista;
	}	
	
}