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
	 * ֡��ͼ����
	 * @param canvas
	 */
	public abstract void doDraw(Canvas canvas);
	
	/**
	 * ֡�߼�����
	 */
	public abstract void logic();
	
	/**
	 * ��Ļ���¼���
	 * @param x
	 * @param y
	 */
	public abstract void touchDownEvent(float x, float y);
	
	/**
	 * ��Ļ���º�̧�����
	 * @param x
	 * @param y
	 */
	public abstract void touchUpEvent(float x, float y);
	
	/**
	 * ��ָ����Ļ���ƶ�����
	 * @param x
	 * @param y
	 */
	public abstract void touchMove(float x, float y);
	
}
