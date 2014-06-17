import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 17.06.2014
  * @author 
  */

public class JavaGame extends Applet implements KeyListener {
  
  String appletpfad = (System.getProperty("user.dir")+"\texture\test.png");
  
  //File texture = new File(appletpfad); 
  File texture = new File("Z:\\test.png");
  Player player1 = new Player(texture,texture);
  
  public void init() {
    
    
    
  } // end of init
  
  public void paint (Graphics g) {
    player1.laden(this,g);
    
  }  
  
} // end of class JavaGame

class Player extends Thread implements KeyListener{
  
  File playertexture,shottexture;
  BufferedImage textureImage = new BufferedImage(1000,1000,1);
  Graphics g;
  
  public Player(File playertexture, File shottexture) {
    this.playertexture = playertexture;
    this.shottexture = shottexture;
    //this.g = g;
    addkeyListener();
    
  }  
  
  public void laden(JavaGame Game,Graphics g) {
    
    try { 
      textureImage = ImageIO.read(playertexture);
    } catch(IOException exeption) {
      
    }
    
  }
  
  public boolean keyPressed(KeyEvent e) {
    int key = e.getKeyChar();
    
  }  
  
}
