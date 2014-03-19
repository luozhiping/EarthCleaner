package xfocus.game.components;

import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * 数据，数据库相关操作
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
	 * 建立与数据库的链接
	 * @param dbName
	 */
	private void openOrCreateDatabase(String dbName) {
		mDatabase = mContext.openOrCreateDatabase(dbName, Context.MODE_PRIVATE, null);
	}
	
	/**
	 * 创建数据表（已有数据表则打开该数据表）
	 * @param tableName
	 */
	public void openOrCreateTable(String tableName) {
		
	}
	
	/**
	 * 将最高分插入到数据表中
	 * @param score
	 */
	public void insertHighScore(int score) {
		
	}
	
	/**
	 * 查询最高的十个分数
	 */
	public ArrayList queryHighScore() {
		return null;
	}
	
	/**
	 * 查询最高分
	 * @return
	 */
	public int getHighScore() {
		return 0;
	}
//	/**
//	 * 删除数据表中内容
//	 */
//	public void deleteData() {
//		
//	}
	
	/**
	 * 关闭链接
	 */
	@Override
	public void finalize() throws Throwable {
		mDatabase.close();
		super.finalize();
	}
}
