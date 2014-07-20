import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Observable; 
import java.util.Observer;
import java.net.*; 
import javax.sound.sampled.FloatControl;
import javax.swing.*; 
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


public class JavaGameServer {
  ServerSocket serverSocket;
  
  public static void main(String[] args) {
    System.out.println("Gestartet!");
    new JavaGameServer();
  } // end of main
  
  public JavaGameServer() {
    try {
      serverSocket = new ServerSocket(8080);
    } catch(Exception e) {
      e.printStackTrace();
    } 
    
    while (true) { 
      try {
        Socket socket = serverSocket.accept();
        DataInputStream dIn = new DataInputStream(socket.getInputStream());
        System.out.println("Connected");
        int score = dIn.readInt();
        String name = dIn.readUTF();
        
        BufferedReader brver = new BufferedReader(new FileReader(new File("score.txt")));
        String currentscore = brver.readLine();
        int currentIScore = Integer.parseInt(currentscore);
        String currentname = brver.readLine();
        brver.close();
        System.out.println("Aktueller Score: "+currentIScore);
        System.out.println("Gesendeter Score: "+score);
        if (score > currentIScore) {
          System.out.println("Neuer Score!!!!!!!!!:"+score);
          PrintWriter writer = new PrintWriter("score.txt", "UTF-8");
          writer.println(""+score);
          writer.println(name);
          writer.close();
        } // end of if
        
        
      } catch(Exception e) {
        e.printStackTrace();
      } 
    } // end of while
    
  }  
} // end of class JavaGameServer
