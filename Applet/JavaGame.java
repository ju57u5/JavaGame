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
  
  File appletpfad = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
  boolean notrunning = true;
  URL  PlayerTextureUrl;
  File texture = new File("K:\\test.png");
  //File texture = new File(appletpfad, "\texture\test.png");
  File shottexture = new File("K:\\shot.png");
  Player player[] = new Player[3];
  Image dbImage;
  Graphics dbGraphics;
  damageLogig DamageLogig;
  //Erzaehler erz = new Erzaehler(player1, player2);
  
  public void init() {
    dbImage = createImage(1920,1080);
    dbGraphics = dbImage.getGraphics();
    
    
    player[1] = new Player(texture,shottexture,dbImage,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_SHIFT,10,10);                // I'm in Space! SPACE!
    player[2] = new Player(texture,shottexture,dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_Q,120,10);
    
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
      player[1].laden(this,dbGraphics,g,0,100);
      player[2].laden(this,dbGraphics,g,300,100);    
      this.addKeyListener(player[1]);
      this.addKeyListener(player[2]);
      
      GameRunner gamerunner = new GameRunner(player,this);
      DamageLogig = new damageLogig (gamerunner);
      
      notrunning = false;
    } // end of if
    //g.drawImage(dbImage,0,0,this); 
    
  }  
  
  public void update(Graphics g) {
    
    paint(g);
    
  }
} // end of class JavaGame



class Player extends Thread implements KeyListener  {
  
  File playertexture,shottexture;
  BufferedImage textureImage, textureImageb = new BufferedImage(1000,1000,1);
  
  Graphics g,db;
  KeyEvent taste;
  JavaGame Game;
  Image dbImage;
  boolean characterInverted,firsttimepressed,freezeControls=false;
  int x,y=0;                                                                                                                  //Positionen
  int schusssperre,left,right,jump,attack,xHealth,yHealth;                                                                                                 //Tasten
  int jumpup, jumpdown = 0;                                                                                                   //Jump Vars
  boolean[] keys = new boolean[1000];
  int health = 100;
  
  public Player(File playertexture, File shottexture, Image dbImage, int left, int right , int jump, int attack, int xHealth, int yHealth) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    this.dbImage = dbImage;
    this.left = left;
    this.right = right;
    this.jump = jump;
    this.attack = attack;
    this.xHealth = xHealth;
    this.yHealth = yHealth;
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
    
    textureImageb = verticalflip(textureImage);
  }
  
  public BufferedImage verticalflip(BufferedImage img) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = new BufferedImage(w, h, img.getType());
    Graphics2D g = dimg.createGraphics();
    /**
    * img - the specified image to be drawn. This method does nothing if
    * img is null. dx1 - the x coordinate of the first corner of the
    * destination rectangle. dy1 - the y coordinate of the first corner of
    * the destination rectangle. dx2 - the x coordinate of the second
    * corner of the destination rectangle. dy2 - the y coordinate of the
    * second corner of the destination rectangle. sx1 - the x coordinate of
    * the first corner of the source rectangle. sy1 - the y coordinate of
    * the first corner of the source rectangle. sx2 - the x coordinate of
    * the second corner of the source rectangle. sy2 - the y coordinate of
    * the second corner of the source rectangle. observer - object to be
    * notified as more of the image is scaled and converted.
    *
    */
    g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
    g.dispose();
    return dimg;
  }
  
  
  
  public void updateKey()
  {
    if (firsttimepressed && !freezeControls) {
      
      if (keys[right] && x<900) {                              
        x +=5;
        characterInverted = false;
      } // end of if
      
      if (keys[left] && x>0) {                                 
        x -=5;
        characterInverted = true; 
      } // end of if
      
      if (keys[jump] && y>0) {                                 
        setJump(80); 
      } // end of if
      
      if (keys[attack] && y<900 && schusssperre == 0) {                             
        Shot bullet = new Shot(shottexture,!characterInverted, 5, Game, this);
        bullet.laden(x,y+50);
        schusssperre = 10;
      } // end of if
    } // end of if  
    
    updateJump(10);
    drawHealth();
    if (schusssperre > 0) {
      schusssperre--;
    } // end of if
    
    if (!characterInverted) {
      Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
    } // end of if
    else {
      Game.dbImage.getGraphics().drawImage(textureImageb,x,y,Game);
    } // end of if-else
    
    
  }
  
  public void move(boolean rechts, int amount)  {
    
    
  }  
  
  public void drawHealth() {
    Graphics gra = Game.dbImage.getGraphics();
    gra.drawLine(xHealth,yHealth,xHealth+100,yHealth);
    gra.drawLine(xHealth,yHealth+10,xHealth+100,yHealth+10);
    gra.setColor(Color.red);
    gra.fillRect(xHealth+1,yHealth,health,10);
  }  
  
  public void run() {
  }
  
  public void setDamage(int damage) {
    health -= damage;
    if (health < 0) {
      freezeControls = true;
    } // end of if
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
  
  int x,y,speed;
  JavaGame Game;
  File shottexture;
  Player owner;
  boolean rechts;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  
  
  public Shot (File shottexture, boolean rechts, int speed, JavaGame Game, Player owner) {
    this.shottexture = shottexture;
    this.Game = Game;
    this.speed = speed;
    this.rechts = rechts;
    this.owner = owner;
  }  
  
  public void laden(int x, int y) {
    
    // this.Game = Game;
    this.x = x;
    this.y = y;
    
    try { 
      textureImage = ImageIO.read(shottexture);
    } catch(IOException exeption) {
      
    }
    
    Game.DamageLogig.registerShot(this);            //Registrierung
  }
  
  public void updateShot() {
    if (rechts) {
      x += speed;
      
    } // end of if
    else {
      x -= speed;
      
    } // end of if-else
    
    Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
    
  }  
}

class damageLogig {
  Shot shot[] = new Shot[100];
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
      for (int counterb = 0;counterb < runner.shot.length;counterb++) {
        if (runner.shot[counterb] != null && runner.shot[counterb].owner != runner.player[counter]) {
          
          xDistance = runner.shot[counterb].x - runner.player[counter].x;
          yDistance = runner.shot[counterb].y - runner.player[counter].y ;
          
          if ( (xDistance > -50 && xDistance <67) && (yDistance > -50 && yDistance < 100) ) {
            runner.player[counter].setDamage(2);
            runner.shot[counterb]=null;
          } // end of if
          
        } // end of if
      } // end of for
    } // end of for
    
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


////// Standart Thread für das aktualisieren aller Komponenten

class GameRunner extends Thread {
  Player player[] = null;
  Shot shot[] = new Shot[100];
  JavaGame Game ;
  boolean isthereshot = false;
  
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
      if (isthereshot) {
        for (int counter=0;counter<shot.length;counter++)
        { 
          if (shot[counter] != null) {
            shot[counter].updateShot();  
          } // end of if
        }
        Game.DamageLogig.updateDamage();
      } // end of if
      
      Game.getGraphics().drawImage(Game.dbImage,0,0,Game);  
    } // end of while
    
  }  
}  


