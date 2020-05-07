package edu.yonatan.dao;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.yonatan.model.Tb_pwd;

public class PwdDAO {
	// �������ݿ������
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	// ���幹�캯��
	public PwdDAO(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	/**
	 * ���������Ϣ
	 * @param tb_pwd
	 */
	public void add(Tb_pwd tb_pwd) {
		db.execSQL("insert into tb_pwd (username, password) values (?, ?)",
				new Object[] { tb_pwd.getUsername(), tb_pwd.getPassword() });
	}
	/**
	 * ����������Ϣ
	 * @param tb_pwd
	 */
	public void update(Tb_pwd tb_pwd) {
		db.execSQL("update tb_pwd set username = ?, password = ?",
				new Object[] { tb_pwd.getUsername(), tb_pwd.getPassword() });
	}
	/**
	 * ����������Ϣ
	 * @return
	 */
	public Tb_pwd find(String username) {
		Cursor cursor = db.rawQuery("select username, password from tb_pwd where username = ?", new String[] {username});
		if (cursor.moveToNext()){
			return new Tb_pwd(cursor.getString(cursor.getColumnIndex("username")),
					cursor.getString(cursor.getColumnIndex("password")));
		}
		cursor.close();
		return null;
	}
	public long getCount() {
		Cursor cursor = db.rawQuery("select count(*) from tb_pwd", null);
		if (cursor.moveToNext()){
			return cursor.getLong(0);
		}
		cursor.close();
		return 0;
	}
}
