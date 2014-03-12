package xfocus.game.components;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

/*
 * ��Ϸ����������
 */
public class World {
	final static int NORMAL = 1;

	private Collision collision;
	private Player player;
	private ArrayList<DropThing> allDt;// ��Ļ����������ļ���
	private int screenW, screenH; // ��Ļ�ߴ�
	private Random random;
	private int dt_x = 0,// ����������ĳ�ʼx����
			addDtOrNot = 0;// �����������������
	private int difficult = NORMAL; // ��Ϸ�Ѷ�
	private long gameTime = 0, beginTime = 0, tempTime = 0, lastTime = 0;
	private DropThing touchedDT = null; // ��ѡȡ��dropthing
	private int rate;

	public World(int screenW, int screenH) {
		this.screenH = screenH;
		this.screenW = screenW;
		collision = new Collision(allDt);
		player = new Player(screenW);
		allDt = new ArrayList<DropThing>();
		random = new Random();
		beginTime = System.currentTimeMillis();
		lastTime = System.currentTimeMillis();
		rate = 50;
		Log.i("debug", "world created");
	}

	public void addDropThing(int x, int radius) { // ���dropthing����Ϸ����
		DropThing dt = new DropThing(x, collision, radius);
		allDt.add(dt);
	}

	public void doDraw(Canvas canvas, Paint paint) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			dt.doDraw(canvas, paint);
		}

		canvas.save();
		// ��������
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		canvas.drawText("score:" + player.getScore(), screenW - 100, 30, paint);
		// �ռ�������
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, screenH - 300, 50, screenH, paint);
		canvas.drawRect(screenW - 50, screenH - 300, screenW, screenH, paint);
		canvas.restore();
	}

	public void logic() {
		if (tempTime > 5000 && (rate - 5) > 0) {
			rate -= 5;
			tempTime = 0;
			lastTime = System.currentTimeMillis();
		}
		
		addDtToWorld(rate);
		dtLogic();

		gameTime = System.currentTimeMillis() - beginTime; // ��Ϸʱ���¼
		tempTime = System.currentTimeMillis() - lastTime;
	}

	private void dtLogic() { //dropthing�߼�
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (screenH < (dt.getDropThingY() - dt.getRadius())) { // dropthing�����Ļ�����ٸö���
				dt.setStatus(DropThing.DEAD);
				allDt.remove(i);
			} else {
				dt.logic();
			}
		}		
	}

	private void addDtToWorld(int rate) {  //���dt��������
		if (addDtOrNot == random.nextInt(rate)) { // ģ����������dropthing
			dt_x = 50 + random.nextInt(screenW - 100);
			boolean createDtFlag = true;
			for (int i = 0; i < allDt.size(); i++) {
				if (CommonMethod.getDistance(dt_x, 0 - 50, allDt.get(i)
						.getDropThingX(), allDt.get(i).getDropThingY()) < 100) {
					createDtFlag = false;
				}
			}
			if (createDtFlag) {
				addDropThing(dt_x, 50);
			}
		}
	}

	public void touchDownEvent(int x, int y) { // ��Ļ���¼���
		touchedDT = dtIsTouched(x, y);
		if (touchedDT == null) {
		}
		if (touchedDT != null) {

			Log.i("debug", "dtnonull");
		}
	}
	
	public DropThing dtIsTouched(int x, int y) {// ���屻��������
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);

			if (Math.sqrt(Math.pow(dt.getDropThingX() - x, 2)
					+ Math.pow(dt.getDropThingY() - y, 2)) <= (dt.getRadius() + 10)) {// �ж��Ƿ�������dropthing������ģ�10�Ǽ��ٲ����Ѷ�
				Log.i("slided", "selected:" + i);
				return allDt.get(i);
			}
		}
		return null;
	}

	public void touchUpEvent() { // ������Ļ����̧�����
		if (touchedDT != null)
			touchedDT = null;
	}

	public void screenSlided(MotionEvent e1, MotionEvent e2) {  //��Ļ������������
		if (touchedDT != null) {
			if (CommonMethod.getDistance((int) e1.getX(), (int) e1.getY(),
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

}
