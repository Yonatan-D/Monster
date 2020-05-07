package edu.yonatan.activity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.yonatan.dao.FlagDAO;
import edu.yonatan.model.Tb_flag;

public class FlagManage extends Activity {
	// 定义页面组件对象
	EditText txtFlag;
	Button btnEdit, btnDel;
	// 定义便签id
	String strid;
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flagmanage);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		txtFlag = (EditText) findViewById(R.id.txtFlagManage);
		btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);
		btnDel = (Button) findViewById(R.id.btnFlagManageDelete);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strid = bundle.getString(Showinfo.FLAG);
		final FlagDAO flagDAO = new FlagDAO(FlagManage.this);
		txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());
		// 为修改按钮设置监听事件
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Tb_flag tb_flag = new Tb_flag();
				tb_flag.setid(Integer.parseInt(strid));
				tb_flag.setFlag(txtFlag.getText().toString());
				flagDAO.update(tb_flag);
				FlagManage.this.finish();
				Toast.makeText(FlagManage.this, "〖便签数据〗修改成功！",
						Toast.LENGTH_SHORT).show();
			}
		});
		// 为删除按钮设置监听事件
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				flagDAO.detele(Integer.parseInt(strid));
				FlagManage.this.finish();
				Toast.makeText(FlagManage.this, "〖便签数据〗删除成功！",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
