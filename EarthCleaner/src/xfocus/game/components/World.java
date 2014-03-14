package xfocus.game.components;

import java.util.ArrayList;
import java.util.Date;
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
	private long gameTime = 0, beginTime = 0, pauseTime = 0, resumeTime = 0,
			pausingTime = 0;
	private DropThing touchedDT = null; // ��ѡȡ��dropthing
	private int rate; // dt��������
	private Paint paint;
	/**
	 * ���캯��
	 * 
	 * @param screenW
	 *            ��Ļ���
	 * @param screenH
	 *            ��Ļ����
	 */
	public World(int screenW, int screenH) {
		this.screenH = screenH;
		this.screenW = screenW;
		allDt = new ArrayList<DropThing>();
		paint = new Paint();

		collision = new Collision(allDt, screenW);
		player = new Player(screenW, screenH);
		random = new Random();
		beginTime = System.currentTimeMillis();
		rate = 10;
		Log.i("debug", "world created");
	}

	public void doDraw(Canvas canvas) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			dt.doDraw(canvas);
		}
		player.doDraw(canvas);

		canvas.save();
		// ��������
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		canvas.drawText("score:" + player.getScore(), screenW - 100, 30, paint);
		canvas.drawText("time:" +Long.toString(gameTime/1000) , screenW - 100, 60,
				paint);
		// �ռ�������
		paint.setColor(Color.GREEN);
		canvas.drawRect(0, screenH - 300, 50, screenH - 50, paint);
		canvas.drawRect(screenW - 50, screenH - 300, screenW, screenH - 50,
				paint);
		canvas.restore();
	}

	public void playingLogic() {
		addDtToWorld(rate);
		dtLogic();
		player.logic();
		gameTime = System.currentTimeMillis() - beginTime; // ��Ϸʱ���¼
	}

	/**
	 * ���dropThing����Ϸ����
	 * 
	 * @param x
	 *            dt��ʼx����
	 * @param radius
	 *            dt�뾶
	 */
	public void addDropThing(int x, int radius) {
		DropThing dt = new DropThing(x, collision, radius);
		allDt.add(dt);
	}

	/**
	 * dt��֡�߼�����
	 */
	private void dtLogic() {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (screenH < (dt.getDropThingY() - dt.getRadius())) { // dropthing�����Ļ�����ٸö���
				dt.setState(DropThing.DEAD);
				allDt.remove(i);
			} else {
				dt.logic();
			}
		}
	}

	/**
	 * ���dt����Ϸ������
	 * 
	 * @param rate
	 *            dt��������
	 */
	private void addDtToWorld(int rate) {
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

	/**
	 * ��Ļ���¼���
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public void touchDownEvent(int x, int y) {
		touchedDT = dtIsTouched(x, y);
		if (touchedDT == null) {
		}
		if (touchedDT != null) {

			Log.i("debug", "dtnonull");
		}
	}

	/**
	 * �ж��Ƿ���dt��ѡ��
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 * @return ���ر�ѡ���dt�����û��ѡ���򷵻�null
	 */
	public DropThing dtIsTouched(int x, int y) {
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

	/**
	 * ������Ļ̧�����
	 */
	public void touchUpEvent() {
		if (touchedDT != null)
			touchedDT = null;
	}

	/**
	 * ��Ļ������������
	 * 
	 * @param e1
	 *            ��ʼ������λ��
	 * @param e2
	 *            ����������λ��
	 */
	public void screenSlided(MotionEvent e1, MotionEvent e2) {
		if (touchedDT != null) {
			if (CommonMethod.getDistance((int) e1.getX(), (int) e1.getY(),
					touchedDT.getDropThingX(), touchedDT.getDropThingY()) <= touchedDT
					.getRadius()) {
				if (e2.getX() < e1.getX()) {
					touchedDT.setSlidedDirect(DropThing.SLIDED_LEFT);
				} else {
					touchedDT.setSlidedDirect(DropThing.SLIDED_RIGHT);
				}
				touchedDT.setState(DropThing.SLIDED);
			}
			touchedDT = null;
		}
	}

	public void unPause() {
		pausingTime = System.currentTimeMillis() - pauseTime;
		pauseTime = 0;
		beginTime += pausingTime;
	}

	public void pause() {
		pauseTime = System.currentTimeMillis();
		Log.i("time", "pause:" + Long.toString(pauseTime));

	}

}
