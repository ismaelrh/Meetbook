package infraestructura;


import javax.persistence.*;

@Entity
@Table(name="usuario")
/**
 * Clase tipo DTO que representa un usuario.
 *
 */
public class UsuarioDTO {

	@Id
	@Column(name="mail", unique = true, nullable = false)
	private String mail; // Clave primaria
	
	@Column(name="contrasegna", unique = false, nullable = false)
	private String contrasegna; // Hash de la contraseña
	
	@Column(name="nombre", unique = false, nullable = false)
	private String nombre;
	
	@Column(name="apellidos", unique = false, nullable = false)
	private String apellidos;
	
	@Column(name="nick", unique = true, nullable = false)
	private String nick; // Único
	
	

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContrasegna() {
		return contrasegna;
	}

	public void setContrasegna(String contrasegna) {
		this.contrasegna = contrasegna;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public boolean equals(Object other){
		if(other==null) return false;
		if(other==this) return true;
		if(other instanceof UsuarioDTO){
			return ((UsuarioDTO) other).getMail()
					.equalsIgnoreCase(this.getMail());
		}
		else{
			return false;
		}
	}
	

}
