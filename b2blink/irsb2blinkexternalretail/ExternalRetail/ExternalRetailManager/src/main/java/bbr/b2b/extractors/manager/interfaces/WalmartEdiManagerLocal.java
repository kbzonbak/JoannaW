package bbr.b2b.extractors.manager.interfaces;

import javax.ejb.Local;

@Local
public interface WalmartEdiManagerLocal {
	
	public void EdiToOc(String message);

}
