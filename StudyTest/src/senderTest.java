import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.IPPacket;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class senderTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0; //My loopback interface's index
		JpcapSender sender=JpcapSender.openDevice(devices[index]);
		
		TCPPacket p = new TCPPacket(12, 32, 35, 45, false, false, false, false, 
				false, false, false, false, 11, 10);
		//See notes
				
		
		p.setIPv4Parameter(0,false,false,false,0,false,false,false,0,1010101,100,IPPacket.IPPROTO_TCP,
				  InetAddress.getByName("www.microsoft.com"),InetAddress.getByName("www.google.com"));
		//See Notes
		
		p.data=("data").getBytes();
		//Gets the number of bytes from the sting and inserts it into the data field of the packet

		EthernetPacket ether=new EthernetPacket();
		//set frame type as IP
		ether.frametype=EthernetPacket.ETHERTYPE_IP;
		//set source and destination MAC addresses
		ether.src_mac=new byte[]{(byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5};
		ether.dst_mac=new byte[]{(byte)0,(byte)6,(byte)7,(byte)8,(byte)9,(byte)10};

		//set the datalink frame of the packet p as ether
		p.datalink=ether;

		//send the packet p
		sender.sendPacket(p);

		sender.close();
	}
}