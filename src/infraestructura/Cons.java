package infraestructura;

/**
 * Clase con valores comunes a todas las capas
 *
 */
public class Cons {
	
		//Códigos de retorno
		public final static int SUCCESS= 0;
		public final static int ERR_MAIL_REPEATED = -1;
		public final static int ERR_NICK_REPEATED = -2;
		public final static int  ERR_NICK_MAIL_REPEATED = -3;
		public final static int ERR_INVALID_LOGIN = -4;
		public final static int ERR_INVALID_USER = -5;
		public final static int ERR_DATABASE_PROBLEM = -6;
		public final static int ERR_INVALID_PARAM = -7;
		public final static int ERR_INVITATION_REPEATED = -8;
		public final static int ERR_PERMISSION_DENIED = -9;
		
		
		//Modos de visualizacion
		public final static int MODE_VIEW_ALL_EVENTS = 11;
		public final static int MODE_VIEW_PAST_EVENTS = 12;
		public final static int MODE_VIEW_FUTURE_EVENTS = 13;
		public final static int MODE_VIEW_OWN_EVENTS = 14;
		public final static int MODE_VIEW_DECLINED_EVENTS = 15;
		public final static int MODE_NOT_SPECIFIED = 16;
}
