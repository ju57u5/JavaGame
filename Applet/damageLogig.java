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
      if (runner.player[counter] != null) {
        
        for (int counterb = 0;counterb < runner.shot.length;counterb++) {
          if (runner.shot[counterb] != null && runner.shot[counterb].owner != runner.player[counter] && !runner.player[counter].freezeControls) {
            
            xDistance = runner.shot[counterb].x - runner.player[counter].x;
            yDistance = runner.shot[counterb].y - runner.player[counter].y ;
            
            if ( (xDistance > -50 && xDistance <67) && (yDistance > -50 && yDistance < 100) ) {
              if (xDistance < 0) {
                runner.player[counter].setDamage(10,true,runner.shot[counterb].owner);
              } // end of if
              else {
                runner.player[counter].setDamage(10,false,runner.shot[counterb].owner);
              } // end of if
              
              runner.shot[counterb]=null;
            } // end of if
            
          } // end of if
        } // end of for
      } // end of if  
    } // end of for
    
  }  
  // Ende Methoden3
}  