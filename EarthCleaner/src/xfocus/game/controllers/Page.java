package xfocus.game.controllers;

import android.graphics.Canvas;
import android.util.Log;

public abstract class Page {
	protected int screenW, screenH;
	public Page(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		Log.i("debug", "page cureated" + Integer.toString(this.screenW));
	}
	
	/**
	 * 帧绘图方法
	 * @param canvas
	 */
	public abstract void doDraw(Canvas canvas);
	
	/**
	 * 帧逻辑方法
	 */
	public abstract void logic();
	
	/**
	 * 屏幕按下监听
	 * @param x
	 * @param y
	 */
	public abstract void touchDownEvent(float x, float y);
	
	/**
	 * 屏幕按下后抬起监听
	 * @param x
	 * @param y
	 */
	public abstract void touchUpEvent(float x, float y);
	
	/**
	 * 手指在屏幕上移动监听
	 * @param x
	 * @param y
	 */
	public abstract void touchMove(float x, float y);
	
}
