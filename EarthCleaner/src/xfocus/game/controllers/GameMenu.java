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
 * 游戏菜单
 */
public class GameMenu extends Page {

	// 开始游戏按钮、游戏记录按钮、设置游戏按钮、退出游戏按钮、实时天气和环保tips按钮
	private RectF menuStart, menuRecord, menuSetting, menuExit, menuMaker,
			menuTipsRect;

	private Paint paint, textPaint;
	private Paint menuStartPaint, menuRecordPaint, menuSettingPaint,
			menuMakerPaint, menuExitPaint, menuTipsRectPaint;

	public GameMenu(int screenW, int screenH) {
		super(screenW, screenH);
		paint = new Paint();
		textPaint = new Paint();
		// 开始游戏矩形初始化
		menuStart = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_BEGIN_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_BEGIN_PBOTTOM, screenW, screenH);
		// 游戏记录矩形初始化
		menuRecord = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_RECORD_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_RECORD_PBOTTOM, screenW, screenH);
		// 设置矩形初始化
		menuSetting = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_SETTING_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_SETTING_PBOTTOM, screenW, screenH);
		// 制作信息矩形初始化
		menuMaker = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_ABOUT_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_ABOUT_PBOTTOM, screenW, screenH);
		// 退出游戏矩形初始化
		menuExit = CommonMethod.getRectWithPaddingPercentage(
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_EXIT_PTOP,
				CommonValue.MENU_BTN_PADDING_SIDE,
				CommonValue.MENU_BTN_EXIT_PBOTTOM, screenW, screenH);
		// 实时天气和环保tips矩形初始化
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

	// 绘制游戏菜单
	public void doDraw(Canvas canvas) { // 绘图

		canvas.save();

		canvas.drawColor(Color.BLUE); // 菜单背景颜色为蓝色
		paint.setAntiAlias(true); // 取消锯齿效果
		paint.setColor(Color.BLACK); // 设置画笔的颜色
		paint.setTextSize(50); // 设置画笔的字体尺寸，写字时设置
		canvas.drawText("肺活量VS雾霾怪", 60, 70, paint);

		paint.setAntiAlias(true); // 取消锯齿效果
		paint.setColor(Color.WHITE); // 设置画笔的颜色

		drawMenuButton(canvas, menuStartPaint, menuStart, CommonValue.MENU_BTN_BEGIN);

		drawMenuButton(canvas, menuRecordPaint, menuRecord, CommonValue.MENU_BTN_RECORD);

		drawMenuButton(canvas, menuSettingPaint, menuSetting, CommonValue.MENU_BTN_SETTING);

		drawMenuButton(canvas, menuMakerPaint, menuMaker, CommonValue.MENU_BTN_ABOUT);

		drawMenuButton(canvas, menuExitPaint, menuExit, CommonValue.MENU_BTN_EXIT);


		canvas.drawRoundRect(menuTipsRect, 10, 10, paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(25);
		paint.setTextAlign(Paint.Align.CENTER); // 字体居中显示
		canvas.drawText("实时天气和环保tips",
				(menuTipsRect.left + menuTipsRect.right) / 2,
				menuTipsRect.bottom - 15, paint);

		canvas.restore();

	}

	private void drawMenuButton(Canvas canvas, Paint paint, RectF rect,// 菜单上的按钮绘制
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
	 * 屏幕按下监听
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */

	// 当屏幕按下时会发生以下状态的变化
	public void touchDownEvent(float x, float y) {
		if (CommonMethod.isTouchInRect(x, y, menuStart)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuRecord)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuSetting)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuExit)) {
		} else if (CommonMethod.isTouchInRect(x, y, menuTipsRect)) {
		}

	}

	/**
	 * 屏幕抬起监听
	 * 
	 * @param x
	 *            x坐标
	 * @param y
	 *            y坐标
	 */
	// 当触屏抬起时发生以下状态的变化
	public void touchUpEvent(float x, float y) {
		if (CommonMethod.isTouchInRect(x, y, menuStart)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_START;
			; // 添加开始游戏语句
		} else if (CommonMethod.isTouchInRect(x, y, menuRecord)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_HIGHSCORE;
			; // 添加记录游戏语句
		} else if (CommonMethod.isTouchInRect(x, y, menuSetting)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_SETTING;
			; // 添加设置游戏语句
		} else if (CommonMethod.isTouchInRect(x, y, menuExit)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_EXIT;
			; // 添加退出游戏语句
		} else if (CommonMethod.isTouchInRect(x, y, menuMaker)) {
			MainSurfaceView.gameState = CommonValue.GAME_STATE_ABOUT;
			; // 添加实时天气和环保tips
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
