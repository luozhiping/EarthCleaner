package xfocus.game.components;

import java.util.ArrayList;
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
	private long gameTime = 0, beginTime = 0, tempTime = 0, lastTime = 0;
	private DropThing touchedDT = null; // 被选取的dropthing
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

	public void addDropThing(int x, int radius) { // 添加dropthing到游戏世界
		DropThing dt = new DropThing(x, collision, radius);
		allDt.add(dt);
	}

	public void doDraw(Canvas canvas, Paint paint) {
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			dt.doDraw(canvas, paint);
		}

		canvas.save();
		// 分数绘制
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		canvas.drawText("score:" + player.getScore(), screenW - 100, 30, paint);
		// 收集器绘制
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

		gameTime = System.currentTimeMillis() - beginTime; // 游戏时间记录
		tempTime = System.currentTimeMillis() - lastTime;
	}

	private void dtLogic() { //dropthing逻辑
		for (int i = 0; i < allDt.size(); i++) {
			DropThing dt = allDt.get(i);
			if (screenH < (dt.getDropThingY() - dt.getRadius())) { // dropthing溢出屏幕则销毁该对象
				dt.setStatus(DropThing.DEAD);
				allDt.remove(i);
			} else {
				dt.logic();
			}
		}		
	}

	private void addDtToWorld(int rate) {  //添加dt到世界中
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

	public void touchDownEvent(int x, int y) { // 屏幕按下监听
		touchedDT = dtIsTouched(x, y);
		if (touchedDT == null) {
		}
		if (touchedDT != null) {

			Log.i("debug", "dtnonull");
		}
	}
	
	public DropThing dtIsTouched(int x, int y) {// 物体被触摸监听
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

	public void touchUpEvent() { // 触摸屏幕结束抬起监听
		if (touchedDT != null)
			touchedDT = null;
	}

	public void screenSlided(MotionEvent e1, MotionEvent e2) {  //屏幕被滑动处理函数
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
