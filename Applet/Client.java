package Applet;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class Client extends Thread{
	JavaGame Game;
	int port;
	DatagramSocket clientSocket;
	InetAddress IPAddress;

	public Client(JavaGame Game) {
		this.Game = Game;

	}  

	public boolean initialise(String address, int port) throws SocketException, UnknownHostException, IOException {
		this.port = port;
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName(address);
		byte[] sendData = new byte[1024];
		String packet = "Justus";
		sendData = packet.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		System.out.println("[Client] Message from Server: " + modifiedSentence);

		return true; 
	}

	public void sendPlayerData() {

	}
	
	public void sendNewShot(Shot shot) {
		
	}

	public void updateFromServer() {



	}
	public void run() {
		
	}



}