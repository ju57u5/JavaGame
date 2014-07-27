package Applet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;



class Server extends Thread{
	JavaGame Game;
	DatagramSocket serverSocket = null;
	ArrayList<String> clients = new ArrayList<String>();
	ArrayList<Integer> clientPorts = new ArrayList<Integer>();
	ArrayList<InetAddress> clientIPs = new ArrayList<InetAddress>();


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

		while (true) {
			try {
				updateClients();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void updateClients () throws IOException {

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];

		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		serverSocket.receive(receivePacket);

		ByteArrayInputStream bais=new ByteArrayInputStream(receiveData);
		DataInputStream dais=new DataInputStream(bais);
		int packetID = dais.readInt();

		switch (packetID) {
		case 0: //PlayerData
			int playerID = dais.readInt();
			int playerx = dais.readInt();
			int playery = dais.readInt();
			int playerhealth = dais.readInt();


			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			DataOutputStream daos=new DataOutputStream(baos);
			daos.writeInt(0); //PacketID

			daos.writeInt(playerID);
			daos.writeInt(playerx);
			daos.writeInt(playery);
			daos.writeInt(playerhealth);

			daos.close();
			sendData = baos.toByteArray();
			System.out.println("[Server] Client "+playerID+" schickt Daten.");
			for (int cou=0;cou<clients.size();cou++) {	
				if (cou != playerID) {

					DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
					serverSocket.send(sendPacket);

				}
			}
			break;
		case 1: //ShotData
			int shotplayerID = dais.readInt();
			
			int shotx = dais.readInt();
			int shoty = dais.readInt();
			boolean rechts = dais.readBoolean();
			int shotspeed = dais.readInt();
			
			ByteArrayOutputStream bao=new ByteArrayOutputStream();
			DataOutputStream dao=new DataOutputStream(bao);
			
			dao.writeInt(1);
			dao.writeInt(shotx);
			dao.writeInt(shoty);
			dao.writeBoolean(rechts);
			dao.writeInt(shotspeed);
			dao.close();
			
			for (int cou=0;cou<clients.size();cou++) {
				if (cou != shotplayerID) {
					DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
					serverSocket.send(sendPacket);
				}
			}
			break;
			
		case 2: //Login

			String packet = dais.readUTF();
			clientPorts.add(receivePacket.getPort());
			clientIPs.add(receivePacket.getAddress());
			System.out.println("[Server] Login from " + clientIPs.get(clientIPs.size()-1).toString() + ":" + clientPorts.get(clientPorts.size()-1) + " "+packet);
			clients.add(packet);

			String id = clients.size()+"";
			sendData = id.getBytes();
			DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, clientIPs.get(clientIPs.size()-1), clientPorts.get(clientPorts.size()-1));
			serverSocket.send(sendPacket);


			break;

		}	



	}

	
}