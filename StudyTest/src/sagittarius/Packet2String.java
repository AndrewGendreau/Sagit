package sagittarius;

import java.net.InetAddress;

import jpcap.PacketReceiver;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class Packet2String implements PacketReceiver {

	//Initial data instance variables
	byte[] src_mac;
	byte[] dst_mac;
	InetAddress src_ip;
	InetAddress dst_ip;
	long src_UDPPort;
	long dst_UDPPort;
	long src_TCPPort;
	long dst_TCPPort;
	String data = "";
	short protocol;
	
	//Strings Created from them
	String srcIPString;
	String dstIPString;
	String src_macInEnglish;
	String dst_macInEnglish;
	String protocolName;
	String sourcePortTCP;
	String destinationPortTCP;
	String sourcePortUDP;
	String destinationPortUDP;
	String[] dataArray;

	@Override
	public void receivePacket(Packet packet) {
		// TODO Auto-generated method stub
		if(packet instanceof TCPPacket) { //If the packet is TCP
			
			String[] dataArray = tcp2String(packet); //Call this method
		}
		else if(packet instanceof UDPPacket) {
			
			String[] dataArray = UDP2String(packet);
		}
	} 
	
	//TODO get accessior methods
	
	public String[] tcp2String(Packet packet) { //Takes apart a tcp Packet
		TCPPacket tcpPacket = (TCPPacket) packet; //Creates a tcpPacket out of the recieved packet
		EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink; 
		//Creates a ethernet packet to get the ether layer stuff
		
		src_macInEnglish = ethernetPacket.getSourceAddress(); //Gets the src_mac
		dst_macInEnglish = ethernetPacket.getDestinationAddress(); //Gets the dst_mac
		
		src_TCPPort = tcpPacket.src_port; //Retrieves the source Port
		sourcePortTCP = String.valueOf(src_TCPPort); //Creates a Src_port string
		dst_TCPPort = tcpPacket.dst_port; //Retrieves the Destination Port
		destinationPortTCP = String.valueOf(dst_TCPPort); //Creates a String for the dst_tcpPort
		
		
		protocol = tcpPacket.protocol; //Holds the protocol Name
			
		protocolName = "TCP"; //Make the protocol name TCP
		
		for(int i = 0; i < tcpPacket.data.length; i++) {
			data += String.valueOf((char) tcpPacket.data[i]);
		}
		
		src_ip = tcpPacket.src_ip; //Retrieves the src Ip address
		srcIPString = src_ip.toString();
		dst_ip = tcpPacket.dst_ip; //Retrieves the dst IP address 
		dstIPString = dst_ip.toString(); //Creates a string with dst_ip
		
		//Creates an array of strings with the created strings to insert into a table
		dataArray = new String[] {src_macInEnglish, dst_macInEnglish,
				sourcePortTCP, destinationPortTCP, protocolName};
		return dataArray;
	}

	public String[] UDP2String(Packet packet) { //Creates a string array with udpPackets
		UDPPacket udpPacket = (UDPPacket) packet; //Creates a udp packet out of the packet
		EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink; 
		//Creates and ethernet packet for layer 2 information
		
		src_macInEnglish = ethernetPacket.getSourceAddress();
		//Gets the string value of the src mac address
		dst_macInEnglish = ethernetPacket.getDestinationAddress();
		//Gets the string value of the dst mac address
		
		src_UDPPort = udpPacket.src_port;
		//Gets the src udp port
		sourcePortUDP = String.valueOf(src_UDPPort);
		//creates a string using the port to place in the dataArray
		
		dst_UDPPort = udpPacket.dst_port;
		//Gets the dst port from the packet
		destinationPortUDP = String.valueOf(dst_UDPPort);
		//Creates a string from dst_UDPPport
		
		src_ip = udpPacket.src_ip; //Gets the inet address from the packet
		srcIPString = src_ip.toString(); //Converts the InetAddress to a string
		dst_ip = udpPacket.dst_ip; //Gets the inet dst Ip address from udppacket
		dstIPString = dst_ip.toString(); //Converts that to a string
		
		protocol = udpPacket.protocol; //Grabs the protocol
		protocolName = "UDP"; //Since this is udp we don't have to worry about other protocols
		
		for(int i = 0; i < udpPacket.data.length; i++) { //Runs through the array containing packet data
			data += String.valueOf((char) udpPacket.data[i]); //Creates a string from every part of that array
		}
		
		dataArray = new String[] {src_macInEnglish, dst_macInEnglish,
				sourcePortUDP, destinationPortUDP, protocolName, data};
		//Array to hold the string data for a table
		return dataArray;
	}
}
