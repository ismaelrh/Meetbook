package negocio;
import infraestructura.MensajeDAO;
import infraestructura.MensajeDTO;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
 

/**
 * Clase que implementa el "Endpoint" del web-service de chat. Contiene métodos para la
 * apertura de una nueva sesión (OnOpen) y el envío de un mensaje (OnMessage).
 * El endpoint tendrá una URL de tipo ws://ip_servidor/<CONTEXT>/chat/<room>
 * Usa dos filtros:
 * 	CodificadorMensaje: Codifica un objeto de tipo 'MensajeChat' a JSON para su envío.
 *  DecodificadorMensaje: Decodifica un JSON a 'MensajeChat' para su procesamiento por el servidor.
 *
 */
@ServerEndpoint(value = "/chat/{room}", encoders = CodificadorMensaje.class, decoders = DecodificadorMensaje.class)
public class ChatEndpoint {
	
	private final Logger log = Logger.getLogger(getClass().getName());
	MensajeServices mdb = new MensajeServices();
	@OnOpen
	/**
	 * Apertura de una nueva conexión WebSockets con un cliente.
	 * Es decir, ha entrado a un chat.
	 * @param session es la sesión del usuario que inicia chat
	 * @param room es el parámetro <room> pasado por URL y que indica la sala de chatl
	 */
	public void open(final Session session, @PathParam("room") final String room) {
		log.info("session openend and bound to room: " + room);
		//Guardamos la room de chat en la sesión de dicho usuario.
		session.getUserProperties().put("room", room);
	}
 
	@OnMessage
	/**
	 * Envío por parte de un cliente de un nuevo mensaje a una room determinada.
	 * Se encarga de distribuir el mensaje a todos los clientes suscritos a 
	 * dicha room.
	 * @param session es la sesión del usuario que envía el mensaje
 	 * @param chatMessage es el mensaje enviado.
	 */
	public void onMessage(final Session session, final MensajeDTO chatMessage) {
		
		
		
		//Obtenemos la room del que ha puesto el mensaje
		String room = (String) session.getUserProperties().get("room");
	
			if(mdb.saveMessage(chatMessage, Integer.parseInt(room))){
				System.out.println("Mensaje guardado en BBDD");
			}
			else{
				System.out.println("Error al guardar mensaje en BBDD");
			}
		
		try {
			for (Session s : session.getOpenSessions()) {
				if (s.isOpen()
						&& room.equals(s.getUserProperties().get("room"))) {
					//A los que esten en misma room, les mandamos un mensaje
					s.getBasicRemote().sendObject(chatMessage);
				}
			}
		} catch (IOException | EncodeException e) {
			log.log(Level.WARNING, "onMessage failed", e);
		}
	}
}