package xfocus.game.controllers;

import xfocus.game.R;
import xfocus.game.components.CommonMethod;
import xfocus.game.components.CommonValue;
import xfocus.game.components.World;
import xfocus.game.view.MainSurfaceView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/*
 * ��Ϸ���̿�����
 */
public class GamePlaying extends Page {
	public final static int GAME_FREE_MODE = 0;
	public final static int GAME_MISSION_MODE = 1;

	private World world; // ��Ϸ����
	private int gameState; // ��Ϸ״̬
	private RectF menuRect, btnResume, btnSetting, btnExit, btnPause, lostRect; // �˵���ť����
	private Paint pBtnResume, pBtnSetting, pBtnExit; // �˵���ť����
	private int musicStatus = 0;
	private MediaPlayer backGroundMusic;// ��������
	private SoundPool sp;// ��Ϸ��Ч
	private Paint paint;
	private int soundClick;
	private int gameMode;
	private int mission;
	private Context mContext;

	/**
	 * ����ģʽ���캯��
	 * 
	 * @param screenW
	 *            ��Ļ��
	 * @param screenH
	 *            ��Ļ��
	 */

	public GamePlaying(int screenW, int screenH) {
		super(screenW, screenH);
		gameState = CommonValue.GAME_STATE_PLAYING;
		paint = new Paint();
		this.gameMode = GAME_FREE_MODE;
	}

	/**
	 * ����ģʽ���캯��
	 * 
	 * @param screenW
	 * @param screenH
	 * @param mission
	 */
//	public GamePlaying(int screenW, int screenH, int mission) {
//		this.screenW = screenW;
//		this.screenH = screenH;
//		gameState = CommonValue.GAME_STATE_PLAYING;
//		paint = new Paint();
//		this.mission = mission;
//		this.gameMode = GAME_MISSION_MODE;
//	}

	public void logic() { // �߼�
		switch (gameState) {
		case CommonValue.GAME_STATE_PLAYING:
			world.playingLogic();
			if (world.getPlayer().getHp() <= 0) {
				gameState = CommonValue.GAME_STATE_LOST;
			}
			break;
		case CommonValue.GAME_STATE_PAUSE:
			break;
		case CommonValue.GAME_STATE_LOST:
			break;
		}
	}

	public void doDraw(Canvas canvas) { // ��ͼ
		drawWorld(canvas);
		switch (gameState) {
		case CommonValue.GAME_STATE_PLAYING:
			break;
		case CommonValue.GAME_STATE_PAUSE:
			drawPauseMenu(canvas, paint);
			break;
		case CommonValue.GAME_STATE_LOST:
			drawLost(canvas);
			break;
		}
	}

	private void drawLost(Canvas canvas) {
		canvas.save();
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(60);
		canvas.drawRoundRect(lostRect, 20, 20, paint);
		paint.setColor(Color.BLACK);
		paint.setTextScaleX(1);
		canvas.drawText("LOST!", screenW / 2 - 100, screenH / 2 - 300, paint);
		canvas.drawText("socre:" + world.getPlayer().getScore(),
				screenW / 2 - 100, screenH / 2 - 200, paint);
		paint.setColor(Color.RED);
		canvas.drawCircle(screenW / 2, screenH / 2, 30, paint);
		canvas.restore();
	}

	public void drawWorld(Canvas canvas) {
		world.doDraw(canvas);
		canvas.save();
		paint.setColor(Color.BLACK);
		canvas.drawRect(10, 10, 20, 40, paint);
		canvas.drawRect(25, 10, 35, 40, paint);
		canvas.restore();
	}

	/**
	 * ��ͣʱ��ͣ�˵��Ļ���
	 * 
	 * @param canvas
	 *            ����
	 * @param paint
	 *            ����
	 */
	private void drawPauseMenu(Canvas canvas, Paint paint) {
		canvas.save();

		paint.setColor(Color.BLUE);
		paint.setAlpha(230);
		canvas.drawRoundRect(menuRect, 10, 10, paint);

		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(3);
		paint.setTextSize(30);
		canvas.drawText(CommonValue.PAUSE_MENU_TITLE, menuRect.left + 40,
				menuRect.top + 45, paint);
		canvas.drawLine(menuRect.left, menuRect.top + 70, menuRect.right,
				menuRect.top + 70, paint);

		drawMenuButton(canvas, pBtnResume, btnResume,
				CommonValue.PAUSE_MENU_BTN_RESUME);
		drawMenuButton(canvas, pBtnSetting, btnSetting,
				CommonValue.PAUSE_MENU_BTN_SETTING);
		drawMenuButton(canvas, pBtnExit, btnExit,
				CommonValue.PAUSE_MENU_BTN_EXIT);

		paint.setAlpha(255);
		canvas.restore();
	}

	/**
	 * ��ͣ�˵��ϵİ�ť����
	 * 
	 * @param canvas
	 *            ����
	 * @param paint
	 *            ����
	 * @param rect
	 *            ����ʵ��
	 * @param btnText
	 *            ��ť����
	 */
	private void drawMenuButton(Canvas canvas, Paint paint, RectF rect,
			String btnText) {
		canvas.save();
		canvas.drawRoundRect(rect, 10, 10, paint);
		Paint textPaint = new Paint();
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(35);
		canvas.drawText(btnText, rect.left + 35, rect.bottom - 12, textPaint);
		canvas.restore();
	}

	/**
	 * ��ʼ��Ϸʱ��ʼ����Ϸ����
	 * 
	 * @param context
	 */
	public void init_world(Context context) {
		world = new World(screenW, screenH);
		init_rect(); // ��ʼ���˵�
		init_music(context); // ��ʼ��������Ч
		Log.i("debug", "init world");
	}

	public void init_world(Context context, int mission) {
		world = new World(screenW, screenH, mission);
		init_rect(); // ��ʼ���˵�
		init_music(context); // ��ʼ��������Ч
	}

	/**
	 * ��ʼ��������Ч
	 * 
	 * @param context
	 */
	private void init_music(Context context) {
		backGroundMusic = MediaPlayer.create(context, R.raw.bgm);
		backGroundMusic.setLooping(true);
		backGroundMusic.start();
		sp = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
		// soundClick = sp.load(context, R.raw.)
	}

	/**
	 * ��ʼ���������
	 */
	private void init_rect() {
		menuRect = new RectF(screenW / 2 - 100, screenH / 2 - 300,
				screenW / 2 + 100, screenH / 2);
		btnResume = new RectF(menuRect.left + 30, menuRect.top + 80,
				menuRect.right - 30, menuRect.top + 130);
		btnSetting = new RectF(btnResume.left, btnResume.bottom + 20,
				btnResume.right, btnResume.bottom + 70);
		btnExit = new RectF(btnSetting.left, btnSetting.bottom + 20,
				btnSetting.right, btnSetting.bottom + 70);
		lostRect = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.LOST_PADDING_LEFT_PER,
				CommonValue.LOST_PADDING_TOP_PER,
				CommonValue.LOST_PADDING_LEFT_PER,
				CommonValue.LOST_PADDING_BOTTOM_PER, screenW, screenH);
		btnPause = new RectF(0, 0, 45, 50);
		Paint btnPaint = new Paint();
		btnPaint.setColor(Color.GRAY);
		btnPaint.setAlpha(255);
		pBtnResume = new Paint(btnPaint);
		pBtnExit = new Paint(btnPaint);
		pBtnSetting = new Paint(btnPaint);
	}

	/**
	 * ��Ļ���¼���
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public void touchDownEvent(float x, float y) {
		switch (gameState) {
		case CommonValue.GAME_STATE_PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				// pause();
			} else {
				world.touchDownEvent(x, y);
			}
			break;
		case CommonValue.GAME_STATE_PAUSE:
			break;
		}
	}

	/**
	 * ��Ļ̧�����
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */
	public void touchUpEvent(float x, float y) {
		switch (gameState) {
		case CommonValue.GAME_STATE_PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				pause();
			} else {
				world.touchUpEvent();
			}
			break;
		case CommonValue.GAME_STATE_PAUSE:
			if (CommonMethod.isTouchInRect(x, y, menuRect)) {
				if (CommonMethod.isTouchInRect(x, y, btnResume)) {
					pBtnResume.setColor(Color.GRAY);
					unPause();
				} else if (CommonMethod.isTouchInRect(x, y, btnExit)) {
					MainSurfaceView.gameState = CommonValue.GAME_STATE_MENU;
				}
			} else {
				unPause();
			}
			break;
		case CommonValue.GAME_STATE_LOST:
			if (CommonMethod.getDistance(x, y, screenW / 2, screenH / 2) <= 30) {
				MainSurfaceView.gameState = CommonValue.GAME_STATE_START;
			}
			break;
		}
	}

	/**
	 * ��������Ļ���ƶ�
	 * 
	 * @param x
	 *            ��ǰx����
	 * @param y
	 *            ��ǰy����
	 */
	public void touchMove(float x, float y) {
		switch (gameState) {
		case CommonValue.GAME_STATE_PLAYING:
			break;
		case CommonValue.GAME_STATE_PAUSE:
			if (CommonMethod.isTouchInRect(x, y, btnResume)) {
				pBtnResume.setColor(Color.DKGRAY);
			} else {
				pBtnResume.setColor(Color.GRAY);
			}
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
		world.screenSlided(e1, e2);
	}

	public void setStatus(int status) {
		this.gameState = status;
	}

	public int getStatus() {
		return gameState;
	}

	/**
	 * ��Ϸ��ͣ
	 */
	public void pause() {
		if (gameState == CommonValue.GAME_STATE_PLAYING) {
			backGroundMusic.pause();
			world.pause();
			gameState = CommonValue.GAME_STATE_PAUSE;
		}
	}

	/**
	 * ������Ϸ״̬
	 * 
	 * @param map
	 */
	public void saveState(Bundle map) {
		if (map != null) {
			// map.putInt("status", gameState);

		}
	}

	/**
	 * ��Ϸ����
	 */
	public void unPause() {
		if (gameState == CommonValue.GAME_STATE_PAUSE) {
			backGroundMusic.start();
			gameState = CommonValue.GAME_STATE_PLAYING;
			world.unPause();
		}
	}

}
