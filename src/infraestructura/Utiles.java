package infraestructura;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Clase que contiene métodos útiles
 * para toda la aplicación.
 *
 */
public class Utiles {

	/** Genera un Hash para la contraseña pasada.
	 * Fuente del método:  http://viralpatel.net/blogs/java-md5-hashing-salting-password/
	 */
	public static String generateHash(String input) {
        
        String md5 = null;
         
        if(null == input) return null;
         
        try {
             
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
         
        //Update input string in message digest
        
        digest.update(input.getBytes(), 0, input.length());
 
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
        }
        return md5;
    }
	
}
