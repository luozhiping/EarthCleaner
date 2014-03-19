package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.Log;

/**
 * 分数显示(漂浮物)
 * 
 */
public class PopMessage {
	private int frame;
	private float currentX, currentY;
	private Paint paint;
	private String text;
	private float screenW, screenH;
	private int alpha, alphaMinus;

	public PopMessage(int screenW, int screenH, String text, float sec,
			float startX, float startY) {
		paint = new Paint();
		this.screenW = screenW;
		this.screenH = screenH;
		currentX = startX;
		currentY = startY;
		frame = (int) (40 * sec);
		this.text = text;
		alpha = 255;
		alphaMinus = 255 / frame;
	}

	public void drawScore(Canvas canvas) {
		alpha -= alphaMinus;
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		paint.setAlpha(alpha);
		canvas.save();
		canvas.drawText(text, currentX, currentY--, paint);
		canvas.restore();
		frame--;
	}

	public int getFrame() {
		return frame;
	}

}
