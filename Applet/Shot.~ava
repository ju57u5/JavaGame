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

class Shot {
  
  // Anfang Attribute2
  int x,y,speed;
  JavaGame Game;
  File shottexture;
  Player owner;
  boolean rechts;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  // Ende Attribute2
  
  
  public Shot (File shottexture, boolean rechts, int speed, JavaGame Game, Player owner) {
    this.shottexture = shottexture;
    this.Game = Game;
    this.speed = speed;
    this.rechts = rechts;
    this.owner = owner;
  }  
  
  public void laden(int x, int y) {
    
    this.x = x;
    this.y = y;
    
    try { 
      textureImage = ImageIO.read(shottexture);
    } catch(IOException exeption) {
      
    }
    
    if (rechts) {
      textureImage = verticalflip(textureImage);
    } // end of if
    Game.DamageLogig.registerShot(this);            //Registrierung
  }
  
  public void updateShot() {
    if (rechts) {
      x += speed;
      
    } // end of if
    else {
      x -= speed;
      
    } // end of if-else
    
    Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
    
  }
  
  public BufferedImage verticalflip(BufferedImage img) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = new BufferedImage(w, h, img.getType());
    Graphics2D g = dimg.createGraphics();
    g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
    g.dispose();
    return dimg;
  }
  
  // Ende Methoden2
}

class damageLogig {
  // Anfang Attribute3
  Shot shot[] = new Shot[1000];
  GameRunner runner;
  int counter = 0;
  int xDistance,yDistance;
  
  public damageLogig(GameRunner runner) {
    this.runner = runner;
    
  }  
  
  public void registerShot(Shot bullet) {
    shot[counter] = bullet;
    runner.shot[counter] = bullet;
    
    runner.isthereshot = true;
    counter++;
  }
  
  public void updateDamage() {  
    
    for (int counter=1;counter < runner.player.length;counter++ ) {
      for (int counterb = 0;counterb < runner.shot.length;counterb++) {
        if (runner.shot[counterb] != null && runner.shot[counterb].owner != runner.player[counter] && !runner.player[counter].freezeControls) {
          
          xDistance = runner.shot[counterb].x - runner.player[counter].x;
          yDistance = runner.shot[counterb].y - runner.player[counter].y ;
          
          if ( (xDistance > -50 && xDistance <67) && (yDistance > -50 && yDistance < 100) ) {
            if (xDistance < 0) {
              runner.player[counter].setDamage(10,true);
            } // end of if
            else {
              runner.player[counter].setDamage(10,false);
            } // end of if
            
            runner.shot[counterb]=null;
          } // end of if
          
        } // end of if
      } // end of for
    } // end of for
    
  }  
  // Ende Methoden3
}  