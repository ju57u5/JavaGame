package Applet;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;



import Applet.JavaGame;



public class ScoreFrame extends Frame {
	JavaGame Game;
	
	public ScoreFrame (JavaGame Game, String winner, int gegner, int kills, int leben, int score) {
		if (Game.highscore.names == null) {
			dispose();
			}
		this.Game = Game;
		setTitle("Online Highscore");  
	    setSize(200,Game.getHeight());                            
	    addWindowListener(new TestWindowListener());
	    setLocation(Game.getLocation().x+Game.getWidth(), Game.getLocation().y);
	    toBack();
	    setVisible(true);
	    
	    Game.highscore.getHighscore();
	    java.awt.Graphics g = this.getGraphics();
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
	    g.drawString("Online Highscore", 20, 60);
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
	    int höhe=0;
	    for (int c=0;c<Game.highscore.scores.length;c++) {
	    	if (Game.highscore.names[c].startsWith(winner)) {
	    		g.setColor(Color.red);
	    	}
	    		g.drawString(Game.highscore.names[c], 20,80+c*20);
	    		g.drawString(Game.highscore.scores[c]+"", 120,80+c*20);
	    		g.setColor(Color.black);
	    		//g.drawLine(0, 80+c*20+5, getWidth(), 80+c*20+5);                               //Trennlinien
	    		höhe=80+c*20;
	    }
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
	    g.drawString("Dein Score", 20, höhe+60);
	    g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
	    
	    g.drawString("Gegner", 20,höhe+1*20+80);
	    g.drawString(""+gegner, 120,höhe+1*20+80);
	    
	    g.drawString("Kills", 20,höhe+2*20+80);
	    g.drawString(""+kills, 120,höhe+2*20+80);
	    
	    g.drawString("Leben", 20,höhe+3*20+80);
	    g.drawString(""+leben, 120,höhe+3*20+80);
	    
	    g.drawString("Gesamt", 20,höhe+4*20+80);
	    g.drawString(""+score, 120,höhe+4*20+80);
	    
	}
	
	 class TestWindowListener extends WindowAdapter
	  {
	    public void windowClosing(WindowEvent e)
	    {
	      e.getWindow().dispose();                   // Fenster "killen"
	    }           
	  }
}
