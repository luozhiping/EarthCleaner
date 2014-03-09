package xfocus.game.components;

/*
 * 下落物体路径类（轨迹）
 */
public class Track {
	private Collision collision;

	public Track(Collision collision) {
		this.collision = collision;
	}

	public void drop(DropThing dt) {// 设置物体自由运动时下一帧的位置
		// ...得到dt当前位置，随机改变位置后，判断碰撞
		dt.setDropThingX(dt.getDropThingX() + 1);
		dt.setDropThingY(dt.getDropThingY() + 1);
		if (collision.isCollision(dt)) {
			// ...遇到其他物体则向反方向下落
		} else {
			// ...没遇到物体
		}
	}

	public void dropAfterSlide(DropThing dt) {// 设置滑动后物体下一帧的位置

	}

}
