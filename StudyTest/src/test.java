import jpcap.JpcapCaptor;
import java.net.InetAddress;
import jpcap.NetworkInterfaceAddress;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		
		for (int i = 0; i < devices.length; i++) {
			  //print out its name and description
			System.out.println(devices[i]);
			  System.out.println(i+": "+devices[i].name + "(" + devices[i].description+")");
	
			  System.out.println(" datalink: "+devices[i].datalink_name + "(" + devices[i].datalink_description+")");

			  //print out its MAC address
			  System.out.print(" MAC address:");
			  for (byte b : devices[i].mac_address)
			    System.out.print(Integer.toHexString(b&0xff) + ":");
			  System.out.println();

		/*	  InetAddress s = null;
			  //print out its IP address, subnet mask and broadcast address
			  for (NetworkInterfaceAddress a : devices[i].addresses)
				  s = a.address;
			 System.out.println(s.getAddress());
			//  System.out.println(s);
			   // System.out.println(" address:"+a.address + " " + a.subnet + " "+ a.broadcast);
			  byte[] addressArray;
			  */
				  //  System.out.println(" address:"+a.address);
	}
}
}
