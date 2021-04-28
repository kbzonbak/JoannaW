package bbr.b2b.portal.classes.utils.app;

/**
* En esta clase se definen los roles de keycloak que pueden tener asignados los usuarios	
*/
public enum RoleTypes {

	BBR("bbr"), 
	RETAIL("retail");
	
	private String role;
	
	RoleTypes(String role){
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
