package xfocus.game.controllers;

import xfocus.game.components.CommonMethod;
import xfocus.game.components.CommonValue;
import xfocus.game.view.MainSurfaceView;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;

/*
 * ��Ϸ�˵�
 */
public class GameMenu extends Page {

	// ��ʼ��Ϸ��ť����Ϸ��¼��ť��������Ϸ��ť���˳���Ϸ��ť��ʵʱ�����ͻ���tips��ť
	private RectF menuStart, menuRecord, menuSetting, menuExit, menuMaker,
			menuTipsRect;

	private Paint paint, textPaint;
	private Paint menuStartPaint, menuRecordPaint, menuSettingPaint,
			menuMakerPaint, menuExitPaint, menuTipsRectPaint;

	public GameMenu(int screenW, int screenH) {
		super(screenW, screenH);
		paint = new Paint();
		textPaint = new Paint();
		// ��ʼ��Ϸ���γ�ʼ��
		menuStart = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_BEGIN_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_BEGIN_PBOTTOM, screenW, screenH);
		// ��Ϸ��¼���γ�ʼ��
		menuRecord = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_RECORD_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_RECORD_PBOTTOM, screenW, screenH);
		// ���þ��γ�ʼ��
		menuSetting = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_SETTING_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_SETTING_PBOTTOM, screenW, screenH);
		// ������Ϣ���γ�ʼ��
		menuMaker = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_ABOUT_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_ABOUT_PBOTTOM, screenW, screenH);
		// �˳���Ϸ���γ�ʼ��
		menuExit = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_EXIT_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_EXIT_PBOTTOM, screenW, screenH);
		// ʵʱ�����ͻ���tips���γ�ʼ��
		menuTipsRect = new RectF(0, screenH - 50, screenW, screenH);

		menuStartPaint = new Paint();
		menuStartPaint.setColor(Color.WHITE);

		menuRecordPaint = new Paint();
		menuRecordPaint.setColor(Color.WHITE);

		menuSettingPaint = new Paint();
		menuSettingPaint.setColor(Color.WHITE);

		menuMakerPaint = new Paint();
		menuMakerPaint.setColor(Color.WHITE);

		menuExitPaint = new Paint();
		menuExitPaint.setColor(Color.WHITE);

		menuTipsRectPaint = new Paint();
		menuTipsRectPaint.setColor(Color.WHITE);

	}

	// ������Ϸ�˵�
	public void doDraw(Canvas canvas) { // ��ͼ

		canvas.save();

		canvas.drawColor(Color.BLUE); // �˵�������ɫΪ��ɫ
		paint.setAntiAlias(true); // ȡ�����Ч��
		paint.setColor(Color.BLACK); // ���û��ʵ���ɫ
		paint.setTextSize(50); // ���û��ʵ�����ߴ磬д��ʱ����
		canvas.drawText("�λ���VS������", 60, 70, paint);

		paint.setAntiAlias(true); // ȡ�����Ч��
		paint.setColor(Color.WHITE); // ���û��ʵ���ɫ

		drawMenuButton(canvas, menuStartPaint, menuStart, CommonValue.MENU_BTN_BEGIN);

		drawMenuButton(canvas, menuRecordPaint, menuRecord, CommonValue.MENU_BTN_RECORD);

		drawMenuButton(canvas, menuSettingPaint, menuSetting, CommonValue.MENU_BTN_SETTING);

		drawMenuButton(canvas, menuMakerPaint, menuMaker, CommonValue.MENU_BTN_ABOUT);

		drawMenuButton(canvas, menuExitPaint, menuExit, CommonValue.MENU_BTN_EXIT);


		canvas.drawRoundRect(menuTipsRect, 10, 10, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		paint.setTextAlign(Paint.Align.CENTER); // ���������ʾ
		canvas.drawText("ʵʱ�����ͻ���tips",
				(menuTipsRect.left + menuTipsRect.right) / 2,
				menuTipsRect.bottom - 15, paint);

		canvas.restore();

	}

	private void drawMenuButton(Canvas canvas, Paint paint, RectF rect,// �˵��ϵİ�ť����
			String btnText) {

		canvas.save();
		canvas.drawRoundRect(rect, 10, 10, paint);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(35);
		textPaint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText(btnText, (rect.left + rect.right) / 2,
				rect.bottom - 30, textPaint);
		canvas.restore();
	}

	/**
	 * ��Ļ���¼���
	 * 
	 * @param x
	 *            x����
	 * @param y
	 *            y����
	 */

	// ����Ļ����ʱ�ᷢ������״̬�ı仯
	public void touchDownEvent(float x, float y) {
		if (CommonMethod.isTouchInRect(x, y, menuStart)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuRecord)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuSetting)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuExit)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuTipsRect)) {
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
	// ������̧��ʱ��������״̬�ı仯
	public void touchUpEvent(float x, float y) {
		if (CommonMethod.isTouchInRect(x, y, menuStart)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_START;
			; // ��ӿ�ʼ��Ϸ���
		} else if (CommonMethod.isTouchInRect(x, y, menuRecord)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_HIGHSCORE;
			; // ��Ӽ�¼��Ϸ���
		} else if (CommonMethod.isTouchInRect(x, y, menuSetting)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_SETTING;
			; // ���������Ϸ���
		} else if (CommonMethod.isTouchInRect(x, y, menuExit)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_EXIT;
			; // ����˳���Ϸ���
		} else if (CommonMethod.isTouchInRect(x, y, menuMaker)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_ABOUT;
			; // ���ʵʱ�����ͻ���tips
		}

	}

	public void logic() {
		// TODO Auto-generated method stub

	}

	public void touchMove(float x, float y) {
		if (CommonMethod.isTouchInRect(x, y, menuStart)) {
			menuStartPaint.setColor(Color.GRAY);
		} else {
			menuStartPaint.setColor(Color.WHITE);
		}
		if (CommonMethod.isTouchInRect(x, y, menuRecord)) {
			menuRecordPaint.setColor(Color.GRAY);
		} else {
			menuRecordPaint.setColor(Color.WHITE);
		}
		if (CommonMethod.isTouchInRect(x, y, menuSetting)) {
			menuSettingPaint.setColor(Color.GRAY);
		} else {
			menuSettingPaint.setColor(Color.WHITE);
		}
		if (CommonMethod.isTouchInRect(x, y, menuMaker)) {
			menuMakerPaint.setColor(Color.GRAY);
		} else {
			menuMakerPaint.setColor(Color.WHITE);
		}
		if (CommonMethod.isTouchInRect(x, y, menuExit)) {
			menuExitPaint.setColor(Color.GRAY);
		} else {
			menuExitPaint.setColor(Color.WHITE);
		}
		if (CommonMethod.isTouchInRect(x, y, menuTipsRect)) {
			menuTipsRectPaint.setColor(Color.GRAY);
		} else {
			menuTipsRectPaint.setColor(Color.WHITE);
		}
	}

}
