package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Paint;
/*
 * ���Ѫ����ʾ
 */
public class Player {
	private int hp_length;//��Ѫ��
	private int hp_current;//��ǰѪ��
	private int score;
	public Player(int screenWidth) {
		hp_length = screenWidth;
		hp_current = screenWidth/2; //Ĭ�ϳ�ʼѪ��Ϊ50%
		score = 0;
	}
	
	public void doDraw(Canvas canvas, Paint paint) {
		canvas.save();
		
		canvas.restore();
	}
	
	public void logic() {
		
	}
	
	public void hp_minus() {
		
	}
	
	public void hp_plus() {
		
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int i) {
		score = i;
	}
	
}
