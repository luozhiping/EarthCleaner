package xfocus.game.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Player extends SurfaceView implements Callback, Runnable {
	
	//���ڿ���SurfaceView
	private SurfaceHolder sfh;
    //����һ������
	private Paint paint = new Paint();
	//����һ���߳�
	private Thread th;
	//�߳������ı�ʶ
	private boolean flag;
	//����һ������
	private Canvas canvas;
	//������Ļ�Ŀ��
	private int screenW, screenH;
	//�׾��εĸ߶�
	private int rectH = 50;
	//��ʾ��ǰѪ���Ƿ��ռ���ȷ
	private boolean collected; 
	//��Ѫ��Ϊ��Ļ�Ŀ��
	private int hp_length ;  
	//������ǰѪ��
	private int hp_current ;   
	
    Bitmap fishBmp[] = new Bitmap[4];
	int currentFrame = 0;      //���ڲ�����ǰ��ʾ֡
	
	public Player(Context context) {
		super(context);
		//ʵ��SurfaceHolder
		sfh = this.getHolder();
		//ΪSurfaceView���״̬����
		sfh.addCallback(this);
		//ʵ��һ������
		paint = new Paint();
		//���û�����ɫΪ��ɫ
		paint.setColor(Color.WHITE);
		setFocusable(true);
		
		//10������ͼƬ
		for (int i=0; i<fishBmp.length; i++) {
			
//			fishBmp[i] = BitmapFactory.decodeResource(this.getResources(), R.drawable.fish0+i);
		}
	}
	
	public void doDraw() {
		int bloodVary = hp_current;        //bloodVary��Ѫ���ı仯
		try{
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawBitmap(fishBmp[currentFrame],0,0,paint);  //ʹ����Ļ�������
				paint.setColor(Color.WHITE);    //��ʾ��Ѫ�����ʵ���ɫ
				canvas.drawRect(0, screenH-rectH, screenW, screenH, paint);//����ͼ������Ѫ������ʾ
				paint.setColor(Color.RED);      //��ʾ��ǰѪ�����ʵ���ɫ
				canvas.drawRect(0, screenH-rectH, bloodVary, screenH, paint);//����ͼ����Ѫ������ʾ
			}		
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}	
	}
	
	/**
	 * ��Ϸ�߼�
	 */
	public void logic() {
		if(collected == true)           //�ж��ռ���ȷ
		{                               
			if(hp_current >= hp_length) //��ǰѪ���Ѿ�����ʱ��
				hp_current = 0;     //�����game success��䡿
		    else
		    	hp_plus();          //���洦������Ѫ����������
	    } else {                       
			if(hp_current<=0)       //��ǰѪ��������0ʱ
				  ;                 //�����game fail��䡿
		    else
				hp_minus();         //���洦������Ѫ���ݼ�����
	    }
		currentFrame++;
		if (currentFrame>=fishBmp.length) {
			currentFrame = 0;
		}
	} 
	
	public void hp_plus() {
		hp_current++;
	}
	
	public void hp_minus() {
		hp_current--;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		screenW = this.getWidth();
		screenH = this.getHeight();
		hp_length = screenW;      //��Ѫ��Ϊ��Ļ�Ŀ��
		hp_current = screenW/2;   //��ʼѪ��Ϊ��Ļ���ȵ�һ��
		collected = true;
		flag = true;
		//ʵ���߳�
		th = new Thread(this);
		//�����߳�
		th.start();
	}

	@Override
	public void run() {
		while(flag){
			doDraw();
			logic();
		}	
	}

	/**
	 * SurfaceView��ͼ״̬�����ı䣬��Ӧ�˺���
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * SurfaceView��ͼ����ʱ����Ӧ�˺���
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;	
	}
	
}




