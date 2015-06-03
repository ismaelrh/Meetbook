package infraestructura;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que ofrece operaciones básicas con la base de datos
 * relacionadas con usuarios.
 * Ojo, es un Singleton, para asi tener solo una conexion.
 * NO VA CON HIBERNATE.
 * Tipo DAO.
 */
public class MensajeDAO {
	Connection connection ;
	//No se va a usar Hibernate para esto... esta claro...
	// Variables para conectarse a la base de datos
		private String db_driver;
		private String db_username;
		private String db_password;

		// Datos del servidor
		private final String DRIVER = "jdbc:mysql://192.168.56.2:3306/meetbook";
		private final String USERNAME = "root";
		private final String PASSWORD = "basesdepatos";
		
		
		private static MensajeDAO single;
		/**
		 * Pre: Cierto
		 * Post: Construye con los atributos definidos un objeto que permite 
		 *       acceder a la bd de la aplicacion.
		 */
		private MensajeDAO() {
			db_driver = DRIVER;
			db_username = USERNAME;
			db_password = PASSWORD;

			if (db_driver.contains("mysql")) {
				try {
					Class.forName("com.mysql.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					System.err.println("Mysql driver not found");
					e.printStackTrace();
				}
			}
			
			try {
				connection = DriverManager.getConnection(db_driver,
					db_username, db_password);
			}
			catch(Exception ex){
				ex.printStackTrace();
			}
		}

		public static MensajeDAO getInstance(){
			if(single==null){
				single = new MensajeDAO();
			}
			return single;
		}
		
	
	/**
	 * Obtiene los <limit> ultimos mensajes del chat de un evento
	 * @param id del evento
	 */
	public List<MensajeDTO> findLast(int idEvento, int limit) throws SQLException{
		
		
		
		
		String queryString = "SELECT * from mensaje where idEvento = ? order by idMensaje DESC;";

		 PreparedStatement stmt = connection.prepareStatement(queryString);
		 stmt.setInt(1, idEvento);
		 stmt.setMaxRows(limit);
		
		
		 ResultSet result = stmt.executeQuery();
		
		ArrayList<MensajeDTO> lista = new ArrayList<>();
		while(result.next()){
			int idEvento_ = result.getInt("idEvento");
			int idMensaje = result.getInt("idMensaje");
			String creador = result.getString("creador");
			String contenido = result.getString("contenido");
			Timestamp fecha = result.getTimestamp("fecha");
			MensajeDTO m = new MensajeDTO();
			m.setRemitente(creador);
			m.setContenido(contenido);
			m.setFecha(fecha);
			lista.add(m);
		}
		ArrayList<MensajeDTO> listaInversa = new ArrayList<>();
		for(int i = lista.size() -1; i >=0; i--){
			listaInversa.add(lista.get(i));
		}
		stmt.close();
		
		return listaInversa;
	}
		
	/**
	 * Obtiene todos los  mensajes del chat de un evento
	 * @param id del evento
	 */
	public List<MensajeDTO> find(int idEvento) throws SQLException{
		
		
		String queryString = "SELECT * from mensaje where idEvento = ? order by idMensaje ASC";

		 PreparedStatement stmt = connection.prepareStatement(queryString);
		 stmt.setInt(1, idEvento);
		
		
		
		 ResultSet result = stmt.executeQuery();
		result.next();
		ArrayList<MensajeDTO> lista = new ArrayList<>();
		while(result.next()){
			int idEvento_ = result.getInt("idEvento");
			int idMensaje = result.getInt("idMensaje");
			String creador = result.getString("creador");
			String contenido = result.getString("contenido");
			Timestamp fecha = result.getTimestamp("fecha");
			MensajeDTO m = new MensajeDTO();
			m.setRemitente(creador);
			m.setContenido(contenido);
			m.setFecha(fecha);
			lista.add(m);
		}
		
		stmt.close();
		
		return lista;
	}
	
	/**
	 * Guarda en la base de datos un mensaje de chat
	 * @param m el mensaje a guardar
	 * @param idEvento es el evento al que corresponde el mensaje
	 * @throws SQLException
	 */
	public void guardarMensaje(MensajeDTO m, int idEvento) throws SQLException{
		
		
		
		 PreparedStatement stmt = connection.prepareStatement("INSERT INTO mensaje(idEvento,creador,contenido,fecha) VALUES (?,?,?,?);");
		
		
		 stmt.setInt(1, idEvento);
		 stmt.setString(2,m.getRemitente());
		 stmt.setString(3, m.getContenido());
		 stmt.setTimestamp(4, new java.sql.Timestamp(m.getFecha().getTime()));
		
		 stmt.execute();
		 stmt.close();
		 

	}
	
	public static void main(String[] args){
		
		
		
	}
	
}
