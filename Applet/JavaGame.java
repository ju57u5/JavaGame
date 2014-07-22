package Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.net.*; 

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


//                           Interfaces
  

public class JavaGame extends Frame implements KeyListener {
  
  // Anfang Attribute
  boolean notrunning = true;
  boolean soundan=false;
  boolean fpsan=false;
  URL  PlayerTextureUrl;
  
  
  File basePath;//Pfad zu den Resourcen
  File backgroundTexture;
  File sound;
  
  File[] texture = new File[100];
  File[] shottexture = new File[100];
  
  Player player[] = new Player[100];
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
  Highscore highscore;
  String[] nachricht = new String[5];
  //FloatControl volume;
  // Ende Attribute
  
  
  
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
    this.args = args;
    Updater updater = new Updater(this);
    highscore = new Highscore(this);
    
    
    setTitle("JavaGame");  // Fenstertitel setzen
    setSize(1200,900);                            // Fenstergröße einstellen
    addWindowListener(new WindowListener());
    setLocationRelativeTo(null);                                        // EventListener für das Fenster hinzufügen
    setVisible(true);
    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
    
    
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
    
    // I'm in Space! SPACE!
    player[1] = new Player(texture[0],shottexture[0],dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,1,35,highscore.getName(1));
    player[2] = new Bot(texture[1],shottexture[1],dbImage,KeyEvent.VK_J,KeyEvent.VK_L,KeyEvent.VK_I,KeyEvent.VK_K,KeyEvent.VK_U,2,35,highscore.getName(1));
    player[3] = new Bot(texture[2],shottexture[2],dbImage,KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN,KeyEvent.VK_ENTER,3,35,highscore.getName(1));
    
    
    
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
      if (soundan) {
        ac.stop();
      } // end of if
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
      restartGame();
    }
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e)
  {
    
  }   
  
  public void restartGame() {
    for (int c=1;c<player.length;c++) {
      if (player[c] != null) {
        player[c].x=(int) (Math.random()*(ebenen[0][1]-ebenen[0][0])+ebenen[0][0]);
        player[c].y=0;
        player[c].health=100;
        player[c].jumpheigth=200;
        player[c].speed=5;
        player[c].sperrzeit=40;
        player[c].freezeControls=false;
        gamerunner.neu=false;
        gamerunner.schonneu=true;
      } // end of if
    } // end of for
    
    for (int c=0;c<gamerunner.shot.length;c++) {
      gamerunner.shot[c]=null;
      DamageLogig.shot[c]=null;
    } // end of for
    DamageLogig.counter=0;
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


