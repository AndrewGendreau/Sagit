
package sagittarius;

import java.awt.Color;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class Sagittarius {

	private Display display; //A display instance variable that provides the foundation for the GUI
	private Shell shell; //A shell instance variable that represents the actual window either a top level or dialog

	public Sagittarius(){ //Default Constructor
		display = new Display(); //Sets the display instance variable to a new display object
		shell = new Shell(display); //Creates a shell using the display object as a foundation
		shell.setText("Sagittarius");  //Sets the window text to Sagittarius
	}

	public void run(){ //void run method for the threads
		launchPad(shell); //Calls the launchpad method on the shell
		shell.open(); //Opens the shell

		while (!shell.isDisposed()){ //while the shell isn't disposed
			if (!display.readAndDispatch()) //If there is no event on the event queue
				display.sleep(); //Put the display to sleep
		}
		shell.dispose(); //Dispose the shell if it is closed
	}

	
	private void launchPad(final Shell shell) { //Void method that takes the shell as a parameter
		FormLayout layout= new FormLayout (); //Creates a form layout variable named layout
		layout.marginHeight = 5; //Sets the layout's margin height to five
		layout.marginWidth = 5; //Sets the layout margin width to five
		shell.setLayout(layout); //Sets the shell's layout to the layout variable

		//Creates a group for the control buttons with a shadow border
		Group buttonsPanel = new Group(shell, SWT.SHADOW_ETCHED_IN); 
		Group splashPanel = new Group(shell, SWT.SHADOW_ETCHED_IN);
		Group infoPanel = new Group(shell, SWT.SHADOW_ETCHED_IN);

		//Creates a formData variable called buttonsData
		//Controls the buttons control panel
		FormData splashData = new FormData();
		
		//Sets the formData's top numerator to 0 to stick it to the top edge
		//with an offset of 5 pixels so it is 5 pixels away from the margin
		splashData.top = new FormAttachment(0,5); 
		
		//Sets the bottom edge to 100 so it attaches to the very bottom of the window
		//With an offset of -5 so it keeps 5 pixels away from the bottom edge
		splashData.bottom = new FormAttachment(100,-5); 
		
		//Sets the left side of the control panel to the left edge with a numerator of 0
		//Sets the offset to 5 so it will be 5 pixels away from the edge
		splashData.left = new FormAttachment(0,5);
		
		//Sets the right side 25 percent of the total window by giving the numerator of 25: 25/100 = 25%
		//Gives it no offset 
		splashData.right = new FormAttachment(38,0);
		
		//Sets the button panel layout data to the layout defined by buttonsData
		splashPanel.setLayoutData(splashData);
		
		//Sets the panel's title text to Control Panel
		
		//Sets the Panel's layout to FormLayout
		splashPanel.setLayout(layout);
		
		splashPanel.setBackgroundImage(getMainLogo());
		
		
		//Definition of the Splash Panel 
		
		//Creates a new FormData object called splashData to hold the layout
		FormData buttonsData = new FormData();
		
		//Sets the top of the panel to 2 percent of the window
		//With a 5 pixel offset from the top of the panel
		buttonsData.top = new FormAttachment(2,4); 
		
		//Sets the left edge of the panel the buttonsPanel as a control
		//And gives it a 5 pixel offset from that
		buttonsData.left = new FormAttachment(splashPanel, 5);
		
		//Gives the bottom a numeration of 50 to position it half way down the window
		//Gives it an offset of 10 so it is 10 pixels from the bottom side
		buttonsData.bottom = new FormAttachment(50, 10);
		
		//Gives it a right side of 100 so it is stuck to the edge
		//Gives an offset of 10 so it is 10 pixels away from the edge
		buttonsData.right = new FormAttachment(100, 10);
		
		//Sets the layout Data to splashData
		
		buttonsPanel.setLayout(layout);
		
		buttonsPanel.setLayoutData(buttonsData);	
		
		buttonsPanel.setText("Control Panel");
				
		Button injectorButton = new Button(buttonsPanel, SWT.PUSH);
		
		injectorButton.addListener(SWT.MouseUp, new Listener() { 
			//Sets the listener to an object of this inner class
			
			public void handleEvent(Event e) { //Definition of handleEvent method
				switch (e.type) { //Switch statement for the event types
				case SWT.MouseUp: //This case is after the mouse has been clicked
					SagitInjectDialog messageBox = new SagitInjectDialog(shell); 
					messageBox.open();
					break;
				}
			}
		});
		
		injectorButton.setText("Man in the Middle Mode");
		
		FormData injectData = new FormData();
		
		injectData.top = new FormAttachment(15,5);
		
		injectData.left = new FormAttachment(2,5);
		
		injectData.right = new FormAttachment(55,0);
		
		injectData.bottom = new FormAttachment(75,0);
		
		injectorButton.setLayoutData(injectData);
		
		Button snifferButton = new Button(buttonsPanel, SWT.PUSH);
		snifferButton.setText("Sniffer Mode");
		
		FormData snifferData = new FormData();
		
		snifferData.left = new FormAttachment(injectorButton,5);
		
		snifferData.top = new FormAttachment(15,5);
		
		snifferData.bottom = new FormAttachment(75,0);
		
		snifferData.right = new FormAttachment(100,0);
		
		snifferButton.setLayoutData(snifferData);
		
		//Defintion of the infoPanel
		
		//Creates a new FormData object called infoData to hold the placement data
		FormData infoData = new FormData();
		
		//Sets the text of the panel to Info
		infoPanel.setText("Info");
		
		//Sets the top to have a control of splash so it positions itself relative to it
		//Offsets the top by 5
		infoData.top = new FormAttachment(buttonsPanel, 5);
		
		//Sets the left to have buttonsPanel as a control
		//Offsets it 5 pixels from the buttons Panel
		infoData.left = new FormAttachment(splashPanel, 5);
		
		//Sets the right side to 100 so it sticks itself there
		//Gives it an offset of 10 pixels so it is 10 pixels off
		infoData.right = new FormAttachment(100, 10);
		
		//Sets the bottom numerator to 100 so it sticks to the bottom
		//Offsets it by 10 from the bottom
		infoData.bottom = new FormAttachment(100, 10);
		
		//Sets the layoutData of the panel
		infoPanel.setLayoutData(infoData);
		
	}
	
	public Image getMainLogo() {
		
		Image logo = new Image(display, "Logo/Sagit_logo_smaller.png");
		
		return logo;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Sagittarius sgt = new Sagittarius();
		sgt.run();
	}

}
