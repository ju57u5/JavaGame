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
        
        for (int c=1;c<=10;c++) {
          BufferedReader brver = new BufferedReader(new FileReader(new File(c+".txt")));
          String currentscore = brver.readLine();
          int currentIScore = Integer.parseInt(currentscore);
          String currentname = brver.readLine();
          brver.close();
          System.out.println("Aktueller Score: "+currentIScore+" Platz:"+c);
          if (score > currentIScore) {
            for (int cou=c;cou<=10;cou++ ) {
              System.out.println("Gesendeter Score: "+score+" Platz:"+cou);
              
              BufferedReader br = new BufferedReader(new FileReader(new File(cou+".txt")));
              String altscore = br.readLine();
              int altIScore = Integer.parseInt(currentscore);
              String altname = br.readLine();
              brver.close();
              
              PrintWriter writer = new PrintWriter((cou+1)+".txt", "UTF-8");
              writer.println(""+altIScore);
              writer.println(altname);
              writer.close();
              
            } // end of for
            
            System.out.println("Neuer Score!!!!!!!!!:"+score+" Auf Platz:"+c);
            PrintWriter writer = new PrintWriter(c+".txt", "UTF-8");
            writer.println(""+score);
            writer.println(name);
            writer.close();
            
            
            break;
          } // end of if
          
        } // end of for
        
        
      } catch(Exception e) {
        e.printStackTrace();
      } 
    } // end of while
    
  }  
} // end of class JavaGameServer
