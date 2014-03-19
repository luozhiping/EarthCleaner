package xfocus.game.components;

/**
 * ���еı�ǩ
 * 
 * @author lzp
 * 
 */
public class CommonValue {
	/** DropThing���ֵ */
	public static final int DT_ROLE_GOOD = 0; // �ö���
	public static final int DT_ROLE_BAD = 1; // ������
	public static final int COLLECTOR_GOOD = 0;
	public static final int COLLECTOR_BAD = 1;

	public static final int DT_TYPE_OXYGEN = 0; // ����
	public static final int DT_TYPE_SULFUR_DIOXIDE = 1; // ��������
	public static final int DT_TYPE_CARBON_DIOXIDE = 2; // ������̼

	public static final String DT_TYPE[] = { "OXYGEN", "SULFUR_DIOXIDE",
			"CARBON_DIOXIDE" };
	public static final int DT_ROLE[] = { DT_ROLE_GOOD, DT_ROLE_BAD,
			DT_ROLE_BAD };

	/** ��Ϸ״̬���ֵ */
	public static final int GAME_STATE_MENU = 0;
	public static final int GAME_STATE_PLAYING = 1;
	public static final int GAME_STATE_WIN = 2;
	public static final int GAME_STATE_LOST = 3;
	public static final int GAME_STATE_START = 4;
	public static final int GAME_STATE_PAUSE = 5; // ��Ϸ״̬�� ��ͣ
	public static final int GAME_STATE_HIGHSCORE = 6;
	public static final int GAME_STATE_ABOUT = 7;
	public static final int GAME_STATE_SETTING = 8;
	public static final int GAME_STATE_EXIT = 9;

	/** ��Ϸ��Ч���ֵ*/
	public static final int MUSIC_PLAY = 0;
	public static final int MUSIC_PAUSE = 1;
	public static final int MUSIC_STOP = 2;
	public static final int MUSIC_ON = 3;
	public static final int MUSIC_OFF = 4;

	/** ��Ϸ��ͣ�˵����ֵ */
	public static final String PAUSE_MENU_BTN_RESUME = "����";
	public static final String PAUSE_MENU_BTN_SETTING = "����";
	public static final String PAUSE_MENU_BTN_EXIT = "�˳�";
	public static final String PAUSE_MENU_TITLE = "��Ϸ�˵�";

	/** ��Ϸʧ�ܻ������ֵ */
	public static final float LOST_PADDING_LEFT_PER = (float) 0.1;
	public static final float LOST_PADDING_TOP_PER = (float) 0.08;
	public static final float LOST_PADDING_BOTTOM_PER = (float) 0.4;

	/** GameMenu��Ϸ�˵����ֵ */
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

	public static final String MENU_BTN_BEGIN= "��ʼ��Ϸ";
	public static final String MENU_BTN_RECORD= "��Ϸ��¼";
	public static final String MENU_BTN_SETTING= "��Ϸ����";
	public static final String MENU_BTN_ABOUT= "��������";
	public static final String MENU_BTN_EXIT= "�˳���Ϸ";


}
