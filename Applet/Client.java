package Applet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

class Client extends Thread{
	JavaGame Game;
	int port,id;
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
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(2);
		daos.writeUTF("Justus");
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String gottenPacket = new String(receivePacket.getData());
		System.out.println("[Client]ID from Server: " + gottenPacket);
		id = Integer.parseInt(gottenPacket.trim());
		return true; 
	}

	public void sendPlayerData(int id) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(0); //PacketID

		daos.writeInt(id);
		daos.writeInt(Game.player[id].x);
		daos.writeInt(Game.player[id].y);
		daos.writeInt(Game.player[id].health);

		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

	}

	public void sendNewShot(Shot shot, int shotid) {

	}

	public void updateFromServer() throws IOException {
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		ByteArrayInputStream bais=new ByteArrayInputStream(receiveData);
		DataInputStream dais=new DataInputStream(bais);
		int packetid = dais.readInt();

		switch (packetid) {
		case 0: //PlayerData
			int playerID = dais.readInt();
			int playerx = dais.readInt();
			int playery = dais.readInt();
			int playerhealth = dais.readInt();

			Game.player[playerID].x = playerx;
			Game.player[playerID].y = playery;
			Game.player[playerID].health = playerhealth;
			System.out.println("[Client] Server sendet Daten von Client " + playerID+". "+playerx+" "+playery+ " "+playerhealth);
			break;

		case 1: //New Shot
			int shotplayerID = dais.readInt();
			//int shotID = dais.readInt();
			int shotx = dais.readInt();
			int shoty = dais.readInt();
			boolean rechts = dais.readBoolean();
			int shotspeed = dais.readInt();
			Shot shot = new Shot(Game.player[shotplayerID].shottexture, rechts, shotspeed, Game, Game.player[shotplayerID]);
			shot.laden(shotx, shoty);
			Game.DamageLogig.registerShot(shot);
			break;
		}
	}
	public void run() {
		while (true) {
			try {
				updateFromServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}