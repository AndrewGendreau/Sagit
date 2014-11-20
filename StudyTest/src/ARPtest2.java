import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;


public class ARPtest2 {

	/**
	 * @param args
	 */
	
	public ARPPacket arp(NetworkInterface Interface, byte[] targetIP, byte[] senderIP, byte[] targetHardware) {
		
		ARPPacket p = new ARPPacket();
		p.hlen = 6;
		p.plen = 4;
		p.hardtype = ARPPacket.HARDTYPE_ETHER;
		p.operation = ARPPacket.ARP_REQUEST; 
		p.sender_hardaddr = Interface.mac_address;
		p.prototype = ARPPacket.PROTOTYPE_IP; 
		p.sender_protoaddr = senderIP;
		p.target_protoaddr = targetIP;
		p.target_hardaddr = targetHardware;
		
		return p;
		
	}
	
	public EthernetPacket ether(byte[] targetHardware, NetworkInterface Interface, ARPPacket p) {
		
		EthernetPacket ether = new EthernetPacket();
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;
		ether.src_mac = Interface.mac_address;
		ether.dst_mac = targetHardware;
		p.datalink=ether;
		
		return ether;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	}

}
