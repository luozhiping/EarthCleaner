package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

/*
 * 
 */
public class DropThing {
	public final static int DROPING = 0; // ״̬����������
	public final static int SLIDED = 1;// ״̬�� �ѱ�����
	final static int DEAD = 2;// ״̬�� �ѱ��ռ�
	final static int GOOD = 0; // �ö���
	final static int BAD = 1; // ������
	final static int OXYGEN = 0; // ����
	final static int SULFUR_DIOXIDE = 1; // ��������
	final static int CARBON_DIOXIDE = 2; // ������̼
	public final static int SLIDED_LEFT = 1; // ״̬�����󻬶�
	public final static int SLIDED_RIGHT = 2; // ״̬�����һ���

	private int dropThingX; // ���嵱ǰλ��Բ��x����
	private int dropThingY; // ���嵱ǰλ��Բ��y����

	private Track track;
	private int dropThingRole; // �����ɫ���û���
	private int dropThingType; // ��������
	private int radius; // ����뾶
	private int slidedDirect = 0; // ��������״̬��ʶ��

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
