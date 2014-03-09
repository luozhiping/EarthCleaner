package xfocus.game.components;

/*
 * ��������·���ࣨ�켣��
 */
public class Track {
	private Collision collision;

	public Track(Collision collision) {
		this.collision = collision;
	}

	public void drop(DropThing dt) {// �������������˶�ʱ��һ֡��λ��
		// ...�õ�dt��ǰλ�ã�����ı�λ�ú��ж���ײ
		dt.setDropThingX(dt.getDropThingX() + 1);
		dt.setDropThingY(dt.getDropThingY() + 1);
		if (collision.isCollision(dt)) {
			// ...���������������򷴷�������
		} else {
			// ...û��������
		}
	}

	public void dropAfterSlide(DropThing dt) {// ���û�����������һ֡��λ��

	}

}
