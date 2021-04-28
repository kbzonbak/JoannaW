//package bbr.b2b.portal.mobile.modules.main;
//
//import java.io.Serializable;
//
//import com.vaadin.addon.touchkit.ui.NavigationView;
//import com.vaadin.server.FontAwesome;
//import com.vaadin.ui.Button;
//import com.vaadin.ui.Button.ClickEvent;
//import com.vaadin.ui.Button.ClickListener;
//import com.vaadin.ui.Component;
//
//import bbr.b2b.portal.mobile.components.login.ViewMoreInformation;
//
//public class MainView extends NavigationView {
//
//	private static final long serialVersionUID = 1427925439133811407L;
//
//	public MainView(Component component) 
//	{
//		super(component);
//		this.initializeView();		
//	}
//	
//	private void initializeView()
//	{
//		Button btnRight = new Button(FontAwesome.QUESTION);
//		btnRight.addClickListener((ClickListener & Serializable)e -> right_clickListener(e)); 
//		this.setRightComponent(btnRight);
//		this.setCaption("Inicio");
//	}
//
//	private void right_clickListener(ClickEvent e) 
//	{
//		this.getNavigationManager().navigateTo(new ViewMoreInformation());
//	}
//}
