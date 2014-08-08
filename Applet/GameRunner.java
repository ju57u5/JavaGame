package Applet;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.awt.event.KeyEvent;

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
  int winner,neustart=-1;
  boolean neu=false,schonneu=false;
  int auftretenvonperks=9;
  int totencounter=0;
  int gesamtticks=0;
  ScoreFrame scoreFrame;
  String chatMessages[] = new String [30];
  boolean sendHighscoreFailed;
  WellenModus wellenModus ;
  


  // Ende Attribute5
  
  public GameRunner (Player[] player, JavaGame Game) {
    this.player = player;
    this.Game = Game;
    perktexture = new File(Game.basePath,"/perk.png");
    wellenModus = new WellenModus(Game);
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
          
          //Game.dbImage.getGraphics().drawImage(Game.backgroundImage,100,200-67,Game);
          Game.dbImage.getGraphics().drawImage(Game.backgroundImage,0,0,Game);
          
          int perkjn= (int) (Math.random()*3000+1);
          if (perkjn<auftretenvonperks && !Game.online) {
            int perkx= (int) (Math.random()*1000+1);
            int perky= (int) (Math.random()*400+100);
            perk[count] = new perks(perktexture, Game, perkx, perky, (int) (Math.random()*6+1)); 
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
            if (Game.player[counter] != null) {
              player[counter].updateKey();
            } // end of if
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
          Graphics gra = Game.dbImage.getGraphics();
          gra.setColor(Color.white);
          for (int c=0;c<Game.nachricht.length;c++) {
            if (Game.nachricht[c] != null) {
              gra.drawString(Game.nachricht[c], 20, Game.getHeight()-20-(c*20));
            } // end of if
          } // end of for
          
          for (int c=0;c<5;c++) {
            if (chatMessages[c] != null) {
              if (chatMessages[c].length()>=50) {
                Game.dbImage.getGraphics().drawString(chatMessages[c].substring(0, 50)+"...", Game.getWidth()-Game.getGraphics().getFontMetrics().stringWidth(chatMessages[c].substring(0, 50))-20, 50+(c*20));
              }
              else {
                Game.dbImage.getGraphics().drawString(chatMessages[c], Game.getWidth()-Game.getGraphics().getFontMetrics().stringWidth(chatMessages[c])-20, 50+(c*20));
              }
            } // end of if
          } // end of for
          
          gra.drawString("Music: Early Riser Kevin MacLeod (incompetech.com)", Game.getWidth()-320, Game.getHeight()-20);
          
          int timediff = ((int) System.currentTimeMillis()-time);
          if (Game.fpsan) {
            //System.out.println("FPS: " + 1000/timediff);
            Game.dbImage.getGraphics().drawString("FPS: "+ 1000/timediff, Game.getWidth()-380, Game.getHeight()-20);
          } // end of if
          time =(int) System.currentTimeMillis();
          if (Game.arg.equals("dev")) {
            for (int c = 0;c<Game.ebenen.length;c++) {
              if (Game.ebenen[c] != null) {
                Game.dbImage.getGraphics().drawLine(Game.ebenen[c][0],Game.ebenen[c][2],Game.ebenen[c][1],Game.ebenen[c][2]);
              } // end of if
            } // end of for
          }
          
          
          int anzahl=0;
          for (int c=1;c<Game.player.length;c++) {
            if (Game.player[c] != null) {
              anzahl++;
              if (Game.player[c].health<=0) {
                totencounter++;
              } // end of if
            } // end of if
          } // end of for
          
          wellenModus.update();
          
          if (totencounter==anzahl-1 && Game.online==false) {
            if (!wellenModus.isOn()) {
              for (int c=1;c<Game.player.length;c++) {
                if (Game.player[c] != null) {
                  if (Game.player[c].health>0) {
                    Game.dbImage.getGraphics().drawString(Game.player[c].name+" hat gewonnen",500,120); 
                    if ((!Game.player[c].name.startsWith("Bot") && !Game.player[c].name.equals("Spieler "+c) ) && neustart==299) {
                      int spielerAnzahl=0;
                      int kills=0;
                      for (int cou=1;cou<Game.player.length;cou++) {
                        if (Game.player[cou] != null) {
                          spielerAnzahl++;
                          if (Game.player[cou].murderer==Game.player[c]) {
                            kills++;
                          } // end of if
                        } // end of if
                      } // end of for
                      int score = (Game.player[c].health*(spielerAnzahl-1)*kills);
                      try {
                        Game.highscore.sendHighscore(Game.player[c].name, score);
                      } catch (IOException e) {
                        e.printStackTrace();
                        sendHighscoreFailed=true;
                      }
                      scoreFrame = new ScoreFrame(Game, Game.player[c].name,spielerAnzahl-1,kills,Game.player[c].health,score);
                      Game.toFront();
                    } // end of if
                  } // end of if
                  
                  
                  if (!neu) {
                    neustart=300;
                    neu=true;
                    schonneu=false;
                  } // end of if
                  
                  
                  
                  Game.dbImage.getGraphics().drawString("Neustart in "+neustart/10,500,135);
                } // end of if
              }
            } // end of for
          } // end of if
          
          
          
          
          
        }
        totencounter=0;
        
        if (Game.updater.arg.equals("dev") && gesamtticks==0) {
          new ScoreFrame(Game, "Justus", 3, 5, 10, 10000000) ;
        }
        
        neustart-=1;
        if (neustart==0 && !schonneu) {
          if (!wellenModus.isOn()) {
            scoreFrame.dispose(); 
          } // end of if
          for (int c=1;c<Game.player.length;c++) {
            if (Game.player[c] != null) {
              Game.player[c].x=(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]);
              Game.player[c].y=0;
              Game.player[c].health=100;
              Game.player[c].jumpheigth=200;
              Game.player[c].speed=5;
              Game.player[c].sperrzeit=40;
              Game.player[c].freezeControls=false;
              neu=false;
              
            } // end of if
          } // end of if
        } // end of Neustart
        
        Game.dbImage.getGraphics().drawString("Modus :",500,650);
        if (wellenModus.isOn()) {
          Game.dbImage.getGraphics().drawString("Wellenmodus",550,650);                        ///Modus Anzeige
        } // end of if
        if (!wellenModus.isOn()) {
          Game.dbImage.getGraphics().drawString("Normal",550,650);  
        } // end of if
        
        
      } // end of if
      
      if (!Game.online) {
        Game.highscore.saveNames();
      }
      
      
      
      
      Game.getGraphics().drawImage(Game.dbImage,0,0,Game);
      gesamtticks++;
      
      
      
    }
    
    
    
  } // end of if
  
} // end of while
  
  
  
