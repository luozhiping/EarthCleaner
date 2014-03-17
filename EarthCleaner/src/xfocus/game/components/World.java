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
 * 游戏的物理世界
 */
public class World {
	final static int NORMAL = 1;

	private Collision collision;
	private Player player;
	private ArrayList<DropThing> allDt;// 屏幕中下落物体的集合
	private ArrayList<Score> allScore;// 分数显示的集合
	private int screenW, screenH; // 屏幕尺寸
	private Random random;
	private float dt_x = 0, dt_radius = 0;// 下落物体类的初始x坐标
	private int addDtOrNot = 0;// 下落物体随机生成器
	private int difficult = NORMAL; // 游戏难度
	private long gameTime = 0, beginTime = 0, pauseTime = 0, pausingTime = 0;
	private DropThing touchedDT = null; // 被选取的dropthing
	private int rate; // dt产生速率
	private Paint paint;
	private int gameMode;
	private RectF collectionLeft;
	private RectF collectionRight;
	private Score combo;
	private int cCombo;

	/**
	 * 构造函数
	 * 
	 * @param screenW
	 *            屏幕宽度
	 * @param screenH
	 *            屏幕长度
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
		rate = 20; // 生成dt的速率
		gameMode = GamePlaying.GAME_FREE_MODE;

		collectionLeft = new RectF(-100, screenH - 150, 100, screenH + 50);
		collectionRight = new RectF(screenW - 100, screenH - 150,
				screenW + 100, screenH + 50);
		Log.i("debug", "world created");

	}

	/**
	 * 闯关模式构造函数
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
		rate = 20; // 生成dt的速率
		gameMode = GamePlaying.GAME_MISSION_MODE;
		Log.i("debug", "world created");
	}

	/**
	 * 游戏运行帧绘图
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
	 * 分数栏绘制
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
	 * 收集器绘制
	 * @param canvas
	 */
	private void drawCollector(Canvas canvas) {
		canvas.save();
		paint.setTextSize(35);
		paint.setColor(Color.GREEN);
		canvas.drawArc(collectionLeft, 270, 90, true, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("好", collectionLeft.left + 120,
				collectionLeft.bottom - 130, paint);
		paint.setColor(Color.YELLOW);
		canvas.drawArc(collectionRight, 180, 90, true, paint);
		paint.setColor(Color.BLACK);
		canvas.drawText("坏", collectionRight.left + 40,
				collectionRight.bottom - 130, paint);
		canvas.restore();
	}

	/**
	 * 游戏运行帧逻辑
	 */
	public void playingLogic() {
		addDtToWorld(rate);
		dtLogic();
		scoreLogic();
		player.logic();
		gameTime = System.currentTimeMillis() - beginTime; // 游戏时间记录
	}

	/**
	 * 漂浮分数帧逻辑
	 */
	private void scoreLogic() {
		for (int i = 0; i < allScore.size(); i++) {
			if (allScore.get(i).getFrame() <= 0) {
				allScore.remove(i);
			}
		}
	}

	/**
	 * dt的帧逻辑函数
	 */
	private void dtLogic() {
		for (int i = 0; i < allDt.size(); i++) {
			if (allDt.get(i) == null) {
				allDt.remove(i);
				Log.i("debug", "dtnull");
			}
			if (screenH < (allDt.get(i).getDropThingY() - allDt.get(i)
					.getRadius())) { // dropthing溢出屏幕则销毁该对象
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
	 * dt被收集出发函数
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
	 * 收集成功漂浮的分数
	 * @param score
	 * @param type
	 */
	private void addScore(int score, int type) {
		allScore.add(new Score(screenW, screenH, score, type));
	}

	/**
	 * 添加dt到游戏世界中
	 * 
	 * @param rate
	 *            dt产生速率
	 */
	private void addDtToWorld(int rate) {
		if (addDtOrNot == random.nextInt(rate)) { // 模拟随机数添加dropthing
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
	 * 添加dropThing到游戏世界
	 * 
	 * @param x
	 *            dt初始x坐标
	 * @param radius
	 *            dt半径
	 */
	public void addDropThing(float x, float radius, int type) {
		allDt.add(new DropThing(x, collision, radius, type,
				CommonValue.DT_ROLE[type]));
	}

	/**
	 * 屏幕按下监听
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
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
	 * 判断是否有dt被选择
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 返回被选择的dt，如果没有选择则返回null
	 */
	public DropThing dtIsTouched(float x, float y) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (CommonMethod.getDistance(x, y, dt.getDropThingX(),
					dt.getDropThingY()) <= (dt.getRadius() + 10)) {// 判断是否触摸到了dropthing，这里的＋10是减少操作难度
				Log.i("slided", "selected:" + i);
				if (allDt.get(i).getState() == DropThing.DROPING)
					return allDt.get(i);
			}
		}
		return null;
	}

	/**
	 * 触摸屏幕抬起监听
	 */
	public void touchUpEvent() {
		if (touchedDT != null)
			touchedDT = null;
	}

	/**
	 * 屏幕滑动监听函数
	 * 
	 * @param e1
	 *            开始滑动的位置
	 * @param e2
	 *            结束滑动的位置
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
