package bbr.esb.web.ui;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.vaadin.dialogs.ConfirmDialog;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.shared.ui.window.WindowMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

import bbr.common.adtclasses.exceptions.AccessDeniedException;
import bbr.common.adtclasses.exceptions.NotFoundException;
import bbr.common.adtclasses.exceptions.OperationFailedException;
import bbr.esb.services.managers.ServiceManagerLocal;
import bbr.esb.users.data.classes.CompanyDTO;
import bbr.esb.users.data.classes.FTPMessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderAndFolderTypeDTO;
import bbr.esb.users.data.classes.MessageFolderDTO;
import bbr.esb.users.data.classes.MessageFolderTypeDTO;
import bbr.esb.web.DialogResult;
import bbr.esb.web.resources.EJBUtils;

@SuppressWarnings("serial")
public class FolderListWindow extends Window {

	private ServiceManagerLocal servicemanagerlocal = null;

	protected Grid folderTable;

	private CompanyDTO company;

	private Button btnAddFolder;

	private Button btnEditFolder;

	private Button btnDeleteFolder;

	private Button btnCancel;

	protected MessageFolderDTO  selectedMessageFolderFolder;
	
	protected Long selectedid;
	
	private DialogResult dialogResult = DialogResult.NONE;

	public FolderListWindow(String caption, CompanyDTO company) {
		super(caption);

		servicemanagerlocal = EJBUtils.getInstance().getServiceManagerLocalEJB();

		this.company = company;
		this.setModal(true);
		this.setWidth(20, Unit.CM);
		this.setHeight(8, Unit.CM);
		this.setWindowMode(WindowMode.NORMAL);

		VerticalSplitPanel layout = new VerticalSplitPanel();

		// Panel Principal : Grid
		Component gridPanel = createGridPanel();

		// Panel Inferior : Botones
		Component commandPanel = createCommandPanel();

		layout.setFirstComponent(gridPanel);
		layout.setSecondComponent(commandPanel);
		layout.setSplitPosition(80);

		this.setContent(layout);

		doShowData();

		this.center();
	}

	protected Component createCommandPanel() {

		Panel panel = new Panel();

		final HorizontalLayout commandLayout = new HorizontalLayout();
		commandLayout.setMargin(true);
		commandLayout.setSizeFull();

		btnAddFolder = new Button("Agregar Casilla");
		btnAddFolder.addStyleName("commandButton");
		btnAddFolder.addClickListener(e -> doAddFolder());

		btnEditFolder = new Button("Editar Casilla");
		btnEditFolder.addStyleName("commandButton");
		btnEditFolder.addClickListener(e -> doEditFolder());

		btnDeleteFolder = new Button("Borrar Casilla");
		btnDeleteFolder.addStyleName("commandButton");
		btnDeleteFolder.addClickListener(e -> doDeleteFolder());

		btnCancel = new Button("Cancelar");
		btnCancel.addStyleName("commandButton");
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				dialogResult = DialogResult.CANCEL;
				FolderListWindow.this.close();
			}
		});

		commandLayout.addComponent(btnAddFolder);
		commandLayout.addComponent(btnEditFolder);
		commandLayout.addComponent(btnDeleteFolder);
		commandLayout.addComponent(btnCancel);

		panel.setContent(commandLayout);
		panel.setSizeFull();
		return panel;
	}

	private Component createGridPanel() {
		Panel panel = new Panel();
		HorizontalLayout layout = new HorizontalLayout();

		folderTable = new Grid("");

		folderTable.setContainerDataSource(new BeanItemContainer<>(MessageFolderDTO.class));
		folderTable.removeColumn("companykey");
		folderTable.removeColumn("id");
		folderTable.getColumn("path").setHeaderCaption("Ruta");
		folderTable.getColumn("messagefoldertypekey").setHeaderCaption("Tipo");
		folderTable.setSizeFull();

		folderTable.addItemClickListener(new ItemClickListener() {

			@Override
			public void itemClick(ItemClickEvent event) {
				// El ID del item corresponde a la llave de la empresa
				if (event.getItemId() != null) {					
					selectedMessageFolderFolder =(MessageFolderDTO) event.getItemId();
					selectedid = selectedMessageFolderFolder.getId();
				}
			}
		});

		layout.addComponent(folderTable);
		layout.setSizeFull();
		panel.setContent(layout);
		panel.setSizeFull();
		return panel;
	}

	private void doAddFolder() {
			
		try {
			MessageFolderAndFolderTypeDTO selectedFolderandtype; //DTO general		
			selectedFolderandtype = new MessageFolderAndFolderTypeDTO(); 	
			
			FTPMessageFolderDTO ftp = new FTPMessageFolderDTO();
			MessageFolderDTO mf = new MessageFolderDTO();
//			MessageFolderTypeDTO ft = new MessageFolderTypeDTO();
			
//			selectedFolderandtype.setMessagefoldertypedto(ft);
			selectedFolderandtype.setFtp(ftp);
			selectedFolderandtype.setMf(mf);
			
			EditFolderWindow window = new EditFolderWindow("Agregar Casilla", company.getId(), selectedFolderandtype);
			window.center();
			FolderListWindow.this.getUI().addWindow(window);
			window.addCloseListener(new CloseListener() {

				@Override
				public void windowClose(CloseEvent e) {
					if (window.getDialogResult().equals(DialogResult.OK)) {
						// Obtener el dato y actualizarlo en la BD
						MessageFolderAndFolderTypeDTO newFolderAndType = window.getMessageFolderAndFolderTypeDTO();
						try {
							
							if(newFolderAndType.getMft().getCode().equals("AS2")){
								servicemanagerlocal.addMessageFolder(newFolderAndType.getMf());
							}else{
								servicemanagerlocal.addFTPMessageFolder(newFolderAndType.getFtp());
							}
							doShowData();
//							selectedFolder = null;
						} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
							e1.printStackTrace();
						}
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void doEditFolder() {
		
		if (selectedMessageFolderFolder == null)
			return;
		MessageFolderAndFolderTypeDTO selectedFolderandtype; //DTO general		
		selectedFolderandtype = new MessageFolderAndFolderTypeDTO(); 					 
		selectedFolderandtype.setMf(selectedMessageFolderFolder);	//messagefolder				 
		FTPMessageFolderDTO ftpmf;
		
		try {
			ftpmf = servicemanagerlocal.getFtpMessageFolderbyId(selectedMessageFolderFolder.getId());
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e2) {
			ftpmf= new FTPMessageFolderDTO();
		}
		
		if(ftpmf==null){
			ftpmf= new FTPMessageFolderDTO();				
		} 
		selectedFolderandtype.setFtp(ftpmf); //ftpmessagefolder			
		MessageFolderTypeDTO[] types;		
		
		try {
			types = servicemanagerlocal.getAllMessageFolderType();
		} catch (AccessDeniedException | OperationFailedException | NotFoundException e2) {
			types= new MessageFolderTypeDTO[0];
		} 
					 
		// se selecciona el tipo segun el registro seleccionado en la ventana 
		for (int i = 0; i < types.length; i++) {
			if(selectedMessageFolderFolder.getMessagefoldertypekey().equals(types[i].getId())){
				selectedFolderandtype.setMft(types[i]);
				break;
			}
		}			
	
		EditFolderWindow window = new EditFolderWindow("Editar Casilla", company.getId(), selectedFolderandtype);
		window.center();
		FolderListWindow.this.getUI().addWindow(window);
		window.addCloseListener(new CloseListener() {
			@Override
			public void windowClose(CloseEvent e) {
				if (window.getDialogResult().equals(DialogResult.OK)) {
					// Obtener el dato y actualizarlo en la BD
					MessageFolderAndFolderTypeDTO editFolderAndType = window.getMessageFolderAndFolderTypeDTO();
					try {
						servicemanagerlocal.updateMessageFolder(editFolderAndType.getMf());
						doShowData();
						selectedMessageFolderFolder = null;
					} catch (AccessDeniedException | OperationFailedException | NotFoundException e1) {
						e1.printStackTrace();
					}
				}
			}
		});		
	}

	private void doDeleteFolder() {
		try {
			if (selectedMessageFolderFolder == null)
				return;

			ConfirmDialog.show(this.getUI(), "Favor Confirmar:", "¿Borrar Casilla?", "OK", "Cancel", new ConfirmDialog.Listener() {
				public void onClose(ConfirmDialog dialog) {
					if (dialog.isConfirmed()) {

						try {
							servicemanagerlocal.deleteMessageFolder(selectedMessageFolderFolder.getId());
							Notification.show("La casilla fue borrada exitosamente", Notification.Type.ERROR_MESSAGE);
							doShowData();
//							selectedFolder = null;
						} catch (AccessDeniedException | OperationFailedException | NotFoundException e) {
							if (e.getCause() instanceof ConstraintViolationException) {
								Notification.show("No se puede eliminar la casilla ya que está siendo utilizada en un servicio", Notification.Type.ERROR_MESSAGE);
							} else {
								Notification.show("Ocurrió un error al eliminar la casilla: \n\n" + e.getMessage(), Notification.Type.ERROR_MESSAGE);
							}
						}

					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public DialogResult getDialogResult() {
		return this.dialogResult;
	}

	private void doShowData() {
		// Listar en la grilla
		try {

			// Obtener todos los servicios de los sitios
			MessageFolderDTO[] foldersDataArr = servicemanagerlocal.getMessageFoldersofCompany(company.getId());
			List<MessageFolderDTO> foldersList = Arrays.asList(foldersDataArr);

			BeanItemContainer<MessageFolderDTO> container = new BeanItemContainer<>(MessageFolderDTO.class, foldersList);
			folderTable.setContainerDataSource(container);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
