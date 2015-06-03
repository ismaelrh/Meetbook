package infraestructura;

import javax.persistence.*;


@Entity
@Table(name="invitacion")
/**
 * Clase tipo DTO que representa una invitación,
 * relacionando usuario y evento, con un estado de invitación.
 *
 */
public class InvitacionDTO {
	
	public static final String PENDIENTE = "PENDIENTE";
	public static final String ACEPTADA = "ACEPTADA";
	public static final String RECHAZADA = "RECHAZADA";
	
	
	 @Id @GeneratedValue
	 @Column(name="id", unique = true, nullable = false)
	 private int idInvitacion;
	 
	

	@ManyToOne
    @JoinColumn(name="mailCreador")
	private UsuarioDTO usuario;
    
	
	@ManyToOne
    @JoinColumn(name="idEvento")
	private EventoDTO evento;

	
	@Column(name="estado", nullable=false)
	private String estado;
	
	
	public InvitacionDTO(){
	
	}
	
	public int getIdInvitacion() {
		return idInvitacion;
	}

	public void setIdInvitacion(int idInvitacion) {
		this.idInvitacion = idInvitacion;
	}

	public void setUsuario(UsuarioDTO u){
		usuario = u;
	}
	
	public UsuarioDTO getUsuario(){
		return usuario;
	}
	
	public void setEvento(EventoDTO e){
		evento = e;
	}
	
	public EventoDTO getEvento(){
		return evento;
	}

	
	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	

}
