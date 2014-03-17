package xfocus.game.components;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 *  ����������
 */
public class DropThing {
	public final static int DROPING = 0; // ״̬����������
	public final static int SLIDED = 1;// ״̬�� �ѱ�����
	public final static int DEAD = 2;// ״̬�� �ѱ��ռ�

	public final static int SLIDED_LEFT = 1; // ״̬�����󻬶�
	public final static int SLIDED_RIGHT = 2; // ״̬�����һ���

	private float dropThingX; // ���嵱ǰλ��Բ��x����
	private float dropThingY; // ���嵱ǰλ��Բ��y����

	private Track track; // �켣ʵ��
	private int dropThingRole; // �����ɫ���û���
	private int dropThingType; // ��������
	private float radius; // ����뾶
	private int slidedDirect = 0; // ��������״̬��ʶ��

	public int state;
	private Paint paint;
	private int score;
	/**
	 * ���캯��
	 * 
	 * @param x
	 *            �����ʼ����
	 * @param collision
	 *            ������ײʵ��
	 * @param radius
	 *            ����뾶
	 * @param type
	 *            ��������
	 * @param role
	 *            �����ɫ���á�����
	 */
	public DropThing(float x, Collision collision, float radius, int type,
			int role) {
		track = new Track(collision);
		dropThingX = x;
		dropThingY = 0 - radius;
		this.radius = radius;
		paint = new Paint();
		state = DROPING;
		dropThingRole = role;
		dropThingType = type;
		score = 2;
		// Log.i("debug", "dropthing created");
	}

	/**
	 * �������屻�����ķ���
	 * 
	 * @param direct
	 *            ����
	 */
	public void setSlidedDirect(int direct) {
		slidedDirect = direct;
		track.slideInit(dropThingX, dropThingY, direct);
	}

	public void doDraw(Canvas canvas) {
		switch (state) {
		case DROPING:
			canvas.save();
			if (dropThingRole == CommonValue.DT_ROLE_GOOD) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.DKGRAY);
			}
			canvas.drawCircle(dropThingX, dropThingY, radius, paint);
			canvas.restore();
			break;
		case SLIDED:
			canvas.save();
			if (dropThingRole == CommonValue.DT_ROLE_GOOD) {
				paint.setColor(Color.BLUE);
			} else {
				paint.setColor(Color.DKGRAY);
			}
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

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
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

	public int getDropThingRole() {
		return dropThingRole;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getState() {
		return state;
	}
}
