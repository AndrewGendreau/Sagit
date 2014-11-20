package sagittarius;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

public class InjectorDialog extends Dialog {
	
	private int octet1; //IP octet 1
	private int octet2; //IP octet 2
	private int octet3; //IP octet 3
	private int octet4; //IP octet 4
	private String message;

	//Constructor definition
	public InjectorDialog(Shell parent) {
		
		this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	}
	
	public InjectorDialog(Shell parent, int style) {
		//Lets users override the default styles
		super(parent, style);
		setText("Input Dialog");
		setMessage("Please enter a value");
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
