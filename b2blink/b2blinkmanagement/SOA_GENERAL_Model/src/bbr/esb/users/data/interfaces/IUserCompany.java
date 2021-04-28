package bbr.esb.users.data.interfaces;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public interface IUserCompany extends IIdentifiable {

	public boolean getActive();

	public void setActive(boolean active);

}
