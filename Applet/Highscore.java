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
  
  public Highscore(JavaGame Game) {
    this.Game = Game;
    
    
  }
  public void saveNames() {
    try {
      PrintWriter writer = new PrintWriter(System.getenv("APPDATA")+"\\texture\\names.txt", "UTF-8");
      
      for (int c = 1;c<Game.player.length;c++) {
        if (Game.player[c] != null) {
          writer.println(Game.player[c].name+"\n");
        } // end of if
      } // end of for
      
      writer.close();
    } catch(Exception e) {
      
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
  
  
}