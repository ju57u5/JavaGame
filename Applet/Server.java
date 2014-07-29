package Applet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;



class Server extends Thread{
	DatagramSocket serverSocket = null;
	int[] healths = new int[99];
	int[] lastupdates = new int[99];
	int tickzähler = 0;
	int restarttick=0;
	int disconnectcounter=0;
	ArrayList<String> clients = new ArrayList<String>();
	ArrayList<Integer> clientPorts = new ArrayList<Integer>();
	ArrayList<InetAddress> clientIPs = new ArrayList<InetAddress>();


	public Server() {
		clients.add("");
		clientPorts.add(null);
		clientIPs.add(null);
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
			//Perk wird vom Server ermittelt
			int perkjn= (int) (Math.random()*3000+1);
			if (perkjn<9) {//Standart 9
				int perkx= (int) (Math.random()*1000+1);
				int perky= (int) (Math.random()*400+100);
				int perkw = (int) (Math.random()*5+1);
				try {
					System.out.println("[Server] Sende Perk.");
					sendNewPerk(perkx,perky,perkw);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} // end of if
			int totencounter=0;
			for (int i = 1; i < clients.size(); i++) {
				if (healths[i]<=0) {
					totencounter++;
				}
			}
			System.out.println("[Server] Tote: "+totencounter+" Spieler: "+(clients.size()-1-disconnectcounter)+" Diconnected: "+disconnectcounter);
			if (totencounter==clients.size()-1-disconnectcounter && tickzähler-restarttick>10) {
				
				try {
					System.out.println("[Server] Restart");
					restarttick=tickzähler;
					sendRestart();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					Thread.sleep(1);
				} catch (Exception e) {
				}
			}
			checkTimeout();
			tickzähler++;
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
			//Health wird gespeichert
			healths[playerID]=playerhealth;
			//Update wird gespeichert
			lastupdates[playerID] = tickzähler;
			boolean playerori = dais.readBoolean();
			String playername = dais.readUTF();

			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			DataOutputStream daos=new DataOutputStream(baos);
			daos.writeInt(0); //PacketID

			daos.writeInt(playerID);
			daos.writeInt(playerx);
			daos.writeInt(playery);
			daos.writeInt(playerhealth);
			daos.writeBoolean(playerori);
			daos.writeUTF(playername);

			daos.close();
			sendData = baos.toByteArray();
			System.out.println("[Server] Client "+playerID+" schickt Daten. Health: " +playerhealth);
			for (int cou=1;cou<clients.size();cou++) {	
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
			dao.writeInt(shotplayerID);
			dao.writeInt(shotx);
			dao.writeInt(shoty);
			dao.writeBoolean(rechts);
			dao.writeInt(shotspeed);
			dao.close();
			sendData = bao.toByteArray();
			
			for (int cou=1;cou<clients.size();cou++) {
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

			String id = clients.size()-1+"";
			sendData = id.getBytes();
			DatagramPacket sendPacket =	new DatagramPacket(sendData, sendData.length, clientIPs.get(clientIPs.size()-1), clientPorts.get(clientPorts.size()-1));
			serverSocket.send(sendPacket);
			
			ByteArrayOutputStream ba=new ByteArrayOutputStream();
			DataOutputStream da=new DataOutputStream(ba);

			da.writeInt(2);
			da.writeInt(clientIPs.size()-1);
			da.close();
			sendData = ba.toByteArray();
			
			for (int cou=1;cou<clients.size();cou++) {
				if (cou != clientIPs.size()-1) {
					DatagramPacket sendPacket1 =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
					serverSocket.send(sendPacket1);
				}
			}
			
			break;
		case 3: //Disconnect
			int disconnectId = dais.readInt();
			
			ByteArrayOutputStream ba1=new ByteArrayOutputStream();
			DataOutputStream da1=new DataOutputStream(ba1);
			da1.writeInt(3);
			da1.writeInt(disconnectId);
			
			da1.close();
			sendData = ba1.toByteArray();
			
			for (int cou=1;cou<clients.size();cou++) {
				if (cou != clientIPs.size()-1) {
					DatagramPacket sendPacket1 =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
					serverSocket.send(sendPacket1);
				}
			}
			break;
		case 4: //New Perk
			// Server muss nichts machen
			break;
		case 5: //Restart Packet
			break;
		case 6: //new Texture
			boolean shottexture = dais.readBoolean();
			int textureid = dais.readInt();
			int playerid = dais.readInt();
			
			ByteArrayOutputStream ba2=new ByteArrayOutputStream();
			DataOutputStream da2=new DataOutputStream(ba2);
			da2.writeInt(6);//PacketID
			da2.writeBoolean(shottexture);
			da2.writeInt(textureid);
			da2.writeInt(playerid);
			da2.close();
			sendData = ba2.toByteArray();
			
			for (int cou=1;cou<clients.size();cou++) {
				if (cou != playerid) {
					DatagramPacket sendPacket1 =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
					serverSocket.send(sendPacket1);
				}
			}
			break;
		
		}	



	}
	public void sendNewPerk(int x,int y,int art) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream ba1=new ByteArrayOutputStream();
		DataOutputStream da1=new DataOutputStream(ba1);
		da1.writeInt(4); //PacketID
		da1.writeInt(x);
		da1.writeInt(y);
		da1.writeInt(art);
		
		da1.close();
		sendData = ba1.toByteArray();
		
		for (int cou=1;cou<clients.size();cou++) {
				DatagramPacket sendPacket1 =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
				serverSocket.send(sendPacket1);
		}
	}
	
	public void sendRestart() throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream ba1=new ByteArrayOutputStream();
		DataOutputStream da1=new DataOutputStream(ba1);
		da1.writeInt(5); //PacketID
		
		
		da1.close();
		sendData = ba1.toByteArray();
		
		for (int cou=1;cou<clients.size();cou++) {
				DatagramPacket sendPacket1 =	new DatagramPacket(sendData, sendData.length, clientIPs.get(cou), clientPorts.get(cou));
				serverSocket.send(sendPacket1);
		}
		for (int c=0;c<healths.length;c++) {
			healths[c]=100;
		}
	}
	
	public void checkTimeout() {
		int disconnectcounter=0;
		for (int c=1;c<clients.size();c++) {
			if (tickzähler-lastupdates[c]>150 && tickzähler-restarttick > 20) {
				disconnectcounter++;
			}
		}
		this.disconnectcounter=clients.size()-2-disconnectcounter;
	}
	
}	

	