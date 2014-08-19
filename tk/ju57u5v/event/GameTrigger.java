package tk.ju57u5v.event;

public interface GameTrigger {
	public boolean trigger ();
	
	public void call(GameEvent event);
}
