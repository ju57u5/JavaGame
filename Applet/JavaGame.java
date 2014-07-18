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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import Applet.*;


//                           Interfaces
  

public class JavaGame extends Frame implements KeyListener {
  
  // Anfang Attribute
  boolean notrunning = true;
  boolean soundan=false;
  boolean fpsan=false;
  URL  PlayerTextureUrl;
  
  //File basePath = new File("/texture"); //Pfad zu den Resourcen
  //URL base ;
  File basePath;// = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()); //Pfad zu den Resourcen
  File backgroundTexture;// = new File(basePath,"/hintergrund.jpg");
  File sound;// = new File(basePath,"/sound.wav");
  
  File[] texture = new File[100];
  File[] shottexture = new File[100];
  
  Player player[] = new Player[4];
  Image dbImage;
  Graphics dbGraphics;
  damageLogig DamageLogig;
  int[][] ebenen = new int[100][3] ;
  GameRunner gamerunner;
  float vol;
  BufferedImage backgroundImage;
  AudioInputStream Stream;
  Clip ac;
  String[] args = new String[100];
  //FloatControl volume;
  // Ende Attribute
  
                          
  //Erzaehler erz = new Erzaehler(player1, player2);
  public static void main(String[] args) {
    new JavaGame(args);
  }
  
  class WindowListener extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      e.getWindow().dispose();                   // Fenster "killen"
    }
  }
  
  public JavaGame(String[] args) {
    args[0]="";
    this.args = args;
    Updater updater = new Updater(this);
    
    
    setTitle("JavaGame");  // Fenstertitel setzen
    setSize(1200,900);                            // Fenstergröße einstellen
    addWindowListener(new WindowListener());
    setLocationRelativeTo(null);                                        // EventListener für das Fenster hinzufügen
    setVisible(true);
    
    try{
      /*URI Path = URLDecoder.decode(getClass().getClassLoader().getResource("texture").toURI();//, "UTF-8"); //Pfad zu den Resourcen
      File F = new File(Path);
      basePath = F;
      System.out.println(basePath);
      */
      File File = new File((System.getenv("APPDATA")));
      basePath = new File(File, "/texture");
      backgroundTexture = new File(basePath,"/hintergrund.jpg");
      sound = new File(basePath,"/sound.wav");
      Stream =AudioSystem.getAudioInputStream(sound);
      ac = AudioSystem.getClip();
      ac.open(Stream);
    }
    catch(Exception ex)
    {  }
    
    try { 
      PlayerTextureUrl = sound.toURI().toURL();
    } catch(MalformedURLException urlexception) {
      
    }
    
    
    try {
      backgroundImage = ImageIO.read(backgroundTexture);
    } catch(IOException exeption) {
      
    }
    
    
    if (soundan) {
      ac.loop(10);
    } // end of if
    
    
    dbImage = createImage(1920,1080);
    //dbGraphics = dbImage.getGraphics();
    
    // Texturen Liste
    
    //Ebenen Liste
    
    ebenen[0][0]= 100;
    ebenen[0][1]= 1000;       // Main Ebene: Kann nicht durchschrittenwerden indem down gedrückt wird
    ebenen[0][2]= 590;
    
    ebenen[1][0]= 430;  //x1
    ebenen[1][1]= 500;  //x2
    ebenen[1][2]= 480;  //y
    
    ebenen[2][0]= 530;
    ebenen[2][1]= 655;
    ebenen[2][2]= 377;
    
    ebenen[3][0]= 250;
    ebenen[3][1]= 375;
    ebenen[3][2]= 377;
    
    // Spieler
    
    player[1] = new Bot(texture[0],shottexture[0],dbImage,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_ENTER,10,35,"Justus");                // I'm in Space! SPACE!
    player[2] = new Player(texture[1],shottexture[1],dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,120,35,"Christian");
    player[3] = new Bot(texture[2],shottexture[2],dbImage,KeyEvent.VK_J,KeyEvent.VK_L,KeyEvent.VK_I,KeyEvent.VK_K,KeyEvent.VK_U,230,35,"Tjorben");
    
    player[1].laden(this,100,400);
    player[2].laden(this,200,400);
    player[3].laden(this,300,400);
    
    this.addKeyListener(player[1]);
    this.addKeyListener(player[2]);
    this.addKeyListener(player[3]);
    
    this.addKeyListener(this);
    
    
    gamerunner = new GameRunner(player,this);
    DamageLogig = new damageLogig (gamerunner);
  } // end of init
  
  
  public void keyPressed(KeyEvent e) 
  {
    if (e.getKeyCode()==KeyEvent.VK_ESCAPE && gamerunner.running) {
      
      Graphics gr = this.getGraphics();
      gr.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
      gr.drawString("PAUSE", (int) this.getWidth()/2, this.getHeight()/2);
      gamerunner.running=false;
      ac.stop();
      Menu menu = new Menu(this);
      //volume.setValue(vol);
    }
    
    else if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {
      gamerunner.running=true;
      if (soundan) {
        ac.loop(10);
      } // end of if
    } // end of if-else
    
    else if (e.getKeyCode()==KeyEvent.VK_R) {
      for (int c=1;c<player.length;c++) {
        player[c].x=(int) (Math.random()*(ebenen[0][1]-ebenen[0][0])+ebenen[0][0]);
        player[c].y=0;
        player[c].health=100;
        player[c].jumpheigth=200;
        player[c].speed=5;
        player[c].sperrzeit=40;
        player[c].freezeControls=false;
        gamerunner.neu=false;
        gamerunner.schonneu=true;
      } // end of for
    }
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e)
  {
    
  }   
  
  public void paint (Graphics g) {
    super.paint(g);
    if (notrunning) {
      notrunning = false;
    } // end of if
    
  }  
  
  public void update(Graphics g) {
    
    paint(g);
    
  }
  // Ende Methoden
} // end of class JavaGame


