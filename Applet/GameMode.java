package Applet;

public class GameMode {
	JavaGame Game;
	boolean modeON=false;
	static int MODE_OFF=0;
	static int MODE_ON=1;
	boolean amanfang=true;


	public GameMode (JavaGame Game) {
		this.Game=Game;
	}

	public void update() {}

	public boolean isOn() {
		return modeON;
	}


	public void setState(int state) {
		if (state==0) {
			modeON=false;
		}
		else {
			modeON=true;
		}
	}
}
