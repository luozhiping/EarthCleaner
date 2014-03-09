package xfocus.game.components;

import java.util.ArrayList;

/*
 * 碰撞检测
 */
public class Collision {
	private ArrayList<DropThing> allDt;

	public Collision(){
		allDt = new ArrayList<DropThing>();
	}
	
	public void addDropThing(DropThing dt) {
		allDt.add(dt);
	}

	// 检测两物体是否碰撞
	public boolean isCollision(DropThing dt) {
		return false;
	}

	// 检测物体是否被收集了
	public boolean isCollected(DropThing dt) {
		return false;
	}
}
