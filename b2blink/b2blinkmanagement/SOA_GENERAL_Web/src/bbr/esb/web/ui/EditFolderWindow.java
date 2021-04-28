package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.server.ClientConnector.DetachEvent;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderAndFolderTypeDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class EditFolderWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	private Long companyid;

	private MessageFolderAndFolderTypeDTO folder;

	private TextField edPath;
	private TextField edUser;
	private TextField edPass;
	private TextField edHost;
	
	private ComboBox typecombo;

	private Button btnSave;
	private Button btnCancel;

	private DialogResult dialogResult = DialogResult.NONE;

	public EditFolderWindow(String caption, Long companyid, MessageFolderAndFolderTypeDTO folder) {
		super(caption);
		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();
		this.companyid = companyid;
		this.folder = folder;
		this.setModal(true);
		this.setWidth(12, Unit.CM);
		this.setHeight(9, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		GridLayout controlsLayout = new GridLayout(2, 6);
		controlsLayout.setColumnExpandRatio(0, 0.4f);
		controlsLayout.setColumnExpandRatio(1, 0.8f);

		controlsLayout.setWidth("100%");
		controlsLayout.setMargin(true);
		controlsLayout.setSpacing(true);

		controlsLayout.addComponent(new Label("Ruta : "), 0, 0);

		edPath = new TextField();
		edPath.setWidth(100F, Unit.PERCENTAGE);
		edPath.setValue(folder != null ? folder.getMf().getPath() : "");
		controlsLayout.addComponent(edPath, 1, 0);
		
		controlsLayout.addComponent(new Label("transporte: "), 0, 2);
		
		typecombo = new ComboBox();
		typecombo.setTextInputAllowed(false);		
		controlsLayout.addComponent(typecombo, 1, 2);
		
		typecombo.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
//				si es AS2 se borra lo que hay en las casillas de FTP y se inhabilitan
				if(((MessageFolderTypeDTO) typecombo.getValue()).getCode().equals("AS2")){
					edUser.setEnabled(false);
					edHost.setEnabled(false);
					edPass.setEnabled(false);					
					
					edUser.setValue("");
					edHost.setValue("");
					edPass.setValue("");
	
				}else{
					edUser.setEnabled(true);
					edHost.setEnabled(true);
					edPass.setEnabled(true);
				}
				
			}
		});
		
		controlsLayout.addComponent(new Label("Usuario : "), 0, 3);
		edUser = new TextField();
//		edUser.setWidth(100F, Unit.PERCENTAGE);
		edUser.setValue(folder != null ? folder.getFtp().getUsername() : "");
		controlsLayout.addComponent(edUser, 1, 3);
		
		controlsLayout.addComponent(new Label("Password: "), 0, 4);
		edPass = new TextField();
//		edPass.setWidth(100F, Unit.PERCENTAGE);
		edPass.setValue(folder != null ? folder.getFtp().getPassword() : "");
		controlsLayout.addComponent(edPass, 1, 4);		
		
		controlsLayout.addComponent(new Label("Host: "), 0, 5);
		edHost = new TextField();
		edHost.setWidth(100F, Unit.PERCENTAGE);
		edHost.setValue(folder != null ? folder.getFtp().getHost() : "");
		controlsLayout.addComponent(edHost, 1, 5);

		HorizontalLayout commandsLayout = new HorizontalLayout();
		commandsLayout.setSpacing(true);
		commandsLayout.setMargin(true);
		commandsLayout.setWidthUndefined();
		
		setFolderType();
		
		btnSave = new Button("Guardar");
		btnSave.addStyleName("commandButton");
		btnSave.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				
				FTPMessageFolderDTO ftpdto =  folder.getFtp();
				MessageFolderDTO mfdto = folder.getMf();
				MessageFolderTypeDTO mftdto = ((MessageFolderTypeDTO)typecombo.getValue());
				
				
				// VALIDAR LOS CAMPOS
				if (edPath.getValue().trim().isEmpty()) {
					Notification.show("La ruta es obligatoria", Type.ERROR_MESSAGE);
					return;
				}else if (((MessageFolderTypeDTO)typecombo.getValue()).getCode().trim().equals("AS2") ){					
					mfdto.setPath(edPath.getValue());	// PATH					
					mfdto.setCompanykey(companyid);		// COMPANY ID 			
					mfdto.setMessagefoldertypekey(((MessageFolderTypeDTO)typecombo.getValue()).getId()); //FOLDERTYPE_ID
					folder.setMf(mfdto);
					
				}else if(edUser.getValue().trim().isEmpty() || edPass.getValue().trim().isEmpty() || edHost.getValue().trim().isEmpty() ){
					Notification.show("Para medios de trasporte distintos de AS2 los campos de Usuario,Passowrd y Host son obligatorios", Type.ERROR_MESSAGE);					
					return;
					
				}else{		
					//-----MessageFolder----
					ftpdto.setPath(edPath.getValue());
					ftpdto.setCompanykey(companyid);
					ftpdto.setMessagefoldertypekey(((MessageFolderTypeDTO)typecombo.getValue()).getId());
					//----FTPMessageFolder---
					ftpdto.setHost(edHost.getValue());
					ftpdto.setUsername(edUser.getValue());
					ftpdto.setPassword(edPass.getValue());
					ftpdto.setProtocol(((MessageFolderTypeDTO)typecombo.getValue()).getCode());
					
					folder.setFtp(ftpdto);
				}			
				folder.setMft(mftdto);			
				EditFolderWindow.this.folder = folder;
				//	 Si todo está OK, cerrar la ventana
				dialogResult = DialogResult.OK;
				EditFolderWindow.this.close();
			}
		});

		commandsLayout.addComponent(btnSave);
		commandsLayout.setComponentAlignment(btnSave, Alignment.MIDDLE_LEFT);

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				EditFolderWindow.this.close();
			}
		});

		commandsLayout.addComponent(btnCancel);
		commandsLayout.setComponentAlignment(btnCancel, Alignment.MIDDLE_CENTER);
		commandsLayout.setSizeFull();
				
		VerticalLayout layout = new VerticalLayout();
		layout.addComponent(controlsLayout);
		layout.addComponent(commandsLayout);

		this.setContent(layout);

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
	
	private void setFolderType() {
		try {
			MessageFolderTypeDTO[] folderstype = servicemanagerlocal.getAllMessageFolderType();
			List<MessageFolderTypeDTO> folderstypeList = Arrays.asList(folderstype);
			BeanItemContainer<MessageFolderTypeDTO> container = new BeanItemContainer<>(MessageFolderTypeDTO.class, folderstypeList);
			typecombo.setContainerDataSource(container);
			typecombo.setItemCaptionPropertyId("code");
			typecombo.setNullSelectionAllowed(false);
			
			if(folderstype.length>0){			
			typecombo.setValue(folderstype[0]);
			}else{
				System.out.println("no hay tipos de carpeta registrados");
			}
			if(folder != null && folder.getMf().getId() != null){			
				Optional<MessageFolderTypeDTO> optFolderType = folderstypeList.stream().filter(foldertype -> folder.getMf().getMessagefoldertypekey().equals(foldertype.getId())).findFirst();
				if (optFolderType != null && optFolderType.isPresent()) {
					typecombo.select(optFolderType.get());
					typecombo.setEnabled(false);
				}
				if(((MessageFolderTypeDTO) typecombo.getValue()).getId().equals(1L)){
					edUser.setEnabled(false);
					edPass.setEnabled(false);
					edHost.setEnabled(false);
					edHost.setValue("");
					edPass.setValue("");
					edUser.setValue("");
				}
			}else{
				edPath.setValue("");
				edHost.setValue("");
				edPass.setValue("");
				edUser.setValue("");
			}
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
			e.printStackTrace();
		}
		
	}

	public MessageFolderAndFolderTypeDTO getMessageFolderAndFolderTypeDTO() {
		return folder;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}
}
