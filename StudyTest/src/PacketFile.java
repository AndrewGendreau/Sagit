import java.io.IOException;

import jpcap.JpcapCaptor;
import jpcap.JpcapWriter;
import jpcap.packet.Packet;


public class PacketFile {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		jpcap.NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 0; //My wireless interface's index
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 1028, false, 1);
		
		JpcapWriter writer=JpcapWriter.openDumpFile(captor, "test");
		
		for(int i = 0; i < 100; i++) {
		Packet packet = captor.getPacket();
		writer.writePacket(packet);
		}
		writer.close();
		}
	}


