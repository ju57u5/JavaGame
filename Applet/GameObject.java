package Applet;


public class GameObject extends Position{
	private int width;
	private int height;
	
	public GameObject (int x, int y, boolean orientation, int width, int height) {
		super(x, y, orientation);
		this.width = width;
		this.height = height;
	}
	
	public boolean isHitBy(GameObject object) {
		int xDistance = this.getX() - object.getPosition().getX();
        int yDistance = this.getY() - object.getPosition().getY();
        
        if ( (xDistance > object.getWidth() && xDistance < -1*(this.width)) && (yDistance > -1*(object.getHeight()) && yDistance < height) ) {
        	return true; 
        }
		return false;
	}
	
	/////////////////////////////////////////////////
	///////////////Get/Set Funktions/////////////////
	/////////////////////////////////////////////////	
	
	
	
     public int getWidth () {
    	 return this.width;
     }

     public int getHeight () {
    	 return this.height;
     }
     public void setHeight(int h) {
    	 this.height = h;
     }
     public void setWidth(int w) {
    	 this.width = w;
     }
     
}
