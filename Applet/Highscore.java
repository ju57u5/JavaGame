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

class Highscore {
  
  JavaGame Game;
  String[] lines ;
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
          System.out.println(line);
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
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }  
  
  
}