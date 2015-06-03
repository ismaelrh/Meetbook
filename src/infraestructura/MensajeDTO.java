package infraestructura;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;




/**
 * DTO que sirve para el intercambio de mensajes de Chat.
 * Consta de un contenido, remitente, fecha y evento.
 * Es un singleton.
 *
 */
public class MensajeDTO {
	
	
	private String contenido;
	private String remitente;
	private Timestamp fecha;
	
	
	
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}


 
	
}