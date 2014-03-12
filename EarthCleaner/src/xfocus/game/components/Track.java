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
		int x = dt.getDropThingX() - 20 + random.nextInt(40);
		dt.setDropThingX(x);
		dt.setDropThingY(dt.getDropThingY() + 10);
		if (collision.isCollision(dt)) {
			// ...���������������򷴷�������
			dt.setDropThingX(dt.getDropThingX() - 2*x);
		} else {
			// ...û��������
		}
	}

	public void dropAfterSlide(DropThing dt, int direct) {// ���û�����������һ֡��λ��
		if(direct == 1) {
			dt.setDropThingX(dt.getDropThingX() - 5);
		} else if (direct == 2){
			dt.setDropThingX(dt.getDropThingX() + 5);

		}
		dt.setDropThingY(dt.getDropThingY() + 10);
	}

}
