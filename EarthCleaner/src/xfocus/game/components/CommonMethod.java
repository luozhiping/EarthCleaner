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
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
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
	public static boolean isTouchInRect(float x, float y, float left, float top,
			float right, float bottom) {
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
	public static boolean isTouchInRect(float x, float y, RectF rect) {
		if (rect.left <= x && x <= rect.right && rect.top <= y && y <= rect.bottom) {
			return true;
		}
		return false;
	}
}
