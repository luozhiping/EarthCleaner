package xfocus.game.components;

import java.util.ArrayList;

/*
 * ��ײ���
 */
public class Collision {
	private ArrayList<DropThing> allDt;

	public Collision(){
		allDt = new ArrayList<DropThing>();
	}
	
	public void addDropThing(DropThing dt) {
		allDt.add(dt);
	}

	// ����������Ƿ���ײ
	public boolean isCollision(DropThing dt) {
		return false;
	}

	// ��������Ƿ��ռ���
	public boolean isCollected(DropThing dt) {
		return false;
	}
}
