import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.Packet;


public class EventHorizon {

	/**
	 * @param args
	 */
	
	private JpcapCaptor captor;

	public EventHorizon(NetworkInterface Interface, int bytesToCapture, boolean promiscuousMode,
			int timeout) throws IOException 
	{
		captor = JpcapCaptor.openDevice(Interface, bytesToCapture, promiscuousMode, timeout );
	}
	
	public int sniff(int NumberOfPackets) {
	    int p = captor.loopPacket(NumberOfPackets, new Reciever());
		return p;
		
	}
 	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0;
		EventHorizon e = new EventHorizon(devices[index], 1028, false, 20);
		e.sniff(100);
	}

}
