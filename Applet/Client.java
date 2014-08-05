package Applet;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
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

import javax.imageio.ImageIO;

import com.sun.org.apache.xml.internal.serializer.ToTextSAXHandler;


class Client extends Thread{
	JavaGame Game;
	int port,id;
	DatagramSocket clientSocket;
	InetAddress IPAddress;

	public Client(JavaGame Game) {
		this.Game = Game;

	}  

	/**
	 * Initialisiert eine Verbindung mit einem GameServer mit <Ipadresse>adress und <Port>port
	 * @param address Serveradress
	 * @param port Port
	 * @return true
	 * @throws SocketException
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public boolean initialise(String address, int port) throws SocketException, UnknownHostException, IOException {
		this.port = port;
		clientSocket = new DatagramSocket();
		IPAddress = InetAddress.getByName(address);
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(2);
		daos.writeUTF(Game.onlinename);
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);

		ByteArrayInputStream bais=new ByteArrayInputStream(receiveData);
		DataInputStream dais=new DataInputStream(bais);
		id = dais.readInt();
		int playersize = dais.readInt();

		System.out.println("[Client]ID from Server: " + id);


		for (int c=1;c<Game.player.length;c++) {
			if (c <= playersize&& c!=id) {
				Game.player[c] = new ClientPlayer(Game.texture[1],Game.shottexture[1],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,c,35,"Online Player "+c);
				Game.addKeyListener(Game.player[c]);
				Game.player[c].laden(Game,-1000,100);
			}
			else if (c==id) {
				Game.player[c] = new Player(Game.texture[1],Game.shottexture[1],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,c,35,Game.onlinename);
				Game.addKeyListener(Game.player[c]);
				Game.player[c].laden(Game,100,100);
			}
			else if (c>playersize) {
				Game.player[c]=null;
			}
		}
		return true; 
	}

	/**
	 * Sendet die Onlinedaten eines Spielers an den Server.
	 * @param id Die SlotID des Spielers
	 * @throws IOException
	 */
	public void sendPlayerData(int id) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(0); //PacketID

		daos.writeInt(id);
		daos.writeInt(Game.player[id].x);
		daos.writeInt(Game.player[id].y);
		daos.writeInt(Game.player[id].health);
		daos.writeBoolean(Game.player[id].characterInverted);
		daos.writeUTF(Game.player[id].name);

		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		//		System.out.println("[Client] Sende Spielerdata. Health: "+Game.player[id].health);


	}

	/**
	 * @param shot Schuss
	 * @param shotplayerid SchussID
	 * @param shotx SchussPositionX
	 * @param shoty SchussPositionY
	 * @param rechts Ist der Schuss invertiert?
	 * @param shotspeed Geschwindigkeit des Schusses
	 * @throws IOException
	 */
	public void sendNewShot(Shot shot, int shotplayerid, int shotx, int shoty, boolean rechts, int shotspeed) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(1); //PacketID

		daos.writeInt(shotplayerid);
		daos.writeInt(shotx);
		daos.writeInt(shoty);
		daos.writeBoolean(rechts);
		daos.writeInt(shotspeed);

		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		//		System.out.println("[Client] Neuer Schuss: "+shot.x+" "+shot.y+" "+shot.rechts+" ID: "+shotplayerid);
	}

	/**
	 * Sendet ein DisconnectPacket an den Server.
	 * @throws IOException
	 */
	public void sendDisconnect() throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(3); //PacketID

		daos.writeInt(this.id);
		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);

	}

	/**
	 * Sendet die Daten eines neuen Schusses an den Server
	 * @param shottexture Schuss der gesendet werden soll
	 * @param textureid Id der Schusstextur
	 * @throws IOException
	 */
	public void sendNewTexture(boolean shottexture, int textureid) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(6); //PacketID
		daos.writeBoolean(shottexture);
		daos.writeInt(textureid);
		daos.writeInt(id);
		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		System.out.println("[Client] Sende neue Texture "+textureid+" "+shottexture);
	}

	/**
	 * Sendet ein ChatPacket an den Server.
	 * @param name
	 * @param message
	 * @throws IOException
	 */
	public void sendNewChatMessage(String name, String message) throws IOException {
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(7); //PacketID
		daos.writeUTF(name);
		daos.writeUTF(message);
		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
	}
	
	/**
	 * Sendet ein Statuspacket an einen Spielserver und gibt den Onlinestatus von diesem zurück.
	 * @param String ipAddress
	 * @param int port
	 * @return boolean
	 * @throws IOException
	 */
	public boolean getOnlineStatus(String ipAddress, int port) throws IOException {
		clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(100);
		IPAddress = InetAddress.getByName(ipAddress);
		byte[] sendData = new byte[1024];
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		DataOutputStream daos=new DataOutputStream(baos);
		daos.writeInt(8); //PacketID
		daos.close();
		sendData = baos.toByteArray();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		clientSocket.send(sendPacket);
		
		byte[] receiveData = new byte[1024];
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		
		ByteArrayInputStream bais=new ByteArrayInputStream(receiveData);
		DataInputStream dais=new DataInputStream(bais);
		
		boolean online = dais.readBoolean();
		return online;
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
			boolean playerori = dais.readBoolean();
			String playername = dais.readUTF();

			if (Game.player[playerID] != null) {
				Game.player[playerID].x = playerx;
				Game.player[playerID].y = playery;
				Game.player[playerID].health = playerhealth;
				Game.player[playerID].characterInverted = playerori;
				Game.player[playerID].name = playername;
			}
			//			System.out.println("[Client] Server sendet Daten von Client " + playerID+". "+playerx+" "+playery+ " "+playerhealth+" "+playerori+" "+playername);
//			if (playerhealth<=0) {
//				if (!Game.gamerunner.tot[playerID]) {
//					writeToChat(playername+" died.");
//				}
//				Game.gamerunner.tot[playerID]=true;
//			}
			break;

		case 1: //New Shot
			int shotplayerID = dais.readInt();
			int shotx = dais.readInt();
			int shoty = dais.readInt();
			boolean rechts = dais.readBoolean();
			int shotspeed = dais.readInt();
			//			System.out.println("[Client] Schuss angekommen "+shotx+" "+shoty+" "+shotplayerID);
			Shot shot = new Shot(Game.player[shotplayerID].shottexture, rechts, shotspeed, Game, Game.player[shotplayerID]);
			shot.laden(shotx, shoty);

			break;
		case 2: //Player connected
			int connectplayerID = dais.readInt();


			Game.player[connectplayerID] = new ClientPlayer(Game.texture[1],Game.shottexture[1],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,connectplayerID,35,"Online Player "+connectplayerID);
			Game.addKeyListener(Game.player[connectplayerID]);
			Game.player[connectplayerID].laden(Game, 100, 100);

			System.out.println("[Client] Spieler mit der ID "+ connectplayerID+" connected.");
			writeToChat( dais.readUTF() + " connected.");
			break;
		case 3: //disconnect
			int disconnectId = dais.readInt();

			Game.player[disconnectId] = null;
			writeToChat( dais.readUTF() + " disconnected.");
			System.out.println("[Client] Spieler mit der ID "+ disconnectId+" disconnected.");
			break;
		case 4: //new perk
			//			System.out.println("[Client] Empfange Perk");
			int perkx = dais.readInt();
			int perky = dais.readInt();
			int perkart = dais.readInt();

			Game.gamerunner.perk[Game.gamerunner.count] = new perks(Game.gamerunner.perktexture, Game, perkx, perky, perkart);
			Game.gamerunner.count++;
			break;
		case 5: //Restart Packet

			System.out.println("[Client] Restart!!!!!!!!!!!!!!");
			Game.gamerunner.running=false;
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
			}
			for (int c=1;c<Game.player.length;c++) {
				if (Game.player[c] != null) {
					//Reihenfolge wichtig!!! Erst health dann controls
					Game.player[c].x=(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]);
					Game.player[c].y=0;
					Game.player[c].health=100;
					Game.player[c].jumpheigth=200;
					Game.player[c].speed=5;
					Game.player[c].sperrzeit=40;
					Game.player[c].freezeControls=false;
					Game.player[c].amstartwarten=42;
					Game.player[c].perkzählerjump=-1;
					Game.player[c].perkzählerrun=-1;
					Game.player[c].perkzählershoot=-1;
					Game.player[c].boomRight=0;
					Game.player[c].boomUp=0;
				    Game.player[c].boomLeft=0;
				}
			}
			Game.player[id].x=(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]);
			Game.player[id].y=0;
			Game.player[id].jumpheigth=200;
			Game.player[id].speed=5;
			Game.player[id].sperrzeit=40;
			Game.player[id].freezeControls=false;
			Game.player[id].health=100;
			sendPlayerData(id);
			Game.gamerunner.running=true;
			break;
		case 6: //new Texture
			boolean shottexture = dais.readBoolean();
			int textureid = dais.readInt();
			int playerid = dais.readInt();
			//			System.out.println("[Client] Neue Texture: "+textureid+" "+shottexture+" "+playerid);
			if (shottexture) {
				Game.player[playerid].shottexture = Game.shottexture[textureid];
			}
			else {
				try {
					BufferedImage Image = ImageIO.read(Game.texture[textureid]);
					Game.player[playerid].textureImage  = Image;
					Game.player[playerid].textureImageb = Game.player[playerid].verticalflip(Image);
				} 
				catch(IOException exeption) {

				}
			}
			break;
		case 7: //New Chat Message
			String name = dais.readUTF();
			String message = dais.readUTF();
			writeToChat("["+name+"]: "+message);
			System.out.println("[Client] Neue Chatnachricht: "+"["+name+"]: "+message);
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
	
	public void writeToChat(String Message) {
		for (int c=Game.gamerunner.chatMessages.length-2;c>=0;c--) {
			Game.gamerunner.chatMessages[c+1]=Game.gamerunner.chatMessages[c];
	}
	Game.gamerunner.chatMessages[0] = Message;
	}



}