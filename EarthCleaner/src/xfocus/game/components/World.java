package xfocus.game.components;

import java.util.ArrayList;

import android.util.Log;

/*
 * 游戏的物理世界
 */
public class World {
	public Collision collision;
	public Player player;
	public ArrayList<DropThing> allDt;
	public World(int screenW, int screenH){
		collision = new Collision();
		player = new Player(screenW);
		allDt = new ArrayList<DropThing>();
	}
	
	public void addDropThing(int x) {
		DropThing dt = new DropThing(x, collision);
		allDt.add(dt);
		collision.addDropThing(dt);
	}
	

}
