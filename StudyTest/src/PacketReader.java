import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.packet.Packet;


public class PacketReader {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		JpcapCaptor captor=JpcapCaptor.openFile("test");
		

		while(true) {
			Packet readPacket = captor.getPacket();
			if(readPacket==null || readPacket==Packet.EOF) break;
			System.out.println(readPacket);
			captor.close();

	}

	}
}
