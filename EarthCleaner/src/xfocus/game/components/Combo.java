package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * 游戏界面漂浮元素
 * @author lzp
 *
 */
public class Combo {
	private int lastCombo;
	private int alpha = 255;
	private float screenW, screenH;
	private float currentX, currentY;
	private Paint paint;
	
	public Combo(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		paint = new Paint();

		currentX = (float) (screenW / 2);
		currentY = (float) (screenH * 0.4);
	}

	public void doDraw(Canvas canvas, int combo) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(140);
		paint.setTextAlign(Align.CENTER);
		if (combo % 5 == 0) {
			paint.setColor(Color.RED);
			paint.setTextSize(240);
		}
		canvas.save();
		if (combo != 0) {
			if (lastCombo == combo) {
				if (alpha != 0)
					alpha -= 5;
				paint.setAlpha(alpha);
				canvas.drawText(Integer.toString(combo), currentX, currentY--,
						paint);
			} else {
				alpha = 255;
				currentX = (float) (screenW / 2);
				currentY = (float) (screenH * 0.4);
				canvas.drawText(Integer.toString(combo), currentX, currentY--,
						paint);
			}
		}
		canvas.restore();
		lastCombo = combo;
	}
}
