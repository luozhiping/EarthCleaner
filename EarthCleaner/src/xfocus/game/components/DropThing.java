package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Paint;

/*
 * 
 */
public class DropThing {
	private int dropThingX;
	private int dropThingY;
	private int dropThingWidth;
	private int dropThingHeight;
	private Track track;
	final static int DROPING = 0; // 状态：自由下落
	final static int SLIDED = 1;// 状态： 已被滑动
	final static int DEAD = 2;// 状态： 已被收集
	public static int status = DROPING;
	
	public DropThing(int x,Collision collision) {
		track = new Track(collision); 
		dropThingX = x;
		dropThingY = 0;
	}

	public void setDropThingX(int x) {
		dropThingX = x;
	}
	public void setDropThingY(int y) {
		dropThingY = y;
	}
	
	public int getDropThingX() {
		return dropThingX;
	}
	
	public int getDropThingY() {
		return dropThingY;
	}
	
	public void doDraw(Canvas canvas, Paint paint) {
		switch (status) {
		case DROPING:
			canvas.save();
			// ...
			canvas.restore();
			break;
		case SLIDED:
			canvas.save();
			// ...
			canvas.restore();
			break;
		}
	}

	public void logic() {
		switch (status) {
		case DROPING:
			track.drop(this);
			break;
		case SLIDED:
			track.dropAfterSlide(this);
			break;
		}
	}
}
