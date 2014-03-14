package xfocus.game.view;

import xfocus.game.controllers.GameMenu;
import xfocus.game.controllers.GamePlaying;
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
 * ��ҪSurfaceView ������ʾ��Ϸ����
 * 
 */
public class MainSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, OnGestureListener, OnTouchListener {

	private MainThread thread;
	private GestureDetector gesture;// ���Ƽ���
	private int screenW, screenH; // ��Ļ�ߴ�
	private Context mContext;
	private Bundle outState;

	/**
	 * ���캯��
	 * 
	 * @param context
	 * @param attrs
	 */
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		SurfaceHolder holder = getHolder();
		holder.addCallback(this);
		mContext = context;
		Log.i("my", "destruct");
		thread = new MainThread(holder, mContext);
		thread.start();
		setFocusable(true);
		gesture = new GestureDetector(this);
		this.setOnTouchListener(this);

		Log.i("debug", "surfaceView created");
	}

	/**
	 * ���캯��
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
	 * ��Ϸ���߳�
	 * 
	 * @author lzp
	 * 
	 */
	class MainThread extends Thread {
		public final static int GAME_MENU = 0;
		public final static int GAME_PLAYING = 1;
		public final static int GAME_WIN = 2;
		public final static int GAME_LOST = 3;
		public final static int GAME_START = 4;

		private SurfaceHolder mSurfaceHolder; // SurfaceViewװ������SurfaceView�ر���
		private Canvas canvas;
		private GamePlaying gamePlaying;
		private GameMenu gameMenu;
		private int gameState = GAME_START; // ��Ϸ״̬��
		private boolean flag = false; // �̱߳�ʶ��

		/**
		 * ���캯��
		 * 
		 * @param surfaceHolder
		 * @param context
		 */
		public MainThread(SurfaceHolder surfaceHolder, Context context) {
			mSurfaceHolder = surfaceHolder;
			mContext = context;
			Log.i("debug", "thread");
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
					if (end - start < 25) { // �趨֡����
						Thread.sleep(25 - (end - start));
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void doDraw() {// ֡��ͼ
			try {
				canvas = mSurfaceHolder.lockCanvas();
				if (canvas != null) {
					canvas.drawRGB(255, 255, 255); // ˢ��
					switch (gameState) {
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

		private void logic() {// ֡�߼�
			switch (gameState) {
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

		/**
		 * ��Ϸ��ʼǰ�ĳ�ʼ��
		 */
		private void prePlayInit() {
			gamePlaying = new GamePlaying(screenW, screenH);
			gamePlaying.init_world(getContext());
			gameState = GAME_PLAYING;
		}

		/**
		 * ������Ϸ״̬
		 * 
		 * @param state
		 */
		public void setState(int state) {
			synchronized (mSurfaceHolder) {
				switch (gameState) {
				case GAME_PLAYING:
					// gamePlaying.setState();
					break;
				}
			}
		}

		/**
		 * ������Ϸ״̬
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
		 * ������Ϸ״̬
		 * 
		 * @param map
		 * @return
		 */
		public Bundle saveState(Bundle map) {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "saveState");

				switch (gameState) {
				case GAME_PLAYING:
					gamePlaying.saveState(map);
					break;
				}
			}
			return map;
		}

		/**
		 * ��Ϸ��ͣ
		 */
		public void pause() {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "pause");
				if (gameState == GAME_PLAYING) {
					// flag = false;
					gamePlaying.pause();
				}
			}
		}

		/**
		 * ��Ϸ����
		 */
		public void unPause() {
			synchronized (mSurfaceHolder) {
				Log.i("debug", "un_pause");
				if (gameState == GAME_PLAYING)
					gamePlaying.unPause();
			}
		}

		/**
		 * �������¼���
		 * 
		 * @param x
		 *            x����
		 * @param y
		 *            y����
		 */
		public void doTouchDown(int x, int y) {
			switch (gameState) {
			case GAME_MENU:
				break;
			case GAME_START:
				break;
			case GAME_PLAYING:
				gamePlaying.touchDownEvent(x, y);
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
			}
		}

		/**
		 * ����̧�����
		 * 
		 * @param x
		 *            x����
		 * @param y
		 *            y����
		 */
		public void doTouchUp(int x, int y) {
			switch (gameState) {
			case GAME_MENU:
				break;
			case GAME_START:
				break;
			case GAME_PLAYING:
				gamePlaying.touchUpEvent(x, y);
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
			}
		}

		/**
		 * �����ƶ�����
		 * 
		 * @param x
		 *            ��ǰx����
		 * @param y
		 *            ��ǰy����
		 */
		public void doTouchMove(int x, int y) {
			switch (gameState) {
			case GAME_MENU:
				break;
			case GAME_START:
				break;
			case GAME_PLAYING:
				gamePlaying.touchMove(x, y);
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
			}
		}

		/**
		 * ��Ļ��������
		 * 
		 * @param e1
		 * @param e2
		 * @param distanceX
		 * @param distanceY
		 */
		public void onFling(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			switch (gameState) {
			case GAME_MENU:
				break;
			case GAME_START:
				break;
			case GAME_PLAYING:
				gamePlaying.onFling(e1, e2, distanceX, distanceY);
				break;
			case GAME_WIN:
				break;
			case GAME_LOST:
				break;
			}
		}

		/**
		 * ���ؼ�����
		 */
		public void keyBack() {
			switch (gameState) {
			case GAME_PLAYING:
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
