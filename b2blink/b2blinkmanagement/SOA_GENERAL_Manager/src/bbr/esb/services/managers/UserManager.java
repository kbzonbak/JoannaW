package bbr.esb.services.managers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import bbr.common.adtclasses.classes.BaseResultDTO;
import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.events.facade.ServiceEventServerLocal;
import bbr.esb.services.data.classes.LogonResultDTO;
import bbr.esb.services.data.classes.QuestionChallengeResultDTO;
import bbr.esb.services.facade.ServiceServerLocal;
import bbr.esb.services.facade.SiteServerLocal;
import bbr.esb.services.facade.SiteServiceServerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.FunctionalityArrayResultDTO;
import bbr.esb.users.data.classes.FunctionalityDTO;
import bbr.esb.users.data.classes.UserCompanyDTO;
import bbr.esb.users.data.classes.UserDTO;
import bbr.esb.users.data.classes.UserTypeDTO;
import bbr.esb.users.entities.Functionality;
import bbr.esb.users.entities.UserType;
import bbr.esb.users.facade.AccessServerLocal;
import bbr.esb.users.facade.CompanyServerLocal;
import bbr.esb.users.facade.FunctionalityServerLocal;
import bbr.esb.users.facade.UserCompanyServerLocal;
import bbr.esb.users.facade.UserServerLocal;
import bbr.esb.users.facade.UserTypeServerLocal;
import bbr.esb.utils.CryptoUtils;
import bbr.esb.utils.Mailer;
import bbr.esb.utils.PasswordGenerator;
import bbr.esb.utils.StatusCodeUtils;

@Stateless(name = "managers/UserManager")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UserManager implements UserManagerLocal {

	@EJB
	CompanyServerLocal companyserver;

	@EJB
	ServiceServerLocal serviceserver;

	@EJB
	ServiceEventServerLocal serviceeventserver;

	@EJB
	SiteServerLocal siteserver;

	@EJB
	SiteServiceServerLocal siteserviceserver;

	@EJB
	AccessServerLocal siteuserserver;

	@EJB
	UserCompanyServerLocal usercompanyserver;

	@EJB
	UserServerLocal userserver;

	@EJB
	UserTypeServerLocal usertypeserver;

	@EJB
	FunctionalityServerLocal functionalityserver;

	public CompanyDTO addCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.addCompany(company);
	}

	@Override
	public UserDTO addUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Setear valores por defecto
		Date now = new Date();
		user.setCheckvolatile(0);
		user.setLastaccess(now);
		user.setLastlogon(now);
		user.setSessionid("firstsession");
		user.setTriescount(0);
		return userserver.addUser(user);
	}

	public UserCompanyDTO addUserCompany(UserCompanyDTO usercompany) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return usercompanyserver.addUserCompany(usercompany);
	}

	@Override
	public void deleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		userserver.deleteUser(userid);
	}

	public void deleteUserCompany(Long userkey, Long companykey) throws OperationFailedException, NotFoundException {
		usercompanyserver.deleteUserCompany(userkey, companykey);
	}

	public UserDTO doAddUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		// Setear valores por defecto
		Date now = new Date();
		user.setPassword("123qwe");
		user.setCheckvolatile(0);
		user.setLastaccess(now);
		user.setLastlogon(now);
		user.setSessionid("firstsession");
		user.setTriescount(0);
		
		user = userserver.addUser(user);
		// Agregar las compañias al usuario
		for (int i = 0; i < companies.length; i++) {
			Long companyid = companies[i];
			CompanyDTO company = companyserver.getById(companyid);
			UserCompanyDTO usercompany = new UserCompanyDTO();
			usercompany.setUserkey(user.getId());
			usercompany.setCompanykey(company.getId());
			usercompanyserver.addUserCompany(usercompany);
		}
		return user;
	}

	public BaseResultDTO doChangePassword(String logid, String oldpassword, String newpassword) {
		// TODO DVI TEST
		BaseResultDTO result = new BaseResultDTO();
		try {
			// Buscar el usuario por logid
			Boolean isnormalized = System.getProperty("clavenormalizada").equals("1");
			Boolean ishashed = System.getProperty("claveencriptada").equals("1");
			// Buscar usuario por logid (normalizar si requerido)
			logid = isnormalized ? logid.toUpperCase() : logid;
			List<UserDTO> listUser = userserver.getByProperty("rut", logid);
			if (listUser == null || listUser.isEmpty()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "100");
				return result;
			}
			// Si el usuario existe, validar la password
			UserDTO user = listUser.get(0);
			// Verificar password antigua
			// Si las passwords son normalizadas, cambiar a uppercase
			// Si las passwords están encriptadas, aplicar funcion hash
			oldpassword = isnormalized ? oldpassword.toUpperCase() : oldpassword;
			oldpassword = ishashed ? CryptoUtils.encodeMD5(oldpassword) : oldpassword;

			if (!user.getPassword().equals(oldpassword)) {
				StatusCodeUtils.getInstance().setStatusCode(result, "100");
				return result;
			}
			// Cambiar la password, resetear reintentos y devolver OK
			newpassword = isnormalized ? newpassword.toUpperCase() : newpassword;
			newpassword = ishashed ? CryptoUtils.encodeMD5(newpassword) : newpassword;
			user.setPassword(newpassword);
			user.setCheckvolatile(0);
			user.setTriescount(0);
			userserver.updateUser(user);
			StatusCodeUtils.getInstance().setStatusCode(result, "0");
			return result;
		} catch (AccessDeniedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public BaseResultDTO doChangeQuestionAndAnswerChallenge(Integer userid, String password, String newquestion, String newanswer) {
		// TODO DVI IMPLEMENTAR
		return null;
	}

	public void doDeleteUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// Borrar las compañias asociadas
		List<UserCompanyDTO> listUC = usercompanyserver.getByProperty("user.id", userid);
		for (Iterator<UserCompanyDTO> iterator = listUC.iterator(); iterator.hasNext();) {
			UserCompanyDTO uc = iterator.next();
			usercompanyserver.deleteUserCompany(uc.getUserkey(), uc.getCompanykey());
		}
		// Borrar el usuario
		userserver.deleteUser(userid);
	}

	public LogonResultDTO doLogonUser(String logid, String password) {
		// TODO DVI TEST9
		LogonResultDTO result = new LogonResultDTO();
		try {
			Boolean isnormalized = System.getProperty("clavenormalizada").equals("1");
			Boolean ishashed = System.getProperty("claveencriptada").equals("1");
			// Buscar usuario por logid (normalizar si requerido)
			logid = isnormalized ? logid.toUpperCase() : logid;
			List<UserDTO> listUser = userserver.getByProperty("rut", logid);
			if (listUser == null || listUser.isEmpty()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "100");
				return result;
			}
			// Si el usuario existe, validar la password
			UserDTO user = listUser.get(0);
			// Si las passwords son normalizadas, cambiar a uppercase
			// Si las passwords están encriptadas, aplicar funcion hash
			password = isnormalized ? password.toUpperCase() : password;
			password = ishashed ? CryptoUtils.encodeMD5(password) : password;

			if (!user.getPassword().equals(password)) {
				// Incrementar el conteo de intentos erróneos
				user.setTriescount(user.getTriescount().intValue() + 1);
				// Si se supera el máximo de intentos, bloquear al usuario
				Integer maxintentos = new Integer(System.getProperty("intentos"));
				if (user.getTriescount().intValue() >= maxintentos.intValue()) {
					user.setBlocked(true);
					user = userserver.updateUser(user);
					StatusCodeUtils.getInstance().setStatusCode(result, "200");
					return result;
				} else {
					user = userserver.updateUser(user);
					StatusCodeUtils.getInstance().setStatusCode(result, "100");
					return result;
				}
			}
			// Si la clave es volatil (2), rechazar login, informando que debe realizar cambio de clave
			// Si la clave es volatil y expirada (1), bloquear el usuario
			if (user.getCheckvolatile().intValue() == 2) {
				user.setCheckvolatile(1);
				user = userserver.updateUser(user);
				StatusCodeUtils.getInstance().setStatusCode(result, "300");
				user.setSessionid(null);
				result.setUser(user);
				return result;
			} else if (user.getCheckvolatile().intValue() == 1) {
				user.setBlocked(true);
				user = userserver.updateUser(user);
				StatusCodeUtils.getInstance().setStatusCode(result, "200");
				return result;
			}
			// Validar que el usuario esté desbloqueado
			if (user.getBlocked() != null && user.getBlocked().booleanValue()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "200");
				return result;
			}
			// Reiniciar conteo y Generar la llave de sesión y actualizar el usuario
			String sessionkey = CryptoUtils.generateSessionKey();
			user.setTriescount(0);
			user.setSessionid(sessionkey);
			user.setLastlogon(new Date());
			user.setLastaccess(new Date());
			user = userserver.updateUser(user);
			StatusCodeUtils.getInstance().setStatusCode(result, "0");
			result.setUser(user);
			return result;
		} catch (AccessDeniedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public UserDTO doUpdateUser(UserDTO user, Long[] companies) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		// Actualizar solo los campos permitidos
		UserDTO olduser = userserver.getById(user.getId());
		olduser.setName(user.getName());
		olduser.setLastname(user.getLastname());
		olduser.setEmail(user.getEmail());
		olduser.setUsertypeid(user.getUsertypeid());
		user = userserver.updateUser(olduser);
		// Agregar/quitar las compañias al usuario
		List<UserCompanyDTO> listUC = usercompanyserver.getByProperty("user.id", user.getId());
		for (Iterator<UserCompanyDTO> iterator = listUC.iterator(); iterator.hasNext();) {
			UserCompanyDTO uc = iterator.next();
			usercompanyserver.deleteUserCompany(uc.getUserkey(), uc.getCompanykey());
		}
		for (int i = 0; i < companies.length; i++) {
			Long companyid = companies[i];
			CompanyDTO company = companyserver.getById(companyid);
			UserCompanyDTO usercompany = new UserCompanyDTO();
			usercompany.setUserkey(user.getId());
			usercompany.setCompanykey(company.getId());
			usercompanyserver.addUserCompany(usercompany);
		}
		return user;
	}

	public BaseResultDTO doValidateQuestionChallengeAnswer(String logid, String answer, boolean newpassword) {
		// TODO DVI TEST
		BaseResultDTO result = new LogonResultDTO();
		try {
			Boolean isnormalized = System.getProperty("clavenormalizada").equals("1");
			// Buscar usuario por logid (normalizar si requerido)
			logid = isnormalized ? logid.toUpperCase() : logid;
			List<UserDTO> listUser = userserver.getByProperty("rut", logid);
			if (listUser == null || listUser.isEmpty()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "100");
				return result;
			}
			// Si el usuario existe, validar la respuesta a la pregunta secreta
			UserDTO user = listUser.get(0);
			// Si no existe pregunta secreta, lanzar error
			if (user.getQuestion() == null || user.getQuestion().trim().length() == 0) {
				StatusCodeUtils.getInstance().setStatusCode(result, "102");
				return result;
			}
			// Si las passwords son normalizadas, cambiar a uppercase
			answer = isnormalized ? answer.toUpperCase() : answer;

			// Si la respuesta es incorrecta lanzar error
			if (!user.getAnswer().equals(answer)) {
				StatusCodeUtils.getInstance().setStatusCode(result, "103");
				return result;
			}
			// Si la respuesta es correcta, desbloquear el usuario o generar clave temporal
			// Si la clave es volatil (2), rechazar login, informando que debe realizar cambio de clave
			// Si la clave es volatil y expirada (1), bloquear el usuario
			if (newpassword) {
				// Generar clave temporal y enviarla por mail
				String tmp_pwd = PasswordGenerator.getAlphaNumericPassword(8);
				tmp_pwd = isnormalized ? tmp_pwd.toUpperCase() : tmp_pwd;
				user.setPassword(tmp_pwd);
				String[] to = new String[1];
				to[0] = user.getEmail();
				String from = System.getProperty("mailsender");
				String text = "Estimado(a) cliente: \n\nSe ha generado una nueva clave temporal para acceder al sitio: " + tmp_pwd + "\n\nAtte.\nCIMSA";
				// public void sendMailBySession(String[] to, String[] bcc, String from, String subject, String text,
				// String mailSession) throws OperationFailedException {
				Mailer.getInstance().sendMailBySession(to, new String[0], from, "Recuperacion de clave", text, "");
				user.setBlocked(false);
				user.setTriescount(0);
				user.setCheckvolatile(2);
				user = userserver.updateUser(user);
				StatusCodeUtils.getInstance().setStatusCode(result, "104");
			} else {
				// Desbloquear clave temporal
				user.setBlocked(false);
				user.setTriescount(0);
				user = userserver.updateUser(user);
				StatusCodeUtils.getInstance().setStatusCode(result, "105");
			}
			return result;
		} catch (AccessDeniedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public UserDTO doValidateUserSession(String logid, String sessionid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		List<UserDTO> listUser = userserver.getByProperty("rut", logid);
		if (listUser == null || listUser.isEmpty())
			throw new NotFoundException("Usuario no encontrado");
		// Si el usuario existe, validar la sesión
		UserDTO user = listUser.get(0);
		if (!user.getSessionid().equals(sessionid))
			throw new AccessDeniedException("Sesión inválida");
		// Validar expiración de sesión
		Calendar caldate = Calendar.getInstance();
		caldate.add(Calendar.MINUTE, -30);
		Date date = caldate.getTime();
		if (user.getLastaccess() == null || user.getLastaccess().before(date))
			throw new AccessDeniedException("La sesión ha expirado");
		user.setLastaccess(new Date());
		user = userserver.updateUser(user);
		return user;
	}

	public CompanyDTO[] getCompanies() throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		CompanyDTO[] result = companyserver.getAllAsArray();
		return result;
	}

	public CompanyDTO[] getCompaniesByRut(String rut) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.getByPropertyAsArray("rut", rut);
	}

	public CompanyDTO[] getCompaniesLikeName(String name) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.getLikeStringPropertyAsArray("name", name);
	}

	public CompanyDTO[] getCompaniesofUser(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		// TODO DVI TEST
		CompanyDTO[] result = companyserver.getCompaniesofUser(userid);
		return result;
	}

	public FunctionalityArrayResultDTO getFunctionalitiesByUserType(Long usertypeid) {
		// TODO DVI TEST
		FunctionalityArrayResultDTO result = new FunctionalityArrayResultDTO();
		try {
			UserType usertype = usertypeserver.findById(usertypeid);
			List<Functionality> listFuncs = new ArrayList<Functionality>(usertype.getFunctionalitites());
			List<FunctionalityDTO> functionalitiesList = functionalityserver.getWrappersByEntities(listFuncs);
			result.setFunctionalities(functionalitiesList.toArray(new FunctionalityDTO[functionalitiesList.size()]));
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public QuestionChallengeResultDTO getQuestionChallengeByUser(String logid) {
		// TODO DVI TEST
		QuestionChallengeResultDTO result = new QuestionChallengeResultDTO();
		// Buscar el usuario por logid
		try {
			Boolean isnormalized = System.getProperty("clavenormalizada").equals("1");
			// Buscar usuario por logid (normalizar si requerido)
			logid = isnormalized ? logid.toUpperCase() : logid;
			List<UserDTO> listUser = userserver.getByProperty("rut", logid);
			if (listUser == null || listUser.isEmpty()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "101");
				return result;
			}
			// Si el usuario existe, validar la password
			UserDTO user = listUser.get(0);
			result.setQuestion(user.getQuestion());
			StatusCodeUtils.getInstance().setStatusCode(result, "0");
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public UserDTO getUserByPK(Long userid) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return userserver.getUserByPK(userid);
	}

	public LogonResultDTO getUserBySession(String sessionid) {
		// TODO DVI TEST
		LogonResultDTO result = new LogonResultDTO();
		try {
			List<UserDTO> listUser = userserver.getByProperty("session", sessionid);
			if (listUser == null || listUser.isEmpty()) {
				StatusCodeUtils.getInstance().setStatusCode(result, "100");
				return result;
			}
			// Si el usuario existe, validar la password
			UserDTO user = listUser.get(0);
			result.setUser(user);
			return result;
		} catch (OperationFailedException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		} catch (NotFoundException e) {
			StatusCodeUtils.getInstance().setStatusCode(result, "400", e.getMessage());
			e.printStackTrace();
			return result;
		}
	}

	public UserDTO[] getUsers() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return userserver.getAllAsArray();
	}

	public UserTypeDTO[] getUserTypes() throws AccessDeniedException, OperationFailedException, NotFoundException {
		return usertypeserver.getAllAsArray();
	}

	public CompanyDTO updateCompany(CompanyDTO company) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return companyserver.updateCompany(company);
	}

	public UserDTO updateUser(UserDTO user) throws AccessDeniedException, OperationFailedException, NotFoundException {
		return userserver.updateUser(user);
	}

}
