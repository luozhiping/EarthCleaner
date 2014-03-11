package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Paint;
/*
 * 玩家血量显示
 */
public class Player {
	private int hp_length;//总血量
	private int hp_current;//当前血量
	private int score;
	public Player(int screenWidth) {
		hp_length = screenWidth;
		hp_current = screenWidth/2; //默认初始血量为50%
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
