package xfocus.game.view;

import xfocus.game.components.CommonValue;
import xfocus.game.controllers.About;
import xfocus.game.controllers.GameMenu;
import xfocus.game.controllers.GamePlaying;
import xfocus.game.controllers.HighScore;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * 主要SurfaceView 用于显示游戏画面
 * 
 */
public class MainSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, OnGestureListener, OnTouchListener {

	private MainThread thread;
	private GestureDetector gesture;// 手势监听
	private int screenW, screenH; // 屏幕尺寸
	private Context mContext;
	private Bundle outState;
	private GameMenu gameMenu;
	public static int gameState = CommonValue.GAME_STATE_MENU; // 游戏状态码

	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param attrs
	 */
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mContext = context;
		thread = new MainThread(holder, mContext);
		thread.start();
		setFocusable(true);
		gesture = new GestureDetector(this);
		this.setOnTouchListener(this);

		Log.i("debug", "surfaceView destructed");
	}

	/**
	 * 构造函数
	 * 
	 * @param context
	 */
	public MainSurfaceView(Context context, int width, int height) {
		super(context);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mContext = context;
		screenW = width;
		screenH = height;
		thread = new MainThread(holder, mContext);
		thread.start();

		setFocusable(true);
		gesture = new GestureDetector(this);
		this.setOnTouchListener(this);
	}

	/**
	 * 游戏主线程
	 * 
	 * @author lzp
	 * 
	 */
	class MainThread extends Thread {
		private SurfaceHolder mSurfaceHolder; // SurfaceView装载器（SurfaceView必备）
		private Canvas canvas;
		private GamePlaying gamePlaying;
		private About about;
		private HighScore highScore;
		private boolean flag = false; // 线程标识符

		/**
		 * 构造函数
		 * 
		 * @param surfaceHolder
		 * @param context
		 */
		public MainThread(SurfaceHolder surfaceHolder, Context context) {
			mSurfaceHolder = surfaceHolder;
			mContext = context;
			Log.i("debug", "thread created");
			Resources res = context.getResources();
		}

		@Override
		public void run() {
			while (true) {
				long start = System.currentTimeMillis();
				if (screenW != 0) {
					logic();
					doDraw();
				}
				long end = System.currentTimeMillis();
				try {
					if (end - start < 25) { // 设定帧速率
						Thread.sleep(25 - (end - start));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void doDraw() {// 帧绘图
			try {
				canvas = mSurfaceHolder.lockCanvas();
				if (canvas != null) {
					canvas.drawRGB(255, 255, 255); // 刷屏
					switch (gameState) {
					case CommonValue.GAME_STATE_MENU:
						gameMenu.doDraw(canvas);
						break;
					case CommonValue.GAME_STATE_HIGHSCORE:
						if (highScore == null)
							highScore = new HighScore(screenW, screenH);
						highScore.doDraw(canvas);
						break;
					case CommonValue.GAME_STATE_ABOUT:
						if (about == null)
							about = new About(screenW, screenH);
						about.doDraw(canvas);
						break;
					case CommonValue.GAME_STATE_START:
						break;
					case CommonValue.GAME_STATE_PLAYING:
						gamePlaying.doDraw(canvas);
						break;
					}

				}
			} catch (Exception e) {

			} finally {
				if (canvas != null) {
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

		private void logic() {// 帧逻辑
			switch (gameState) {
			case CommonValue.GAME_STATE_MENU:
				gameMenu.logic();
				break;
			case CommonValue.GAME_STATE_START:
				prePlayInit();
				break;
			case CommonValue.GAME_STATE_PLAYING:
				gamePlaying.logic();
				break;
			case CommonValue.GAME_STATE_WIN:
				break;
			case CommonValue.GAME_STATE_LOST:
				break;
			}
		}

		/**
		 * 游戏开始前的初始化
		 */
		private void prePlayInit() {
			gamePlaying = new GamePlaying(screenW, screenH);
			gamePlaying.init_world(getContext());
			gameState = CommonValue.GAME_STATE_PLAYING;
		}

		/**
		 * 设置游戏状态
		 * 
		 * @param state
		 */
		public void setState(int state) {
			synchronized (mSurfaceHolder) {
				switch (gameState) {
				case CommonValue.GAME_STATE_PLAYING:
					// gamePlaying.setState();
					break;
				}
			}
		}

		/**
		 * 重载游戏状态
		 * 
		 * @param savedState
		 */
		public synchronized void restoreState(Bundle savedState) {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "restoreState");
				if (savedState != null) {
					gamePlaying.setStatus(savedState.getInt("status"));
				}
			}
		}

		/**
		 * 保存游戏状态
		 * 
		 * @param map
		 * @return
		 */
		public Bundle saveState(Bundle map) {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "saveState");

				switch (gameState) {
				case CommonValue.GAME_STATE_PLAYING:
					gamePlaying.saveState(map);
					break;
				}
			}
			return map;
		}

		/**
		 * 游戏暂停
		 */
		public void pause() {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "pause");
				if (gameState == CommonValue.GAME_STATE_PLAYING) {
					// flag = false;
					gamePlaying.pause();
				}
			}
		}

		/**
		 * 游戏继续
		 */
		public void unPause() {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "un_pause");
				if (gameState == CommonValue.GAME_STATE_PLAYING)
					gamePlaying.unPause();
			}
		}

		/**
		 * 触摸按下监听
		 * 
		 * @param x
		 *            x坐标
		 * @param y
		 *            y坐标
		 */
		public void doTouchDown(float x, float y) {
			switch (gameState) {
			case CommonValue.GAME_STATE_MENU:
				gameMenu.touchDownEvent(x, y);
				break;
			case CommonValue.GAME_STATE_HIGHSCORE:
				highScore.touchDownEvent(x, y);
				break;
			case CommonValue.GAME_STATE_ABOUT:
				about.touchDownEvent(x, y);
				break;
			case CommonValue.GAME_STATE_START:
				break;
			case CommonValue.GAME_STATE_PLAYING:
				gamePlaying.touchDownEvent(x, y);
				break;
			case CommonValue.GAME_STATE_WIN:
				break;
			case CommonValue.GAME_STATE_LOST:
				break;
			}
		}

		/**
		 * 触摸抬起监听
		 * 
		 * @param x
		 *            x坐标
		 * @param y
		 *            y坐标
		 */
		public void doTouchUp(float x, float y) {
			switch (gameState) {
			case CommonValue.GAME_STATE_MENU:
				gameMenu.touchUpEvent(x, y);
				break;
			case CommonValue.GAME_STATE_HIGHSCORE:
				highScore.touchUpEvent(x, y);
				break;
			case CommonValue.GAME_STATE_ABOUT:
				about.touchUpEvent(x, y);
				break;
			case CommonValue.GAME_STATE_START:
				break;
			case CommonValue.GAME_STATE_PLAYING:
				gamePlaying.touchUpEvent(x, y);
				break;
			case CommonValue.GAME_STATE_WIN:
				break;
			case CommonValue.GAME_STATE_LOST:
				break;
			}
		}

		/**
		 * 触摸移动监听
		 * 
		 * @param x
		 *            当前x坐标
		 * @param y
		 *            当前y坐标
		 */
		public void doTouchMove(float x, float y) {
			switch (gameState) {
			case CommonValue.GAME_STATE_MENU:
				gameMenu.touchMove(x, y);
				break;
			case CommonValue.GAME_STATE_HIGHSCORE:
				highScore.touchMove(x, y);
				break;
			case CommonValue.GAME_STATE_ABOUT:
				about.touchMove(x, y);
				break;
			case CommonValue.GAME_STATE_START:
				break;
			case CommonValue.GAME_STATE_PLAYING:
				gamePlaying.touchMove(x, y);
				break;
			case CommonValue.GAME_STATE_WIN:
				break;
			case CommonValue.GAME_STATE_LOST:
				break;
			}
		}

		/**
		 * 屏幕滑动监听
		 * 
		 * @param e1
		 * @param e2
		 * @param distanceX
		 * @param distanceY
		 */
		public void onFling(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			switch (gameState) {
			case CommonValue.GAME_STATE_MENU:
				// gameMenu.onFling(e1, e2, distanceX, distanceY);
				break;
			case CommonValue.GAME_STATE_START:
				break;
			case CommonValue.GAME_STATE_PLAYING:
				gamePlaying.onFling(e1, e2, distanceX, distanceY);
				break;
			case CommonValue.GAME_STATE_WIN:
				break;
			case CommonValue.GAME_STATE_LOST:
				break;
			}
		}

		/**
		 * 返回键监听
		 */
		public void keyBack() {
			switch (gameState) {
			case CommonValue.GAME_STATE_PLAYING:
				break;
			case CommonValue.GAME_STATE_HIGHSCORE:
			case CommonValue.GAME_STATE_ABOUT:
			case CommonValue.GAME_STATE_SETTING:
				gameState = CommonValue.GAME_STATE_MENU;
				break;
			case CommonValue.GAME_STATE_EXIT:
				break;

			}
		}

	}

	public MainThread getThread() {
		return thread;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return true;
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("debug", "surfaceCreated");
		screenW = this.getWidth();
		screenH = this.getHeight();
		// thread.start();
		gameMenu = new GameMenu(screenW, screenH);
		thread.restoreState(outState);
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus) {
		if (!hasWindowFocus)
			thread.pause();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.i("debug", "destory surface");

		thread.saveState(outState);
	}

	byte[] lock = new byte[0];

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			thread.doTouchDown(event.getX(), event.getY());
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			thread.doTouchUp(event.getX(), event.getY());
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			thread.doTouchMove(event.getX(), event.getY());
		}
		synchronized (lock) {//
			try {
				lock.wait(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return gesture.onTouchEvent(event);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		if (Math.abs(e1.getX() - e2.getX()) > 10) {
			thread.onFling(e1, e2, distanceX, distanceY);
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

}
