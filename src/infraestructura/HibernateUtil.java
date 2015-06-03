package infraestructura;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 * Singleton que proporciona instancia del SessionFactory.
 * Al ser pesado, interesa que solo se cree una vez.
 *
 */
public class HibernateUtil {
	
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Creat el  SessionFactory desde hibernate.cfg.xml
            return new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
           
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}