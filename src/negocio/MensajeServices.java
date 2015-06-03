package negocio;

import java.util.List;
/**
 * Clase que implementa servicios relacionados con los mensajes de Chat.
 */

import infraestructura.*;
public class MensajeServices {

	private MensajeDAO mensajeDAO;
	
	public MensajeServices(){
		mensajeDAO = MensajeDAO.getInstance();
	}
	
	/**
	 * Devuelve los 10 ultimos mensajes de un evento.
	 * @param idEvento es el numero de evento 
	 * @return los mensajes, o null si hay problema de base de datos.
	 */
	public List<MensajeDTO> lastTenMessages(int idEvento){
		
		try{
			return mensajeDAO.findLast(idEvento,10);
		}
		catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * Guarda el mensaje indicado en la base de datos.
	 * @param m el mensaje indicado
	 * @param idEvento el evento al que esta relacionado
	 * @return true si exito, falso en caso contrario.
	 */
	public boolean saveMessage(MensajeDTO m, int idEvento){
		try{
			 mensajeDAO.guardarMensaje(m, idEvento);
			 return true;
		}
		catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
}
