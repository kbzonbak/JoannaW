package bbr.esb.web.ui;

import java.util.ArrayList;

import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.data.classes.ContractedServiceDataDTO;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class ResendWindow extends Window {
	
	private ServiceManagerLocal servicemanagerlocal = null;
	

	private CheckBox  numebrway;
	private TextArea orders;
	private CheckBox  stateway;
	private ComboBox statescombo;	

	private Button btnOk;
	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	public ResendWindow(String titulo,ContractedServiceDataDTO selectedContractedService) {	
		
		super(titulo);
		
		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();
		
		this.setModal(true);
		this.setWidth(152, Unit.MM);
		this.setHeight(97, Unit.MM);
		this.setWindowMode(WindowMode.NORMAL);
		this.setResizable(false);

		VerticalLayout mainlayout = new VerticalLayout();

		mainlayout.setWidth("100%");
		mainlayout.setMargin(true);
		mainlayout.setSpacing(true);
		
		numebrway = new CheckBox("Ordenes",true);
		stateway  = new CheckBox("Estado", false);

		ArrayList states = new ArrayList<String>();
		states.add("NOTIFICADO");
		states.add("ENVIADO");
		states.add("RECIBIDO_ERROR");
		
		statescombo = new ComboBox("", states);
		statescombo.setCaption(null);
		statescombo.setEnabled(false);
		statescombo.setNullSelectionAllowed(false);
		statescombo.setTextInputAllowed(false);		
		statescombo.setValue("NOTIFICADO");

		orders = new TextArea();
		orders.setWidth(147, Unit.MM);
		orders.setHeight(52 , Unit.MM);		
		
		numebrway.addValueChangeListener(event -> 	stateway.setValue(! numebrway.getValue()));		
		numebrway.addValueChangeListener(event -> 	orders.setEnabled(orders.isEnabled() ? true : false ));
		stateway.addValueChangeListener(event -> 	statescombo.setEnabled(statescombo.isEnabled() ? true : false ));

		stateway.addValueChangeListener(event ->	numebrway.setValue(! stateway.getValue()));	
		stateway.addValueChangeListener(event -> 	orders.setEnabled(orders.isEnabled() ? false : true ));		
		stateway.addValueChangeListener(event -> 	statescombo.setEnabled(statescombo.isEnabled() ? false : true ));				
		

		btnOk = new Button(" Enviar ");
		btnOk.addStyleName("commandButton");
		btnOk.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				String json="{\n";
//				validaciones al apretar en boton ok
				json+="\"orders\":";
				if(numebrway.getValue()){
					String[] orderArray =orders.getValue().split(",");					
					json +="[";  					
					for (int i = 0; i < orderArray.length; i++) {
						System.out.println(orderArray[i]);
						
						if(i+1==orderArray.length){
							json += " " +orderArray[i]+ "]";
							json+=",\n\"state\":null";		
						}else{
							json += " "+ orderArray[i] + " ,";
						}
					}
				}else if(stateway.getValue()){
					json+="null,\n\"state\":\"";
					json+=statescombo.getValue().toString();	
					json+="\"";
				}
				json+="\n}";
				
//					enviar a la base de datos
				try {
					servicemanagerlocal.doAddServiceEventByContracted(selectedContractedService, json);
				} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ResendWindow.this.close();
			}
		});
		
		HorizontalLayout hlayout = new HorizontalLayout();
		hlayout.addComponent(btnOk);
		hlayout.setWidth(105, Unit.MM);	
		
		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				ResendWindow.this.close();
			}
		});

		
		
		hlayout.addComponent(btnCancel);
		
		mainlayout.addComponent(numebrway);
		mainlayout.addComponent(orders);
		mainlayout.addComponent(stateway);
		mainlayout.addComponent(statescombo);
		mainlayout.addComponent(hlayout);
		
		
		
		this.setContent(mainlayout);

		this.addCloseListener(new CloseListener() {

			@Override
			public void windowClose(CloseEvent e) {
				// Si se cerró usando el botón propio de la ventana, se asume CANCEL
				if (dialogResult.equals(DialogResult.NONE))
					dialogResult = DialogResult.CANCEL;
			}
		});
		this.center();
	}
	
	

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
		
}
