package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/*
 * 
 */
public class DropThing {
	public final static int DROPING = 0; // 状态：自由下落
	public final static int SLIDED = 1;// 状态： 已被滑动
	final static int DEAD = 2;// 状态： 已被收集
	final static int GOOD = 0; // 好东西
	final static int BAD = 1; // 坏东西
	final static int OXYGEN = 0; // 氧气
	final static int SULFUR_DIOXIDE = 1; // 二氧化硫
	final static int CARBON_DIOXIDE = 2; // 二氧化碳
	public final static int SLIDED_LEFT = 1; // 状态：向左滑动
	public final static int SLIDED_RIGHT = 2; // 状态：向右滑动

	private int dropThingX; // 物体当前位置圆心x坐标
	private int dropThingY; // 物体当前位置圆心y坐标

	private Track track;
	private int dropThingRole; // 物体角色（好坏）
	private int dropThingType; // 物体种类
	private int radius; // 物体半径
	private int slidedDirect = 0; // 滑动方向状态标识符

	public int status;

	public DropThing(int x, Collision collision, int radius) {
		track = new Track(collision);
		dropThingX = x;
		dropThingY = 0;
		this.radius = radius;

		status = DROPING;
		Log.i("debug", "dropthing created");
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

	public int getRadius() {
		return radius;
	}

	public void setSlidedDirect(int direct) {
		slidedDirect = direct;
	}

	public void doDraw(Canvas canvas, Paint paint) {
		switch (status) {
		case DROPING:
			canvas.save();
			paint.setColor(Color.RED);
			canvas.drawCircle(dropThingX, dropThingY, radius, paint);
			canvas.restore();
			break;
		case SLIDED:
			canvas.save();
			paint.setColor(Color.RED);
			canvas.drawCircle(dropThingX, dropThingY, radius, paint);
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
			track.dropAfterSlide(this, slidedDirect);
			break;
		case DEAD:
			try {
				finalize();
			} catch (Throwable e) {
				e.printStackTrace();
			}
			break;
		}
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
