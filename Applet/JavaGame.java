import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Observable; 
import java.util.Observer; 


//                           Interfaces
  

public class JavaGame extends Applet implements KeyListener {
  
  String appletpfad = (System.getProperty("user.dir")+"\texture\test.png");
  
  //File texture = new File(appletpfad); 
  File texture = new File("Z:\\test.png");
  Player player1 = new Player(texture,texture);
  Player player2 = new Player(texture,texture);
  
  
  
  public void keyPressed(KeyEvent e) 
  {
    
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e) 
  {
    
  }
  
  
  public void init() {
    
    
    
  } // end of init
  
  public void paint (Graphics g) {
    player1.laden(this,g);
    this.addKeyListener(player1);
    Erzaehler erz = new Erzaehler(player1, player2);
    
  }  
  
} // end of class JavaGame

class Player extends Thread implements KeyListener, Observer  {
  
  File playertexture,shottexture;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  Graphics g;
  KeyEvent taste;
  JavaGame Game;
  int x,y=0;
  
  public Player(File playertexture, File shottexture) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    
    
    
  }  
  
  public void laden(JavaGame Game, Graphics g) {
    
    this.Game = Game;
    this.g = g;
    try { 
      textureImage = ImageIO.read(playertexture);
    } catch(IOException exeption) {
      
    }
    g.drawImage(textureImage,x,y,Game);
  }
  
  public void update(Observable obserable, Object keyEvent)
  {
    taste = (KeyEvent) keyEvent;
    if (taste.getKeyCode()==taste.VK_UP) {
      g.drawImage(textureImage,0,0,Game);
    } // end of if
  }
  
  public void keyPressed(KeyEvent e) 
  {
    
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e) 
  {
    
  }
  
}



/////////////////////////////////  Observable Classe f�r die �bergabe

class Erzaehler extends Observable { 
  
  public Erzaehler(Player p1, Player p2){ 
    this.addObserver(p1);
    this.addObserver(p2); 
    
    
  } 
  
  
  public void tell(KeyEvent info){ 
    if(countObservers()>0){ 
      setChanged(); 
      notifyObservers(info); 
    } 
  } 
  
  
} 


