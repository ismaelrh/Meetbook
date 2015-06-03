package infraestructura;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Clase que ofrece operaciones básicas con la base de datos relacionadas con
 * invitaciones. Tipo DAO.
 */
public class InvitacionDAO {

	/**
	 * Inserta en la base de datos una invitacion.
	 * 
	 * @param in
	 *            La invitacion a insertar
	 * @exception HibernateException
	 *                con error de la base de datos.
	 */

	public void insert(InvitacionDTO in) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción

		session.save(in); // Guardamos invitacion
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();

	}

	/**
	 * Actualiza en la base de datos una invitacion.
	 * 
	 * @param in
	 *            la invitacion a actualizar
	 * @exception HibernateException
	 *                con error de la base de datos.
	 */
	public void update(InvitacionDTO in) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción

		session.update(in); // Guardamos invitacion
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();

	}

	/**
	 * Obtiene de la base de datos una invitacion segun su identificador y la
	 * devuelve.
	 * 
	 * @params id es el identificador unico de la invitación.
	 * @throws HibernateException
	 *             con error de la base de datos.
	 * @returns El objeto InvitacionDTO correspondiente a la invitacion buscada.
	 */
	public InvitacionDTO find(int id) throws HibernateException {
		InvitacionDTO in = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from InvitacionDTO where id = :id";
		Query query = session.createQuery(queryString);
		query.setInteger("id", id);
		in = (InvitacionDTO) query.uniqueResult();

		session.flush();
		session.close();
		return in;

	}

	/**
	 * Obtiene de la base de datos y devuelve la invitación de un usuario a un
	 * evento.
	 * 
	 * @params user El usuario del que devolver la invitacion
	 * @params ev El evento del que devolver la invitacion
	 * @returns La invitacion del usuario al evento si existe, o null en caso
	 *          contrario.
	 * @throws HibernateException
	 *             con error de la base de datos.
	 */
	public InvitacionDTO find(UsuarioDTO user, EventoDTO ev)
			throws HibernateException {
		InvitacionDTO in = null;

		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from InvitacionDTO where evento.id = :id AND usuario.mail = :mail";
		Query query = session.createQuery(queryString);
		query.setInteger("id", ev.getId());
		query.setString("mail", user.getMail());
		in = (InvitacionDTO) query.uniqueResult();

		session.flush();
		session.close();
		return in;

	}

	/**
	 * Borra la invitacion indicada de la base de datos.
	 * 
	 * @params in La invitacion a borrar
	 * @throws HibernateException
	 *             en caso de error de la base de datos, o en caso de que la
	 *             invitacion no exista.
	 */
	public void delete(InvitacionDTO in) throws HibernateException {
		Transaction trns = null;
		// Abrimos una sesión
		Session session = HibernateUtil.getSessionFactory().openSession();

		trns = session.beginTransaction(); // Empezar transacción

		session.delete(in); // Guardamos invitacion
		trns.commit(); // Hacemos commit

		// En todo caso, cerramos la sesión
		session.flush();
		session.close();

	}

	/**
	 * Devuelve una lista de los usuarios invitados a un evento con estado de
	 * invitacion indicado.
	 * 
	 * @params evento El evento del que obtener los usuarios invitados
	 * @params estado El estado de la invitacion a dicho evento de los usuarios
	 *         a devolver
	 * @throws HibernateException
	 *             en caso de error de la base de datos
	 *
	 */
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> findInvitados(EventoDTO evento, String estado)
			throws HibernateException {
		List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from InvitacionDTO where evento.id = :id AND estado = :estado";
		Query query = session.createQuery(queryString);
		query.setInteger("id", evento.getId());
		query.setString("estado", estado);
		for (InvitacionDTO i : (List<InvitacionDTO>) query.list()) {
			usuarios.add(i.getUsuario());
		}

		session.flush();
		session.close();
		return usuarios;
	}

	/**
	 * Devuelve una lista de los usuarios invitados al evento indicado,
	 * independientemente del estado de su invitación.
	 * 
	 * @params evento El evento del que obtener los usuarios invitados
	 * @throws HibernateException
	 *             en caso de error de la base de datos.
	 */
	@SuppressWarnings("unchecked")
	public List<UsuarioDTO> findInvitados(EventoDTO evento)
			throws HibernateException {

		List<UsuarioDTO> usuarios = new ArrayList<>();
		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from InvitacionDTO where evento.id = :id";
		Query query = session.createQuery(queryString);
		query.setInteger("id", evento.getId());
		// System.out.println(query.getQueryString());

		for (InvitacionDTO i : (List<InvitacionDTO>) query.list()) {
			usuarios.add(i.getUsuario());
		}

		session.flush();
		session.close();
		return usuarios;
	}

	/**
	 * Devuelve el numero de invitados a un evento, con el estado de invitación
	 * igual al indicado.
	 * 
	 * @param evento
	 *            El evento del que obtener el numero de invitados
	 * @param estado
	 *            El estado de la invitacion
	 * @throws HibernateException
	 *             en caso de error de la base de datos.
	 */
	public int countInvitados(EventoDTO evento, String estado)
			throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Query query = session
				.createQuery("select count(*) from InvitacionDTO WHERE estado = :estado AND evento.id = :id");
		query.setString("estado", estado);
		query.setInteger("id", evento.getId());
		int count = ((Long) query.uniqueResult()).intValue();

		session.flush();
		session.close();
		return count;

	}

	/**
	 * Devuelve el numero de invitados a un evento, independientemente del
	 * estado de su invitación.
	 * 
	 * @param evento
	 *            El evento del que obtener el numero de invitados
	 * @throws HibernateException
	 *             en caso de error de la base de datos.
	 */
	public int countInvitados(EventoDTO evento) throws HibernateException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		int count = ((Long) session.createQuery(
				"select count(*) from InvitacionDTO").uniqueResult())
				.intValue();
		session.flush();
		session.close();
		return count;

	}

	
	/**
	 * Devuelve la lista de invitaciones de un usuario concreto, con un estado de 
	 * invitacion determinado, y comprendidas entre una fecha de inicio y fin.
	 * 
	 * @param user El usuario propietario de dichas invitaciones, es decir, el invitado.
	 * @param estado El estado de las invitaciones a devolver.
	 * @param fechaInicio La fecha minima a partir de la cual buscar.
	 * @param fechaFin La fecha maxima a partir de la cual buscar.
	 * @throws HibernateException si se produce algun error con la base de datos.
	 */
	public List<InvitacionDTO> findByEstado(UsuarioDTO user, String estado,
			Date fechaInicio, Date fechaFin) throws HibernateException {

		Session session = HibernateUtil.getSessionFactory().openSession();

		String queryString = "from InvitacionDTO where usuario.mail = :mail AND evento.fecha >= :fechaInicio AND "
				+ "evento.fecha <= :fechaFin AND estado = :estado";

		Query query = session.createQuery(queryString);
		query.setString("mail", user.getMail());
		query.setDate("fechaInicio", fechaInicio);
		query.setDate("fechaFin", fechaFin);
		query.setString("estado", estado);
		List<InvitacionDTO> lista = (List<InvitacionDTO>) query.list();

		session.flush();
		session.close();
		return lista;
	}

}
