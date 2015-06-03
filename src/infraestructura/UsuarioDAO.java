package infraestructura;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase que ofrece operaciones básicas con la base de datos
 * relacionadas con usuarios.
 * Tipo DAO.
 */
public class UsuarioDAO {

	/**
	 * Inserta un nuevo usuario <user> en la base de datos.
	 * 
	 * @param user
	 * @throws HibernateException
	 */
	public void insert(UsuarioDTO user) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesion
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transaccion

		session.save(user); // Guardamos usuario
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesion
		session.flush();
		session.close();

	}

	/**
	 * Actualiza el usuario <user> en la base de datos
	 * 
	 * @param user
	 * @throws HibernateException
	 */
	public void update(UsuarioDTO user) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción

		session.update(user); // Guardamos usuario
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();

	}

	/**
	 * Borra de la base de datos el usuario cuyo mail es <mail>
	 * 
	 * @param mail
	 * @throws HibernateException
	 */
	public void delete(String mail) throws HibernateException {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		trns = session.beginTransaction();
		UsuarioDTO user = (UsuarioDTO) session.load(UsuarioDTO.class, mail);
		session.delete(user);
		trns.commit();

		session.flush();
		session.close();

	}

	/**
	 * Obtiene el usuario cuyo mail es <mail>
	 * @param mail
	 * @return el objeto usuarioDTO pedido
	 * @throws HibernateException
	 */
	public UsuarioDTO findByMail(String mail) throws HibernateException {
		UsuarioDTO user = null;
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		
		String queryString = "from UsuarioDTO where mail = :mail";
		Query query = session.createQuery(queryString);
		query.setString("mail", mail);
		user = (UsuarioDTO) query.uniqueResult();

		session.flush();
		session.close();
		return user;
	}
	
	/**
	 * Obtiene el usuarioDTO cuyo nick es <nick>
	 * 
	 * @param nick
	 * @return el objeto UsuarioDTO pedido
	 * @throws HibernateException
	 */
	public UsuarioDTO findByNick(String nick) throws HibernateException {
		UsuarioDTO user = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		String queryString = "from UsuarioDTO where nick = :nick";
		Query query = session.createQuery(queryString);
		query.setString("nick", nick);
		user = (UsuarioDTO) query.uniqueResult();

		session.flush();
		session.close();
		return user;
	}
	
	/**
	 * Obtiene una lista de los usuarios que se ajustan a los criterios indicados.
	 * Busca por mail parecido si solo mail.length>0,
	 * por nick si solo nick.length>0,
	 * por nick ó por mail si nick.length>0 y mail.length>0
	 * o devuelte todos los usuarios si nick.length=0 y mail.length =0
	 * @param mail es el mail a buscar
	 * @param nick es el nick a buscar
	 * @return lista de los usuarios ajustados al criterio de busqueda
	 * @throws HibernateException
	 */
	public List<UsuarioDTO> search(String mail, String nick) throws HibernateException{
		
		List<UsuarioDTO> user = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		String queryString = "from UsuarioDTO";
		Query query = session.createQuery(queryString);
		
		if( (mail==null || mail.length()==0) && (nick==null || nick.length()==0)){
			//Se obtienen todos
			query = session.createQuery(queryString);
		}
		else{
			if ((mail!=null && mail.length()>0) && (nick!=null && nick.length()>0)){
				//Hay ambos campos
				queryString+= " WHERE mail LIKE :mail OR nick LIKE :nick";
				query = session.createQuery(queryString);
				query.setString("mail", "%"+mail+"%");
				query.setString("nick","%"+nick+"%");
			}
			else{
				//Solo hay un campo
				if(mail!=null && mail.length()>0){
					//Solo hay mail
					queryString+= " WHERE mail LIKE :mail";
					query = session.createQuery(queryString);
					query.setString("mail", "%"+mail+"%");
					
				}
				if(nick!=null && nick.length()>0){
					//Solo hay nick
					queryString+= " WHERE nick LIKE :nick";
					query = session.createQuery(queryString);
					query.setString("nick", "%"+nick+"%");
					
				}
			}
		}
		
		
		for(UsuarioDTO i: (List<UsuarioDTO>) query.list()){
			user.add(i);
		}
		

		session.flush();
		session.close();
		return user;
	}
	
	
	
	
}
