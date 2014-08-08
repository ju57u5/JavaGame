package Applet;

import java.awt.event.KeyEvent;

public class StoryModus {
  JavaGame Game;
  boolean storyModus=false;
  static int MODE_OFF=0;
  static int MODE_ON=1;
  boolean neuereingeborerener=false,weitweg,amanfang=true;
  
  
  public StoryModus (JavaGame Game) {
    this.Game=Game;
  }
  
  public void update() {
    if (storyModus) {
      if (amanfang) {
        for (int c=1;c<Game.player.length ;c++ ) {
          if (Game.player[c] instanceof Bot) {
            Game.player[c] = null;
          } // end of if
        }
        amanfang=false;
      } // end of if
      
      
      for (int c=1;c<Game.player.length ;c++ ) {
        if (Game.player[c] != null && !(Game.player[c] instanceof Bot)) {
          if (Game.player[c].x<850 && Game.player[c].x>800 && Game.player[c].y<600 && Game.player[c].y>400) { 
            if (!neuereingeborerener && weitweg) {
              neuereingeborerener=true;
            } // end of if
          }
          if (neuereingeborerener) {
            int spielerAnzahl = Game.getPlayerCount();
            Game.player[spielerAnzahl+1] = new Bot(Game.texture[6],Game.shottexture[2],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,spielerAnzahl+1,35,"");
            Game.player[spielerAnzahl+1].laden(Game,850,450);
            neuereingeborerener=false;
            weitweg=false;
          } // end of if
          
          if (Game.player[c].x>850 || Game.player[c].x<800 || Game.player[c].y<400 || Game.player[c].y>600) { 
            weitweg=true;
          }
        }
      } // end of for
      
      
      
      
      
      
      
      
    }
    
    
  }   //Ende void update
  public boolean isOn() {
    return storyModus;
  }
  
  
  public void setState(int state) {
    if (state==0) {
      storyModus=false;
    }
    else {
      storyModus=true;
    }
  }
}