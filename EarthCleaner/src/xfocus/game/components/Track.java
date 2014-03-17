package xfocus.game.components;

import java.util.Random;

import android.util.Log;

/*
 * ��������·���ࣨ�켣��
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

	public void drop(DropThing dt) {// �������������˶�ʱ��һ֡��λ��
		// ...�õ�dt��ǰλ�ã�����ı�λ�ú��ж���ײ
		int ran = random.nextInt(5);

		dt.setDropThingX(dt.getDropThingX() + ran - 2);
		dt.setDropThingY(dt.getDropThingY() + 2);
		if (collision.isCollision(dt)) {
			// ...���������������򷴷�������
			dt.setDropThingX(dt.getDropThingX() - 2 * ran + 4);
		} else {
			// ...û��������
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

	public void dropAfterSlide(DropThing dt, int direct) {// ���û�����������һ֡��λ��
		dt.setDropThingX(dt.getDropThingX() + spX);
		dt.setDropThingY(dt.getDropThingY() + spY);
	}

}
