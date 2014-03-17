package xfocus.game.components;

import java.util.Random;

import android.util.Log;

/*
 * 下落物体路径类（轨迹）
 */
public class Track {
	private Collision collision;
	private Random random;
	private float leftX, leftY, rightX, rightY;
	private float xc, yc, spX, spY;

	public Track(Collision collision) {
		this.collision = collision;
		random = new Random();
		leftX = 0;
		leftY = collision.getScreenHeight() - 50;
		rightX = collision.getScreenWidth();
		rightY = collision.getScreenHeight() - 50;
	}

	public void drop(DropThing dt) {// 设置物体自由运动时下一帧的位置
		// ...得到dt当前位置，随机改变位置后，判断碰撞
		int ran = random.nextInt(5);

		dt.setDropThingX(dt.getDropThingX() + ran - 2);
		dt.setDropThingY(dt.getDropThingY() + 2);
		if (collision.isCollision(dt)) {
			// ...遇到其他物体则向反方向下落
			dt.setDropThingX(dt.getDropThingX() - 2 * ran + 4);
		} else {
			// ...没遇到物体
		}
	}

	public void slideInit(float x, float y, int direct) {
		if (direct == 1) {
			xc = leftX - x;
			yc = leftY - y;
		} else {
			xc = rightX - x;
			yc = rightY - y;
		}
		spX = xc / 60;
		spY = yc / 60;
	}

	public void dropAfterSlide(DropThing dt, int direct) {// 设置滑动后物体下一帧的位置
		dt.setDropThingX(dt.getDropThingX() + spX);
		dt.setDropThingY(dt.getDropThingY() + spY);
	}

}
