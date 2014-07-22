package Applet;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

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

