package sagittarius;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import demoCode.ArpVenom;
import demoCode.SagitCapture;
import demoCode.SagitInject;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

public class SagitForward {

	private final SagitCapture cap;
	private final SagitInject inject;
	//private final SagitCapture arpCap;
	private ArrayList<Packet> list = new ArrayList<Packet>(5);
	
	NetworkInterface[] devices;
	private int deviceSelected;

	byte[] targetIP;
	ARPPacket ARP;
	EthernetPacket ether;
	Packet toForward;
	private boolean isDisposed = false;
	short hardwareOctets = 6;
	short protocolOctets = 4;

	boolean targetRecieved;

	public SagitForward(int deviceIndex) {
		cap = new SagitCapture(deviceIndex);
		inject = new SagitInject();
		devices = cap.getDevices();
	}

	public SagitForward(byte[] target, int deviceIndex) {
		cap = new SagitCapture();
		inject = new SagitInject();
		cap.openDevice(deviceIndex);
		inject.openDevice(deviceIndex);
	}

	public List<Packet> whoIsTarget(final byte[] target, List<Packet> buffer) {
		//byte[] targetMac = new byte[6];
		
		// contruct ARP request who is so and so
		// Send it on injector
		// Set filter for ARP response
		// REad response and pick out the mac address
		ARP = this.ARPGen(target);
		inject.inject(ARP);
		cap.setFilter("arp");
		cap.acquirePackets(buffer, 5);
		return buffer;
	}
	
	private boolean hasIPAddress(ARPPacket packet, byte[] ipAddress){
		byte[] thatIp = packet.sender_protoaddr;
		if (ipAddress.length != thatIp.length)
			return false;
		for (int i = 0; i < thatIp.length; i++){
			if (ipAddress[i] != thatIp[i])
				return false;
		}
		return true;
	}
	
	public byte[] listReader(ArrayList<Packet> buffer, byte[] target) {
	
		 	byte[] targetMac = new byte[6];
			//Object[] packets = buffer.toArray();

			//for(int i = 0; i < packets.length; i++) {
				
			//}
		
		Iterator<Packet> it = buffer.iterator();
		ARPPacket p = (ARPPacket) it.next();
		while(it.hasNext()) {
			p = (ARPPacket) it.next();
			if(hasIPAddress(p, target)) {
				targetMac =  p.sender_hardaddr;
				System.out.println("Target Found!");
			}
			//else{
				//System.out.println("Target not found");
				//targetMac = null;
			//}
		}
	/*	if (packets.length == 0)
			return null;
		ARPPacket p = (ARPPacket) packets[0];
		System.out.println("Sender IP address: " + p.getSenderProtocolAddress());
		System.out.println("Target IP Address: " + p.getTargetProtocolAddress());
		System.out.println("Packet Operation: " + p.operation); //0 = request, 1 = reply
		return ((ARPPacket)packets[0]).sender_hardaddr; */
		return targetMac;
	}
	

	public void setTargetIP(byte[] target) {
		targetIP = target;
	}

	public byte[] getTargetIP() {
		return this.targetIP;
	}

	public void openDevice(int index) { // Opens the device takes in index you
		cap.openDevice(index);
		inject.openDevice(index);
		deviceSelected = index;
	}

	public void dispose() { // Method to close the open device
		cap.dispose();
		inject.dispose();
		isDisposed = true;
	}

	private ARPPacket ARPGen(byte[] targetIP) { // Poison Arp Generation

		byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255,
				(byte) 255, (byte) 255, (byte) 255 };
		ArpVenom temp = new ArpVenom();
		ARP = new ARPPacket();
		ARP.hlen = hardwareOctets;
		ARP.plen = protocolOctets;
		ARP.prototype = ARPPacket.PROTOTYPE_IP;
		ARP.hardtype = ARPPacket.HARDTYPE_ETHER;
		ARP.operation = ARPPacket.ARP_REQUEST;

		ARP.sender_hardaddr = temp.getInterfaceMAC(cap.getCurrentDevice());
		ARP.target_hardaddr = broadcast;

		ARP.target_protoaddr = targetIP;
		ARP.sender_protoaddr = targetIP;

		ARP.datalink = this.etherGen(0, ARP);
		return ARP;
	}

	private EthernetPacket etherGen(int deviceIndex, ARPPacket p) { // Helper
		// method to
		// create
		// the frame
		byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255,
				(byte) 255, (byte) 255, (byte) 255 };
		ether = new EthernetPacket();
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;
		ether.src_mac = devices[deviceIndex].mac_address;
		ether.dst_mac = broadcast;
		p.datalink = ether;
		return ether;
	}

	public static String macToEnglish(byte[] mac){
		return String.format("%02X-%02X-%02X-%02X-%02X-%02X\n", 
				mac[0], mac[1], mac[2], mac[3], mac[4], mac[5]);
	}
	
	public static void main(String[] args) {

		byte[] test = new byte[] { (byte) 151, (byte) 161, (byte) 244, (byte) 1 };
		SagitForward replyTest = new SagitForward(1);
		replyTest.whoIsTarget(test, replyTest.list);
		byte[] tester = replyTest.listReader(replyTest.list, test);
		System.out.printf(macToEnglish(tester));
		System.out.println("\nDone!");
	}

}
