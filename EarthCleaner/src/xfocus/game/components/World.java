package xfocus.game.components;

import java.util.ArrayList;
import java.util.Random;

import xfocus.game.controllers.GamePlaying;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
	private ArrayList<Score> allScore;// ������ʾ�ļ���
	private int screenW, screenH; // ��Ļ�ߴ�
	private Random random;
	private float dt_x = 0, dt_radius = 0;// ����������ĳ�ʼx����
	private int addDtOrNot = 0;// �����������������
	private int difficult = NORMAL; // ��Ϸ�Ѷ�
	private long gameTime = 0, beginTime = 0, pauseTime = 0, pausingTime = 0;
	private DropThing touchedDT = null; // ��ѡȡ��dropthing
	private int rate; // dt��������
	private Paint paint;
	private int gameMode;
	private RectF collectionLeft;
	private RectF collectionRight;
	private Score combo;
	private int cCombo;

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
		allScore = new ArrayList<Score>();
		paint = new Paint();
		cCombo = 0;
		combo = new Score(screenW, screenH);
		collision = new Collision(allDt, screenW, screenH);
		player = new Player(screenW, screenH);
		random = new Random();
		beginTime = System.currentTimeMillis();
		rate = 20; // ����dt������
		gameMode = GamePlaying.GAME_FREE_MODE;

		collectionLeft = new RectF(-100, screenH - 150, 100, screenH + 50);
		collectionRight = new RectF(screenW - 100, screenH - 150,
				screenW + 100, screenH + 50);
		Log.i("debug", "world created");

	}

	/**
	 * ����ģʽ���캯��
	 * 
	 * @param screenW
	 * @param screenH
	 * @param mission
	 */
	public World(int screenW, int screenH, int mission) {
		this.screenH = screenH;
		this.screenW = screenW;
		allDt = new ArrayList<DropThing>();
		paint = new Paint();

		collision = new Collision(allDt, screenW, screenH);
		player = new Player(screenW, screenH);
		random = new Random();
		beginTime = System.currentTimeMillis();
		rate = 20; // ����dt������
		gameMode = GamePlaying.GAME_MISSION_MODE;
		Log.i("debug", "world created");
	}

	/**
	 * ��Ϸ����֡��ͼ
	 * @param canvas
	 */
	public void doDraw(Canvas canvas) {
		combo.drawCombo(canvas, cCombo);
		for (int i = 0; i < allDt.size(); i++) {
			allDt.get(i).doDraw(canvas);
		}
		for (int i = 0; i < allScore.size(); i++) {
			allScore.get(i).drawScore(canvas);
		}
		player.doDraw(canvas);
		drawScore(canvas);
		drawCollector(canvas);
	}

	/**
	 * ����������
	 * @param canvas
	 */
	private void drawScore(Canvas canvas) {
		canvas.save();
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		canvas.drawText("score:" + player.getScore(), screenW - 200, 30, paint);
		canvas.drawText("time:" + Long.toString(gameTime / 1000),
				screenW - 100, 60, paint);
		canvas.restore();
	}

	/**
	 * �ռ�������
	 * @param canvas
	 */
	private void drawCollector(Canvas canvas) {
		canvas.save();
		paint.setTextSize(35);
		paint.setColor(Color.GREEN);
		canvas.drawArc(collectionLeft, 270, 90, true, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("��", collectionLeft.left + 120,
				collectionLeft.bottom - 130, paint);
		paint.setColor(Color.YELLOW);
		canvas.drawArc(collectionRight, 180, 90, true, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("��", collectionRight.left + 40,
				collectionRight.bottom - 130, paint);
		canvas.restore();
	}

	/**
	 * ��Ϸ����֡�߼�
	 */
	public void playingLogic() {
		addDtToWorld(rate);
		dtLogic();
		scoreLogic();
		player.logic();
		gameTime = System.currentTimeMillis() - beginTime; // ��Ϸʱ���¼
	}

	/**
	 * Ư������֡�߼�
	 */
	private void scoreLogic() {
		for (int i = 0; i < allScore.size(); i++) {
			if (allScore.get(i).getFrame() <= 0) {
				allScore.remove(i);
			}
		}
	}

	/**
	 * dt��֡�߼�����
	 */
	private void dtLogic() {
		for (int i = 0; i < allDt.size(); i++) {
			if (allDt.get(i) == null) {
				allDt.remove(i);
				Log.i("debug", "dtnull");
			}
			if (screenH < (allDt.get(i).getDropThingY() - allDt.get(i)
					.getRadius())) { // dropthing�����Ļ�����ٸö���
				allDt.get(i).setState(DropThing.DEAD);
				allDt.remove(i);
				player.hp_minus();
				cCombo = 0;
			} else {
				allDt.get(i).logic();
			}
			if (CommonMethod.getDistance(allDt.get(i).getDropThingX(), allDt
					.get(i).getDropThingY(), collectionLeft.left + 100,
					collectionLeft.top + 100) <= allDt.get(i).getRadius() + 100) {
				dtCollected(i, CommonValue.DT_ROLE_GOOD);
			} else if (CommonMethod.getDistance(allDt.get(i).getDropThingX(),
					allDt.get(i).getDropThingY(), collectionRight.left + 100,
					collectionRight.top + 100) <= allDt.get(i).getRadius() + 100) {
				dtCollected(i, CommonValue.DT_ROLE_BAD);
			}
		}
	}

	/**
	 * dt���ռ���������
	 * @param i
	 * @param type
	 */
	private void dtCollected(int i, int type) {
		if (allDt.get(i).getDropThingRole() == type) {
			player.setScore(player.getScore() + allDt.get(i).getScore());
			player.hp_plus();
			addScore(allDt.get(i).getScore(), type);
			cCombo++;
		} else {
			player.setScore(player.getScore() - allDt.get(i).getScore());
			player.hp_minus();
			addScore(0 - allDt.get(i).getScore(), type);
			cCombo = 0;
		}
		allDt.remove(i);
	}

	/**
	 * �ռ��ɹ�Ư���ķ���
	 * @param score
	 * @param type
	 */
	private void addScore(int score, int type) {
		allScore.add(new Score(screenW, screenH, score, type));
	}

	/**
	 * ���dt����Ϸ������
	 * 
	 * @param rate
	 *            dt��������
	 */
	private void addDtToWorld(int rate) {
		if (addDtOrNot == random.nextInt(rate)) { // ģ����������dropthing
			dt_x = 50 + random.nextFloat() * (screenW - 100);
			boolean createDtFlag = true;
			for (int i = 0; i < allDt.size(); i++) {
				if (CommonMethod.getDistance(dt_x, 0 - 50, allDt.get(i)
						.getDropThingX(), allDt.get(i).getDropThingY()) < 100) {
					createDtFlag = false;
				}
			}
			if (createDtFlag) {
				dt_radius = 20 * random.nextFloat() + 30;
				int dt_type = random.nextInt(CommonValue.DT_TYPE.length);
				if (allDt.size() < 15)
					addDropThing(dt_x, dt_radius, dt_type);
			}
		}
	}

	/**
	 * ���dropThing����Ϸ����
	 * 
	 * @param x
	 *            dt��ʼx����
	 * @param radius
	 *            dt�뾶
	 */
	public void addDropThing(float x, float radius, int type) {
		allDt.add(new DropThing(x, collision, radius, type,
				CommonValue.DT_ROLE[type]));
	}

	/**
	 * ��Ļ���¼���
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public void touchDownEvent(float x, float y) {
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
	public DropThing dtIsTouched(float x, float y) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (CommonMethod.getDistance(x, y, dt.getDropThingX(),
					dt.getDropThingY()) <= (dt.getRadius() + 10)) {// �ж��Ƿ�������dropthing������ģ�10�Ǽ��ٲ����Ѷ�
				Log.i("slided", "selected:" + i);
				if (allDt.get(i).getState() == DropThing.DROPING)
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
			if (CommonMethod.getDistance(e1.getX(), e1.getY(),
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

	public Player getPlayer() {
		return player;
	}
}
