package Applet;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

  class perks extends Thread 
{
  int x,y,wert,perkx,perky;
  File perktexture;
  BufferedImage textureImage;
  boolean rechts;
  boolean active=true;
  int speed;
  JavaGame Game;
  
  public perks (File perktexture, boolean rechts, int speed, JavaGame Game, int perkx, int perky) {
    this.perktexture = perktexture;
    this.Game = Game;
    this.speed = speed;
    this.rechts = rechts;
    this.perkx=perkx;
    this.perky=perky;
    
    laden();
  } 
  public void laden() {
    try { 
      textureImage = ImageIO.read(perktexture);
    } catch(IOException exeption) {
      
    }
  }
  
  public void update() {
    
    for (int counter = 0;counter<Game.player.length;counter++) {
      if (Game.player[counter] != null) {
        
        int xDistance = perkx - Game.player[counter].x;
        int yDistance = perky - Game.player[counter].y;
        
        
        if ( (xDistance > -50 && xDistance <67) && (yDistance > -50 && yDistance < 100)) {
          active=false;
          int perkw = (int) (Math.random()*6+1);
          switch (perkw) {
            case  1:
            Game.player[counter].jumpheigth=300;
            Game.player[counter].perkzählerjump=600;
            break;
            case  2:
            Game.player[counter].sperrzeit=25;
            Game.player[counter].perkzählershoot=600;
            break;
            case 3: 
            Game.player[counter].speed=10;
            Game.player[counter].perkzählerrun=600;
            break;
            case 4:
            if (Game.player[counter].health<=90) {
              Game.player[counter].health += 10;
            } // end of if
            break;
            case 5:
            Game.player[counter].drawboom=0;
            
            Game.player[counter].boomx = perkx;
            Game.player[counter].boomy = perky;
            Game.player[counter].health -= 10;
            Game.player[counter].boomUp += 600;
            break;
            case 6:
            Game.player[counter].gefroren=100;
            Game.player[counter].freeze=true;
            break;
            
          }
          
        } 
        Game.dbImage.getGraphics().drawImage(textureImage,perkx,perky,Game);
      } // end of if
    } // end of if
  } // end of for
}