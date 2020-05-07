package edu.yonatan.activity;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import edu.yonatan.dao.FlagDAO;
import edu.yonatan.dao.InaccountDAO;
import edu.yonatan.dao.OutaccountDAO;
import edu.yonatan.model.Tb_flag;
import edu.yonatan.model.Tb_inaccount;
import edu.yonatan.model.Tb_outaccount;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Showinfo extends Activity {
	public static final String FLAG = "id";// ����һ��������������Ϊ������
	// ����ҳ���������
	Button btnoutinfo, btnininfo, btnflaginfo;
	ListView lvinfo;
	String strType = "";
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		lvinfo = (ListView) findViewById(R.id.lvinfo);
		btnoutinfo = (Button) findViewById(R.id.btnoutinfo);
		btnininfo = (Button) findViewById(R.id.btnininfo);
		btnflaginfo = (Button) findViewById(R.id.btnflaginfo);

		ShowInfo(R.id.btnflaginfo);// Ĭ����ʾ��ǩ��Ϣ
		// Ϊ֧����Ϣ��ť���ü����¼�
		btnoutinfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnoutinfo);// ��ʾ֧����Ϣ
					}
				});
		// Ϊ������Ϣ��ť���ü����¼�
		btnininfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnininfo);// ��ʾ������Ϣ
					}
				});
		// Ϊ��ǩ��Ϣ��ť���ü����¼�
		btnflaginfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnflaginfo);// ��ʾ��ǩ��Ϣ
					}
				});
		// ΪListView�������¼�
		lvinfo.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));
				Intent intent = null;
				if (strType == "btnflaginfo") {
					intent = new Intent(Showinfo.this, FlagManage.class);
					intent.putExtra(FLAG, strid);
					startActivity(intent);
				}
			}
		});
	}
	/**
	 * ��дonResume����
	 */
	@Override
	protected void onResume() {
		super.onResume();
		ShowInfo(R.id.btnflaginfo);// ���±�ǩ��Ϣ
	}
	/**
	 * ���ر�ǩ��Ϣ
	 * @param intType
	 */
	private void ShowInfo(int intType) {
		String[] strInfos = null;
		ArrayAdapter<String> arrayAdapter = null;
		Intent intent = null;
		switch (intType) {
		case R.id.btnoutinfo:// �����֧����ťbtnoutinfo
			strType = "outinfo";
			intent = new Intent(Showinfo.this, TotalChart.class);
			intent.putExtra("passType", strType);
			startActivity(intent);
			break;
		case R.id.btnininfo:// ��������밴ťbtnininfo
			strType = "ininfo";
			intent = new Intent(Showinfo.this, TotalChart.class);
			intent.putExtra("passType", strType);
			startActivity(intent);
			break;
		case R.id.btnflaginfo:// �����btnflaginfo��ť
			strType = "btnflaginfo";
			FlagDAO flaginfo = new FlagDAO(Showinfo.this);
			List<Tb_flag> listFlags = flaginfo.getScrollData(0,
					(int) flaginfo.getCount());
			strInfos = new String[listFlags.size()];
			int n = 0;
			for (Tb_flag tb_flag : listFlags) {
				strInfos[n] = tb_flag.getid() + "|" + tb_flag.getFlag();
				if (strInfos[n].length() > 20)
					strInfos[n] = strInfos[n].substring(0, 20) + "����";
				n++;
			}
			arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, strInfos);
			lvinfo.setAdapter(arrayAdapter);
			break;
		}
	}

}
