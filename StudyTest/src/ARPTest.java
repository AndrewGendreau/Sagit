import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;


public class ARPTest {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, NullPointerException {
		// TODO Auto-generated method stub

		byte[] send = new byte[]{(byte)0,(byte)1,(byte)2,(byte)3,(byte)4,(byte)5};
		//6 byte sender MAC Address
		byte[] destination = new byte[]{(byte)0xA,(byte)6,(byte)7,(byte)8,(byte)9,(byte)10};
		//6 byte destination MAC Address
		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface test = devices[1];
		//Grabs the devices on this machine and places them into the array called devices
		int index = 1; //My eth0 interface's index
		JpcapSender sender=JpcapSender.openDevice(devices[index]);
		//Creates a Jpcap sender instance called sender and opens the device in devices[index]
		
		byte[] broadcast=new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255};
		//Broadcast MAC address
		ARPPacket p = new ARPPacket(); //Creates an empty ARPPacket Object
		p.hlen = 6; //Length in octets of a hardware address Ethernet is 6
		p.plen = 4; //Length in octets of addresses of the upper layer protocol IPv4 is 4
		p.hardtype = ARPPacket.HARDTYPE_ETHER; //Specifies the network protocol type ethernet
		p.operation = ARPPacket.ARP_REQUEST; 
		//Specifies the operation the sender is performing reply or request request is 0 reply is 1
		p.sender_hardaddr = test.mac_address;
		//Specifies the sender hardware address as the device in devices[index]
		p.prototype = ARPPacket.PROTOTYPE_IP; 
		//Specifies the internetwork protocol the ARP Packet is intended for in this case IPv4
		p.sender_protoaddr = new byte[] {(byte)1, (byte)2, (byte)3, (byte)4};
		//IPv4 address of the sender
		p.target_protoaddr = new byte[] {(byte) 1, (byte) 2, (byte) 3,(byte)4};
		//IPv4 address of the target
		p.target_hardaddr = broadcast;
		//Sets the hardware target of the ARP Packet
		
		EthernetPacket ether = new EthernetPacket(); //Creates a new EthernetPacket object
		ether.frametype=EthernetPacket.ETHERTYPE_ARP; //Sets the frame type to ARP
		ether.src_mac=devices[index].mac_address; //Sets the frame's source mac address as the variable send
		ether.dst_mac=broadcast; //Sets the frame's destination MAC address
		p.datalink=ether; 
		//Creates a datalink to the previously defined ARP packet needed to send a packet
		
		sender.sendPacket(p); //Sends the Packet
		System.out.println(p); //Prints it out
		sender.close(); //Closes the sender instance
				
	}
}
