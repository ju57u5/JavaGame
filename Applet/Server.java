package Applet;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;



class Server extends Thread{
	JavaGame Game;
	DatagramSocket serverSocket = null;
	ArrayList<String> clients = new ArrayList<String>(); 

	public Server(JavaGame Game) {
		this.Game = Game;
		start();
	}  

	public void run() {

		
		try {
			serverSocket = new DatagramSocket(9876);
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			try {
				serverSocket.receive(receivePacket);
			String packet = new String(receivePacket.getData());
			int port = receivePacket.getPort();
			InetAddress IPAddress = receivePacket.getAddress();
			System.out.println("[Server] Message from " + IPAddress.toString() + ":" +port);
			clients.add(packet);
			
			String sendString = "pong";
			sendData = sendString.getBytes();
			DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateClients () {

	}

	public void sendClients() {


	}  
}