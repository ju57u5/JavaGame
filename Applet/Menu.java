package Applet;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class Menu extends Frame implements ActionListener,ItemListener,KeyListener,AdjustmentListener {
  
  JavaGame Game;
  Button[] buttonSpieler = new Button[100] ;
  Button[] buttonSchuss = new Button[100] ;
  Button Sound,Restart,Fps,Bot,bPlayer,Weiter;
  Choice SpielerAuswahl;
  Button Right,Left,Up,Down,Shot;
  boolean bRight,bLeft,bUp,bDown,bShot;
  TextField Name,maxFps,Spieler;
  Label L1,L2;
  Scrollbar perks;
  public Menu (JavaGame Game) 
  
  {
    this.Game = Game;
    this.setLayout(null);
    this.addKeyListener(this);
    setTitle("Menu");  // Fenstertitel setzen
    setSize(Game.getWidth(),Game.getHeight());                            // Fenstergröße einstellen  
    addWindowListener(new TestWindowListener());                        // EventListener für das Fenster hinzufügen
    setLocationRelativeTo(null);
    setVisible(true);                                                   // Fenster (inkl. Inhalt) sichtbar machen
    
    for (int c=0;c<Game.texture.length;c++) {
      if (Game.texture[c] != null) {
        buttonSpieler[c] = new Button("Auswählen");
        buttonSpieler[c].setBounds(120+71*c,250,67,20);
        buttonSpieler[c].addActionListener(this);
        buttonSpieler[c].addKeyListener(this);
        this.add(buttonSpieler[c]);
      } // end of if
    } // end of for
    
    for (int c=0;c<Game.shottexture.length;c++) {
      if (Game.shottexture[c] != null) {
        buttonSchuss[c] = new Button("Auswählen");
        buttonSchuss[c].setBounds(120+71*c,330,67,20);
        buttonSchuss[c].addActionListener(this);
        buttonSchuss[c].addKeyListener(this);
        this.add(buttonSchuss[c]);
      }  
    } // end of for 
    
    SpielerAuswahl = new Choice();
    SpielerAuswahl.addKeyListener(this);
    SpielerAuswahl.setBounds(120,50,200,30);
    
    Name = new TextField(Game.player[1].name);
    Name.setBounds(120,90,200,20);
    Name.addKeyListener(this);
    this.add(Name);
    
    
    perks=new Scrollbar(Scrollbar.HORIZONTAL,Game.gamerunner.auftretenvonperks,5,1,100); 
    perks.addKeyListener(this);
    perks.addAdjustmentListener(this);
    this.add(perks);
    perks.setBounds(210,460,200,20);
    
    L1=new Label("Perkhäufigkeit:");
    L1.setBounds(120,460,90,20);
    L1.addKeyListener(this);
    this.add(L1);
    
    if (Game.soundan) {
      Sound = new Button("Sound aus");
    } // end of if
    else {
      Sound = new Button("Sound an");
    } // end of if-else
    Sound.setBounds(120,370,100,20);
    Sound.addActionListener(this);
    Sound.addKeyListener(this);
    this.add(Sound);
    
    if (Game.fpsan) {
      Fps = new Button("FPS ausblenden");
    } // end of if
    else {
      Fps = new Button("FPS anzeigen");
    } // end of if-else
    Fps.setBounds(120,400,100,20);
    Fps.addActionListener(this);
    Fps.addKeyListener(this);
    this.add(Fps);
    
    
    Bot = new Button("Bot an");
    Bot.setBounds(320,100,100,20);
    Bot.addActionListener(this);
    Bot.addKeyListener(this);
    this.add(Bot);
    
    bPlayer = new Button("Spieler an");
    bPlayer.setBounds(320,80,100,20);
    bPlayer.addActionListener(this);
    bPlayer.addKeyListener(this);
    this.add(bPlayer);
    
    
    int spielerAnzahl=0;
    for (int c=1;c<Game.player.length;c++) {
      if (Game.player[c] != null) {
        spielerAnzahl++;
      } // end of if
    } // end of for
    L1=new Label("Spieleranzahl :");
    L1.setBounds(400,50,90,20);
    L1.addKeyListener(this);
    this.add(L1);
    
    Spieler = new TextField(spielerAnzahl+"");
    Spieler.setBounds(490,50,100,20);
    Spieler.addKeyListener(this);
    this.add(Spieler);
    
    Restart = new Button("Restart");
    Restart.setBounds(120,430,100,20);
    Restart.addActionListener(this);
    Restart.addKeyListener(this);
    this.add(Restart);
    
    Weiter = new Button("Weiterspielen");
    Weiter.setBounds(230,430,100,20);
    Weiter.addActionListener(this);
    Weiter.addKeyListener(this);
    this.add(Weiter);
    
    Left = new Button("Left");
    Left.setBounds(120,570,100,20);
    Left.addActionListener(this);
    Left.addKeyListener(this);
    this.add(Left);
    
    Right = new Button("Right");
    Right.setBounds(350,570,100,20);
    Right.addActionListener(this);
    Right.addKeyListener(this);
    this.add(Right);
    
    Up = new Button("Up");
    Up.setBounds(240,540,100,20);
    Up.addActionListener(this);
    Up.addKeyListener(this);
    this.add(Up);
    
    Down = new Button("Down");
    Down.setBounds(240,570,100,20);
    Down.addActionListener(this);
    Down.addKeyListener(this);
    this.add(Down);
    
    Shot = new Button("Shot");
    Shot.setBounds(120,540,100,20);
    Shot.addActionListener(this);
    Shot.addKeyListener(this);
    this.add(Shot);
    
    maxFps = new TextField(Game.gamerunner.maxFPS+"");
    maxFps.setBounds(230,400,100,20);
    maxFps.addKeyListener(this);
    this.add(maxFps);
    
    for (int c=1;c<Game.player.length;c++) {
      if (Game.player[c] != null) {
        SpielerAuswahl.addItem("Spieler "+c);
      } // end of if
    } // end of for
    SpielerAuswahl.addItemListener(this);
    
    this.add(SpielerAuswahl);
  }
  
  public void adjustmentValueChanged(AdjustmentEvent e) {
    
    Game.gamerunner.auftretenvonperks=perks.getValue();
  }
  public void itemStateChanged(ItemEvent ie)
  {
    if (Game.player[SpielerAuswahl.getSelectedIndex()+1] != null) {
      Name.setText(Game.player[SpielerAuswahl.getSelectedIndex()+1].name);
      Left.setLabel(KeyEvent.getKeyText(Game.player[SpielerAuswahl.getSelectedIndex()+1].left));
      Right.setLabel(KeyEvent.getKeyText(Game.player[SpielerAuswahl.getSelectedIndex()+1].right));
      Up.setLabel(KeyEvent.getKeyText(Game.player[SpielerAuswahl.getSelectedIndex()+1].jump));
      Down.setLabel(KeyEvent.getKeyText(Game.player[SpielerAuswahl.getSelectedIndex()+1].down));
      Shot.setLabel(KeyEvent.getKeyText(Game.player[SpielerAuswahl.getSelectedIndex()+1].attack));
    } // end of if
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
    if (e.getKeyCode()==KeyEvent.VK_ESCAPE) {              //Zurückkehren zum Spiel
      playerCount();
      Game.gamerunner.running=true;
      if (Game.soundan) {
        Game.ac.loop(10);
      } // end of if
      this.dispose(); 
    }   
    else if (bLeft) {
      bLeft=false;
      Game.player[SpielerAuswahl.getSelectedIndex()+1].left = e.getKeyCode();
      Left.setLabel(""+e.getKeyChar());
    } // end of if
    else if (bRight) {
      bRight=false;
      Game.player[SpielerAuswahl.getSelectedIndex()+1].right = e.getKeyCode();
      Right.setLabel(""+e.getKeyChar());
    } // end of if
    else if (bUp) {
      bUp=false;
      Game.player[SpielerAuswahl.getSelectedIndex()+1].jump = e.getKeyCode();
      Up.setLabel(""+e.getKeyChar());
    } // end of if
    else if (bDown) {
      bDown=false;
      Game.player[SpielerAuswahl.getSelectedIndex()+1].down = e.getKeyCode();
      Down.setLabel(""+e.getKeyChar());
    } // end of if
    else if (bShot) {
      bShot=false;
      Game.player[SpielerAuswahl.getSelectedIndex()+1].attack = e.getKeyCode();
      Shot.setLabel(""+e.getKeyChar());
    } // end of if
    else {
      playerCount();
    } // end of if-else
    
    if (isNumeric(maxFps.getText()) && e.getKeyCode()!=KeyEvent.VK_ESCAPE) {
      if (Integer.parseInt(maxFps.getText())<=1000 && Integer.parseInt(maxFps.getText())>0) {
        Game.gamerunner.maxFPS=Integer.parseInt(maxFps.getText());
      } // end of if
    } // end of if
    
    
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
  
  public void playerCount() {
    if (isNumeric(Spieler.getText()) ) {
      int anzahl = Integer.parseInt(Spieler.getText());
      int spielerAnzahl=0;
      for (int c=1;c<Game.player.length;c++) {
        if (Game.player[c] != null) {
          spielerAnzahl++;
        } // end of if
      } // end of for
      if (anzahl>1 && anzahl<Game.player.length && anzahl != spielerAnzahl) {
        int textureAnzahl=0;
        int shottextureAnzahl=0;
        for (int cou=0;cou<Game.texture.length;cou++) {
          if (Game.texture[cou] != null) {
            textureAnzahl++;
          } // end of if
        } // end of for
        for (int cou=0;cou<Game.shottexture.length;cou++) {
          if (Game.shottexture[cou] != null) {
            shottextureAnzahl++;
          } // end of if
        } // end of for
        
        for (int c=1;c<Game.player.length;c++) {
          if (c>anzahl) {
            Game.player[c] = null;
          } // end of if
          
          else if(Game.player[c]==null) {
            
            Game.player[c] = new Bot(Game.texture[(int) (Math.random()*textureAnzahl)],Game.shottexture[(int) (Math.random()*shottextureAnzahl)],Game.dbImage,KeyEvent.VK_A,KeyEvent.VK_D,KeyEvent.VK_W,KeyEvent.VK_S,KeyEvent.VK_Q,c,35,"Bot");
            Game.player[c].laden(Game,(int) (Math.random()*(Game.ebenen[0][1]-Game.ebenen[0][0])+Game.ebenen[0][0]),0);
            
          } // end of if-else
        } // end of for
        SpielerAuswahl.removeAll();
        for (int c=1;c<Game.player.length;c++) {
          if (Game.player[c] != null) {
            SpielerAuswahl.addItem("Spieler "+c);
          } // end of if
        } // end of for
        Game.restartGame();
        
      } // end of if
    } // end of if
  }
  
  class TestWindowListener extends WindowAdapter
  {
    public void windowClosing(WindowEvent e)
    {
      playerCount();
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
      if (Game.texture[c] != null) {
        try {
          BufferedImage Image = ImageIO.read(Game.texture[c]);
          this.getGraphics().drawImage(Image,120+71*c,140,this);
        } catch(IOException exeption) {
          
        }
      }  
    } // end of for
    
    for (int c=0;c<Game.shottexture.length;c++) {
      if (Game.shottexture[c] != null) {
        try {
          BufferedImage Image = ImageIO.read(Game.shottexture[c]);
          this.getGraphics().drawImage(Image,120+71*c,280,this);
        } catch(IOException exeption) {
          
        }
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
      playerCount();
      Game.restartGame();
      if (Game.soundan) {
        Game.ac.loop(10);
      } // end of if
      this.dispose();
      Game.gamerunner.running=true;
    } // end of if 
    
    if (e.getSource()==Weiter) {
      playerCount();
      Game.gamerunner.running=true;
      if (Game.soundan) {
        Game.ac.loop(10);
      } // end of if
      this.dispose();
    } // end of if  
    
    if (e.getSource()==Bot) {
      int spieler = SpielerAuswahl.getSelectedIndex()+1;
      Game.player[spieler] = new Bot(Game.player[spieler].playertexture,Game.player[spieler].shottexture,Game.player[spieler].dbImage,Game.player[spieler].left,Game.player[spieler].right,Game.player[spieler].jump,Game.player[spieler].down,Game.player[spieler].attack,spieler,Game.player[spieler].yHealth,Game.player[spieler].name);
      Game.player[spieler].laden(Game,Game.player[spieler].x,Game.player[spieler].y);
      Game.player[spieler].name="Bot ";
      Name.setText("Bot");
    } // end of if
    
    if (e.getSource()==bPlayer) {
      int spieler = SpielerAuswahl.getSelectedIndex()+1;
      Game.player[spieler] = new Player(Game.player[spieler].playertexture,Game.player[spieler].shottexture,Game.player[spieler].dbImage,Game.player[spieler].left,Game.player[spieler].right,Game.player[spieler].jump,Game.player[spieler].down,Game.player[spieler].attack,spieler,Game.player[spieler].yHealth,Game.player[spieler].name);
      Game.player[spieler].laden(Game,Game.player[spieler].x,Game.player[spieler].y);
      Game.addKeyListener(Game.player[spieler]);
      String playername = Game.highscore.getName(spieler);
      if (playername != null && !playername.equals("Bot") && !playername.equals(" ")) {
        Game.player[spieler].name=playername;
      } // end of if
      else {
        playername = "Spieler "+spieler;
        Game.player[spieler].name="Spieler "+spieler;
      } // end of if-else
      Name.setText(playername);
    } // end of if
    
    if (e.getSource()==Left) {
      bLeft=true;
    } // end of if
    if (e.getSource()==Right) {
      bRight=true;
    } // end of if
    if (e.getSource()==Up) {
      bUp=true;
    } // end of if
    if (e.getSource()==Down) {
      bDown=true;
    } // end of if
    if (e.getSource()==Shot) {
      bShot=true;
    } // end of if
    
  }
  
  
  public void update(Graphics g) {
    
  }  
  
}