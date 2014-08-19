package tk.ju57u5v.event;

import java.awt.Event;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Applet.JavaGame;

public class EventHandler {
	ArrayList<GameCallable> observer = new ArrayList<GameCallable>();
	ArrayList<String> observerQuery = new ArrayList<String>();
	JavaGame Game;
	
	public EventHandler (JavaGame Game) {
		this.Game = Game;
	}
	
	public void registerCallable(String eventQuery, GameCallable callable) {
		observerQuery.add(eventQuery);
		observer.add(callable);
	}
	
	public void removeCallable(int index) {
		observer.remove(index);
		observerQuery.remove(index);
	}
	
	public void trigger(String query) {

		for (int c=0;c<observer.size();c++) {
			if (observerQuery.get(c).equals(query)) {
				observer.get(c).call(new GameEvent(Game, null));
			}
		}
	}
	
	public void trigger(String query, HashMap<String,String> arguments) {
		for (int c=0;c<observer.size();c++) {
			if (observerQuery.get(c).equals(query)) {
				observer.get(c).call(new GameEvent(Game, arguments));
			}
		}
	}
	
	public void registerTestEvents() {
		registerCallable("death", event -> { //Tipp: Lambda lernen :P
			JavaGame Game = event.getGame();
			System.out.println("TestEvent");
			System.out.println("Spieler mit ID: "+event.getArgument("id"));
		});
	}
	
}
