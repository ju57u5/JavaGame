import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Observable; 
import java.util.Observer;
import java.net.*; 


//                           Interfaces
  

public class JavaGame extends Applet implements KeyListener {
  
  String appletpfad = (System.getProperty("user.dir")+"\texture\test.png");
  boolean notrunning = true;
  //File texture = new File(appletpfad);
  URL  PlayerTextureUrl;
  File texture = new File("J:\\test.png");
  Player player[] = new Player[3];
  Image dbImage;
  Graphics dbGraphics;
  //Erzaehler erz = new Erzaehler(player1, player2);
  
  public void init() {
    dbImage = createImage(1920,1080);
    dbGraphics = dbImage.getGraphics();
    
    
    player[1] = new Player(texture,texture,dbImage,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_SHIFT);                // I'm in Space! SPACE!
    player[2] = new Player(texture,texture,dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_Q);
    
    try {
      PlayerTextureUrl = new URL("http://www.mariowiki.com/images/5/57/WaluigiMP8Official.png");
    } catch(Exception e) {
      e.printStackTrace();
    } 
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
      player[1].laden(this,dbGraphics,g,0,0);
      player[2].laden(this,dbGraphics,g,100,100);    
      this.addKeyListener(player[1]);
      this.addKeyListener(player[2]);
      
      GameRunner gamerunner = new GameRunner(player,this);
      
      notrunning = false;
    } // end of if
    //g.drawImage(dbImage,0,0,this); 
    
  }  
  
} // end of class JavaGame

class Player extends Thread implements KeyListener  {
  
  File playertexture,shottexture;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  Graphics g,db;
  KeyEvent taste;
  JavaGame Game;
  Image dbImage;
  boolean firsttimepressed=false;
  int x,y=0;                                                                                                                  //Positionen
  int left,right,jump,attack;                                                                                                 //Tasten
  int jumpup, jumpdown = 0;                                                                                                   //Jump Vars
  boolean[] keys = new boolean[1000];
  
  public Player(File playertexture, File shottexture, Image dbImage, int left, int right , int jump, int attack) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    this.dbImage = dbImage;
    this.left = left;
    this.right = right;
    this.jump = jump;
    this.attack = attack;
    
    
  }  
  
  public void laden(JavaGame Game, Graphics db, Graphics g, int x, int y) {
    
    this.Game = Game;
    this.g = g;
    this.db = db;
    this.x = x;
    this.y = y;
    
    for (int c=0;c<keys.length;c++ ) {
      keys[c]=false;
    } // end of for
    
    try { 
      textureImage = ImageIO.read(playertexture);
    } catch(IOException exeption) {
      
    }
    
    
  }
  
  public void updateKey()
  {
    if (firsttimepressed) {
      
      if (keys[right] && x<900) {                              
        x +=5;
      } // end of if
      
      if (keys[left] && x>0) {                                 
        x -=5; 
      } // end of if
      
      if (keys[jump] && y>0) {                                 
        setJump(50); 
      } // end of if
      
      if (keys[attack] && y<900) {                             
        y +=5; 
      } // end of if
    } // end of if  
    
    updateJump(10);
    Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
    
    System.out.println(x+" "+y);
    
  }
  
  public void run() {
    
    
    
    
  }  
  
  public void keyPressed(KeyEvent e) 
  {
    firsttimepressed = true;
    this.taste = e;
    keys[taste.getKeyCode()]=true;   
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
    this.taste = e;
    keys[taste.getKeyCode()]=false;   
  }
  
  public void keyTyped(KeyEvent e) 
  {
    
  }
  
  // PlayerLogig
  
  public void setJump(int heigth) {
    if (jumpup == 0 && jumpdown == 0) {
      jumpup += heigth;
      jumpdown += heigth;
    } // end of if
    
  } 
  
  public void updateJump(int speed) {
    if (jumpup>0) {
      y -= speed;
      jumpup -= speed;
    } // end of if
    
    else if (jumpdown>0) {
      y += speed;
      jumpdown -= speed;
    } // end of if-else
    
  }   
}

class Shot {
  
  int x,y;
  JavaGame Game;
  File shottexture;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  
  public Shot (File shottexture) {
    this.shottexture = shottexture;
    
  }  
  
  public void laden(JavaGame Game, int x, int y) {
    
    this.Game = Game;
    this.x = x;
    this.y = y;
    
    try { 
      textureImage = ImageIO.read(shottexture);
    } catch(IOException exeption) {
      
    }
    
    
  }
  
  public void updateShot() {
    
    
    
    
    
  }  
}

class damageLogig {
  
  public void registerShot() {
    
    
  }
  
  public void updateDamage() {  
    
    
  }  
}  
  
/////////////////////////////////  Observable Classe f�r die �bergabe       

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


////// Standart Thread f�r das aktualisieren aller Komponenten

class GameRunner extends Thread {
  Player player[] = null;
  JavaGame Game ;
  
  public GameRunner (Player[] player, JavaGame Game) {
    this.player = player;
    this.Game = Game;
    this.start();
  }  
  
  public void run() {
    while (true) { 
      try {
        sleep(33);
      }
      catch(InterruptedException e) {
      }
      Game.repaint();
      Game.dbImage.getGraphics().clearRect(0,0, (int)Game.getWidth(), (int)Game.getHeight());
      for (int counter=1;counter<player.length;counter++)
      { 
        
        player[counter].updateKey(); 
        
      }
      Game.getGraphics().drawImage(Game.dbImage,0,0,Game);  
    } // end of while
    
  }  
}  


