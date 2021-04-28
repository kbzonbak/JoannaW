package bbr.common.adtclasses.interfaces;

import java.util.List;

import bbr.common.adtclasses.classes.PropertyInfoDTO;

public interface IBaseServer {

	public void clear();

	public int executeUpdate(String querystr);

	public void flush();

	public List getResultList(int pagenumber, int rows, String querystr);

	public List getResultList(int pagenumber, int rows, String querystr, PropertyInfoDTO... infos);

	public List getResultList(String querystr);

	public List getResultList(String querystr, PropertyInfoDTO... infos);

	public Object getSingleResult(String querystr);

}
