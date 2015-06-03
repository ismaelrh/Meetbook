package negocio;

import infraestructura.MensajeDTO;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
 
/**
 * Transforma de MensajeDTO a JSON para enviar.
 *
 */
public class CodificadorMensaje implements Encoder.Text<MensajeDTO> {
	@Override
	public void init(final EndpointConfig config) {
	}
 
	@Override
	public void destroy() {
	}
 
	@Override
	/**
	 * Codifica el objeto MensajeDTO a JSON
	 */
	public String encode(final MensajeDTO chatMessage) throws EncodeException {
		
		Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
		return gson.toJson(chatMessage);
		
	}
}
