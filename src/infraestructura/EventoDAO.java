package infraestructura;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase que ofrece operaciones basicas con la base de datos
 * relacionadas con eventos.
 * Tipo DAO.
 */
public class EventoDAO {

	
	
	/**
	 * Inserta el evento <ev> en la base de datos.
	 * 
	 * @param ev
	 * @throws HibernateException
	 */
	public void insert(EventoDTO ev) throws HibernateException{
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción
		
		session.save(ev); // Guardamos evento
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();
		
	}
	
	
	/**
	 * Obtiene el evento cuyo identificador hace referencia a <id>
	 * 
	 * @param id
	 * @return el objeto EventoDTO pedido
	 * @throws HibernateException
	 */
	public EventoDTO findById(int id) throws HibernateException {
		EventoDTO ev = null;
		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from EventoDTO where id = :id";
		Query query = session.createQuery(queryString);
		query.setInteger("id", id);
		ev = (EventoDTO) query.uniqueResult();

		session.flush();
		session.close();
		return ev;
	}
	

	/**
	 * Borra el evento <evento> de la base de datos.
	 * 
	 * @param evento
	 * @throws HibernateException
	 */
	public void delete(EventoDTO evento) throws HibernateException {
		Transaction trns = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		trns = session.beginTransaction();

		session.delete(evento);
		trns.commit();

		session.flush();
		session.close();

	}

	
	/**
	 *  Actualiza el evento <evento>
	 *  
	 * @param evento
	 * @throws HibernateException
	 */
	public void update(EventoDTO evento) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción

		session.update(evento); // Guardamos evento
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();

	}
	
	
	/**
	 * Busca todos los evento que ha creado el usuario <user> indicado
	 * 
	 * @param user
	 * @return la lista de EventoDTO pedida
	 * @throws HibernateException
	 */
	public List<EventoDTO> findByCreator(UsuarioDTO user) throws HibernateException {
		List<EventoDTO> ev = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		String queryString = "from EventoDTO where creador.mail = :creador";
		Query query = session.createQuery(queryString);
		query.setString("creador", user.getMail());
		
		for(EventoDTO i: (List<EventoDTO>) query.list()){
			ev.add(i);
		}
		
		session.flush();
		session.close();
		return ev;
	}
	
	
	/**
	 * Busca todos los eventos existentes en la base de datos
	 * 
	 * @return la lista de EventoDTO pedida
	 * @throws HibernateException
	 */
	public List<EventoDTO> findAll() throws HibernateException {
		List<EventoDTO> ev = new ArrayList<>();
		
		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from EventoDTO";
		Query query = session.createQuery(queryString);
		

	
		for(EventoDTO i: (List<EventoDTO>) query.list()){
			ev.add(i);
		}
		
		
		session.flush();
		session.close();
		return ev;
		
	}
	
}
