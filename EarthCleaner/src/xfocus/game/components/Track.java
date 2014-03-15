package xfocus.game.components;

import java.util.Random;
/*
 * ��������·���ࣨ�켣��
 */
public class Track {
	private Collision collision;
	private Random random;
	public Track(Collision collision) {
		this.collision = collision;
		random = new Random();
	}

	public void drop(DropThing dt) {// �������������˶�ʱ��һ֡��λ��
		// ...�õ�dt��ǰλ�ã�����ı�λ�ú��ж���ײ
		int ran = random.nextInt(5);
		
		dt.setDropThingX(dt.getDropThingX() + ran - 2);
		dt.setDropThingY(dt.getDropThingY() + 2);
		if (collision.isCollision(dt)) {
			// ...���������������򷴷�������
			dt.setDropThingX(dt.getDropThingX() - 2*ran + 4);
		} else {
			// ...û��������
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
	public void dropAfterSlide(DropThing dt, int direct) {// ���û�����������һ֡��λ��
		dt.setDropThingX(dt.getDropThingX() + spX);
		dt.setDropThingY(dt.getDropThingY() + spY);
	}

}
