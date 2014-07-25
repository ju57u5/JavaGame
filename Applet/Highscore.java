package Applet;

import java.io.*;
import java.net.*;


class Highscore {

	JavaGame Game;
	String[] lines ;
	int[] scores = new int[10];
	String[] names = new String[10];
	boolean failed;

	public Highscore(JavaGame Game) {
		this.Game = Game;


	}
	public void saveNames() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\names.txt")));

			lines = new String [Game.player.length];
			int cou = 1;

			while (((lines[cou] = br.readLine()) != null )&& cou<lines.length-1) {
				cou += 1;
			} 
			br.close(); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		try {
			PrintWriter writer = new PrintWriter(System.getenv("APPDATA")+"\\texture\\names.txt", "UTF-8");
			for (int c = 1;c<Game.player.length;c++) {
				if (Game.player[c] != null && !Game.player[c].name.equals("Bot") && !Game.player[c].name.equals("Spieler "+c)) {
					lines[c]=Game.player[c].name;
				} // end of if
			} // end of for

			for (int c = 1;c<Game.player.length;c++) {
				if (lines[c] != null) {
					writer.println(lines[c]);
				}  
			} // end of for

			writer.close();
		} catch(Exception e) {
			e.printStackTrace();
		} 



	}

	public String getName(int pos) {
		String name = "Spieler "+pos;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\names.txt")));
			for (int c = 1;c<Game.player.length;c++) {
				String line = br.readLine();
				if (line == null) {

				} // end of if
				else if (c==pos) {
					name = line;
					br.close();
					return name;
				} // end of if
			} // end of for
			br.close();
		} catch(Exception e) {

		} 

		return name;
	}

	public void sendHighscore(String name, int score) {

		try {
			Socket echoSocket = new Socket("ju57u5v.tk", 8080);
			DataOutputStream dOut = new DataOutputStream(echoSocket.getOutputStream());
			dOut.writeInt(score);
			dOut.writeUTF(name);
			echoSocket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}  

	public void getHighscore() {
		
		File folder = new File(System.getenv("APPDATA")+"\\texture\\scores"); //Wir müssen erst gucken ob der Ordner existiert
		if (!folder.isDirectory()) {
			folder.mkdirs();
		} // end of if
		
		for (int c=0;c<10;c++) {
			try {
				Game.updater.download("http://ju57u5v.tk/JavaGame/Server/"+(c+1)+".txt",System.getenv("APPDATA")+"\\texture\\scores", false);
			} catch (IOException e) {
				e.printStackTrace();
				failed=true;
				return;
			}  
			BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\scores\\"+(c+1)+".txt")));

				try {
					scores[c] = Integer.parseInt(br.readLine());
					names[c] = br.readLine();
				} catch (NumberFormatException | IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}



		}



	}




}