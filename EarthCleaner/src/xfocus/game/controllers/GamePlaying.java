package xfocus.game.controllers;

import xfocus.game.R;
import xfocus.game.components.CommonMethod;
import xfocus.game.components.World;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;

/*
 * 游戏过程控制器
 */
public class GamePlaying {
	public final static int PLAYING = 0; // 游戏状态： 进行中
	public final static int PAUSE = 1; // 游戏状态： 暂停
	final static int MUSIC_PLAY = 0;
	final static int MUSIC_PAUSE = 1;
	final static int MUSIC_STOP = 2;

	private World world; // 游戏世界
	private int screenW, screenH; // 屏幕尺寸
	private int status; // 游戏状态
	private RectF menuRect, btnResume, btnSetting, btnExit, btnPause; // 菜单按钮矩形
	private Paint pBtnResume, pBtnSetting, pBtnExit; // 菜单按钮画笔
	private int musicStatus = 0;
	private MediaPlayer backGroundMusic;// 背景音乐
	private SoundPool sp;// 游戏音效
	private Paint paint; 
	public GamePlaying(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		status = PLAYING;
		paint = new Paint();
	}

	public void logic() { // 逻辑
		switch (status) {
		case PLAYING:
			world.logic();
			break;
		case PAUSE:
			break;
		}
	}

	public void doDraw(Canvas canvas) { // 绘图
		world.doDraw(canvas);
		canvas.save();
		paint.setColor(Color.BLACK);
		canvas.drawRect(10, 10, 20, 40, paint);
		canvas.drawRect(25, 10, 35, 40, paint);
		canvas.restore();
		switch (status) {
		case PLAYING:
			break;
		case PAUSE:
			drawPauseMenu(canvas, paint);
			break;
		}
	}

	private void drawPauseMenu(Canvas canvas, Paint paint) {// 暂停菜单绘制
		canvas.save();

		paint.setColor(Color.BLUE);
		paint.setAlpha(230);
		canvas.drawRoundRect(menuRect, 10, 10, paint);

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		paint.setTextSize(30);
		canvas.drawText("游戏菜单", menuRect.left + 40, menuRect.top + 45, paint);
		canvas.drawLine(menuRect.left, menuRect.top + 70, menuRect.right,
				menuRect.top + 70, paint);

		drawMenuButton(canvas, pBtnResume, btnResume, "继续");
		drawMenuButton(canvas, pBtnSetting, btnSetting, "设置");
		drawMenuButton(canvas, pBtnExit, btnExit, "退出");

		paint.setAlpha(255);
		canvas.restore();
	}

	private void drawMenuButton(Canvas canvas, Paint paint, RectF rect,// 暂停菜单上的按钮绘制
			String btnText) {
		canvas.save();
		canvas.drawRoundRect(rect, 10, 10, paint);
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(35);
		canvas.drawText(btnText, rect.left + 35, rect.bottom - 12, textPaint);
		canvas.restore();
	}

	public void init_world(Context context) {
		world = new World(screenW, screenH);
		init_menu();
		init_music(context);
	}

	private int soundClick;
	
	private void init_music(Context context) {
		backGroundMusic = MediaPlayer.create(context, R.raw.bgm);
		backGroundMusic.setLooping(true);
		backGroundMusic.start();
		sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
//		soundClick = sp.load(context, R.raw.)
	}

	private void init_menu() {// 暂停菜单初始化
		menuRect = new RectF(screenW / 2 - 100, screenH / 2 - 300,
				screenW / 2 + 100, screenH / 2);
		btnResume = new RectF(menuRect.left + 30, menuRect.top + 80,
				menuRect.right - 30, menuRect.top + 130);
		btnSetting = new RectF(btnResume.left, btnResume.bottom + 20,
				btnResume.right, btnResume.bottom + 70);
		btnExit = new RectF(btnSetting.left, btnSetting.bottom + 20,
				btnSetting.right, btnSetting.bottom + 70);
		btnPause = new RectF(0, 0, 45, 50);
		Paint btnPaint = new Paint();
		btnPaint.setColor(Color.GRAY);
		btnPaint.setAlpha(255);
		pBtnResume = new Paint(btnPaint);
		pBtnExit = new Paint(btnPaint);
		pBtnSetting = new Paint(btnPaint);
	}

	public void touchDownEvent(int x, int y) { // 屏幕按下监听
		switch (status) {
		case PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				// status = PAUSE;
			} else {
				world.touchDownEvent(x, y);
			}
			break;
		case PAUSE:

			break;
		}
	}

	public void touchUpEvent(int x, int y) { // 触摸屏幕结束抬起监听
		switch (status) {
		case PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				status = PAUSE;
				backGroundMusic.pause();
			} else {
				world.touchUpEvent();
			}
			break;
		case PAUSE:
			if (CommonMethod.isTouchInRect(x, y, menuRect)) {
				if (CommonMethod.isTouchInRect(x, y, btnResume)) {
					status = PLAYING;
					pBtnResume.setColor(Color.GRAY);
					backGroundMusic.start();
				} else if (CommonMethod.isTouchInRect(x, y, btnExit)) {
				}
			} else {
				status = PLAYING;
				backGroundMusic.start();
			}
			break;
		}
	}

	public void touchMove(int x, int y) {
		switch (status) {
		case PLAYING:
			break;
		case PAUSE:
			if (CommonMethod.isTouchInRect(x, y, btnResume)) {
				pBtnResume.setColor(Color.DKGRAY);
			} else {
				pBtnResume.setColor(Color.GRAY);
			}
			break;
		}
	}

	public void onFling(MotionEvent e1, MotionEvent e2, float distanceX, // 滑动屏幕监听
			float distanceY) {
		world.screenSlided(e1, e2);
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
	
	

}
