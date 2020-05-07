package edu.yonatan.dao;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import edu.yonatan.model.Tb_flag;

public class FlagDAO {
	// �������ݿ������
	private DBOpenHelper helper;
	private SQLiteDatabase db;
	// ���幹�캯��
	public FlagDAO(Context context){
		helper = new DBOpenHelper(context);
		db = helper.getWritableDatabase();
	}
	/**
	 * ��ӱ�ǩ��Ϣ
	 * @param tb_flag
	 */
	public void add(Tb_flag tb_flag) {
		db.execSQL("insert into tb_flag (_id,flag) values (?,?)", new Object[] {
				tb_flag.getid(), tb_flag.getFlag() });
	}
	/**
	 * ���±�ǩ��Ϣ
	 * @param tb_flag
	 */
	public void update(Tb_flag tb_flag) {
		db.execSQL("update tb_flag set flag = ? where _id = ?", new Object[] {
				tb_flag.getFlag(), tb_flag.getid() });
	}
	/**
	 * ���ұ�ǩ��Ϣ
	 * @param id
	 * @return
	 */
	public Tb_flag find(int id) {
		Cursor cursor = db.rawQuery(
				"select _id,flag from tb_flag where _id = ?",
				new String[] { String.valueOf(id) });
		if (cursor.moveToNext()){
			return new Tb_flag(cursor.getInt(cursor.getColumnIndex("_id")),
					cursor.getString(cursor.getColumnIndex("flag")));
		}
		cursor.close();
		return null;
	}
	/**
	 * �h����ǩ��Ϣ
	 * @param ids
	 */
	public void detele(Integer... ids) {
		if (ids.length > 0){
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < ids.length; i++){
				sb.append('?').append(',');
			}
			sb.deleteCharAt(sb.length() - 1);
			db.execSQL("delete from tb_flag where _id in (" + sb + ")",
					(Object[]) ids);
		}
	}
	/**
	 * ��ȡ��ǩ��Ϣ
	 * @param start ��ʼλ��
	 * @param count ÿҳ��ʾ����
	 * @return
	 */
	public List<Tb_flag> getScrollData(int start, int count) {
		List<Tb_flag> lisTb_flags = new ArrayList<Tb_flag>();
		Cursor cursor = db.rawQuery("select * from tb_flag limit ?,?",
				new String[] { String.valueOf(start), String.valueOf(count) });
		while (cursor.moveToNext()){
			lisTb_flags.add(new Tb_flag(cursor.getInt(cursor
					.getColumnIndex("_id")), cursor.getString(cursor
					.getColumnIndex("flag"))));
		}
		cursor.close();
		return lisTb_flags;
	}
	/**
	 * ��ȡ�ܼ�¼��
	 * @return
	 */
	public long getCount() {
		Cursor cursor = db.rawQuery("select count(_id) from tb_flag", null);
		if (cursor.moveToNext()){
			return cursor.getLong(0);
		}
		cursor.close();
		return 0;
	}
	/**
	 * ��ȡ��ǩ�����
	 * @return
	 */
	public int getMaxId() {
		Cursor cursor = db.rawQuery("select max(_id) from tb_flag", null);
		while (cursor.moveToLast()) {
			return cursor.getInt(0);
		}
		cursor.close();
		return 0;
	}
}
