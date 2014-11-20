package sagittarius;

import java.io.IOException;

import sagittarius.EventHorizon;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

public class SagitInject {

	private JpcapSender sender; // captor instance variable
	private jpcap.NetworkInterface[] devices; // Network device array instance
												// variable
	private int deviceIndex; // Index of device you want to use
	short hardwareOctets = 6;
	short protocolOctets = 4;
	byte[] target;
	byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255,
			(byte) 255, (byte) 255, (byte) 255 };
	ARPPacket ARP;
	EthernetPacket ether;

	private boolean done = false;
	private int packetsRecieved = 0;
	private boolean isDisposed = false;

	// Future Abstract methods
	public void openDevice(int index) { // Opens the device takes in index you
										// want to use
		deviceIndex = index; // Sets the index to the parameter

		try { // beginning of a try catch block
			sender = JpcapSender.openDevice(devices[deviceIndex]); // Opens the
																	// selected
																	// device
		} catch (IOException e) { // catches an input output error
			// TODO Auto-generated catch block
			e.printStackTrace(); // prints the throwable
		}
	}

	public void dispose() { // Method to close the open device
		sender.close(); // Closes the device
		isDisposed = true;
	}

	public boolean isDisposed() {
		return isDisposed;
	}

	public int getDeviceCount() { // Method to get the number of available
									// devices
		return devices.length; // returns the length of devices
	}

	public int getCurrentDevice() { // Method to get the index of the current
									// device
		return deviceIndex; // Returns the deviceIndex instance variable
	}

	public SagitInject() { // Default Constructor
		devices = JpcapCaptor.getDeviceList();
	}
	

	public byte[] getInterfaceMAC(int index) { // Helper Method to get the
												// interface MAC

		return devices[deviceIndex].mac_address;
	}

	public EthernetPacket etherGen(int deviceIndex, ARPPacket p) { // Helper
																	// method to
																	// create
																	// the frame

		ether = new EthernetPacket();
		ether.frametype = EthernetPacket.ETHERTYPE_ARP;
		ether.src_mac = devices[deviceIndex].mac_address;
		ether.dst_mac = broadcast;
		p.datalink = ether;
		return ether;
	}

	public void inject(ARPPacket p) { // Injects the poison on the network
		try {
			sender = JpcapSender.openDevice(devices[deviceIndex]);
			sender.sendPacket(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		SagitInject test = new SagitInject();
		test.openDevice(0);
		byte[] rep = new byte[] { (byte) 10, (byte) 1, (byte) 1, (byte) 1 };

	}
}
