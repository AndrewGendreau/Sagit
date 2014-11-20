package sagittarius;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SagitInjectDialog extends Dialog {

	private String HostIP; //String to hold the host IP
	private String RouterIP; //String to hold the router IP
	private final int WIDTH = 600; //Sets the message box's width
	private final int HEIGHT = 450; //Sets the message box's height
	private Display display; //Display variable
	private boolean isLocked = false; //Check if the lock image is locked

	private jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList(); //Device list array

	public SagitInjectDialog(Shell parent) { //Default constructor taking a shell parent parameter
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL); //Constructs using a style
	}

	public SagitInjectDialog(Shell parent, int style) { //Constructor that takes a style
		// Lets users override the default styles
		super(parent, style);
		setText("Man in the middle mode");
	}


	public String[] Interface2StringArray(NetworkInterface[] devices) { //turns the device array into an array of strings
		String[] DevicestoString = new String[devices.length]; 
		//Sets a String variable array equal in size to devices
		for (int i = 0; i < devices.length; i++) { //For each object in devices
			DevicestoString[i] = devices[i].name; 
			//Create an equivalent String and put it in the string array
		}
		return DevicestoString; //Return the string array
	}


	public String getHostIP() { //Gets the hostIP
		return HostIP; //Return statement
	}

	public void setHostIP(String hostIP) { //Sets the hostIP
		HostIP = hostIP; //Assignment
	}

	public String getRouterIP() { //Gets the router IP
		return RouterIP; //Return statement
	}

	public void setRouterIP(String routerIP) { //Sets the router IP
		RouterIP = routerIP; //Assignment
	}

	public void open() {
		// Create the dialog window
		Shell shell = new Shell(getParent(), getStyle()); //Creates a shell object
		shell.setText(getText()); //Sets the shell text to the return of getText
		shell.setSize(WIDTH, HEIGHT); //Sets the size
		createContents(shell); //Calls create contents
		shell.open(); //Opens the shell
		Display display = getParent().getDisplay(); //Sets the display to the display object of the parent
		while (!shell.isDisposed()) { //While the shell isn't disposed
			if (!display.readAndDispatch()) { //If there are no events on the stack
				display.sleep(); //Put the display to sleep
			}
		}
	}

	private void createContents(final Shell shell) { //Creates the box contents
		// Creates the box contents
		int lineSpace = 20; //Space between lines
		int horizMargin = 5; //Horizontal margin
		int vertMargin = 5; //Vertical margin
		int bottomMargin = 6; //Bottom margin
		int interSpace = 20; //Space between boxes
		
		int tab1 = 25; //First tab distance
		int tab2 = 75; //Second tab distance
		
		FormLayout layout = new FormLayout(); // Creates a form layout variable
												// named layout
		layout.marginHeight = vertMargin; // Sets the layout's margin height to five
		layout.marginWidth = horizMargin; // Sets the layout margin width to five
		shell.setLayout(layout); //Sets the layout

		// Show the next to the textbox
		Label hostLabel = new Label(shell, SWT.NONE);
		hostLabel.setText("Target Host: ");
		FormData data = new FormData(); // Similar to formlayout's formdata
		data.top = new FormAttachment(0, vertMargin); //Sets the top part of the label
		hostLabel.setLayoutData(data); // Sets the formlayout to the gridData

		// Display the input box
		final Text hostText = new Text(shell, SWT.BORDER); // Sets the textbox
		data = new FormData(); // Fills the unused horizontal space
		data.left = new FormAttachment(hostLabel, interSpace);
		data.top = new FormAttachment(0, vertMargin);
		data.right = new FormAttachment(tab2,0);
		hostText.setLayoutData(data); // Sets the button

		// Show the next to the textbox
		Label gateLabel = new Label(shell, SWT.NONE);
		gateLabel.setText("Default Gateway: ");
		data = new FormData(); // Similar to formlayout's formdata
		data.left = new FormAttachment(0,0);
		//data.right = new FormAttachment(tab2,0);
		data.top = new FormAttachment(hostLabel, lineSpace);
		gateLabel.setLayoutData(data); // Sets the formlayout to the gridData

		final Text gateText = new Text(shell, SWT.BORDER); // Sets the textbox
		data = new FormData(); // Fills the unused horizontal space
		data.left = new FormAttachment(gateLabel, interSpace);
		data.top = new FormAttachment(hostText, bottomMargin);
		data.right = new FormAttachment(tab2, 0);
		gateText.setLayoutData(data); // Sets the button

		Label readMenuLabel = new Label(shell, SWT.NONE);
		readMenuLabel.setText("Read Interface: ");
		data = new FormData(); // Similar to formlayout's formdata
		data.top = new FormAttachment(gateLabel, lineSpace);
		data.left = new FormAttachment(0,0);
		readMenuLabel.setLayoutData(data); // Sets the formlayout to the
											// gridData

		Menu readMenu = new Menu(shell, SWT.BAR);

		Combo readCombo = new Combo(shell, SWT.DROP_DOWN);
		readCombo.setItems(this.Interface2StringArray(devices));
		data = new FormData(); // Similar to formlayout's formdata
		data.top = new FormAttachment(gateText, bottomMargin);
		data.left = new FormAttachment(readMenuLabel, 5);
		readCombo.setLayoutData(data);
		shell.setMenuBar(readMenu);

		Label writeMenuLabel = new Label(shell, SWT.NONE);
		writeMenuLabel.setText("Write Interface: ");
		data = new FormData(); // Similar to formlayout's formdata
		data.top = new FormAttachment(readMenuLabel, lineSpace);
		writeMenuLabel.setLayoutData(data); // Sets the formlayout to the
											// gridData

		Menu writeMenu = new Menu(shell, SWT.BAR);

		Combo writeCombo = new Combo(shell, SWT.DROP_DOWN);
		writeCombo.setItems(this.Interface2StringArray(devices));
		data = new FormData(); // Similar to formlayout's formdata
		data.top = new FormAttachment(readCombo, bottomMargin);
		data.left = new FormAttachment(writeMenuLabel, 5);
		writeCombo.setLayoutData(data);
		shell.setMenuBar(writeMenu);
		
		final Button locks = new Button(shell, SWT.PUSH);
		data = new FormData();
		data.left = new FormAttachment(readCombo, lineSpace);
		data.top = new FormAttachment(tab1, 0);
		locks.setImage(getUnlockedLock());
		isLocked = false;
		locks.setLayoutData(data);
		locks.addListener(SWT.MouseUp, new Listener() {
			
			public void handleEvent(Event e) {
				
				switch (e.type) { //Switch statement for the event types
				case SWT.MouseUp: if(isLocked == false) {
					locks.setImage(getLockedLock());
					isLocked = true;
				} else {
					locks.setImage(getUnlockedLock());
					isLocked = false;
				}
				break;
			}
		}
	});
		Button start = new Button(shell, SWT.PUSH);
		start.setText("Start");
		data = new FormData();
		data.top = new FormAttachment(writeMenuLabel, lineSpace);
		data.left = new FormAttachment(0, 80);
		start.setLayoutData(data);

		// Create the Stop button and add a handler
		// so that pressing it will set input to null
		Button forward = new Button(shell, SWT.PUSH);
		forward.setText("Relay/Drop");
		data = new FormData();
		data.top = new FormAttachment(writeMenuLabel, lineSpace);
		data.left = new FormAttachment(start, 5);
		forward.setLayoutData(data);

		// Set the OK button as the default, so
		// user can type input and press Enter
		// to dismiss

		// Creates a bar menu (dropdown)
	}
	
	public Image getLockedLock() {
		
		Image locked = new Image(display, "Locks/1415223701_lock yellow.png");
		
		return locked;
	}
	
	public Image getUnlockedLock() {
		
		Image unlocked = new Image(display, "Locks/1415223674_unlock yellow.png");
		
		return unlocked;
	}

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		SagitInjectDialog test = new SagitInjectDialog(shell);
		test.createContents(shell);
		test.open();

		// TODO
		// Create a formlayout button to add to the boxs next to the menus
	}
}
