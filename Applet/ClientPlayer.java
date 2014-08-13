package Applet;

import java.awt.Image;
import java.io.File;
import java.io.IOException;



public class ClientPlayer extends Player {
	public ClientPlayer(int x, int y, boolean orientation,int width, int height, File playertexture, File shottexture, int left, int right , int jump, int down, int attack, int xHealth, int yHealth, String name) {
	    super(x,y,orientation,width,height,playertexture, shottexture, left, right, jump, down, attack, xHealth, yHealth, name);
	    this.id = xHealth;
	} 
	
	public void updateKey()
	{
		 if (firsttimepressed && !freezeControls) {
		     
		    if (y + textureImage.getHeight() > Game.ebenen[0][2] + 200) {
		      health=0;
		    } // end of if
		    drawHealth();
		    if (schusssperre > 0) {
		      schusssperre--;
		    } // end of if
		    
		    
		    if (!orientation && health>0) {
		      Game.dbImage.getGraphics().drawString(name,x+33-(name.length()/2),y-10);
		      Game.dbImage.getGraphics().drawImage(textureImage,x,y,Game);
		    } // end of if
		    else if (orientation && health>0){
		      Game.dbImage.getGraphics().drawString(name,x+33-name.length()/2,y-10);
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
}
}

