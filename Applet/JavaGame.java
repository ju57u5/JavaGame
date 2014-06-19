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
  boolean notrunning = true;
  //File texture = new File(appletpfad); 
  File texture = new File("Z:\\test.png");
  Player player1,player2;
  Image dbImage;
  Graphics dbGraphics;
  Erzaehler erz = new Erzaehler(player1, player2);
  
  public void init() {
    dbImage = createImage(1920,1080);
    dbGraphics = dbImage.getGraphics();
    
    
    player1 = new Player(texture,texture,dbImage);
    player2 = new Player(texture,texture,dbImage);
  } // end of init
  
  
  public void keyPressed(KeyEvent e) 
  {
    
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e) 
  {
    
  }
  
  
  
  
  public void paint (Graphics g) {
    
    if (notrunning) {
      player1.laden(this,g,dbGraphics,0,0);
      player2.laden(this,g,dbGraphics,100,100);    
      this.addKeyListener(player1);
      this.addKeyListener(player2);
      
      player1.start();
      player2.start();
      notrunning = false;
    } // end of if
    g.drawImage(dbImage,0,0,this);
  }  
  
} // end of class JavaGame

class Player extends Thread implements KeyListener  {
  
  File playertexture,shottexture;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  Graphics g,db;
  KeyEvent taste;
  JavaGame Game;
  Image dbImage;
  boolean firsttimepressed = false;
  int x,y=0;
  
  public Player(File playertexture, File shottexture, Image dbImage) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    this.dbImage = dbImage;
    
    
  }  
  
  public void laden(JavaGame Game, Graphics db, Graphics g, int x, int y) {
    
    this.Game = Game;
    this.g = g;
    this.db = db;
    this.x = x;
    this.y = y;
    try { 
      textureImage = ImageIO.read(playertexture);
    } catch(IOException exeption) {
      
    }
    db.drawImage(textureImage,x,y,Game);
  }
  
  public void updateKey()
  {
    if (firsttimepressed) {
      
      
      if (taste.getKeyCode()==taste.VK_RIGHT && x<300) {                            // I'm in Space! SPACE!
        x +=1;
      } // end of if
      
      if (taste.getKeyCode()==taste.VK_LEFT && x>0) {                               // I'm in Space! SPACE!
        x -=1; 
      } // end of if
      
      if (taste.getKeyCode()==taste.VK_UP && y>0) {                                 // I'm in Space! SPACE!
        y -=1; 
      } // end of if
      
      if (taste.getKeyCode()==taste.VK_DOWN && y<300) {                             // I'm in Space! SPACE!
        y +=1; 
      } // end of if
    } // end of if  
    Game.repaint();                         
    db.drawImage(textureImage,x,y,Game);
    
  }
  
  public void run() {
    while (true) { 
      updateKey();
      System.out.println("test"); 
      try {
        sleep(1);
      }
      catch(InterruptedException e) {
      }
      
    } // end of while
    
  }  
  
  public void keyPressed(KeyEvent e) 
  {
    firsttimepressed = true;
    this.taste = e;   
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e) 
  {
    
  }
  
}



/////////////////////////////////  Observable Classe für die Übergabe       

class Erzaehler extends Observable { 
  
  public Erzaehler(Player p1, Player p2){ 
    //this.addObserver(p1);
    //this.addObserver(p2); 
    
    
  } 
  
  
  public void tell(KeyEvent info){ 
    if(countObservers()>0){ 
      setChanged(); 
      notifyObservers(info); 
    } 
  } 
  
  
} 


