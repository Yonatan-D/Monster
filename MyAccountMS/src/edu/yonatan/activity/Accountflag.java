package edu.yonatan.activity;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.yonatan.dao.FlagDAO;
import edu.yonatan.model.Tb_flag;

public class Accountflag extends Activity {
	// 定义页面组件对象
	EditText txtFlag;
	Button btnflagSaveButton;
	Button btnflagCancelButton;
	/**
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.accountflag);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		txtFlag = (EditText) findViewById(R.id.txtFlag);
		btnflagSaveButton = (Button) findViewById(R.id.btnflagSave);
		btnflagCancelButton = (Button) findViewById(R.id.btnflagCancel);
		// 为保存按钮设置监听事件
		btnflagSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strFlag = txtFlag.getText().toString();
				if (!strFlag.isEmpty()) {// 判断获取的值不为空
					FlagDAO flagDAO = new FlagDAO(Accountflag.this);
					Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId() + 1, strFlag);
					flagDAO.add(tb_flag);
					Accountflag.this.finish();
					Toast.makeText(Accountflag.this, "〖新增便签〗数据添加成功！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(Accountflag.this, "请输入便签！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 为取消按钮设置监听事件
		btnflagCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Accountflag.this.finish();
			}
		});
	}
}
