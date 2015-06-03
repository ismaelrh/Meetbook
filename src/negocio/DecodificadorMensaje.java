package negocio;

import infraestructura.MensajeDTO;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Transforma de JSON a MensajeDTO.
 *
 */
public class DecodificadorMensaje implements Decoder.Text<MensajeDTO> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	
	/**
	 * Transforma de JSON a MensajeDTO
	 * @param textMessage el mensaje JSON a transformar
	 */
	public MensajeDTO decode(final String textMessage) throws DecodeException {
		
		System.out.println("Decoding " + textMessage);
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		MensajeDTO chatMessage = gson.fromJson(textMessage, MensajeDTO.class);
		return chatMessage;
	}
 
	@Override
	/**
	 * Indica si el decodificador es capaz de decodificar.
	 * @returns true
	 */
	public boolean willDecode(final String s) {
		return true;
	}
}
