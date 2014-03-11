package xfocus.game.controllers;

import java.util.Random;

import xfocus.game.components.DropThing;
import xfocus.game.components.World;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/*
 * 游戏过程控制器
 */
public class GamePlaying {
	private World world;
	private int screenW, screenH;

	public GamePlaying(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
	}

	public void logic() {
		world.logic();

	}

	public void doDraw(Canvas canvas, Paint paint) {
		world.doDraw(canvas, paint);
	}

	public void init_world() {
		world = new World(screenW, screenH);

	}

	private DropThing touchedDT = null; // 被选取的dropthing

	public void touchDownEvent(int x, int y) { // 屏幕按下监听
		touchedDT = world.dtIsTouched(x, y);
		if (touchedDT == null) {
		}
		if (touchedDT != null) {

			Log.i("debug", "dtnonull");
		}
	}

	public void touchUpEvent() { // 触摸屏幕结束抬起监听
		if (touchedDT != null)
			touchedDT = null;
	}

	public void onFling(MotionEvent e1, MotionEvent e2, float distanceX,  // 滑动屏幕监听
			float distanceY) {
		if (touchedDT != null) {
			if (getDistance((int) e1.getX(), (int) e1.getY(),
					touchedDT.getDropThingX(), touchedDT.getDropThingY()) <= touchedDT
					.getRadius()) {
				if (e2.getX() < e1.getX()) {
					touchedDT.setSlidedDirect(DropThing.SLIDED_LEFT);
				} else {
					touchedDT.setSlidedDirect(DropThing.SLIDED_RIGHT);
				}
				touchedDT.setStatus(DropThing.SLIDED);
			}
			touchedDT = null;
		}

	}

	private int getDistance(int x1, int y1, int x2, int y2) {  // 获取两点距离
		return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

}
