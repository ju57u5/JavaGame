package Applet;

import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Graphics;

public class WellenModus extends GameMode{
  ScoreFrame scoreFrame;
  int wptotencounter=0,wbtotencounter=0,wbanzahl=0,wpanzahl=0,wgewonnen;
  int wAnzeige;
  boolean wNeu=false,nNeu=false,amanfang=true;
  
  public WellenModus (JavaGame Game) {
    super(Game);
  }
  
  
  /**
  * Updated den Spielmodus
  */
  public void update() {
    if (modeON) {
      if (amanfang) {
        for (int c=1;c<Game.player.length ;c++ ) {
          if (Game.player[c] instanceof Bot) {
            if (c>2) {
              Game.player[c] = null;
            } // end of if
            
          } // end of if
        }
        amanfang=false;
      } // end of if
      
      
      
      
      for (int c=1;c<Game.player.length ;c++ ) {
        if (Game.player[c] != null) {
          if (Game.player[c] instanceof Bot) {
            wbanzahl+=1;
          } else {
            wpanzahl+=1;
          } // end of if-else    
          ///Bot/Spieler Zähler und Totenzähler
          if (Game.player[c].health<=0) {
            if (Game.player[c] instanceof Bot) {
              wbtotencounter+=1;
            } else {
              wptotencounter+=1;
            } // end of if-else
          } // end of if
        }
      } // end of for         
      Graphics gra = Game.dbImage.getGraphics();
      gra.setColor(Color.white);
      gra.drawString("Welle "+wbanzahl,400,650);
      gra.setColor(Color.black);
      
      if (wbtotencounter==wbanzahl && !wNeu) {///Sieg
        wAnzeige=100;
        wNeu=true;
      } // end of if                                                                         ///////WELLENMODUS - Sieg oder Niederlage
      if (wptotencounter==wpanzahl && !nNeu) {                                            ///Niederlage
    	  modeON=false;
        wAnzeige=100;
        nNeu=true;
      } // end of if    
      wbanzahl=0;
      wpanzahl=0;
      wbtotencounter=0;
      wptotencounter=0;
      
      
    }     /// Ende vom Wellenmodus
    
    wAnzeige-=1;
    
    if (modeON==false && wAnzeige>0) {
      Game.dbImage.getGraphics().drawString("Welle nicht überstanden    Neustart in "+wAnzeige/10,500,120);
      
    } // end of if
    if (modeON==true && wAnzeige>0) {
      Game.dbImage.getGraphics().drawString("Welle überstanden    Neustart in "+wAnzeige/10,500,120);
      
    } // end of if
    
    if (wNeu && wAnzeige<0) {
      int spielerAnzahl = Game.getPlayerCount();
      Game.player[spielerAnzahl+1] = new Bot((int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]),0,false,67,100,Game.texture[0],Game.shottexture[0],KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,spielerAnzahl+1,35,"Bot");
      Game.player[spielerAnzahl+1].laden(Game);
      Game.restartGame();
      wNeu=false;
      nNeu=false;
    } // end of if
    
  }
}
