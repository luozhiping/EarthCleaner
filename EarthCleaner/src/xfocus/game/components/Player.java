package xfocus.game.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Player {
	// 声明一个画笔
	private Paint paint = new Paint();
	// 声明屏幕的宽高
	private int screenW, screenH;
	// 底矩形的高度
	private int rectH = 50;
	// 显示当前血量是否收集正确
	private boolean collected;
	// 总血量为屏幕的宽度
	private int hp_length;
	// 声明当前血量
	private int hp_current;
	int score;
	int currentFrame = 0; // 用于操作当前显示帧

	public Player(int sw, int sh) {
		screenW = sw;
		screenH = sh;
		paint = new Paint();
		hp_current = 100;
		score = 0;
	}

	public void doDraw(Canvas canvas) {
		int bloodVary = hp_current; // bloodVary是血量的变化
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED); // 显示当前血量画笔的颜色
		canvas.drawRect(0, screenH - rectH, screenW, screenH, paint);// 矩形图案即总血量的显示
		paint.setStyle(Style.FILL);
		canvas.drawRect(0, screenH - rectH, bloodVary, screenH, paint);// 矩形图案即血量的显示

	}

	/**
	 * 游戏逻辑
	 */
	public void logic() {
//		if (collected == true) // 判断收集正确
//		{
//			if (hp_current >= hp_length) // 当前血量已经满的时候
//				hp_current = 0; // 【添加game success语句】
//			else
//				hp_plus(); // 常规处理，调用血量增加情形
//		} else {
//			if (hp_current <= 0) // 当前血量不大于0时
//				; // 【添加game fail语句】
//			else
//				hp_minus(); // 常规处理，调用血量递减情形
//		}
	}

	public void hp_plus() {
		hp_current++;
	}

	public void hp_minus() {
		hp_current--;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public int getScore() {
		return score;
	}

}
