package Applet;

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

import Applet.*;

class Updater {
  
  int counter = 0;
  int c=0;
  int currentver=-1;
  int ver= -1; 
  //Counter gibt die Art der Textur an:
  //0: Schuss
  //1: Character
  //2: Alles andere
  boolean firstrun = false;
  
  public Updater(JavaGame Game) {
    try {
      File folder = new File(System.getenv("APPDATA")+"\\texture");
      if (!folder.isDirectory()) {
        folder.mkdirs();
      } // end of if
      
      download("http://ju57u5v.tk/JavaGame/list.txt" , System.getenv("APPDATA")+"\\texture");
      
      BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\list.txt")));
      
      File filever = new File(System.getenv("APPDATA")+"\\texture\\version.txt");
      
      if (filever.exists()) {
        BufferedReader brver = new BufferedReader(new FileReader(new File(System.getenv("APPDATA")+"\\texture\\version.txt")));
        String currentversion = brver.readLine();
        currentver = Integer.parseInt(currentversion);
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
          System.out.println(firstrun +" "+ currentver);
          if (currentver<ver || firstrun) {
            download("http://ju57u5v.tk/JavaGame/" + line, System.getenv("APPDATA")+"\\texture");
          }
        } // end of if-else
        if (counter==0 && !line.startsWith("!")) {
          Game.shottexture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
        } // end of if
        if (counter==1 && !line.startsWith("!")) {
          Game.texture[c] = new File(System.getenv("APPDATA")+"\\texture\\"+line);
        } // end of if
        
        c++;
      }
      br.close();
    }
    catch(Exception e) {
      
    }
    
    try {
      PrintWriter writer = new PrintWriter(System.getenv("APPDATA")+"\\texture\\version.txt", "UTF-8");
      writer.println(""+ver);
      writer.println(" ");
      writer.close();
    } catch(Exception e) {
      
    } 
    
  } 
  
  private void download(String fileURL, String destinationDirectory) throws IOException {
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
    
    System.out.print("Downloading " + downloadedFileName);
    while ((bytesRead = is.read(buffer)) != -1) {
      System.out.print(".");  // Progress bar :)
      fos.write(buffer,0,bytesRead);
    }
    System.out.println("done!");
    
    // Close destination stream
    fos.close();
    // Close URL stream
    is.close();
  }  
  
  
}
  
















