package Applet;

import java.awt.*;
import java.io.File;

class Bot extends Player {
  
  public Bot(File playertexture, File shottexture, Image dbImage, int left, int right , int jump, int down, int attack, int xHealth, int yHealth, String name) {
    super(playertexture, shottexture, dbImage, left, right, jump, down, attack, xHealth, yHealth, name);
  }  
  
  public void updateKey()
  {
    if (name.equals("boss")) {          //Cheat
      health=200;                       //Cheat
    } // end of if
    name = "Bot";  //ERste Änderung an der update Klasse: hier sollte die KI/KD sachen stehen und die Keysachen nicht...
    
    
    if (y + textureImage.getHeight() > Game.ebenen[0][2] + 200) {
      health=0;
    } // end of if
    updateJump(jumpupdate);
    updateBoom(15);
    drawHealth();
    if (schusssperre > 0) {
      schusssperre--;
    } // end of if
    
    if (health>0) {
      if (!characterInverted && health>0) {
        Game.dbImage.getGraphics().drawString(name,x+33,y-10);
        Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
      } // end of if
      else if (characterInverted && health>0){
        Game.dbImage.getGraphics().drawString(name,x+33,y-10);
        Game.dbImage.getGraphics().drawImage(textureImageb,x,y,Game);
      } // end of if-else
      
      if (x+textureImage.getWidth()<0) {                                               
        int xpoints[] = {0,10,10};
        int ypoints[] = {y,y+10,y-10};
        Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3);
      } // end of if
      
      if (x>Game.getWidth()) {
        int xpoints[] = {Game.getWidth(),Game.getWidth()-10,Game.getWidth()-10};
        int ypoints[] = {y,y+10,y-10};
        Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3);
      } // end of if
      
      if (y+textureImage.getHeight()<0) {
        int xpoints[] = {x,x-10,x+10};
        int ypoints[] = {0,10,10};
        Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3); 
      } // end of if
      
      if (health <= 0) {
        freezeControls=true;
      } // end of if
      
      //Perk begrenzen
      //perks anzeigen
      if (jumpheigth==300) {
        Game.dbImage.getGraphics().drawString("Springen "+perkzählerjump/10,xHealth,yHealth+50);
        perkzählerjump=perkzählerjump-1;
      } // end of if
      if (sperrzeit==25) {
        Game.dbImage.getGraphics().drawString("Schießen "+perkzählershoot/10,xHealth,yHealth+65);
        perkzählershoot=perkzählershoot-1;
      } // end of if
      if (speed==10) {
        Game.dbImage.getGraphics().drawString("Rennen   "+perkzählerrun/10,xHealth,yHealth+80);
        perkzählerrun=perkzählerrun-1;
      } // end of if
      //perks anzeigen Ende
      
      //perks zurücksetzen
      if (perkzählerjump<0) {
        jumpheigth=200;
      } // end of if
      if (perkzählerrun<0) {
        speed=5;
      } // end of if
      if (perkzählershoot<0) {
        sperrzeit=40;
      } // end of if
      //perks zurücksetzen Ende
      
      //Perk begrenzen Ende
      drawboom=drawboom+1;
      
      if (drawboom<15) {
        Game.dbImage.getGraphics().drawImage(boomImage,boomx,boomy,Game);
      } // end of if
      
      if (gefroren<0) {
        freezeControls=false;
        if (x<100 || x>1000) {      //selbstschutz
          if (x<100)
          {x += speed;
            characterInverted = false;
          }
          if (x>1000)
          {x -= speed;
            characterInverted = true;
          }
        } 
        else {
          
          if (Game.player[angriffsziel].health>0 && Game.player[angriffsziel] != this) {
            int botdif=y-Game.player[angriffsziel].y;      //Kampf gegen Spieler 2
            
            if (x<Game.player[angriffsziel].x && Game.player[angriffsziel].x<1000) {
              x +=speed/2;
              characterInverted = false;
              
            } // end of if
            if (x>Game.player[angriffsziel].x && Game.player[angriffsziel].x>100) {
              x -=speed/2;
              characterInverted = true; 
            } // end of if 
            
            if (x==Game.player[angriffsziel].x && y<Game.player[angriffsziel].y) {
              y -=1;
              justupdated = true;
              aufeinerebeneüberjemandem=false;                              /////Runterfallen
            } // end of if
            
            
            if (x==Game.player[angriffsziel].x && botdif<80 && botdif>-80) {
              if (schusssperre == 0) {
                Shot bullet = new Shot(shottexture,!characterInverted, 10, Game,this);
                bullet.laden(x,y+50);
                schusssperre = sperrzeit;
              } // end of if
            }
            if (botdif<80 && botdif>-80) {     //selbe Höhe
              
              if (schusssperre == 0) {
                Shot bullet = new Shot(shottexture,!characterInverted, 10, Game,this);
                bullet.laden(x,y+50);
                schusssperre = sperrzeit;
              } // end of if
              
            } // end of if
            
            if (y==Game.player[angriffsziel].y && x==Game.player[angriffsziel].x) {
              aufeinerebeneüberjemandem=true;
            } // end of if
            
            if (y>Game.player[angriffsziel].y) {
              setJump(jumpheigth);
            } // end of if
            
          } 
          
        }
      } // end of if
      if (gefroren>0) {
        freezeControls=true;
        Game.dbImage.getGraphics().drawImage(freezeImage,x-5,y,Game);
        Game.dbImage.getGraphics().drawString("Gefroren   "+gefroren/10,xHealth,yHealth+115);
        
      } // end of if
      gefroren-=1;
      
      min=1000000;  
      for (int c=1;c<Game.player.length;c++) {
        if (Game.player[c] != null) {
          dif=Game.player[c].x-x;
          if (dif<min && Game.player[c].health>0 && Game.player[c] != this) {
            min=dif;
            angriffsziel=c;
          } // end of if
        }
      } // end of if
      
      
      
      
      
      
    }
  }
  
  
  
  
  
  
  
  
  
  
}