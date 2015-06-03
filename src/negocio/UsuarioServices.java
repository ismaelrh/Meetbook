package negocio;

import infraestructura.Cons;
import infraestructura.UsuarioDAO;
import infraestructura.UsuarioDTO;
import infraestructura.Utiles;


import java.util.ArrayList;
import java.util.List;

/** 
 * Clase que implementa los metodos de logica de negocio de eventos.
 */
public class UsuarioServices {

	
	private UsuarioDAO usuarioDAO;

	public UsuarioServices() {
		usuarioDAO = new UsuarioDAO();
	}


	/**
	 * A√±ade a la base de datos el usuario pasado por parametro
	 * @param user
	 * @return	SUCCESS en caso de exito
	 * 			ERR_MAIL_REPEATED en caso de mail repetido, 
	 * 			ERR_NICK_REPEATED en caso de nick repetido,
	 * 			ERR_NICK_MAIL_REPEATED si ambos repetidos,
	 * 			ERR_DATABASE_PROBLEM en caso de error con la base de datos.
	 */
	public int addUsuario(UsuarioDTO user) {

		try {
			UsuarioDTO mailUser = usuarioDAO.findByMail(user.getMail());
			UsuarioDTO nickUser = usuarioDAO.findByNick(user.getNick());
			user.setContrasegna(Utiles.generateHash(user.getContrasegna()));
			if (mailUser == null && nickUser == null) {
				// No hay ninguno repetido
				usuarioDAO.insert(user);
				return Cons.SUCCESS;
			} else if (mailUser != null) {
				if (nickUser != null) {
					// Ambos repetidos
					return Cons.ERR_NICK_MAIL_REPEATED;
				} else {
					// Solo repetido el mail
					return Cons.ERR_MAIL_REPEATED;
				}
			} else {
				// Solo repetido el nick
				return Cons.ERR_NICK_REPEATED;
			}
		} catch (Exception ex) {
			return Cons.ERR_DATABASE_PROBLEM;
		}
	}
	
	public int updateUsuario(UsuarioDTO user) {
		int respuesta = 0;
		try {
			UsuarioDTO mailUser = usuarioDAO.findByMail(user.getMail());
			user.setContrasegna(Utiles.generateHash(user.getContrasegna()));
			if(mailUser != null) {	// El usuario a modificar existe
				UsuarioDTO existe = usuarioDAO.findByNick(user.getNick());
				
				if(existe == null || existe.equals(user)) { 
					// El nuevo nick no est· ocupado
					usuarioDAO.update(user);
					respuesta =  Cons.SUCCESS;
				}
				// El nick est· usado por otro usuario
				else respuesta = Cons.ERR_NICK_REPEATED;	
			}
			// Se intenta modificar usuario que no existe
			else respuesta = Cons.ERR_INVALID_USER;
			
		} catch (Exception ex) {
			return Cons.ERR_DATABASE_PROBLEM;
		}
		return respuesta;
	}

	
	/**
	 * Devuelve el usuario identificado por el <email> mail,
	 * o null si no existe o ha habido un problema con la BD.
	 * @param mail
	 * @return El objeto tipo UsuarioDTO pedido
	 */
	public UsuarioDTO getUsuario(String mail) {
		try {
			return usuarioDAO.findByMail(mail);
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Valida que el usuario identificado por el email <mail> 
	 *  tiene como contrase√±a la cadena <pass>.
	 * @param mail
	 * @param pass
	 * @return	Cons.SUCCESS si y solo si el pass es correcto para dicho mail,
	 *  		ERR_INVALID_LOGIN en caso contrario,
	 * 			 ERR_DATABASE_PROBLEM si hay un problema con la BD.
	 */
	public int validarUsuario(String mail, String pass) {
		try {
			UsuarioDTO savedUser = usuarioDAO.findByMail(mail);
			int correcto = Cons.ERR_INVALID_LOGIN;
			if (savedUser != null) {
				// Comprobamos contrase√±a
				// Calculamos hash de pass
				String hashedPass = Utiles.generateHash(pass);
				// Comprobamos hash con pass de usuario
				if (hashedPass.equals(savedUser.getContrasegna()))
					correcto = Cons.SUCCESS;
			}
			return correcto;
		} catch (Exception ex) {
			ex.printStackTrace();
			return Cons.ERR_DATABASE_PROBLEM;
		}
	}
	
	public List<UsuarioDTO> searchUsuarioByMail(String mail) {
		
		List<UsuarioDTO> lista = new ArrayList<>();
		try {
			lista = usuarioDAO.search(mail, "");

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return lista;
	}
	
	public List<UsuarioDTO> searchUsuarioByNick(String nick) {
		
		List<UsuarioDTO> lista = new ArrayList<>();
		try {
			lista = usuarioDAO.search("",nick);

		} catch (Exception ex) {
			ex.printStackTrace();

		}
		return lista;
	}
	
	public List<UsuarioDTO> findAllUsers(){
		
		List<UsuarioDTO> lista = new ArrayList<>();
		try{
			lista = usuarioDAO.search("","");
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return lista;

	}
	
	public static void main(String[] args) {
		UsuarioServices este = new UsuarioServices();
		List<UsuarioDTO> lista = este.findAllUsers();
		for(UsuarioDTO u: lista){
			System.out.println(u.getNick() + " - " + u.getMail());
		}
		
	}
}
