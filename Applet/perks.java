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
  boolean art=false;
  boolean active=true;
  int speed, perkart;
  JavaGame Game;
  boolean heilung=true;
  
  public perks (File perktexture, JavaGame Game, int perkx, int perky) {
    this.perktexture = perktexture;
    this.Game = Game;
    this.perkx=perkx;
    this.perky=perky;
    
    laden();
  } 
  public perks (File perktexture, JavaGame Game, int perkx, int perky, int perkart) {
    this.perktexture = perktexture;
    this.Game = Game;
    this.perkx=perkx;
    this.perky=perky;
    this.perkart = perkart;
    this.art = true;
    
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
          int perkw;
          if (art) {
            perkw=perkart;
          }
          else {
            System.out.println("Hallo");
            if (Game.gamerunner.storyModus.isOn()) {
              perkw = (int) (Math.random()*7+1);
            } else {
              perkw = (int) (Math.random()*6+1);
            } // end of if-else
          }
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
            case 7:
            System.out.println("Heilung");
            for (int c=1;c<Game.player.length ;c++) {
              if (Game.player[c].health <0 && heilung && !(Game.player[c] instanceof Bot)) {
                heilung=false;
                Game.player[c].health=100;
                Game.player[c].x=200;
                Game.player[c].y=650;
              } // end of if
            } // end of for
            break;
            
            
            
          }
          
        } 
        Game.dbImage.getGraphics().drawImage(textureImage,perkx,perky,Game);
      } // end of if
    } // end of if
  } // end of for
}