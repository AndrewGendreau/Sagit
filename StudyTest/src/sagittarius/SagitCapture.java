package sagittarius;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.Packet;

public class SagitCapture {

	private JpcapCaptor captor; //captor instance variable
	private jpcap.NetworkInterface[] devices; //Network device array instance variable
	private int deviceIndex; //Index of device you want to use

	String filter = null;
	
	private boolean done = false;
	private int packetsRecieved = 0;
	private boolean isDisposed = false;
	
	private Packet packet; //Incoming packet variable
	
	public SagitCapture(){ //Default constructor
		devices = JpcapCaptor.getDeviceList(); //Feeds devices all network interfaces on the machine
	}
	
	public SagitCapture(String filter){
		this();
		this.filter = filter;
	}
	
	public SagitCapture(String filter, int deviceIndex){
		this(filter);
		openDevice(deviceIndex);
	}
	
	public void openDevice(int index){ //Opens the device takes in index you want to use
		deviceIndex = index; //Sets the index to the parameter
		
		try { //beginning of a try catch block
			captor = JpcapCaptor.openDevice(devices[index], 1028, false, 20 ); //Opens the selected device
		} catch (IOException e) { //catches an input output error
			// TODO Auto-generated catch block
			e.printStackTrace(); //prints the throwable
		}
		if (filter != null){

			try {
				captor.setFilter(filter, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public void dispose(){ //Method to close the open device
		System.out.println("Captor - " + captor);
		captor.breakLoop();
		captor.close(); //Closes the device
		isDisposed = true;
	}

	public boolean isDisposed(){
		return isDisposed;
	}
	
	public int getDeviceCount() { //Method to get the number of available devices
		return devices.length; //returns the length of devices
	}

	public NetworkInterface[] getDevices(){
		return devices;
	}
	
	public int getCurrentDevice(){ //Method to get the index of the current device
		return deviceIndex; //Returns the deviceIndex instance variable
	}
	
	public Thread acquirePackets(final List<Packet> buffer, final int count) { //Method to hold the received packets in an arrayList of packets

		//ArrayList of packets called List
		
		final PacketReceiver receiver = new PacketReceiver() { //Declaration of a new packetReciever called receiver
			
			@Override
			public void receivePacket(Packet packet) { //Method to process the packet
				
				synchronized (buffer) {
					buffer.add(packet); //Adds the packet to the ArrayList					
				}
				if(count > -1) {
					System.out.println("received packet.."); //Prints a line to confirm packet was received 
				}
				if (done || packetsRecieved == count)
					captor.breakLoop();
			
				packetsRecieved++;
			}
		}; //End of InnerClass
		
		//captor.loopPacket(count, receiver);
		Runnable runnable = new Runnable(){

			@Override
			public void run() {
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
		return snifferThread;
	}
	
	public void acquirePacketsTimed(final List<Packet> buffer,
							final int count, long milliseconds) {
		Thread sniffer = acquirePackets(buffer, count);
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		done = true;
	}
	
	public int getPacketsReceived(){
		return packetsRecieved;
	}
	
	public void cancelLoop(){
		done = true;
	}
	
	public void setFilter(String filter){
		if (captor != null)
			try {
				captor.setFilter(filter, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/*
	public static void main(String[] args){ //Main Method
		SagitCapture cap = new SagitCapture(); //Creates a new captor object
		cap.openDevice(0); //Opens the device 1
		List<Packet> buffer = Collections.synchronizedList(new ArrayList<Packet>()); //Creates a new list for the packets
		cap.acquirePackets(buffer, -1); //making it crash find the problem
	}
	*/
}
