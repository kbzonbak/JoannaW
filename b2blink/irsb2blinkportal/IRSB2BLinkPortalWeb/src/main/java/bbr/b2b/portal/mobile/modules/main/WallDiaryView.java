//package bbr.b2b.portal.mobile.modules.main;
//
//
//import com.vaadin.addon.touchkit.ui.NavigationView;
//import com.vaadin.ui.Label;
//
//import bbr.b2b.portal.classes.beans.SessionBean;
//
//public class WallDiaryView extends NavigationView {
//
//	private static final long serialVersionUID = 1427925439133811407L;
//	private SessionBean bean = null;
//	public WallDiaryView() 
//	{
//		this("", null);
//	}
//
//	public WallDiaryView(String caption, SessionBean bean) 
//	{
//		super(caption);
//
//		this.bean = bean;
//		this.initializeView();
//	}
//	
//	private void initializeView()
//	{
////		String url = "https://sense-demo.qlik.com/sense/app/b37ed60f-7a8b-4dfe-a838-1ebf2019794f/sheet/MkWRnL/state/analysis";
////		Resource resourse = null ;
////		VerticalLayout mainLayout = null;
////		try 
////		{
////			
////			URL externalURL = new URL(url);
////			resourse = new ExternalResource(externalURL);
////			BrowserFrame iframe = new BrowserFrame(null, resourse);
////			iframe.setSizeFull();
////			mainLayout = new VerticalLayout(iframe);
////			mainLayout.setSizeFull();
////		} 
////		catch (MalformedURLException e) 
////		{
////			e.printStackTrace();
////		}
//		
//		this.setContent(new Label(this.bean.getUserFullName()));
//	}
//}
