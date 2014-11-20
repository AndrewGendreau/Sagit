package sagittarius;

import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;

public class EventHorizon {
	
	private jpcap.NetworkInterface[] devices;
	JpcapSender sender;
	private JpcapCaptor captor; //captor instance variable
	private int deviceIndex; //Index of device you want to use
	private InetAddress address; //InetAddress variable to hold the IP address
	private byte[] ipAddress;
	short hardwareOctets = 6; //Length in octets of a hardware address Ethernet is 6
	short protocolOctets = 4; //Length in octets of addresses of the upper layer protocol IPv4 is 4
	byte[] broadcast = 
			new byte[]{(byte)255,(byte)255,(byte)255,(byte)255,(byte)255,(byte)255};
	ARPPacket p;
	EthernetPacket ether;
	
	public EventHorizon() {
		devices = JpcapCaptor.getDeviceList(); //Feeds devices all network interfaces on the machine
	}
	
	public void openDevice(int index) {
		deviceIndex = index;
	}
	
	public byte[] getInterfaceMAC(int index) {
		
		return devices[index].mac_address;
	}
	
	public ARPPacket ARPGen(int index, byte[] targetIP) {
	
		EventHorizon temp = new EventHorizon();
		p = new ARPPacket();
		p.hlen = hardwareOctets;
		p.plen = protocolOctets;
		p.prototype = ARPPacket.PROTOTYPE_IP;
		p.hardtype = ARPPacket.HARDTYPE_ETHER;
		p.operation = ARPPacket.ARP_REQUEST;
		
		p.sender_hardaddr = temp.getInterfaceMAC(0);
		p.target_hardaddr = broadcast;
		
		p.target_protoaddr = targetIP;
		p.sender_protoaddr = targetIP;
		
		p.datalink = this.etherGen(0, p);
		return p;
	}
	
	public EthernetPacket etherGen(int deviceIndex, ARPPacket p) {
		
		ether = new EthernetPacket();
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;
		ether.src_mac = devices[deviceIndex].mac_address;
		ether.dst_mac = broadcast;
		p.datalink = ether;
		return ether;
	}
	
	public void Fire(int deviceIndex, ARPPacket p) {
		try {
			sender=JpcapSender.openDevice(devices[deviceIndex]);
			sender.sendPacket(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		byte[] rep = new byte[] {(byte)1, (byte)2, (byte)3, (byte)4};
		EventHorizon send = new EventHorizon();
		send.openDevice(0);
		ARPPacket test = send.ARPGen(0, rep);
		send.Fire(0, test);
	}
	
}
