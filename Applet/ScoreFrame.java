package Applet;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import Applet.JavaGame;



public class ScoreFrame extends Frame {
	JavaGame Game;
	public ScoreFrame (JavaGame Game, String winner) {
		this.Game = Game;
		setTitle("Menu");  // Fenstertitel setzen
	    setSize(200,Game.getHeight());                            // Fenstergröße einstellen  
	    addWindowListener(new TestWindowListener());                        // EventListener für das Fenster hinzufügen
	    setLocationRelativeTo(null);
	    setVisible(true);
	    
	    Game.highscore.getHighscore();
	    for (int c=0;c<Game.highscore.scores.length;c++) {
	    	if (Game.highscore.names[c].startsWith(winner)) {
	    		this.getGraphics().drawString(Game.highscore.names[c], 20,20+c*20);
	    		this.getGraphics().drawString(Game.highscore.scores[c]+"", 120,20+c*20);
	    	}
	    }
	}
	
	 class TestWindowListener extends WindowAdapter
	  {
	    public void windowClosing(WindowEvent e)
	    {
	      e.getWindow().dispose();                   // Fenster "killen"
	    }           
	  }
}
