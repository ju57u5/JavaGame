package tk.ju57u5v.event;

import java.util.HashMap;

import Applet.JavaGame;

public class GameEvent {
	
	JavaGame Game;
	HashMap<String,String> arguments;
	
	public GameEvent(JavaGame Game, HashMap<String,String> arguments) {
		this.Game = Game;
		this.arguments = arguments;
	}
	
	public JavaGame getGame() {
		return this.Game;
	}
	
	public HashMap<String,String> getArguments() {
		return arguments;
	}
	
	public String getArgument(String index) {
		return arguments.get(index);
	}
}
