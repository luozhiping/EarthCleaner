package xfocus.game.components;

import java.util.ArrayList;
import java.util.LinkedList;

/*
 * ��ײ���
 */
public class Collision {
	private ArrayList<DropThing> allDt;

	public Collision(ArrayList<DropThing> allDt){
		this.allDt = allDt;
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
