package xfocus.game.components;

import android.graphics.RectF;

public class CommonMethod {

	public static int getDistance(int x1, int y1, int x2, int y2) { // 获取两点距离
		return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	public static boolean isTouchInRect(int x, int y, int left, int top,
			int right, int bottom) {
		if (left <= x && x <= right && top <= y && y <= bottom) {
			return true;
		}
		return false;
	}
	
	public static boolean isTouchInRect(int x, int y, RectF rect) {
		if (rect.left <= x && x <= rect.right && rect.top <= y && y <= rect.bottom) {
			return true;
		}
		return false;
	}
}
