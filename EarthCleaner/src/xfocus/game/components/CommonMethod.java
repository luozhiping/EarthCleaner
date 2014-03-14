package xfocus.game.components;

import android.graphics.RectF;

public class CommonMethod {

	/**
	 * 获取两点间距离
	 * @param x1 第一点x坐标
	 * @param y1 第一点y坐标
	 * @param x2 第二点x坐标
	 * @param y2 第二点y坐标
	 * @return 返回距离
	 */
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * 判断是否触摸到了矩形
	 * @param x 触摸点的x坐标
	 * @param y 触摸点的y坐标
	 * @param left 矩形左上角x坐标
	 * @param top 矩形左上角y坐标
	 * @param right 矩形右下角x坐标
	 * @param bottom 矩形右下角y坐标
	 * @return
	 */
	public static boolean isTouchInRect(float x, float y, float left, float top,
			float right, float bottom) {
		if (left <= x && x <= right && top <= y && y <= bottom) {
			return true;
		}
		return false;
	}
	
	/**
	 * 判断是否触摸到了矩形
	 * @param x 触摸点的x坐标
	 * @param y 触摸点的y坐标
	 * @param rect 矩形实例
	 * @return
	 */
	public static boolean isTouchInRect(float x, float y, RectF rect) {
		if (rect.left <= x && x <= rect.right && rect.top <= y && y <= rect.bottom) {
			return true;
		}
		return false;
	}
}
