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
	public final static int DEAD = 2;// ״̬�� �ѱ��ռ�
	
	public final static int GOOD = 0; // �ö���
	public final static int BAD = 1; // ������
	
	public final static int OXYGEN = 0; // ����
	public final static int SULFUR_DIOXIDE = 1; // ��������
	public final static int CARBON_DIOXIDE = 2; // ������̼
	
	public final static int SLIDED_LEFT = 1; // ״̬�����󻬶�
	public final static int SLIDED_RIGHT = 2; // ״̬�����һ���

	private float dropThingX; // ���嵱ǰλ��Բ��x����
	private float dropThingY; // ���嵱ǰλ��Բ��y����

	private Track track; //�켣ʵ��
	private int dropThingRole; // �����ɫ���û���
	private int dropThingType; // ��������
	private float radius; // ����뾶
	private int slidedDirect = 0; // ��������״̬��ʶ��

	public int state;
	private Paint paint;
	/**
	 * ���캯��
	 * @param x �����ʼ����
	 * @param collision ������ײʵ��
	 * @param radius ����뾶
	 */
	public DropThing(float x, Collision collision, float radius) {
		track = new Track(collision);
		dropThingX = x;
		dropThingY = 0 - radius;
		this.radius = radius;
		paint = new Paint();
		state = DROPING;
//		Log.i("debug", "dropthing created");
	}
	
	/**
	 * �������屻�����ķ���
	 * @param direct ����
	 */
	public void setSlidedDirect(int direct) {
		slidedDirect = direct;
	}

	public void doDraw(Canvas canvas) {
		switch (state) {
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

}
