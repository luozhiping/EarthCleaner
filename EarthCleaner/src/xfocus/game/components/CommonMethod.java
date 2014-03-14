package xfocus.game.components;

import android.graphics.RectF;

public class CommonMethod {

	/**
	 * ��ȡ��������
	 * @param x1 ��һ��x����
	 * @param y1 ��һ��y����
	 * @param x2 �ڶ���x����
	 * @param y2 �ڶ���y����
	 * @return ���ؾ���
	 */
	public static int getDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * �ж��Ƿ������˾���
	 * @param x �������x����
	 * @param y �������y����
	 * @param left �������Ͻ�x����
	 * @param top �������Ͻ�y����
	 * @param right �������½�x����
	 * @param bottom �������½�y����
	 * @return
	 */
	public static boolean isTouchInRect(int x, int y, int left, int top,
			int right, int bottom) {
		if (left <= x && x <= right && top <= y && y <= bottom) {
			return true;
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ������˾���
	 * @param x �������x����
	 * @param y �������y����
	 * @param rect ����ʵ��
	 * @return
	 */
	public static boolean isTouchInRect(int x, int y, RectF rect) {
		if (rect.left <= x && x <= rect.right && rect.top <= y && y <= rect.bottom) {
			return true;
		}
		return false;
	}
}
