package Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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
  
  public Updater(JavaGame Game) {
    try {
      System.out.println("hi");
      for (int c=0;c<Game.textureS.length;c++) {
        System.out.println("http://ju57u5v.tk/JavaGame" + Game.textureS[c]);
        download("http://ju57u5v.tk/JavaGame" + Game.textureS[c], System.getenv("APPDATA")+"\\texture");
      } // end of for
      
      for (int c=0;c<Game.shottextureS.length;c++) {
        download("http://ju57u5v.tk/JavaGame" + Game.shottextureS[c], System.getenv("APPDATA")+"\\texture");
      } // end of for
      download("http://ju57u5v.tk/JavaGame/hintergrund.jpg" , System.getenv("APPDATA")+"\\texture");
      download("http://ju57u5v.tk/JavaGame/perk.png" , System.getenv("APPDATA")+"\\texture");
      download("http://ju57u5v.tk/JavaGame/boom.png" , System.getenv("APPDATA")+"\\texture");
    } catch(IOException e) {
      
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
  
  public void getVersion() {
    
    
    
  }  
}
  
















