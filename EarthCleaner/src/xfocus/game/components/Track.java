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
		int x = dt.getDropThingX() - 20 + random.nextInt(40);
		dt.setDropThingX(x);
		dt.setDropThingY(dt.getDropThingY() + 10);
		if (collision.isCollision(dt)) {
			// ...遇到其他物体则向反方向下落
			dt.setDropThingX(dt.getDropThingX() - 2*x);
		} else {
			// ...没遇到物体
		}
	}

	public void dropAfterSlide(DropThing dt, int direct) {// 设置滑动后物体下一帧的位置
		if(direct == 1) {
			dt.setDropThingX(dt.getDropThingX() - 5);
		} else if (direct == 2){
			dt.setDropThingX(dt.getDropThingX() + 5);

		}
		dt.setDropThingY(dt.getDropThingY() + 10);
	}

}
