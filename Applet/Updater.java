package Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*; 
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JProgressBar;

class Updater extends Frame{

	int counter = 0;
	int c=0;
	int currentver=-1;
	int ver= -1; 
	//Counter gibt die Art der Textur an:
	//0: Schuss
	//1: Character
	//2: Alles andere
	boolean firstrun = false;
	String arg;
	TextArea console;
	JavaGame Game;
	String listenName = "listneu.txt";
	JProgressBar progressBar;
	int fileSize=0, doneSize=0;

	class WindowListener extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			e.getWindow().dispose();                   // Fenster "killen"
			System.exit(0);
		}
	}

	public Updater(JavaGame Game) {
		this.Game = Game;

		setTitle("JavaGame");  
		setSize(1200,900);                            
		addWindowListener(new WindowListener());
		setLocationRelativeTo(null);                                        
		
		console = new TextArea("",1200,900,TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		try {
			arg = Game.args[0]  ;
		}
		catch (ArrayIndexOutOfBoundsException e){
			arg="nothing";
		}

		if (arg.equals("dev")) {
			offlineUpdate();
		}
		else {	
			try {
				File folder = new File(System.getenv("APPDATA")+"\\texture");
				if (!folder.isDirectory()) {
					folder.mkdirs();
				} // end of if
				
				download("http://ju57u5v.tk/JavaGame/" + listenName , System.getenv("APPDATA")+"\\texture", false);
				try {
					fileSize = getContentSize();
					System.out.println(fileSize);
				} catch (Exception e) {
					e.printStackTrace();
				}
				add(console, BorderLayout.CENTER);
				progressBar = new JProgressBar(0, fileSize);
				progressBar.setValue(0);
				progressBar.setStringPainted(true);
				add(progressBar, BorderLayout.PAGE_END);
				setVisible(true);

				
				BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\" + listenName)));

				File filever = new File(System.getenv("APPDATA")+"\\texture\\version.txt");

				if (filever.exists()) {
					BufferedReader brver = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\version.txt")));
					String currentversion = brver.readLine();
					currentver = Integer.parseInt(currentversion);
					brver.close();
				} // end of if
				else {
					firstrun = true;
					currentver = -1;
				} // end of if-else

				String line;
				String version = br.readLine();
				ver = Integer.parseInt(version);


				while ((line = br.readLine()) != null) {
					if (line.startsWith("!")) { 
						counter += 1;
						c=-1;
					} // end of if
					else {
						if ((currentver<ver || firstrun) && !line.startsWith("#")) {
							download("http://ju57u5v.tk/JavaGame/" + line, System.getenv("APPDATA")+"\\texture", true);
						}
					} // end of if-else
					if (counter==0 && !line.startsWith("!")) {
						Game.shottexture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
					} // end of if
					if (counter==1 && !line.startsWith("!")) {
						Game.texture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
					} // end of if
					if (line.startsWith("#")) {
						console.append("\n"+line.replace("#",""));
						Game.nachricht[c]=line.replace("#","");
					}

					c++;
				}
				br.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				System.out.println("Keine Verbindung");
				offlineUpdate();  //onlineVerbindung ist schiefgegangen
			}

			try {
				PrintWriter writer = new PrintWriter(System.getenv("APPDATA")+"\\texture\\version.txt", "UTF-8");
				writer.println(""+ver);
				writer.println(" ");
				writer.close();
			} catch(Exception e) {

			} 

			if (currentver<ver || firstrun ) {
				console.append("\nPlease start the Game again, to load the update.");

				while (true) { 
					try {
						Thread.sleep( 2000 );
					} catch (InterruptedException e) {}
					console.append(".");
				} // end of while
			} // end of if
		} // end of if
		this.dispose();



	} 

	public void download(String fileURL, String destinationDirectory, boolean processBarEnabled) throws IOException {
		// File name that is being downloaded
		String downloadedFileName = fileURL.substring(fileURL.lastIndexOf("/")+1);

		// Open connection to the file
		URL url = new URL(fileURL);
		InputStream is = url.openStream();
		// Stream to the destionation file
		FileOutputStream fos = new FileOutputStream(destinationDirectory + "/" + downloadedFileName);

		// Read bytes from URL to the local file
		byte[] buffer = new byte[4096];
		int bytesRead = 0;

		console.append("\nDownloading " + downloadedFileName);
		while ((bytesRead = is.read(buffer)) != -1) {
			console.append(".");  // Progress bar :)
			doneSize += bytesRead;
			if (processBarEnabled) {
				this.progressBar.setValue(doneSize);
			}
			fos.write(buffer,0,bytesRead);
		}
		console.append("done!");

		// Close destination stream
		fos.close();
		// Close URL stream
		is.close();
	}  

	public static boolean testInet(String site) {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress(site,80);
		try {
			sock.connect(addr,3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {sock.close();}
			catch (IOException e) {}
		}
	}

	public void offlineUpdate() {
		try {

			File folder = new File(System.getenv("APPDATA")+"\\texture");
			if (!folder.isDirectory()) {
				folder.mkdirs();
			} // end of if

			BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\" + listenName)));
			File filever = new File(System.getenv("APPDATA")+"\\texture\\version.txt");

			if (filever.exists()) {
				BufferedReader brver = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\version.txt")));
				String currentversion = brver.readLine();
				currentver = Integer.parseInt(currentversion);
				brver.close();
			} // end of if
			else {
				firstrun = true;
				currentver = -1;
			} // end of if-else

			String line;
			String version = br.readLine();
			ver = Integer.parseInt(version);

			while ((line = br.readLine()) != null) {
				if (line.startsWith("!")) { 
					counter += 1;
					c=-1;
				} // end of if
				if (counter==0 && !line.startsWith("!")) {
					Game.shottexture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
				} // end of if
				if (counter==1 && !line.startsWith("!")) {
					Game.texture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
				} // end of if
				c++;
			}
			br.close();
		} catch(Exception ex) {
			//console.append("Es konnte nicht gestartet werden, da möglicherweise Spieldateien fehlen.");
			//while (true) {}
		}

	}
	
	public int getFileSize(String urlString) throws Exception {
		  int size;
	      URL url = new URL(urlString);
	      URLConnection conn = url.openConnection();
	      size = conn.getContentLength();
	      if (size < 0) {
	    	  return 0;
	      }
	      else {
	      conn.getInputStream().close();
	      return size;
	      }
	}
	
	public int getContentSize() throws Exception {
		int size=0;
		BufferedReader br = null;
		br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\" + listenName)));
		String line;
		br.readLine();

		while ((line = br.readLine()) != null) {
			if (!line.startsWith("!") && !line.startsWith("#")) {
				size += getFileSize("http://ju57u5v.tk/JavaGame/" + line);
			} // end of if
		}
		br.close();
		
		return size;
	}
}




