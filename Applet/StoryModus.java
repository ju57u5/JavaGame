package Applet;

import java.awt.event.KeyEvent;

public class StoryModus extends GameMode{

  
  
  public StoryModus (JavaGame Game) {
	super(Game);
  }
  
  public void update() {
    if (modeON) {
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
          if (Game.player[c].x>850 || Game.player[c].x<800 || Game.player[c].y<400 || Game.player[c].y>600) { 
            Game.player[c].weitweg=true;
          }
          if (Game.player[c].x<850 && Game.player[c].x>800 && Game.player[c].y<600 && Game.player[c].y>400) { 
            if (!Game.player[c].neuereingeborerener && Game.player[c].weitweg) {
              Game.player[c].neuereingeborerener=true;
            } // end of if
          }
          if (Game.player[c].neuereingeborerener) {
            int spielerAnzahl = Game.getPlayerCount();
            Game.player[spielerAnzahl+1] = new Bot(850,450,false,67,100,Game.texture[6],Game.shottexture[2],KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,spielerAnzahl+1,35,"");
            Game.player[spielerAnzahl+1].laden(Game);
            Game.player[c].neuereingeborerener=false;
            Game.player[c].weitweg=false;
          } // end of if
        }
      } // end of for
    }
    
    
  }   //Ende void update
}