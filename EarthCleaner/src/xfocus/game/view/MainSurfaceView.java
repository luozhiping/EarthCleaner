package xfocus.game.view;

import xfocus.game.controllers.GameMenu;
import xfocus.game.controllers.GamePlaying;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
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

	// 构造函数
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);

		thread = new MainThread(holder, context);
		setFocusable(true);
		gesture = new GestureDetector(this);
		this.setOnTouchListener(this);

		Log.i("debug", "surfaceView created");
	}

	class MainThread extends Thread {
		final static int GAME_MENU = 0;
		final static int GAME_PLAYING = 1;
		final static int GAME_WIN = 2;
		final static int GAME_LOST = 3;
		final static int GAME_START = 4;

		private SurfaceHolder mSurfaceHolder; // SurfaceView装载器（SurfaceView必备）
		private Thread th; // 游戏主线程
		private Canvas canvas;
		private GamePlaying gamePlaying;
		private GameMenu gameMenu;
		private int status = GAME_START; // 游戏状态码
		private boolean flag; // 线程标识符
		private Context mContext;
//		private int screenW, screenH;
		public MainThread(SurfaceHolder surfaceHolder, Context context) {
			mSurfaceHolder = surfaceHolder;
			mContext = context;
//			this.screenH = screenH;
//			this.screenW = screenW;
			Resources res = context.getResources();

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
				canvas = mSurfaceHolder.lockCanvas();
				if (canvas != null) {
					canvas.drawRGB(255, 255, 255); // 刷屏
					switch (status) {
					case GAME_MENU:
						gameMenu.doDraw(canvas);
						break;
					case GAME_START:
						break;
					case GAME_PLAYING:
						gamePlaying.doDraw(canvas);
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
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}

		private void logic() {// 帧逻辑
			switch (status) {
			case GAME_MENU:
				gameMenu.logic();
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

		private void prePlayInit() {// 游戏开始前初始化
			Log.i("debug", "scw:"+screenW);
			
			gamePlaying = new GamePlaying(screenW, screenH);
			gamePlaying.init_world(getContext());
			status = GAME_PLAYING;
		}

		public void setRunning(boolean b) {
			flag = b;
		}

		public void setState(int mode) {
            synchronized (mSurfaceHolder) {
            	switch(status) {
            	case GAME_PLAYING:
            		//gamePlaying.setState();
            		break;
            	}
            }
        }
		public synchronized void restoreState(Bundle savedState) {
            synchronized (mSurfaceHolder) {
            	
            }
		}
		
		 public Bundle saveState(Bundle map) {
	            synchronized (mSurfaceHolder) {
	            }
	            return map;
	        }
		
		public void pause() {
			synchronized (mSurfaceHolder) {
				// if (mMode == STATE_RUNNING) setState(STATE_PAUSE);
			}
		}

		public void doTouchDown(int x, int y) {
			gamePlaying.touchDownEvent(x, y);
		}

		public void doTouchUp(int x, int y) {
			gamePlaying.touchUpEvent(x, y);
		}

		public void doTouchMove(int x, int y) {
			gamePlaying.touchMove(x, y);
		}

		public void onFling(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			gamePlaying.onFling(e1, e2, distanceX, distanceY);
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
		screenW = this.getWidth();
		screenH = this.getHeight();
		thread.setRunning(true);
		thread.start();
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
		boolean retry = true;
		thread.setRunning(false);
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			thread.doTouchDown((int) event.getX(), (int) event.getY());

		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			thread.doTouchUp((int) event.getX(), (int) event.getY());

		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			thread.doTouchMove((int) event.getX(), (int) event.getY());

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
			thread.onFling(e1, e2, distanceX, distanceY);
			
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
