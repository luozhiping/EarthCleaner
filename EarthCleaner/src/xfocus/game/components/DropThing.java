package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 *  下落物体类
 */
public class DropThing {
	public final static int DROPING = 0; // 状态：自由下落
	public final static int SLIDED = 1;// 状态： 已被滑动
	public final static int DEAD = 2;// 状态： 已被收集

	public final static int SLIDED_LEFT = 1; // 状态：向左滑动
	public final static int SLIDED_RIGHT = 2; // 状态：向右滑动

	private float dropThingX; // 物体当前位置圆心x坐标
	private float dropThingY; // 物体当前位置圆心y坐标

	private Track track; // 轨迹实例
	private int dropThingRole; // 物体角色（好坏）
	private int dropThingType; // 物体种类
	private float radius; // 物体半径
	private int slidedDirect = 0; // 滑动方向状态标识符

	public int state;
	private Paint paint;
	private int score;
	/**
	 * 构造函数
	 * 
	 * @param x
	 *            物体初始坐标
	 * @param collision
	 *            传入碰撞实例
	 * @param radius
	 *            物体半径
	 * @param type
	 *            物体类型
	 * @param role
	 *            物体角色（好、坏）
	 */
	public DropThing(float x, Collision collision, float radius, int type,
			int role) {
		track = new Track(collision);
		dropThingX = x;
		dropThingY = 0 - radius;
		this.radius = radius;
		paint = new Paint();
		state = DROPING;
		dropThingRole = role;
		dropThingType = type;
		score = 2;
		// Log.i("debug", "dropthing created");
	}

	/**
	 * 设置物体被滑动的方向
	 * 
	 * @param direct
	 *            方向
	 */
	public void setSlidedDirect(int direct) {
		slidedDirect = direct;
		track.slideInit(dropThingX, dropThingY, direct);
	}

	public void doDraw(Canvas canvas) {
		switch (state) {
		case DROPING:
			canvas.save();
			if (dropThingRole == CommonValue.DT_ROLE_GOOD) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.DKGRAY);
			}
			canvas.drawCircle(dropThingX, dropThingY, radius, paint);
			canvas.restore();
			break;
		case SLIDED:
			canvas.save();
			if (dropThingRole == CommonValue.DT_ROLE_GOOD) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.DKGRAY);
			}
			canvas.drawCircle(dropThingX, dropThingY, radius, paint);
			canvas.restore();
			break;
		}
	}

	public void logic() {
		switch (state) {
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

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	public void setDropThingX(float x) {
		dropThingX = x;
	}

	public void setDropThingY(float y) {
		dropThingY = y;
	}

	public void setState(int state) {
		this.state = state;
	}

	public float getDropThingX() {
		return dropThingX;
	}

	public float getDropThingY() {
		return dropThingY;
	}

	public float getRadius() {
		return radius;
	}

	public int getDropThingRole() {
		return dropThingRole;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getState() {
		return state;
	}
}
