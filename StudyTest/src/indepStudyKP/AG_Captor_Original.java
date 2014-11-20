package indepStudyKP;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.PacketReceiver;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;
import jpcap.packet.UDPPacket;

public class AG_Captor_Original {

	private JpcapCaptor captor; //captor instance variable
	private jpcap.NetworkInterface[] devices; //Network device array instance variable
	private int deviceIndex; //Index of device you want to use
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
	String methodData;
	String output = "";
	private boolean done = false;
	private int packetsRecieved = 0;
	private boolean isDisposed = false;
	
	private Packet packet; //Incoming packet variable
	
	public AG_Captor_Original(){ //Default constructor
		devices = JpcapCaptor.getDeviceList(); //Feeds devices all network interfaces on the machine
	}
	
	public void openDevice(int index){ //Opens the device takes in index you want to use
		deviceIndex = index; //Sets the index to the parameter
		
		try { //beginning of a try catch block
			captor = JpcapCaptor.openDevice(devices[index], 1028, false, 20 ); //Opens the selected device
		} catch (IOException e) { //catches an input output error
			// TODO Auto-generated catch block
			e.printStackTrace(); //prints the throwable
		}
	}
	
	public void dispose(){ //Method to close the open device
		captor.close(); //Closes the device
		isDisposed = true;
	}

	public boolean isDisposed(){
		return isDisposed;
	}
	
	public int getDeviceCount() { //Method to get the number of available devices
		return devices.length; //returns the length of devices
	}

	public int getCurrentDevice(){ //Method to get the index of the current device
		return deviceIndex; //Returns the deviceIndex instance variable
	}

	public String tcp2String(Packet packet) {
		TCPPacket tcpPacket = (TCPPacket) packet; //Creates a tcpPacket out of the received packet
		EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink; 
		//Creates a ethernet packet to get the ether layer stuff
		
		methodData += src_macInEnglish = ethernetPacket.getSourceAddress(); //Gets the src_mac
		methodData += dst_macInEnglish = ethernetPacket.getDestinationAddress(); //Gets the dst_mac
		
		methodData += src_TCPPort = tcpPacket.src_port; //Retrieves the source Port
		methodData += sourcePortTCP = String.valueOf(src_TCPPort); //Creates a Src_port string
		methodData += dst_TCPPort = tcpPacket.dst_port; //Retrieves the Destination Port
		methodData += destinationPortTCP = String.valueOf(dst_TCPPort); //Creates a String for the dst_tcpPort
		
		
		protocol = tcpPacket.protocol; //Holds the protocol Name
			
		methodData += protocolName = "TCP"; //Make the protocol name TCP
		
		for(int i = 0; i < tcpPacket.data.length; i++) {
			data += String.valueOf((char) tcpPacket.data[i]);
		}
		methodData += data;
		
		methodData += src_ip = tcpPacket.src_ip; //Retrieves the src Ip address
		methodData += srcIPString = src_ip.toString();
		methodData += dst_ip = tcpPacket.dst_ip; //Retrieves the dst IP address 
		methodData += dstIPString = dst_ip.toString(); //Creates a string with dst_ip
		
		return methodData;
	}
	
	public String udp2String(Packet packet) {
		
		UDPPacket udpPacket = (UDPPacket) packet; //Creates a udp packet out of the packet
		EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink; 
		//Creates and ethernet packet for layer 2 information
		
		methodData += src_macInEnglish = ethernetPacket.getSourceAddress();
		//Gets the string value of the src mac address
		methodData += dst_macInEnglish = ethernetPacket.getDestinationAddress();
		//Gets the string value of the dst mac address
		
		methodData += src_UDPPort = udpPacket.src_port;
		//Gets the src udp port
		methodData += sourcePortUDP = String.valueOf(src_UDPPort);
		//creates a string using the port to place in the dataArray
		
		methodData += dst_UDPPort = udpPacket.dst_port;
		//Gets the dst port from the packet
		methodData += destinationPortUDP = String.valueOf(dst_UDPPort);
		//Creates a string from dst_UDPPport
		
		methodData += src_ip = udpPacket.src_ip; //Gets the inet address from the packet
		methodData += srcIPString = src_ip.toString(); //Converts the InetAddress to a string
		methodData += dst_ip = udpPacket.dst_ip; //Gets the inet dst Ip address from udppacket
		methodData += dstIPString = dst_ip.toString(); //Converts that to a string
		
		protocol = udpPacket.protocol; //Grabs the protocol
		methodData += protocolName = "UDP"; //Since this is udp we don't have to worry about other protocols
		
		for(int i = 0; i < udpPacket.data.length; i++) { //Runs through the array containing packet data
			data += String.valueOf((char) udpPacket.data[i]); //Creates a string from every part of that array
		}
		methodData += data;
		return methodData;
			
	}

	public void acquirePackets(final List<Packet> buffer, final int count) { //Method to hold the received packets in an arrayList of packets

		//ArrayList of packets called List
		
		final PacketReceiver receiver = new PacketReceiver() { //Declaration of a new packetReciever called receiver
			
			@Override
			public void receivePacket(Packet packet) { //Method to process the packet

				System.out.println("received packet.."); //Prints a line to confirm packet was received 
				synchronized (buffer) {
					buffer.add(packet); //Adds the packet to the ArrayList					
				}
				packetsRecieved++;
				if (done || packetsRecieved == count)
					captor.breakLoop();
			}
		}; //End of InnerClass
		
		//captor.loopPacket(count, receiver);
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
				//AG_Captor test = new AG_Captor(); //Temporary calling object
				//test.openDevice(0); //opens the device
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				captor.loopPacket(count, receiver); //Loop for packets until closed
			}
			
		};
		Thread snifferThread = new Thread(runnable);
		snifferThread.setName("Sniffer");
		snifferThread.start();
		System.out.println(snifferThread.getState());
	}
	
	public int getPacketsReceived(){
		return packetsRecieved;
	}
	
	public void stopLoop(){
		done = true;
	}
	
	public static void main(String[] args){ //Main Method
		AG_Captor_Original cap = new AG_Captor_Original(); //Creates a new captor object
		cap.openDevice(1); //Opens the device 1
		List<Packet> buffer = Collections.synchronizedList(new ArrayList<Packet>()); //Creates a new list for the packets
		cap.acquirePackets(buffer, 10); //making it crash find the problem
		while(cap.getPacketsReceived() < 10){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		cap.dispose(); //Closes the device and returns resources
	}
	
}
