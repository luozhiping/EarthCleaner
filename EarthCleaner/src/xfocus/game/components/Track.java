package xfocus.game.components;

import java.util.Random;
/*
 * 下落物体路径类（轨迹）
 */
public class Track {
	private Collision collision;
	private Random random;
	public Track(Collision collision) {
		this.collision = collision;
		random = new Random();
	}

	public void drop(DropThing dt) {// 设置物体自由运动时下一帧的位置
		// ...得到dt当前位置，随机改变位置后，判断碰撞
		int ran = random.nextInt(5);
		
		dt.setDropThingX(dt.getDropThingX() + ran - 2);
		dt.setDropThingY(dt.getDropThingY() + 2);
		if (collision.isCollision(dt)) {
			// ...遇到其他物体则向反方向下落
			dt.setDropThingX(dt.getDropThingX() - 2*ran + 4);
		} else {
			// ...没遇到物体
		}
	}

	private float leftX = 0, leftY = 960 - 50,
			rightX = 600, rightY = 960 - 50;
	private float xc,yc,spX,spY;
	public void slideInit(float x, float y, int direct) {
		if(direct == 1) {
			xc = leftX - x;
			yc = leftY - y;
		} else { 
			xc = rightX - x;
			yc = rightY - y;
		}
		spX = xc/120;
		spY = yc/120;
	}
	public void dropAfterSlide(DropThing dt, int direct) {// 设置滑动后物体下一帧的位置
		dt.setDropThingX(dt.getDropThingX() + spX);
		dt.setDropThingY(dt.getDropThingY() + spY);
	}

}
