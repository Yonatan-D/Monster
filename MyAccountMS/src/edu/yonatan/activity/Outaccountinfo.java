package edu.yonatan.activity;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import edu.yonatan.dao.OutaccountDAO;
import edu.yonatan.model.Tb_outaccount;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Outaccountinfo extends Activity {
	public static final String FLAG = "id";// 定义请求码
	ListView lvinfo;// 创建ListView对象
	String strType = "";// 记录管理类型
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.outaccountinfo);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		
		lvinfo = (ListView) findViewById(R.id.lvoutaccountinfo);

		ShowInfo(R.id.btnoutinfo);// 显示支出信息
		// 为ListView添加项单击事件
		lvinfo.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());
				String strid = strInfo.substring(0, strInfo.indexOf('|'));
				Intent intent = new Intent(Outaccountinfo.this,
						InfoManage.class);
				intent.putExtra(FLAG, new String[] { strid, strType });
				startActivity(intent);
			}
		});
	}
	/**
	 * 显示支出信息
	 */
	private void ShowInfo(int intType) {
		String[] strInfos = null;
		ArrayAdapter<String> arrayAdapter = null;
		strType = "btnoutinfo";
		OutaccountDAO outaccountinfo = new OutaccountDAO(Outaccountinfo.this);
		List<Tb_outaccount> listoutinfos = outaccountinfo.getScrollData(0,
				(int) outaccountinfo.getCount());
		strInfos = new String[listoutinfos.size()];
		int i = 0;
		for (Tb_outaccount tb_outaccount : listoutinfos) {
			strInfos[i] = tb_outaccount.getid() + "|" + tb_outaccount.getType()
					+ " " + String.valueOf(tb_outaccount.getMoney()) + "元     "
					+ tb_outaccount.getTime();
			i++;
		}
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);
	}
	/**
	 * 重写onRestart方法
	 */
	@Override
	protected void onRestart() {
		super.onRestart();
		ShowInfo(R.id.btnoutinfo);// 显示支出信息
	}
}
