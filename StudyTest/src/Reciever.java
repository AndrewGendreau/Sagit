import jpcap.PacketReceiver;

import jpcap.packet.Packet;


public class Reciever implements PacketReceiver { //Implements the PacketReciever interface 

//The interface has one method and is designed to process packets

	@Override
	public void receivePacket(Packet packet) { //Receives a packet and prints it out
		// TODO Auto-generated method stub
		System.out.println(packet);
		
	}
}
