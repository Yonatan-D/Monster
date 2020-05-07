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
	public static final String FLAG = "id";// 定义一个常量，用来作为请求码
	// 定义页面组件对象
	Button btnoutinfo, btnininfo, btnflaginfo;
	ListView lvinfo;
	String strType = "";
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.showinfo);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		lvinfo = (ListView) findViewById(R.id.lvinfo);
		btnoutinfo = (Button) findViewById(R.id.btnoutinfo);
		btnininfo = (Button) findViewById(R.id.btnininfo);
		btnflaginfo = (Button) findViewById(R.id.btnflaginfo);

		ShowInfo(R.id.btnflaginfo);// 默认显示便签信息
		// 为支出信息按钮设置监听事件
		btnoutinfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnoutinfo);// 显示支出信息
					}
				});
		// 为收入信息按钮设置监听事件
		btnininfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnininfo);// 显示收入信息
					}
				});
		// 为便签信息按钮设置监听事件
		btnflaginfo.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ShowInfo(R.id.btnflaginfo);// 显示便签信息
					}
				});
		// 为ListView添加项单击事件
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
	 * 重写onResume方法
	 */
	@Override
	protected void onResume() {
		super.onResume();
		ShowInfo(R.id.btnflaginfo);// 更新便签信息
	}
	/**
	 * 加载便签信息
	 * @param intType
	 */
	private void ShowInfo(int intType) {
		String[] strInfos = null;
		ArrayAdapter<String> arrayAdapter = null;
		Intent intent = null;
		switch (intType) {
		case R.id.btnoutinfo:// 如果是支出按钮btnoutinfo
			strType = "outinfo";
			intent = new Intent(Showinfo.this, TotalChart.class);
			intent.putExtra("passType", strType);
			startActivity(intent);
			break;
		case R.id.btnininfo:// 如果是收入按钮btnininfo
			strType = "ininfo";
			intent = new Intent(Showinfo.this, TotalChart.class);
			intent.putExtra("passType", strType);
			startActivity(intent);
			break;
		case R.id.btnflaginfo:// 如果是btnflaginfo按钮
			strType = "btnflaginfo";
			FlagDAO flaginfo = new FlagDAO(Showinfo.this);
			List<Tb_flag> listFlags = flaginfo.getScrollData(0,
					(int) flaginfo.getCount());
			strInfos = new String[listFlags.size()];
			int n = 0;
			for (Tb_flag tb_flag : listFlags) {
				strInfos[n] = tb_flag.getid() + "|" + tb_flag.getFlag();
				if (strInfos[n].length() > 20)
					strInfos[n] = strInfos[n].substring(0, 20) + "……";
				n++;
			}
			arrayAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, strInfos);
			lvinfo.setAdapter(arrayAdapter);
			break;
		}
	}

}
