package bbr.esb.web.resources;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import bbr.esb.web.constants.BbrAppConstants;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class LDAPUtils {

	private static LDAPUtils _instance;

	private Hashtable env = null;

	private String ldapUrl = "";

	private String ldapSystemDN = "";

	private String ldapSystemPass = "";

	private String ldapBase = "";

	private LDAPUtils() {
		setProperties();
	}

	public static LDAPUtils getInstance() {
		if (_instance == null)
			_instance = new LDAPUtils();

		return _instance;
	}

	private void setProperties() {

		// VARIABLES DE ENTORNO
		ldapUrl = System.getProperty("ldap.url");
		ldapSystemDN = System.getProperty("ldap.system.dn");
		ldapSystemPass = System.getProperty("ldap.system.pass");
		ldapBase = System.getProperty("ldap.base");

		// VARIABLES DE CONEXION A IPA
		env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(Context.SECURITY_PRINCIPAL, ldapSystemDN);
		env.put(Context.SECURITY_CREDENTIALS, ldapSystemPass);
	}

	public LDAPLoginResultDTO doLogin(String userName, String pass) {
		LDAPLoginResultDTO result = new LDAPLoginResultDTO();

		DirContext ctx = null;

		try {

			ctx = new InitialDirContext(env);

			// UNA VEZ REALIZADA LA CONEXIÓN, DEBE CONSULTAR POR EL USUARIO
			// SE AGREGA LA VALIDACION QUE NO TENGA PASSWORD EXPIRADA, ES DECIR SIGA ACTIVO

			// SE FIJAN LOS ATRIBUTOS DE RETORNO DE LA CONSULTA

			// NOMBRE DE USUARIO
			String attribute1 = "uid";
			// FECHA DE EXPIRACION DE CLAVE
			String attribute2 = "krbpasswordexpiration";
			// ULTIMA VEZ QUE CAMBIO CLAVE
			String attribute3 = "krblastpwdchange";

			// SE DEFINE UN CONTROLADOR DE LA BUSQUEDA CON LOS PARAMETROS DE RETORNO
			String[] attributeFilter = { attribute1, attribute2, attribute3 };
			SearchControls sc = new SearchControls();
			sc.setReturningAttributes(attributeFilter);
			sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

			// FORMATO DE LA FECHA EN QUE DEBE BUSCAR
			DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

			Date now = new Date();
			String date = df.format(now) + "Z";

			// SE DEFINE EL FILTRO DE BUSQUEDA
			String uidFilter = "(" + attribute1 + "=" + userName + ")";
			String expirationFilter = "(" + attribute2 + ">=" + date + ")";

			String searchFilter = "(&" + uidFilter + expirationFilter + ")";

			NamingEnumeration<SearchResult> resultsSearch = ctx.search(ldapBase, searchFilter, sc);

			if (resultsSearch.hasMore()) {
				// SI EXISTE, AHORA TOCA COMPROBAR QUE LA PASSWORD ES LA INDICADA
				SearchResult resultSearch = resultsSearch.next();
				String distinguishedName = resultSearch.getNameInNamespace();

				try {
					// Validar contraseña

					Properties authEnv = new Properties();
					authEnv.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
					authEnv.put(Context.PROVIDER_URL, ldapUrl);
					authEnv.put(Context.SECURITY_PRINCIPAL, distinguishedName);
					authEnv.put(Context.SECURITY_CREDENTIALS, pass);
					InitialDirContext context = new InitialDirContext(authEnv);
					System.out.println(context);

					// Validar que el usuario pertenezca al grupo "b2b_profiles"

					String searchFilterGroup = "(memberOf=cn=" + BbrAppConstants.LDAP_GROUP_NAME + ",cn=groups,cn=accounts,dc=bbr,dc=cl)";
					searchFilter = "(&" + uidFilter + searchFilterGroup + ")";

					NamingEnumeration<SearchResult> resultsSearchGroup = ctx.search(ldapBase, searchFilter, sc);

					if (resultsSearchGroup.hasMore()) {
						// SI EXISTE, ENTONCES ESTÁ ASOCIADO AL GRUPO
						// SearchResult resultSearchGroup = resultsSearchGroup.next();
						// String distinguishedNameGroup = resultSearchGroup.getNameInNamespace();
						// System.out.println(distinguishedNameGroup);
						result.setValid(true);
					}

				} catch (Exception e) {
					// e.printStackTrace();
					// CONTRASEÑA INVÁLIDA
					result.setStatuscode("I300");
				}

			} else {
				// NO EXISTE EL USUARIO O ESTA INACTIVO
				result.setStatuscode("I200");
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// ERROR DE CONEXION CON IPA
			result.setStatuscode("I100");
		} finally {
			try {
				if (ctx != null)
					ctx.close();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}
}
