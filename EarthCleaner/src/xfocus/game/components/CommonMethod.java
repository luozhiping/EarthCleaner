package xfocus.game.components;

import android.graphics.RectF;
import android.util.Log;

public class CommonMethod {

	/**
	 * 获取两点间距离
	 * 
	 * @param x1
	 *            第一点x坐标
	 * @param y1
	 *            第一点y坐标
	 * @param x2
	 *            第二点x坐标
	 * @param y2
	 *            第二点y坐标
	 * @return 返回距离
	 */
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * 判断是否触摸到了矩形
	 * 
	 * @param x
	 *            触摸点的x坐标
	 * @param y
	 *            触摸点的y坐标
	 * @param left
	 *            矩形左上角x坐标
	 * @param top
	 *            矩形左上角y坐标
	 * @param right
	 *            矩形右下角x坐标
	 * @param bottom
	 *            矩形右下角y坐标
	 * @return
	 */
	public static boolean isTouchInRect(float x, float y, float left,
			float top, float right, float bottom) {
		if (left <= x && x <= right && top <= y && y <= bottom) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否触摸到了矩形
	 * 
	 * @param x
	 *            触摸点的x坐标
	 * @param y
	 *            触摸点的y坐标
	 * @param rect
	 *            矩形实例
	 * @return
	 */
	public static boolean isTouchInRect(float x, float y, RectF rect) {
		if (rect.left <= x && x <= rect.right && rect.top <= y
				&& y <= rect.bottom) {
			return true;
		}
		return false;
	}

	/**
	 * 利用与窗口的距离创建rect实例
	 * 
	 * @param paddingLeft
	 *            矩形左边与窗口左边的距离
	 * @param paddingTop
	 *            矩形上边与窗口上边的距离
	 * @param paddingRight
	 *            矩形右边与窗口右边的距离
	 * @param paddingBottom
	 *            矩形下边与窗口下边的距离
	 * @param screenW
	 *            窗口宽
	 * @param screenH
	 *            窗口高
	 * @return
	 */
	public static RectF getRectWithPadding(float paddingLeft, float paddingTop,
			float paddingRight, float paddingBottom, int screenW, int screenH) {
		RectF rect = new RectF(paddingLeft, paddingTop, screenW - paddingRight,
				screenH - paddingBottom);
		return rect;
	}

	/**
	 * 利用占窗口长宽的比创建rect实例
	 * 
	 * @param percentPL
	 *            矩形的左边与窗口左边的距离占窗口宽的百分比
	 * @param percentPT
	 *            矩形的上边与窗口上边的距离占窗口高的百分比
	 * @param percentPR
	 *            矩形的右边与窗口右边的距离窗口宽的百分比
	 * @param percentPB
	 *            矩形的下边与窗口下边的距离占窗口高的百分比
	 * @param screenW
	 *            窗口宽
	 * @param screenH
	 *            窗口高
	 * @return
	 */
	public static RectF getRectWithPaddingPercentage(float percentPLeft,
			float percentPTop, float percentPRight, float percentPBottom,
			int screenW, int screenH) {
		RectF rect = new RectF(screenW * percentPLeft, screenH * percentPTop,
				screenW * (1 - percentPRight), screenH * (1 - percentPBottom));
		return rect;
	}
}
