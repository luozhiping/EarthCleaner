package xfocus.game.components;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * ���ݣ����ݿ���ز���
 * @author lzp
 *
 */
public class DataHandler {
	private Context mContext;
	private SQLiteDatabase mDatabase;
	public DataHandler(Context context, String dbName) {
		mContext = context;
		openOrCreateDatabase(dbName);
	}
	
	/**
	 * ���������ݿ������
	 * @param dbName
	 */
	private void openOrCreateDatabase(String dbName) {
		mDatabase = mContext.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
	}
	
	/**
	 * �������ݱ��������ݱ���򿪸����ݱ�
	 * @param tableName
	 */
	public void openOrCreateTable(String tableName) {
		
	}
	
	/**
	 * ����߷ֲ��뵽���ݱ���
	 * @param score
	 */
	public void insertHighScore(int score) {
		
	}
	
	/**
	 * ��ѯ��ߵ�ʮ������
	 */
	public ArrayList queryHighScore() {
		return null;
	}
	
	/**
	 * ��ѯ��߷�
	 * @return
	 */
	public int getHighScore() {
		return 0;
	}
//	/**
//	 * ɾ�����ݱ�������
//	 */
//	public void deleteData() {
//		
//	}
	
	/**
	 * �ر�����
	 */
	@Override
	public void finalize() throws Throwable {
		mDatabase.close();
		super.finalize();
	}
}
