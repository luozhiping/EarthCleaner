package xfocus.game.view;

import xfocus.game.R;
import xfocus.game.view.MainSurfaceView.MainThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
/*
 * 主程序，程序入口。
 */
public class MainActivity extends Activity {
	private MainSurfaceView msfv;
	private MainThread thread;
	private Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置程序全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.i("debug", "activity created");
		int width = getWindowManager().getDefaultDisplay().getWidth();
		int height = getWindowManager().getDefaultDisplay().getHeight();
		
//		msfv = new MainSurfaceView(this, width, height);
		setContentView(R.layout.activity_main);
//		setContentView(msfv);
		msfv = (MainSurfaceView) findViewById(R.id.msfv);
		thread = msfv.getThread();
		if (savedInstanceState == null) {
			Log.i("debug", "saveStateNULL");
            thread.setState(MainSurfaceView.MainThread.GAME_PLAYING);
        } else {
			Log.i("debug", "saveStateNotNULL");
            thread.restoreState(savedInstanceState);
        }
		
	}
	
	@Override
    protected void onPause() {
        super.onPause();
        thread.pause(); // pause game when Activity pauses
    }
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
		Log.i("bundle", "onSaveInstanceState");
        thread.saveState(outState);
    }
	
	@Override
	protected void onResume() {
		Log.i("bundle", "onresume");
		if(thread != null) {
		}
//		thread.unPause();
		super.onResume();
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.i("bundle", "restore");
		if (savedInstanceState == null) {
			Log.i("debug", "restoreNull");
            thread.setState(MainSurfaceView.MainThread.GAME_PLAYING);
        } else {
			Log.i("debug", "restoreNotNull");
            thread.restoreState(savedInstanceState);
        }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("debug", "onKeyDownActivity");
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			thread.keyBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
