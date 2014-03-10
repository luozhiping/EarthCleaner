package xfocus.game;

import xfocus.game.components.World;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 主要SurfaceView 用于显示游戏画面
 * 
 */
public class MainSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	private SurfaceHolder sfh; // SurfaceView装载器（SurfaceView必备）
	private Paint paint; // 画笔
	private Thread th;
	private boolean flag; // 线程标识符
	private Canvas canvas;
	private int screenW, screenH;
	private World world;
	private GamePlaying gamePlaying;

	final static int GAME_MENU = 0;
	final static int GAME_PLAYING = 1;
	final static int GAME_WIN = 2;
	final static int GAME_LOST = 3;
	final static int GAME_START = 4;
	public static int status = 4;

	// 构造函数
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sfh = this.getHolder(); // 装载器初始化
		sfh.addCallback(this);
		paint = new Paint(); // 画笔初始化
		setFocusable(true);
		Log.i("debug", "surfaceView created");
	}

	@Override
	public void run() { // 游戏主线程
		while (flag) {
			long start = System.currentTimeMillis();
			logic();
			doDraw();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 100) { // 每帧100ms
					Thread.sleep(100 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void doDraw() {// 帧绘图
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawRGB(255, 255, 255); // 刷屏
				switch (status) {
				case GAME_MENU:
					break;
				case GAME_START:
					break;
				case GAME_PLAYING:
					gamePlaying.doDraw(canvas, paint);
					break;
				case GAME_WIN:
					break;
				case GAME_LOST:
					break;
				}

			}
		} catch (Exception e) {

		} finally {
			if (canvas != null) {
				sfh.unlockCanvasAndPost(canvas);
			}
		}
	}

	private void logic() {// 帧逻辑
		switch (status) {
		case GAME_MENU:
			break;
		case GAME_START:
			prePlayInit();
			break;
		case GAME_PLAYING:
			gamePlaying.logic();
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
	}

	private void prePlayInit() {//游戏开始前初始化
		gamePlaying = new GamePlaying(screenW, screenH);
		gamePlaying.init_world();
		status = GAME_PLAYING;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (status) {
		case GAME_MENU:
			break;
		case GAME_PLAYING:
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
		}
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenH = this.getHeight();
		screenW = this.getWidth();
		flag = true;
		th = new Thread(this);
		th.start();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
	}
}
