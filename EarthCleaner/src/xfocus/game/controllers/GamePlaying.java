package xfocus.game.controllers;

import xfocus.game.components.CommonMethod;
import xfocus.game.components.World;
import android.R;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.view.MotionEvent;

/*
 * ��Ϸ���̿�����
 */
public class GamePlaying {
	final static int PLAYING = 0; // ��Ϸ״̬�� ������
	final static int PAUSE = 1; // ��Ϸ״̬�� ��ͣ
	final static int MUSIC_PLAY = 0;
	final static int MUSIC_PAUSE = 1;
	final static int MUSIC_STOP = 2;

	private World world; // ��Ϸ����
	private int screenW, screenH; // ��Ļ�ߴ�
	private int status; // ��Ϸ״̬
	private RectF menuRect, btnResume, btnSetting, btnExit, btnPause; // �˵���ť����
	private Paint pBtnResume, pBtnSetting, pBtnExit; // �˵���ť����
	private int musicStatus = 0;
	private MediaPlayer backGroundMusic;// ��������
	private SoundPool sp;// ��Ϸ��Ч

	public GamePlaying(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		status = PLAYING;
	}

	public void logic() { // �߼�
		switch (status) {
		case PLAYING:
			world.logic();
			break;
		case PAUSE:
			break;
		}
	}

	public void doDraw(Canvas canvas, Paint paint) { // ��ͼ
		world.doDraw(canvas, paint);
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

	private void drawPauseMenu(Canvas canvas, Paint paint) {// ��ͣ�˵�����
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

	private void drawMenuButton(Canvas canvas, Paint paint, RectF rect,// ��ͣ�˵��ϵİ�ť����
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
		backGroundMusic = MediaPlayer.create(context, );
		init_menu();

	}

	private void init_menu() {// ��ͣ�˵���ʼ��
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

	public void touchDownEvent(int x, int y) { // ��Ļ���¼���
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

	public void touchUpEvent(int x, int y) { // ������Ļ����̧�����
		switch (status) {
		case PLAYING:
			if (CommonMethod.isTouchInRect(x, y, btnPause)) {
				status = PAUSE;
			} else {
				world.touchUpEvent();
			}
			break;
		case PAUSE:
			if (CommonMethod.isTouchInRect(x, y, menuRect)) {
				if (CommonMethod.isTouchInRect(x, y, btnResume)) {
					status = PLAYING;
					pBtnResume.setColor(Color.GRAY);
				} else if (CommonMethod.isTouchInRect(x, y, btnExit)) {
				}
			} else {
				status = PLAYING;
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

	public void onFling(MotionEvent e1, MotionEvent e2, float distanceX, // ������Ļ����
			float distanceY) {
		world.screenSlided(e1, e2);
	}

}
