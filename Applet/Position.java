package Applet;

public class Position {
	protected boolean orientation;
	protected int x,y;
	
	public Position (int x,int y,boolean orientation) {
		this.x=x;
		this.y=y;
		this.orientation = orientation;
	}
	
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	public boolean getOrientation() {
		return this.orientation;
	}
	public Position getPosition () {
		return this;
	}
	public void setX(int x) {
		this.x = x;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void setOrientation(boolean orientation) {
		this.orientation=orientation;
	}
}
