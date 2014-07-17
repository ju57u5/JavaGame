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

class Menu extends Frame implements ActionListener,ItemListener,KeyListener {
  
  JavaGame Game;
  Button[] buttonSpieler = new Button[100] ;
  Button[] buttonSchuss = new Button[100] ;
  Button Sound,Restart,Fps;
  Choice SpielerAuswahl;
  TextField Name,maxFps;
  
  public Menu (JavaGame Game) 
  {
    this.Game = Game;
    this.setLayout(null);
    this.addKeyListener(this);
    setTitle("Menu");  // Fenstertitel setzen
    setSize(240+71*Game.texture.length,900);                            // Fenstergröße einstellen  
    addWindowListener(new TestWindowListener());
    setLocationRelativeTo(null);                                        // EventListener für das Fenster hinzufügen
    setVisible(true);                                                   // Fenster (inkl. Inhalt) sichtbar machen
    
    for (int c=0;c<Game.texture.length;c++) {
      
      buttonSpieler[c] = new Button("Auswählen");
      buttonSpieler[c].setBounds(120+71*c,250,67,20);
      buttonSpieler[c].addActionListener(this);
      buttonSpieler[c].addKeyListener(this);
      this.add(buttonSpieler[c]);
      
    } // end of for
    
    for (int c=0;c<Game.shottexture.length;c++) {
      
      buttonSchuss[c] = new Button("Auswählen");
      buttonSchuss[c].setBounds(120+71*c,330,67,20);
      buttonSchuss[c].addActionListener(this);
      buttonSchuss[c].addKeyListener(this);
      this.add(buttonSchuss[c]);
      
    } // end of for 
    
    SpielerAuswahl = new Choice();
    SpielerAuswahl.addKeyListener(this);
    SpielerAuswahl.setBounds(120,50,300,30);
    
    Name = new TextField(Game.player[1].name);
    Name.setBounds(120,90,300,20);
    Name.addKeyListener(this);
    this.add(Name);
    
    Sound = new Button("Sound aus");
    Sound.setBounds(120,370,100,20);
    Sound.addActionListener(this);
    Sound.addKeyListener(this);
    this.add(Sound);
    
    Fps = new Button("FPS anzeigen");
    Fps.setBounds(120,400,100,20);
    Fps.addActionListener(this);
    Fps.addKeyListener(this);
    this.add(Fps);
    
    Restart = new Button("Restart");
    Restart.setBounds(120,430,100,20);
    Restart.addActionListener(this);
    Restart.addKeyListener(this);
    this.add(Restart);
    
    maxFps = new TextField(Game.gamerunner.maxFPS+"");
    maxFps.setBounds(230,400,100,20);
    maxFps.addKeyListener(this);
    this.add(maxFps);
    
    for (int c=1;c<Game.player.length;c++) {
      SpielerAuswahl.addItem("Spieler "+c);
    } // end of for
    SpielerAuswahl.addItemListener(this);
    
    this.add(SpielerAuswahl);
  }
  
  
  public void itemStateChanged(ItemEvent ie)
  {
    Name.setText(Game.player[SpielerAuswahl.getSelectedIndex()+1].name);
  }
  
  public void keyReleased(KeyEvent e) 
  {
    
  }
  
  public void keyTyped(KeyEvent e)
  {
    
  }   
  
  public void keyPressed(KeyEvent e)
  {
    Game.player[SpielerAuswahl.getSelectedIndex()+1].name=Name.getText();
    if (isNumeric(maxFps.getText())) {
      if (Integer.parseInt(maxFps.getText())<=1000 && Integer.parseInt(maxFps.getText())>0) {
        Game.gamerunner.maxFPS=Integer.parseInt(maxFps.getText());
      } // end of if
    } // end of if
    if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {              //Zurückkehren zum Spiel
      Game.gamerunner.running=true;
      if (Game.soundan) {
        Game.ac.loop(10);
      } // end of if
      this.dispose(); 
    }   
  }  
  
  public static boolean isNumeric(String str)  
  {  
    try  
    {  
      double d = Double.parseDouble(str);  
    }  
    catch(NumberFormatException nfe)  
    {  
      return false;  
    }  
    return true;  
  }
  
  class TestWindowListener extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      Game.gamerunner.running=true;
      if (Game.soundan) {
        Game.ac.loop(10);
      } // end of if
      e.getWindow().dispose();                   // Fenster "killen"
    }           
  }
  
  public void paint(Graphics g)  
  {  
    super.paint(g);  
    for (int c=0;c<Game.texture.length;c++) {
      
      try {
        BufferedImage Image = ImageIO.read(Game.texture[c]);
        this.getGraphics().drawImage(Image,120+71*c,140,this);
      } catch(IOException exeption) {
        
      }
      
    } // end of for
    
    for (int c=0;c<Game.shottexture.length;c++) {
      
      try {
        BufferedImage Image = ImageIO.read(Game.shottexture[c]);
        this.getGraphics().drawImage(Image,120+71*c,280,this);
      } catch(IOException exeption) {
        
      }
      
    } // end of for  
  }  
  
  
  public void actionPerformed(ActionEvent e) {
    for (int c=0;c<buttonSpieler.length;c++ ) {
      if (e.getSource()==buttonSpieler[c]) {
        int spieler = SpielerAuswahl.getSelectedIndex()+1;
        try {
          BufferedImage Image = ImageIO.read(Game.texture[c]);
          Game.player[spieler].textureImage  = Image;
          Game.player[spieler].textureImageb = Game.player[spieler].verticalflip(Image);
        } 
        catch(IOException exeption) {
          
        }
      } // end of if
    }
    
    for (int c=0;c<buttonSchuss.length;c++ ) {
      if (e.getSource()==buttonSchuss[c]) {
        int spieler = SpielerAuswahl.getSelectedIndex()+1;
        Game.player[spieler].shottexture = Game.shottexture[c];
      } // end of if
    } // end of for
    
    if (e.getSource()==Sound) {
      if (Game.soundan) {
        Game.soundan=false;
        Sound.setLabel("Sound an");
      } // end of if
      else {
        Game.soundan=true;
        Sound.setLabel("Sound aus");
      } // end of if-else
    } // end of if
    
    if (e.getSource()==Fps) {
      if (Game.fpsan) {
        Game.fpsan=false;
        Fps.setLabel("FPS anzeigen");
      } // end of if
      else {
        Game.fpsan=true;
        Fps.setLabel("FPS ausblenden");
      } // end of if-else
    } // end of if
    
    if (e.getSource()==Restart) {
      for (int c=1;c<Game.player.length;c++) {
        Game.player[c].x=(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]);
        Game.player[c].y=0;
        Game.player[c].health=100;
        Game.player[c].jumpheigth=200;
        Game.player[c].speed=5;
        Game.player[c].sperrzeit=40;
        Game.player[c].freezeControls=false;
      } // end of for
      
      for (int c=0;c<Game.gamerunner.shot.length;c++) {
        Game.gamerunner.shot[c]=null;
        Game.DamageLogig.shot[c]=null;
      } // end of for
      Game.DamageLogig.counter=0;
      
    } // end of if   
    
    
  }
  
  
  public void update(Graphics g) {
    
  }  
  
}