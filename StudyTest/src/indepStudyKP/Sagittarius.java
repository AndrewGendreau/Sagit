package indepStudyKP;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

public class Sagittarius {

	private Sniffer sniffer = new Sniffer();
	private Injector injector = new Injector();
	private GuiInterface gui = new GuiInterface();
	
	//public void poisonArp(ip address of target machine)
	//
	/**
	 * @param args
	 */
	
	private Display display;
	private Shell shell;
	
	public Sagittarius() {
		display = new Display();
		shell = new Shell(display);
		shell.setText("Sagittarius");
	}
	
	public void run() {
		shell.open();
		while(!shell.isDisposed()) { //while shell is not disposed
			if(!display.readAndDispatch()) { // if there is no event on the event queue
				display.sleep(); //return the resources to the cpu and sleep
			}
		}
		display.dispose(); //Dispose the display
	}
	
	public void launchPot(Shell shell) {
		FormLayout layout = new FormLayout();
		layout.marginHeight = 40;
		layout.marginWidth = 40;
		shell.setLayout(layout);
		
		//Sets Injector button
		Button injectButton = new Button(shell, SWT.PUSH);
		injectButton.setText("Injector");
		FormData data = new FormData();
		data.top = new FormAttachment(0, -50);
	}
	
	Group buttonPanel = new Group(shell, SWT.SHADOW_ETCHED_IN);
	Group splashPanel = new Group(shell, SWT.SHADOW_ETCHED_IN);
	Group InformationPanel = new Group(shell, SWT.SHADOW_ETCHED_IN);
	
	FormData buttonData = new FormData();
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sagittarius sgt = new Sagittarius();
		sgt.run();
	}

}
