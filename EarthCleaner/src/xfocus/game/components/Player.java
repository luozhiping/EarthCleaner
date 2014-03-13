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
	
	//用于控制SurfaceView
	private SurfaceHolder sfh;
    //声明一个画笔
	private Paint paint = new Paint();
	//声明一个线程
	private Thread th;
	//线程消亡的标识
	private boolean flag;
	//声明一个画布
	private Canvas canvas;
	//声明屏幕的宽高
	private int screenW, screenH;
	//底矩形的高度
	private int rectH = 50;
	//显示当前血量是否收集正确
	private boolean collected; 
	//总血量为屏幕的宽度
	private int hp_length ;  
	//声明当前血量
	private int hp_current ;   
	
    Bitmap fishBmp[] = new Bitmap[4];
	int currentFrame = 0;      //用于操作当前显示帧
	
	public Player(Context context) {
		super(context);
		//实例SurfaceHolder
		sfh = this.getHolder();
		//为SurfaceView添加状态监听
		sfh.addCallback(this);
		//实例一个画笔
		paint = new Paint();
		//设置画笔颜色为白色
		paint.setColor(Color.WHITE);
		setFocusable(true);
		
		//10幅测试图片
		for (int i=0; i<fishBmp.length; i++) {
			
//			fishBmp[i] = BitmapFactory.decodeResource(this.getResources(), R.drawable.fish0+i);
		}
	}
	
	public void doDraw() {
		int bloodVary = hp_current;        //bloodVary是血量的变化
		try{
			canvas = sfh.lockCanvas();
			if(canvas != null){
				canvas.drawBitmap(fishBmp[currentFrame],0,0,paint);  //使得屏幕背景变黑
				paint.setColor(Color.WHITE);    //显示总血量画笔的颜色
				canvas.drawRect(0, screenH-rectH, screenW, screenH, paint);//矩形图案即总血量的显示
				paint.setColor(Color.RED);      //显示当前血量画笔的颜色
				canvas.drawRect(0, screenH-rectH, bloodVary, screenH, paint);//矩形图案即血量的显示
			}		
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			if(canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}	
	}
	
	/**
	 * 游戏逻辑
	 */
	public void logic() {
		if(collected == true)           //判断收集正确
		{                               
			if(hp_current >= hp_length) //当前血量已经满的时候
				hp_current = 0;     //【添加game success语句】
		    else
		    	hp_plus();          //常规处理，调用血量增加情形
	    } else {                       
			if(hp_current<=0)       //当前血量不大于0时
				  ;                 //【添加game fail语句】
		    else
				hp_minus();         //常规处理，调用血量递减情形
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
		hp_length = screenW;      //总血量为屏幕的宽度
		hp_current = screenW/2;   //初始血量为屏幕长度的一半
		collected = true;
		flag = true;
		//实例线程
		th = new Thread(this);
		//启动线程
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
	 * SurfaceView视图状态发生改变，响应此函数
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * SurfaceView视图消亡时，响应此函数
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;	
	}
	
}




