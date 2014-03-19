package xfocus.game.components;

/**
 * 共有的标签
 * 
 * @author lzp
 * 
 */
public class CommonValue {
	/** DropThing相关值 */
	public static final int DT_ROLE_GOOD = 0; // 好东西
	public static final int DT_ROLE_BAD = 1; // 坏东西
	public static final int COLLECTOR_GOOD = 0;
	public static final int COLLECTOR_BAD = 1;

	public static final int DT_TYPE_OXYGEN = 0; // 氧气
	public static final int DT_TYPE_SULFUR_DIOXIDE = 1; // 二氧化硫
	public static final int DT_TYPE_CARBON_DIOXIDE = 2; // 二氧化碳

	public static final String DT_TYPE[] = { "OXYGEN", "SULFUR_DIOXIDE",
			"CARBON_DIOXIDE" };
	public static final int DT_ROLE[] = { DT_ROLE_GOOD, DT_ROLE_BAD,
			DT_ROLE_BAD };

	/** 游戏状态相关值 */
	public static final int GAME_STATE_MENU = 0;
	public static final int GAME_STATE_PLAYING = 1;
	public static final int GAME_STATE_WIN = 2;
	public static final int GAME_STATE_LOST = 3;
	public static final int GAME_STATE_START = 4;
	public static final int GAME_STATE_PAUSE = 5; // 游戏状态： 暂停
	public static final int GAME_STATE_HIGHSCORE = 6;
	public static final int GAME_STATE_ABOUT = 7;
	public static final int GAME_STATE_SETTING = 8;
	public static final int GAME_STATE_EXIT = 9;

	/** 游戏音效相关值*/
	public static final int MUSIC_PLAY = 0;
	public static final int MUSIC_PAUSE = 1;
	public static final int MUSIC_STOP = 2;
	public static final int MUSIC_ON = 3;
	public static final int MUSIC_OFF = 4;

	/** 游戏暂停菜单相关值 */
	public static final String PAUSE_MENU_BTN_RESUME = "继续";
	public static final String PAUSE_MENU_BTN_SETTING = "设置";
	public static final String PAUSE_MENU_BTN_EXIT = "退出";
	public static final String PAUSE_MENU_TITLE = "游戏菜单";

	/** 游戏失败画面相关值 */
	public static final float LOST_PADDING_LEFT_PER = (float) 0.1;
	public static final float LOST_PADDING_TOP_PER = (float) 0.08;
	public static final float LOST_PADDING_BOTTOM_PER = (float) 0.4;

	/** GameMenu游戏菜单相关值 */
	public static final float MENU_BTN_PADDING_SIDE = (float) 0.15;
	public static final float MENU_DISTANCE_BETWEEN_TWO_BUTTON = (float) 0.05;
	public static final float MENU_BTN_HEIGHT = (float) 0.1;

	public static final float MENU_BTN_BEGIN_PTOP = (float) 0.1;
	public static final float MENU_BTN_BEGIN_PBOTTOM = (float) 1
			- (MENU_BTN_BEGIN_PTOP + MENU_BTN_HEIGHT);

	public static final float MENU_BTN_RECORD_PTOP = (float) MENU_BTN_BEGIN_PTOP
			+ MENU_DISTANCE_BETWEEN_TWO_BUTTON + MENU_BTN_HEIGHT;
	public static final float MENU_BTN_RECORD_PBOTTOM = (float) 1
			- (MENU_BTN_RECORD_PTOP + MENU_BTN_HEIGHT);

	public static final float MENU_BTN_SETTING_PTOP = (float) MENU_BTN_RECORD_PTOP
			+ MENU_DISTANCE_BETWEEN_TWO_BUTTON + MENU_BTN_HEIGHT;
	public static final float MENU_BTN_SETTING_PBOTTOM = (float) 1
			- (MENU_BTN_SETTING_PTOP + MENU_BTN_HEIGHT);

	public static final float MENU_BTN_ABOUT_PTOP = (float) MENU_BTN_SETTING_PTOP
			+ MENU_DISTANCE_BETWEEN_TWO_BUTTON + MENU_BTN_HEIGHT;
	public static final float MENU_BTN_ABOUT_PBOTTOM = (float) 1
			- (MENU_BTN_ABOUT_PTOP + MENU_BTN_HEIGHT);

	public static final float MENU_BTN_EXIT_PTOP = (float) MENU_BTN_ABOUT_PTOP
			+ MENU_DISTANCE_BETWEEN_TWO_BUTTON + MENU_BTN_HEIGHT;
	public static final float MENU_BTN_EXIT_PBOTTOM = (float) 1
			- (MENU_BTN_EXIT_PTOP + MENU_BTN_HEIGHT);

	public static final String MENU_BTN_BEGIN= "开始游戏";
	public static final String MENU_BTN_RECORD= "游戏记录";
	public static final String MENU_BTN_SETTING= "游戏设置";
	public static final String MENU_BTN_ABOUT= "关于我们";
	public static final String MENU_BTN_EXIT= "退出游戏";


}
