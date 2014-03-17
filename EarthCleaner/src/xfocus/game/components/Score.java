package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;

/**
 * ·ÖÊýÏÔÊ¾
 * 
 */
public class Score {
	private int frame;
	private float lStartX, lStartY, rStartX, rStartY;
	private float currentX, currentY;
	private Paint paint;
	private String score;
	private float screenW, screenH;

	public Score(int screenW, int screenH, int score, int side) {
		paint = new Paint();
		paint.setColor(Color.BLACK);
		paint.setTextSize(40);
		this.screenW = screenW;
		this.screenH = screenH;
		lStartX = 20;
		lStartY = screenH - 150;
		rStartX = screenW - 100;
		rStartY = screenH - 150;
		if (score > 0) {
			this.score = "+" + score;
		} else {
			this.score = "" + score;
		}
		if (side == 0) {
			currentX = lStartX;
			currentY = rStartY;
		} else if (side == 1) {
			currentX = rStartX;
			currentY = rStartY;
		}
		frame = 60;
	}

	private int lastCombo;
	private int alpha = 255;

	public Score(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		paint = new Paint();

		currentX = (float) (screenW / 2);
		currentY = (float) (screenH * 0.4);
	}

	public void drawCombo(Canvas canvas, int combo) {
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

	public void drawScore(Canvas canvas) {
		canvas.save();
		canvas.drawText(score, currentX, currentY--, paint);
		frame--;
		canvas.restore();
	}

	public int getFrame() {
		return frame;
	}

}
