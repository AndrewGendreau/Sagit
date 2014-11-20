import java.io.IOException;

import jpcap.JpcapCaptor;


public class CaptureTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		//Gets all the interfaces and places them into an array
		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0; //The index of my wireless adapter
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 1028, false, 20 );
			  //Creates a captor variable and sets it equal to the following:
			  //Opens the wlan interface stored in place 1 in devices
			  //Sets the max number of Bytes to capture to 1028
			  //Sets to non promiscuous mode only capturing packets sent and received by the host
			  //Sets a 20 second timeout

			  captor.loopPacket(200,new Reciever());
			  captor.close();
		}
			  //Calls loopPacket with the parameters -1 and a new instance of receiver
			  //-1 means to capture infinitely this can be any limit you want
			  //The new constructor means create a new receiver instance
			  //for every packet to print them out
			  //Closes captor and returns the resources
}
