package xfocus.game.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Player {
	// ����һ������
	private Paint paint = new Paint();
	// ������Ļ�Ŀ��
	private int screenW, screenH;
	// �׾��εĸ߶�
	private int rectH = 50;
	// ��ʾ��ǰѪ���Ƿ��ռ���ȷ
	private boolean collected;
	// ��Ѫ��Ϊ��Ļ�Ŀ��
	private int hp_length;
	// ������ǰѪ��
	private int hp_current;
	int score;
	int currentFrame = 0; // ���ڲ�����ǰ��ʾ֡

	public Player(int sw, int sh) {
		screenW = sw;
		screenH = sh;
		paint = new Paint();
		hp_current = 100;
		score = 0;
	}

	public void doDraw(Canvas canvas) {
		int bloodVary = hp_current; // bloodVary��Ѫ���ı仯
		paint.setStyle(Style.STROKE);
		paint.setColor(Color.RED); // ��ʾ��ǰѪ�����ʵ���ɫ
		canvas.drawRect(0, screenH - rectH, screenW, screenH, paint);// ����ͼ������Ѫ������ʾ
		paint.setStyle(Style.FILL);
		canvas.drawRect(0, screenH - rectH, bloodVary, screenH, paint);// ����ͼ����Ѫ������ʾ

	}

	/**
	 * ��Ϸ�߼�
	 */
	public void logic() {
//		if (collected == true) // �ж��ռ���ȷ
//		{
//			if (hp_current >= hp_length) // ��ǰѪ���Ѿ�����ʱ��
//				hp_current = 0; // �����game success��䡿
//			else
//				hp_plus(); // ���洦������Ѫ����������
//		} else {
//			if (hp_current <= 0) // ��ǰѪ��������0ʱ
//				; // �����game fail��䡿
//			else
//				hp_minus(); // ���洦������Ѫ���ݼ�����
//		}
	}

	public void hp_plus() {
		hp_current++;
	}

	public void hp_minus() {
		hp_current--;
	}
	
	public void setScore(int s) {
		score = s;
	}
	
	public int getScore() {
		return score;
	}

}
