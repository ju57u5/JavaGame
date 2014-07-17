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
import Applet.*;

  ////// Standart Thread für das aktualisieren aller Komponenten
  
class GameRunner extends Thread {
  // Anfang Attribute5
  Player player[] = null;
  Shot shot[] = new Shot[1000];
  JavaGame Game ;
  boolean isthereshot = false;
  boolean running = true;
  File perktexture ;
  perks perk[] = new perks[100000];
  int count=0;
  int time =(int) System.currentTimeMillis()*1000;
  int maxFPS = 30;
  
  // Ende Attribute5
  
  public GameRunner (Player[] player, JavaGame Game) {
    this.player = player;
    this.Game = Game;
    perktexture = new File(Game.basePath,"/perk.png");
    this.start();
  }  
  // Anfang Komponenten5
  // Ende Komponenten5
  // Anfang Methoden5
  
  public void run() {
    while (true) {
      synchronized(getClass()) { 
        try {
          sleep(1000/maxFPS);
        }
        catch(InterruptedException e) {
        }
        if (running) {
          Game.repaint();
          Game.dbImage.getGraphics().clearRect(0,0, (int)Game.getWidth(), (int)Game.getHeight());
          
          Game.dbImage.getGraphics().drawImage(Game.backgroundImage,100,200-67,Game);
          
          int perkjn= (int) (Math.random()*3000+1);
          if (perkjn<5) {
            int perkx= (int) (Math.random()*1000+1);
            int perky= (int) (Math.random()*400+100);
            perk[count] = new perks(perktexture, false, 10, Game, perkx, perky); 
            count++;
            
          } // end of if
          
          for (int counter=0;counter<perk.length;counter++)
          { 
            if (perk[counter] != null) {
              perk[counter].update();
              if (!perk[counter].active) {
                perk[counter]=null;  
              } // end of if
              
            } // end of if
          }
          
          for (int counter=1;counter<player.length;counter++)
          { 
            
            player[counter].updateKey(); 
            
          }
          if (isthereshot) {
            for (int counter=0;counter<shot.length;counter++)
            { 
              if (shot[counter] != null) {
                shot[counter].updateShot();  
              } // end of if
            }
            Game.DamageLogig.updateDamage();
          } // end of if
          // Game.dbImage.getGraphics().fillRect(100,600,900,10);
          Game.dbImage.getGraphics().drawString("Music: Early Riser Kevin MacLeod (incompetech.com)", Game.getWidth()-320, Game.getHeight()-20);
          
          int timediff = ((int) System.currentTimeMillis()-time);
          if (Game.fpsan) {
            //System.out.println("FPS: " + 1000/timediff);
            Game.dbImage.getGraphics().drawString("FPS: "+ 1000/timediff, Game.getWidth()-380, Game.getHeight()-20);
          } // end of if
          time =(int) System.currentTimeMillis();
          
          for (int c = 1;c<Game.ebenen.length;c++) {
            if (Game.ebenen[c] != null) {
              Game.dbImage.getGraphics().drawLine(Game.ebenen[c][0],Game.ebenen[c][2],Game.ebenen[c][1],Game.ebenen[c][2]);
            } // end of if
          } // end of for
          
          Game.getGraphics().drawImage(Game.dbImage,0,0,Game);
        }
        
        
        
      } // end of if
      
    } // end of while
    
  }  
  // Ende Methoden5
}  
  