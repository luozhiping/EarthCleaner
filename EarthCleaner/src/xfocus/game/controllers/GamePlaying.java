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
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;

/*
 * ��Ϸ���̿�����
 */
public class GamePlaying {
	public final static int PLAYING = 0; // ��Ϸ״̬�� ������
	public final static int PAUSE = 1; // ��Ϸ״̬�� ��ͣ
	final static int MUSIC_PLAY = 0;
	final static int MUSIC_PAUSE = 1;
	final static int MUSIC_STOP = 2;

	private World world; // ��Ϸ����
	private int screenW, screenH; // ��Ļ�ߴ�
	private int gameState; // ��Ϸ״̬
	private RectF menuRect, btnResume, btnSetting, btnExit, btnPause; // �˵���ť����
	private Paint pBtnResume, pBtnSetting, pBtnExit; // �˵���ť����
	private int musicStatus = 0;
	private MediaPlayer backGroundMusic;// ��������
	private SoundPool sp;// ��Ϸ��Ч
	private Paint paint;
	private int soundClick;

	/**
	 * ���캯��
	 * 
	 * @param screenW
	 *            ��Ļ��
	 * @param screenH
	 *            ��Ļ��
	 */
	public GamePlaying(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		gameState = PLAYING;
		paint = new Paint();
	}

	public void logic() { // �߼�
		switch (gameState) {
		case PLAYING:
			world.playingLogic();
			break;
		case PAUSE:
			break;
		}
	}

	public void doDraw(Canvas canvas) { // ��ͼ
		world.doDraw(canvas);
		canvas.save();
		paint.setColor(Color.BLACK);
		canvas.drawRect(10, 10, 20, 40, paint);
		canvas.drawRect(25, 10, 35, 40, paint);
		canvas.restore();
		switch (gameState) {
		case PLAYING:
			break;
		case PAUSE:
			drawPauseMenu(canvas, paint);
			break;
		}
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
		canvas.drawText("��Ϸ�˵�", menuRect.left + 40, menuRect.top + 45, paint);
		canvas.drawLine(menuRect.left, menuRect.top + 70, menuRect.right,
				menuRect.top + 70, paint);

		drawMenuButton(canvas, pBtnResume, btnResume, "����");
		drawMenuButton(canvas, pBtnSetting, btnSetting, "����");
		drawMenuButton(canvas, pBtnExit, btnExit, "�˳�");

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
		init_menu(); // ��ʼ���˵�
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
	 * ��ʼ����ͣ�˵�
	 */
	private void init_menu() {
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
		case PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				pause();
			} else {
				world.touchUpEvent();
			}
			break;
		case PAUSE:
			if (CommonMethod.isTouchInRect(x, y, menuRect)) {
				if (CommonMethod.isTouchInRect(x, y, btnResume)) {
					pBtnResume.setColor(Color.GRAY);
					pause();
				} else if (CommonMethod.isTouchInRect(x, y, btnExit)) {
				}
			} else {
				unPause();
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
		if (gameState == PLAYING) {
			backGroundMusic.pause();
			world.pause();
			gameState = PAUSE;
		}
	}

	/**
	 * ������Ϸ״̬
	 * 
	 * @param map
	 */
	public void saveState(Bundle map) {
		if (map != null) {
			map.putInt("status", gameState);

		}
	}

	/**
	 * ��Ϸ����
	 */
	public void unPause() {
		if (gameState == PAUSE) {
			backGroundMusic.start();
			gameState = PLAYING;
			world.unPause();
		}
	}

}
