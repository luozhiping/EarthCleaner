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
 * 游戏的物理世界
 */
public class World {
	final static int NORMAL = 1;

	private Collision collision;
	private Player player;
	private ArrayList<DropThing> allDt;// 屏幕中下落物体的集合
	private int screenW, screenH; // 屏幕尺寸
	private Random random;
	private int dt_x = 0,// 下落物体类的初始x坐标
			addDtOrNot = 0;// 下落物体随机生成器
	private int difficult = NORMAL; // 游戏难度
	private long gameTime = 0, beginTime = 0, pauseTime = 0, resumeTime = 0,
			pausingTime = 0;
	private DropThing touchedDT = null; // 被选取的dropthing
	private int rate; // dt产生速率
	private Paint paint;
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
		// 分数绘制
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		canvas.drawText("score:" + player.getScore(), screenW - 100, 30, paint);
		canvas.drawText("time:" +Long.toString(gameTime/1000) , screenW - 100, 60,
				paint);
		// 收集器绘制
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
		gameTime = System.currentTimeMillis() - beginTime; // 游戏时间记录
	}

	/**
	 * 添加dropThing到游戏世界
	 * 
	 * @param x
	 *            dt初始x坐标
	 * @param radius
	 *            dt半径
	 */
	public void addDropThing(int x, int radius) {
		DropThing dt = new DropThing(x, collision, radius);
		allDt.add(dt);
	}

	/**
	 * dt的帧逻辑函数
	 */
	private void dtLogic() {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (screenH < (dt.getDropThingY() - dt.getRadius())) { // dropthing溢出屏幕则销毁该对象
				dt.setState(DropThing.DEAD);
				allDt.remove(i);
			} else {
				dt.logic();
			}
		}
	}

	/**
	 * 添加dt到游戏世界中
	 * 
	 * @param rate
	 *            dt产生速率
	 */
	private void addDtToWorld(int rate) {
		if (addDtOrNot == random.nextInt(rate)) { // 模拟随机数添加dropthing
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
	 * 屏幕按下监听
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
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
	 * 判断是否有dt被选择
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 * @return 返回被选择的dt，如果没有选择则返回null
	 */
	public DropThing dtIsTouched(int x, int y) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);

			if (Math.sqrt(Math.pow(dt.getDropThingX() - x, 2)
					+ Math.pow(dt.getDropThingY() - y, 2)) <= (dt.getRadius() + 10)) {// 判断是否触摸到了dropthing，这里的＋10是减少操作难度
				Log.i("slided", "selected:" + i);
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
