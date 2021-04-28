package bbr.esb.services.data.interfaces;

import bbr.common.adtclasses.interfaces.IIdentifiable;

public interface ISiteService extends IIdentifiable {

	public boolean getActive();

	public void setActive(boolean active);

}
