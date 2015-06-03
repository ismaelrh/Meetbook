package infraestructura;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name="evento")
/**
 * Clase tipo DTO que representa un evento.
 *
 */
public class EventoDTO implements Comparable<EventoDTO> {
	
	
	 @Id @GeneratedValue
	 @Column(name="id", unique = true, nullable = false)
	 private int id;
	 
	 @Column(name="titulo", unique = false, nullable = false)
	 private String titulo;
	 
	 @Column(name="lugar", unique = false, nullable = false)
	 private String lugar;
	 
	 @Column(name="fecha", unique = false, nullable = false)
	 private Date fecha;
	 
	 @Column(name="descripcion", unique = false, nullable = true)
	 private String descripcion;

	 @ManyToOne
	 @JoinColumn(name="mailCreador")
	 private UsuarioDTO creador;
	 
	 
	public EventoDTO(){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuarioDTO getCreador() {
		return creador;
	}

	public void setCreador(UsuarioDTO creador) {
		this.creador = creador;
	}

	@Override
	public int compareTo(EventoDTO arg0) {
		return this.getFecha().compareTo(arg0.getFecha());
		
	}
	 
	 
	 
}
