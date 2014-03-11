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
 * ��ҪSurfaceView ������ʾ��Ϸ����
 * 
 */
public class MainSurfaceView extends SurfaceView implements
		SurfaceHolder.Callback, Runnable {
	private SurfaceHolder sfh; // SurfaceViewװ������SurfaceView�ر���
	private Paint paint; // ����
	private Thread th;
	private boolean flag; // �̱߳�ʶ��
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

	// ���캯��
	public MainSurfaceView(Context context, AttributeSet attrs) {
		super(context, attrs);
		sfh = this.getHolder(); // װ������ʼ��
		sfh.addCallback(this);
		paint = new Paint(); // ���ʳ�ʼ��
		setFocusable(true);
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

	private void prePlayInit() {//��Ϸ��ʼǰ��ʼ��
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
