package xfocus.game.view;

import xfocus.game.R;
import xfocus.game.view.MainSurfaceView.MainThread;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
/*
 * 主程序，程序入口。
 */
public class MainActivity extends Activity {
	private MainSurfaceView msfv;
	private MainThread thread;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置程序全屏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.i("debug", "activity created");
		msfv = (MainSurfaceView) findViewById(R.id.msfv);
		thread = msfv.getThread();
		if (savedInstanceState == null) {
//            thread.setState();
        } else {
//            thread.restoreState(savedInstanceState);
        }
	}
}
