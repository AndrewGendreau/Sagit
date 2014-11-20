import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

public class ARPGen extends ARPPacket {
	
	private InetAddress address; //InetAddress variable to hold the IP address
	private byte[] ipAddress;
	short hardwareOctets = 6; //Length in octets of a hardware address Ethernet is 6
	short protocolOctets = 4; //Length in octets of addresses of the upper layer protocol IPv4 is 4
	NetworkInterface Interface; //Interface
	byte[] broadcast = 
			new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255};
	ARPPacket ARP;

	public ARPGen (NetworkInterface Interface, byte[] targetIP, byte[] targetHardware) {

		super();
		//Octets and protocol
		this.hlen = hardwareOctets; 
		this.plen = protocolOctets; 
		this.prototype = ARPPacket.PROTOTYPE_IP; //Specifies the protocol type in this case IP
		
		//Hardware and operaton
		this.hardtype = ARPPacket.HARDTYPE_ETHER; //Specifies the network protocol type ethernet
		this.operation = ARPPacket.ARP_REQUEST; //Specifies the operation in this case ARP Request
		
		//Hardware Addresses
		this.sender_hardaddr = Interface.mac_address; //Specifies the hardware address of the user
		this.target_hardaddr = targetHardware; //The target hardware address taken from the parameter
		
		//IP address assignment
		this.sender_protoaddr = ipAddress;
		this.target_protoaddr = targetIP; //Target IP address taken from the parameter
		
		//Ethernet packet generation
		EthernetPacket  ether = new EthernetPacket(); //Creates an empty ethernet packet
		ether.frametype = EthernetPacket.ETHERTYPE_ARP; //Sets the ethernet Framtype to ARP
		ether.src_mac = Interface.mac_address; //Gives it a source mac of the interface it's using
		ether.dst_mac = broadcast; //Gives it a target from the parameter
		this.datalink = ether; //Links the ARP and Ethernet Packets
				
	}
	
	
	public void interfaceIP(NetworkInterface Interface) {
		//Changes the InetAddress variable to a byte Array
		
		 for (NetworkInterfaceAddress a : Interface.addresses) { 
			 //Runs through the interface's addresses
			 int count = 0;
			 count++;
			// address = a.address; //Sets the private variable address to the InetAddress
			 //ipAddress = address.getAddress(); 
			 //String stringtest = address.toString();
			 //Changes the InetAddress to a byte array and stores it in ipAddress
			// System.out.println(Interface.addresses);
			 System.out.println(count);
		 }
	}
	
	private void Fire(NetworkInterface Interface) throws IOException { //Fires the packet
		JpcapSender sender=JpcapSender.openDevice(Interface); //Opens a sender on the interface
		sender.sendPacket(ARP); //Fires out the packet
	}
		
	public static void main(String[] args) throws IOException {
		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		NetworkInterface test = devices[1];
		
		byte[] send = new byte[] {(byte)1, (byte)2, (byte)3, (byte)4};
		byte[] broadcast=new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255};
		
		ARPGen p = new ARPGen(test, send, broadcast);
		System.out.println(p);
		p.interfaceIP(test);
	}
}
