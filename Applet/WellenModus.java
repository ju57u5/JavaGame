package Applet;

import java.awt.event.KeyEvent;

public class WellenModus {
  ScoreFrame scoreFrame;
  int wptotencounter=0,wbtotencounter=0,wbanzahl=0,wpanzahl=0,wgewonnen;
  JavaGame Game;
  boolean wellenModus=false;
  static int MODE_OFF=0;
  static int MODE_ON=1;
  int wAnzeige;
  boolean wNeu=false,nNeu=false;
  
  public WellenModus (JavaGame Game) {
    this.Game=Game;
  }
  
  
  /**
  * Updated den Spielmodus
  */
  public void update() {
    if (wellenModus) {
      
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
      
      
      
      if (wbtotencounter==wbanzahl && !wNeu) {///Sieg
        wAnzeige=100;
        wNeu=true;
      } // end of if                                                                         ///////WELLENMODUS - Sieg oder Niederlage
      if (wptotencounter==wpanzahl && !nNeu) {                                            ///Niederlage
        wellenModus=false;
        wAnzeige=100;
        nNeu=true;
      } // end of if    
      wbanzahl=0;
      wpanzahl=0;
      wbtotencounter=0;
      wptotencounter=0;
      
      
    }     /// Ende vom Wellenmodus
    
    wAnzeige-=1;
    
    if (wellenModus==false && wAnzeige>0) {
      Game.dbImage.getGraphics().drawString("Welle nicht überstanden    Neustart in "+wAnzeige/10,500,120);
      
    } // end of if
    if (wellenModus==true && wAnzeige>0) {
      Game.dbImage.getGraphics().drawString("Welle überstanden    Neustart in "+wAnzeige/10,500,120);
      
    } // end of if
    
    if (wNeu && wAnzeige<0) {
      int spielerAnzahl = Game.getPlayerCount();
      Game.player[spielerAnzahl+1] = new Bot(Game.texture[0],Game.shottexture[0],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,spielerAnzahl+1,35,"Bot");
      Game.player[spielerAnzahl+1].laden(Game,(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]),0);
      Game.restartGame();
      wNeu=false;
      nNeu=false;
    } // end of if
    
  }
  
  
  
  /**
  * Methode gibt zurück ob der Modus angeschaltet ist.
  * @return boolean
  */
  public boolean isOn() {
    return wellenModus;
  }
  
  /**
  * Setzt den Status des Modus.
  * <br>Möglichkeiten sind als statische Variablen angegeben.
  * @param int state
  */
  public void setState(int state) {
    if (state==0) {
      wellenModus=false;
    }
    else {
      wellenModus=true;
    }
  }
}
