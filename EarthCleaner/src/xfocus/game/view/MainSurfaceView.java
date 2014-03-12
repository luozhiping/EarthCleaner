package xfocus.game.view;

import xfocus.game.controllers.GameMenu;
import xfocus.game.controllers.GamePlaying;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
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
		SurfaceHolder.Callback, Runnable, OnGestureListener, OnTouchListener {
	final static int GAME_MENU = 0;
	final static int GAME_PLAYING = 1;
	final static int GAME_WIN = 2;
	final static int GAME_LOST = 3;
	final static int GAME_START = 4;

	private SurfaceHolder sfh; // SurfaceViewװ������SurfaceView�ر���
	private Paint paint; // ����
	private Thread th; // ��Ϸ���߳�
	private boolean flag; // �̱߳�ʶ��
	private Canvas canvas;
	private int screenW, screenH; // ��Ļ�ߴ�
	private GamePlaying gamePlaying;
	private GameMenu gameMenu;
	private GestureDetector gesture;// ���Ƽ���
	private int status = GAME_START; // ��Ϸ״̬��

	// ���캯��
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sfh = this.getHolder(); // װ������ʼ��
		sfh.addCallback(this);
		paint = new Paint(); // ���ʳ�ʼ��
		setFocusable(true);
		gesture = new GestureDetector(this);
		this.setOnTouchListener(this);
		Log.i("debug", "surfaceView created");
	}

	@Override
	public void run() { // ��Ϸ���߳�
		while (flag) {
			long start = System.currentTimeMillis();
			logic();
			doDraw();
			long end = System.currentTimeMillis();
			try {
				if (end - start < 100) { // ÿ֡100ms
					Thread.sleep(100 - (end - start));
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void doDraw() {// ֡��ͼ
		try {
			canvas = sfh.lockCanvas();
			if (canvas != null) {
				canvas.drawRGB(255, 255, 255); // ˢ��

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

	private void logic() {// ֡�߼�
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

	private void prePlayInit() {// ��Ϸ��ʼǰ��ʼ��
		gamePlaying = new GamePlaying(screenW, screenH);
		gamePlaying.init_world(getContext());
		status = GAME_PLAYING;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

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

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (status) {
		case GAME_MENU:
			break;
		case GAME_PLAYING:
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				gamePlaying.touchDownEvent((int) event.getX(),
						(int) event.getY());
			} else if (event.getAction() == MotionEvent.ACTION_UP) {
				gamePlaying
						.touchUpEvent((int) event.getX(), (int) event.getY());
			} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
				gamePlaying.touchMove((int) event.getX(), (int) event.getY());
			}
			break;
		case GAME_WIN:
			break;
		case GAME_LOST:
			break;
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
		switch (status) {
		case GAME_PLAYING:
			gamePlaying.onFling(e1, e2, distanceX, distanceY);
			break;
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
