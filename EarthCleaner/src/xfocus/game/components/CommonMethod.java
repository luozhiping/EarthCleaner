package xfocus.game.components;

import android.graphics.RectF;
import android.util.Log;

public class CommonMethod {

	/**
	 * ��ȡ��������
	 * 
	 * @param x1
	 *            ��һ��x����
	 * @param y1
	 *            ��һ��y����
	 * @param x2
	 *            �ڶ���x����
	 * @param y2
	 *            �ڶ���y����
	 * @return ���ؾ���
	 */
	public static float getDistance(float x1, float y1, float x2, float y2) {
		return (float) Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
	}

	/**
	 * �ж��Ƿ������˾���
	 * 
	 * @param x
	 *            �������x����
	 * @param y
	 *            �������y����
	 * @param left
	 *            �������Ͻ�x����
	 * @param top
	 *            �������Ͻ�y����
	 * @param right
	 *            �������½�x����
	 * @param bottom
	 *            �������½�y����
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
	 * �ж��Ƿ������˾���
	 * 
	 * @param x
	 *            �������x����
	 * @param y
	 *            �������y����
	 * @param rect
	 *            ����ʵ��
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
	 * �����봰�ڵľ��봴��rectʵ��
	 * 
	 * @param paddingLeft
	 *            ��������봰����ߵľ���
	 * @param paddingTop
	 *            �����ϱ��봰���ϱߵľ���
	 * @param paddingRight
	 *            �����ұ��봰���ұߵľ���
	 * @param paddingBottom
	 *            �����±��봰���±ߵľ���
	 * @param screenW
	 *            ���ڿ�
	 * @param screenH
	 *            ���ڸ�
	 * @return
	 */
	public static RectF getRectWithPadding(float paddingLeft, float paddingTop,
			float paddingRight, float paddingBottom, int screenW, int screenH) {
		RectF rect = new RectF(paddingLeft, paddingTop, screenW - paddingRight,
				screenH - paddingBottom);
		return rect;
	}

	/**
	 * ����ռ���ڳ���ıȴ���rectʵ��
	 * 
	 * @param percentPL
	 *            ���ε�����봰����ߵľ���ռ���ڿ�İٷֱ�
	 * @param percentPT
	 *            ���ε��ϱ��봰���ϱߵľ���ռ���ڸߵİٷֱ�
	 * @param percentPR
	 *            ���ε��ұ��봰���ұߵľ��봰�ڿ�İٷֱ�
	 * @param percentPB
	 *            ���ε��±��봰���±ߵľ���ռ���ڸߵİٷֱ�
	 * @param screenW
	 *            ���ڿ�
	 * @param screenH
	 *            ���ڸ�
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
