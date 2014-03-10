package xfocus.game;

import java.util.Random;

import xfocus.game.components.DropThing;
import xfocus.game.components.World;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GamePlaying {
	private World world;
	private int screenW, screenH;
	Random random;
	int dt_x = 0, addDtOrNot = 0;
	public GamePlaying(int screenW, int screenH) {
		this.screenW = screenW;
		this.screenH = screenH;
		random = new Random();
	}

	public void logic() {
		if (++addDtOrNot == random.nextInt(30)) {
			dt_x = 20 + random.nextInt(60);
			world.addDropThing(dt_x);
			addDtOrNot = 0;
		}
		for (int i = 0; i < world.allDt.size(); i++) {
			DropThing dt = world.allDt.get(i);
			dt.logic();
		}
	}

	public void doDraw(Canvas canvas, Paint paint) {
		for (int i = 0; i < world.allDt.size(); i++) {
			DropThing dt = world.allDt.get(i);
			dt.doDraw(canvas, paint);
		}
	}

	public void init_world() {
		world = new World(screenW, screenH);

	}

}
