package Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Player extends Thread implements KeyListener  {
  
  // Anfang Attribute1
  File playertexture,shottexture;
  BufferedImage textureImage,boomImage,freezeImage, textureImageb = new BufferedImage(1000,1000,1);
  
  Graphics g,db;
  KeyEvent taste;
  JavaGame Game;
  Image dbImage;
  boolean justupdated,characterInverted,firsttimepressed,freezeControls=false;
  int x,y=0;
  int stehenzahl = -1;                                                                                                    
  int schusssperre,left,right,jump,down,attack,xHealth,yHealth;                                                                                             
  int jumpup, jumpdown = 0;                                                                                                   
  boolean[] keys = new boolean[1000];
  int health = 100;
  String name;
  int jumpheigth = 200;
  int speed=5;
  int boomUp,boomLeft,boomRight;
  int jumpupdate=10;
  int sperrzeit=40;
  int perkzählerjump,perkzählerrun,perkzählershoot,perkw,drawboom=20,boomx,boomy;
   File boomtexture,freezetexture;
  int gefroren=0;
  boolean freeze,aufeinerebeneüberjemandem=true;
  int min=1000000000,dif,angriffsziel;
  Player murderer; 
  
  // Ende Attribute1
  
  public Player(File playertexture, File shottexture, Image dbImage, int left, int right , int jump, int down, int attack, int xHealth, int yHealth, String name) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    this.dbImage = dbImage;
    this.left = left;
    this.right = right;
    this.jump = jump;
    this.attack = attack;
    this.xHealth = (xHealth-1)*110+10;
    this.yHealth = yHealth;
    this.down = down;
    this.name = name;
  }  
  // Anfang Komponenten1
  // Ende Komponenten1
  // Anfang Methoden1
  
  public void laden(JavaGame Game, int x, int y) {
    
    this.Game = Game;
    
    this.x = x;
    this.y = y;
    
    boomUp=0;
    boomRight=0;
    boomLeft=0;
    boomtexture = new File(Game.basePath, "/boom.png");
    freezetexture = new File(Game.basePath, "/freeze.png");
    
    for (int c=0;c<keys.length;c++ ) {
      keys[c]=false;
    } // end of for
    
    try { 
      textureImage = ImageIO.read(playertexture);
    } catch(IOException exeption) {}
    
    try { 
      boomImage = ImageIO.read(boomtexture);
    } catch(IOException exeption) {}
    try { 
      freezeImage = ImageIO.read(freezetexture);
    } catch(IOException exeption) {}
    
    textureImageb = verticalflip(textureImage);
  }
  
  public BufferedImage verticalflip(BufferedImage img) {
    int w = img.getWidth();
    int h = img.getHeight();
    BufferedImage dimg = new BufferedImage(w, h, img.getType());
    Graphics2D g = dimg.createGraphics();
    
    g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
    g.dispose();
    return dimg;
  }
  
  
  
  public void updateKey()
  {
    if (firsttimepressed && !freezeControls) {
      
      if (keys[right] ) {                              
        x +=speed;
        characterInverted = false;
      } // end of if
      
      if (keys[left] ) {                                 
        x -=speed;
        characterInverted = true; 
      } // end of if
      
      if (keys[jump] ) {                                 
        setJump(jumpheigth);
      } // end of if
      
      if (keys[down]  && stehenzahl != 0 ) {                  // Stehenzahl 0: Player steht auf der Mainebene               
        jumpupdate = 20;
        y -=1;
        justupdated = true;
      } // end of if
      if (!keys[down]) {                  // Stehenzahl 0: Player steht auf der Mainebene
        jumpupdate = 10;
        
      } // end of if
      
      if (keys[attack] && y<900 && schusssperre == 0) {                             
        Shot bullet = new Shot(shottexture,!characterInverted, 10, Game, this);
        bullet.laden(x,y+50);
        schusssperre = sperrzeit;
      } // end of if
    } // end of if  
    
    
    if (y + textureImage.getHeight() > Game.ebenen[0][2] + 200) {
      health=0;
    } // end of if
    updateJump(jumpupdate);
    updateBoom(15);
    drawHealth();
    if (schusssperre > 0) {
      schusssperre--;
    } // end of if
    
    
    if (!characterInverted && health>0) {
      Game.dbImage.getGraphics().drawString(name,x+33,y-10);
      Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
    } // end of if
    else if (characterInverted && health>0){
      Game.dbImage.getGraphics().drawString(name,x+33,y-10);
      Game.dbImage.getGraphics().drawImage(textureImageb,x,y,Game);
    } // end of if-else
    
    if (x+textureImage.getWidth()<0) {                                               
      int xpoints[] = {0,10,10};
      int ypoints[] = {y,y+10,y-10};
      Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3);
    } // end of if
    
    if (x>Game.getWidth()) {
      int xpoints[] = {Game.getWidth(),Game.getWidth()-10,Game.getWidth()-10};
      int ypoints[] = {y,y+10,y-10};
      Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3);
    } // end of if
    
    if (y+textureImage.getHeight()<0) {
      int xpoints[] = {x,x-10,x+10};
      int ypoints[] = {0,10,10};
      Game.dbImage.getGraphics().drawPolygon(xpoints,ypoints,3); 
    } // end of if
    
    if (health <= 0) {
      freezeControls=true;
    } // end of if
    
    //Perk begrenzen
    //perks anzeigen
    if (jumpheigth==300) {
      Game.dbImage.getGraphics().drawString("Springen "+perkzählerjump/10,xHealth,yHealth+50);
      perkzählerjump=perkzählerjump-1;
    } // end of if
    if (sperrzeit==25) {
      Game.dbImage.getGraphics().drawString("Schießen "+perkzählershoot/10,xHealth,yHealth+65);
      perkzählershoot=perkzählershoot-1;
    } // end of if
    if (speed==10) {
      Game.dbImage.getGraphics().drawString("Rennen   "+perkzählerrun/10,xHealth,yHealth+80);
      perkzählerrun=perkzählerrun-1;
    } // end of if
    //perks anzeigen Ende
    
    //perks zurücksetzen
    if (perkzählerjump<0) {
      jumpheigth=200;
    } // end of if
    if (perkzählerrun<0) {
      speed=5;
    } // end of if
    if (perkzählershoot<0) {
      sperrzeit=40;
    } // end of if
    //perks zurücksetzen Ende
    
    //Perk begrenzen Ende
    drawboom=drawboom+1;
    
    if (drawboom<15) {
      Game.dbImage.getGraphics().drawImage(boomImage,boomx,boomy,Game);
    } // end of if
    
    if (gefroren<0&& freeze) {
      freezeControls=false;
      freeze=false;
    } // end of if
    if (gefroren>0) {
      freezeControls=true;
      Game.dbImage.getGraphics().drawImage(freezeImage,x-5,y,Game);
      Game.dbImage.getGraphics().drawString("Gefroren   "+gefroren/10,xHealth,yHealth+115);
      
    } // end of if
    gefroren-=1;
    
  }
  
  public void move(boolean rechts, int amount)  {
    
    
  }  
  
  public void drawHealth() {
    Graphics gra = Game.dbImage.getGraphics();
    gra.drawLine(xHealth,yHealth,xHealth+100,yHealth);
    gra.drawLine(xHealth,yHealth+10,xHealth+100,yHealth+10);
    gra.setColor(Color.red);
    gra.fillRect(xHealth+1,yHealth,health,10);
    gra.setColor(Color.black);
    gra.drawString(name,xHealth,yHealth+30);
  }  
  
  public void run() {
  }
  
  public void setDamage(int damage, boolean inverted, Player murderer) {
    health -= damage;
    boomUp = boomUp+(100-health)*10;
    this.murderer=murderer;
    if (inverted) {
      boomRight = boomRight+(100-health)*10;
    } // end of if
    else {
      boomLeft = boomLeft+(100-health)*10;
    } // end of if-else
    
    if (health < 0) {
      freezeControls = true;
    } // end of if
  }  
  
  public void updateBoom(int speed) {
    if (boomUp<0) {
      boomUp = 0;
    } // end of if
    
    if (boomUp>0) {
      y -= speed;
      boomUp -= speed;
    } // end of if
    
    
    if (boomLeft<0) {
      boomLeft = 0;
    } // end of if
    
    if (boomLeft>0) {
      x -= speed;
      boomLeft -= speed;
    } // end of if
    
    
    if (boomRight<0) {
      boomRight = 0;
    } // end of if
    
    if (boomRight>0) {
      x += speed;
      boomRight -= speed;
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
      justupdated = true;
    } // end of if
    
  } 
  
  public void updateJump(int speed) {
    boolean ebene = false;
    boolean kannstehen = false;
    int zahl = -1;
    stehenzahl = -1;
    int height = textureImage.getHeight();
    for (int counter=0;counter < Game.ebenen.length;counter++ ) {
      if (Game.ebenen[counter] != null ) {
        if ( (y-speed+height)<=Game.ebenen[counter][2] && y+height>=Game.ebenen[counter][2]) {  
          if (x+67 >= Game.ebenen[counter][0] && x <= Game.ebenen[counter][1]) {
            ebene = true;
            zahl = counter;
          }
        }
        
        if (y+height==Game.ebenen[counter][2]) {  
          if (x+67 >= Game.ebenen[counter][0] && x <= Game.ebenen[counter][1]) {
            kannstehen = true;
            stehenzahl = counter;
          }
        }
      }
    } // end of for
    
    if (ebene && !justupdated) {
      jumpdown = 0; 
    } // end of if
    
    if (ebene && !justupdated && jumpup<=0) {          // Beim losspringen soll nichts erkannt werden
      y = Game.ebenen[zahl][2]-height;
      jumpup = 0;
      
    }
    
    else if (jumpup>0) {
      y -= speed;
      jumpup -= speed;
    } // end of if
    
    else if (!kannstehen) {
      y += speed;
    } // end of if-else
    
    justupdated = false; //Setjump ist mindestens ein update her
  }   
  // Ende Methoden1
}
