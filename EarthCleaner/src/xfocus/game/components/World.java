package xfocus.game.components;

import java.util.ArrayList;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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
	private long gameTime = 0;
	private long beginTime = 0;

	public World(int screenW, int screenH) {
		this.screenH = screenH;
		this.screenW = screenW;
		collision = new Collision(allDt);
		player = new Player(screenW);
		allDt = new ArrayList<DropThing>();
		random = new Random();
		beginTime = System.currentTimeMillis();
		Log.i("debug", "world created");
	}

	public void addDropThing(int x, int radius) { // ���dropthing����Ϸ����
		DropThing dt = new DropThing(x, collision, radius);
		allDt.add(dt);
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
		if (addDtOrNot == random.nextInt(50)) { // ģ�����dropthing
			dt_x = 20 + random.nextInt(screenW - 40);
			addDropThing(dt_x, 50);
		}
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (screenH < (dt.getDropThingY() - dt.getRadius())) { // dropthing�����Ļ�����ٸö���
				dt.setStatus(DropThing.DEAD);
				allDt.remove(i);
			} else {
				dt.logic();
			}
		}

		gameTime = System.currentTimeMillis() - beginTime; // ��Ϸʱ���¼
	}

}
