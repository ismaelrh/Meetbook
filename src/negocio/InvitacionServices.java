package negocio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import infraestructura.UsuarioDAO;
import infraestructura.UsuarioDTO;
import infraestructura.InvitacionDAO;
import infraestructura.InvitacionDTO;
import infraestructura.EventoDTO;
import infraestructura.Cons;

/** 
 * Clase que implementa los metodos de logica de negocio de invitacion.
 */
public class InvitacionServices {

	
	private InvitacionDAO invitacionDAO;

	public InvitacionServices() {
		invitacionDAO = new InvitacionDAO();
	}

	

	/**
	 * Añade la invitacion del usuario <user> al evento <evento>
	 * con el estado PENDIENTE,siendo el usuario que desencadena la accion
	 * <realizadorAccion>
	 * Devuelve SUCCESS en caso de operacion correcta,
	 * ERR_INVITATION_REPEATED si el usuario estaba ya invitado,
	 * ERR_PERMISION_DENIED si el usuario logueado no tiene permiso para invitarle,
	 * ERR_DATABASE_PROBLEM en caso de problema de BD.
	 */
	
	/**
	 * Añade una invitacion a la base de datos
	 * @param evento evento al que ha sido invitado
	 * @param participante usuario invitado
	 * @param realizadorAccion usuario que realiza la accion
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_INVITATION_REPEATED en caso de que participante ya este invitado
	 * 			Cons.ERR_PERMISSION_DENIED en caso de que el evnto no haya sido creado por realizadorAccion
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de error en la base de datos
	 */
	public int invitarUsuario(EventoDTO evento, UsuarioDTO participante,
			 UsuarioDTO realizadorAccion) {
		try {
			UsuarioDTO creador = evento.getCreador();
			if (creador.equals(realizadorAccion)) {
				InvitacionDTO inv = new InvitacionDTO();
				inv.setEvento(evento);
				inv.setUsuario(participante);
				inv.setEstado(InvitacionDTO.PENDIENTE);

				// Miramos si ya existe
				if (invitacionDAO.find(participante, evento) == null) {
					// No existe, creamos
					invitacionDAO.insert(inv);
					return Cons.SUCCESS;
				} else {
					// Existe, el usuario ya esta invitado
					return Cons.ERR_INVITATION_REPEATED;
				}
			} else {
				// No es el propietario, no tiene permisos
				return Cons.ERR_PERMISSION_DENIED;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			return Cons.ERR_DATABASE_PROBLEM;
		}
	}
	
	/**
	 * Devuelve la invitacion del usuario user al evento evento
	 * @param user
	 * @param evento
	 * @return objecto InvitacionDTO pedido
	 */
	public InvitacionDTO findInvitacion(UsuarioDTO user, EventoDTO evento) {
		InvitacionDTO inv = null;
		try {
			inv = invitacionDAO.find(user, evento);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return inv;

	}



	/**
	 * Modifica el estado de una invitacion <inv> a ACEPTADA, 
	 * siendo <realizadorAccion> el usuaroi que realiza la accion.
	 * Devuelve SUCCESS en caso de exito, 
	 * ERR_DATABASE_CONEXION si ha habido un error en la BD,
	 * ERR_INVALID_USER en caso de que el realizador de la accion
	 * no sea el propietario de la invitacion.
	 */

	/**
	 * Modifica el estado de la invitacion inv a ACEPTADA
	 * @param inv
	 * @param realizadorAccion
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de error con la base de datos
	 * 			Cons.ERR_INVALID_USER en caso de que la invitacion no corresponda al usuario realizadorAccion
	 */
	public int aceptarInvitacion(InvitacionDTO inv, UsuarioDTO realizadorAccion) {
		if (inv.getUsuario().equals(realizadorAccion)) {
			// El usuario actual quiere aceptar su invitacion
			try {
				inv.setEstado("ACEPTADA");
				invitacionDAO.update(inv);
				return Cons.SUCCESS;
			} catch (Exception ex) {
				ex.printStackTrace();
				return Cons.ERR_DATABASE_PROBLEM;
			}
		} else {
			return Cons.ERR_INVALID_USER;
		}
	}

	

	/**
	 * Modifica el estado de la invitacion inv a RECHAZADA
	 * @param inv
	 * @param realizadorAccion
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de error con la base de datos
	 * 			Cons.ERR_INVALID_USER en caso de que la invitacion no corresponda al usuario realizadorAccion
	 */
	public int rechazarInvitacion(InvitacionDTO inv, UsuarioDTO realizadorAccion) {
		if (inv.getUsuario().equals(realizadorAccion)) {
			
			try {
				inv.setEstado("RECHAZADA");
				invitacionDAO.update(inv);
				return Cons.SUCCESS;
			} catch (Exception ex) {
				ex.printStackTrace();
				return Cons.ERR_DATABASE_PROBLEM;
			}
		} else {
			return Cons.ERR_INVALID_USER;
		}
	}

	/**
	 * Devuelve una lista de los eventos a los que el usuario <user>
	 * acepto la invitacion y ya han pasado.
	 * @param user
	 * @return La lista de EventoDTO pedida
	 */
	public List<EventoDTO> obtenerEventosAsistidosPasados(UsuarioDTO user) {

		List<EventoDTO> lista = new ArrayList<>();
		try {
			Calendar calendarInicio = new GregorianCalendar(0, 1, 1, 0, 0, 0);
			Date fechaInicio = calendarInicio.getTime();
			Date fechaFin = Calendar.getInstance().getTime();
			String estado = "ACEPTADA";
			for (InvitacionDTO inv : invitacionDAO.findByEstado(user, estado,
					fechaInicio, fechaFin)) {
				lista.add(inv.getEvento());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Devuelve una lista de los eventos a los que el usuario <user>
	 * acepto la invitacion y todavia no han pasado.
	 * @param user
	 * @return La lista de EventoDTO pedida
	 */
	public List<EventoDTO> obtenerEventosAsistidosFuturos(UsuarioDTO user) {

		List<EventoDTO> lista = new ArrayList<>();
		try {

			Date fechaInicio = Calendar.getInstance().getTime();
			Calendar calendarFin = new GregorianCalendar(2999, 1, 1, 0, 0, 0);
			Date fechaFin = calendarFin.getTime();
			String estado = "ACEPTADA";
			for (InvitacionDTO inv : invitacionDAO.findByEstado(user, estado,
					fechaInicio, fechaFin)) {
				lista.add(inv.getEvento());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Devuelve una lista de los eventos a los que el usuario <user>
	 * rechazo la invitacion y todavia no han pasado.
	 * @param user
	 * @return La lista de EventoDTO pedida
	 */
	public List<EventoDTO> obtenerEventosRechazadosFuturos(UsuarioDTO user) {

		List<EventoDTO> lista = new ArrayList<>();
		try {

			Date fechaInicio = Calendar.getInstance().getTime();
			Calendar calendarFin = new GregorianCalendar(2999, 1, 1, 0, 0, 0);
			Date fechaFin = calendarFin.getTime();
			String estado = "RECHAZADA";
			for (InvitacionDTO inv : invitacionDAO.findByEstado(user, estado,
					fechaInicio, fechaFin)) {
				lista.add(inv.getEvento());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lista;
	}

	/**
	 * Devuelve una lista de invitaciones del usuario <user>
	 * cuyos eventos todavia no han pasado
	 * @param user
	 * @return La lista de InvitacionDTO pedida
	 */
	public List<InvitacionDTO> obtenerInvitacionesPendientesFuturas(
			UsuarioDTO user) {
		List<InvitacionDTO> lista = new ArrayList<>();
		try {
			Date fechaInicio = Calendar.getInstance().getTime();
			Calendar calendarFin = new GregorianCalendar(2999, 1, 1, 0, 0, 0);
			Date fechaFin = calendarFin.getTime();
			String estado = "PENDIENTE";
			lista = invitacionDAO
					.findByEstado(user, estado, fechaInicio, fechaFin);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Devuelve el numero de asistences al evento <evento>, es decir,
	 * usuarios cuyo estado de invitacion es "ACEPTADA".
	 * @param evento
	 * @return El numero de asistentes pedido
	 */
	public int obtenerNumAsistentes(EventoDTO evento) {
		int inv = 0;
		try {
			inv = invitacionDAO.countInvitados(evento, "ACEPTADA");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return inv;
	}

	/**
	 * Obtiene los usuarios que van a asistir al evento <ev>,
	 * es decir, usuarios cuyo estado de invitacion es "ACEPTADA".
	 * @param ev
	 * @return El numero de asistentes pedido
	 */
	public List<UsuarioDTO> obtenerAsistentes(EventoDTO ev) {

		List<UsuarioDTO> lista = new ArrayList<UsuarioDTO>();
		try {
			lista = invitacionDAO.findInvitados(ev, "ACEPTADA");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lista;
	}
	
	/**
	 * Devuelve el estado de la invitacion del usuario <user> al evento <evento>,
	 * pudiendo ser "NO INVITADO" si no esta invitado.
	 * @param user
	 * @param evento
	 * @return El estado de la invitacion pedido
	 */
	public String obtenerEstadoInvitacion(UsuarioDTO user, EventoDTO evento) {
		String estado = "NO INVITADO";
		try {
			InvitacionDTO inv = invitacionDAO.find(user, evento);
			if (inv != null) {
				estado = inv.getEstado();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return estado;

	}
	
	/**
	 * Borra las invitaciones
	 * @param evento
	 * @return	Cons.SUCCESS en caso de exito
	 * 			Cons.ERR_DATABASE_PROBLEM en caso de error con la base de datos
	 */
	public int borrarInvitaciones(EventoDTO evento){
		try{
			List<UsuarioDTO> invitados = invitacionDAO.findInvitados(evento);
			for(UsuarioDTO u: invitados){
				InvitacionDTO i = invitacionDAO.find(u,evento);
				invitacionDAO.delete(i);
		}
			return Cons.SUCCESS;
		
		}
		catch(Exception ex){
			return Cons.ERR_DATABASE_PROBLEM;
		}
	}

}