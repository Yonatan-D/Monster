package edu.yonatan.activity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import edu.yonatan.dao.PwdDAO;
import edu.yonatan.model.Tb_pwd;

public class Sysset extends Activity {
	// 定义页面组件对象
	EditText txtusrname, txtpwd;
	Button btnSet, btnsetCancel;
	// 记录修改的用户名和密码
	String username, password;
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysset);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		txtusrname = (EditText) findViewById(R.id.txtUsrname);
		txtpwd = (EditText) findViewById(R.id.txtPwd);
		btnSet = (Button) findViewById(R.id.btnSet);
		btnsetCancel = (Button) findViewById(R.id.btnsetCancel);
		// 获得私有类型的SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		txtusrname.setText(username);
		txtpwd.setText(password);
		// 为设置按钮添加监听事件
		btnSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(txtusrname.getText().toString().isEmpty() || txtpwd.getText().toString().isEmpty()) {
					Toast.makeText(Sysset.this, "用户名和密码不能为空！",
							Toast.LENGTH_SHORT).show();
					return;
				}
				PwdDAO pwdDAO = new PwdDAO(Sysset.this);
				Tb_pwd tb_pwd = new Tb_pwd(txtusrname.getText().toString(), txtpwd.getText().toString());
				if (pwdDAO.getCount() == 0) {// 判断数据库中是否已经设置了密码
					pwdDAO.add(tb_pwd);// 添加用户密码
					SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("username", txtusrname.getText().toString());// 增加用户名
					editor.putString("password", txtpwd.getText().toString());// 增加密码
					editor.commit();// 确认提交
				} else {
					pwdDAO.update(tb_pwd);// 修改用户密码
					SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("username", txtusrname.getText().toString());
					editor.putString("password", txtpwd.getText().toString());
					editor.commit();
				}
				Toast.makeText(Sysset.this, "〖密码〗设置成功！", Toast.LENGTH_SHORT)
						.show();
			}
		});
		// 为返回按钮设置监听事件
		btnsetCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Sysset.this.finish();
			}
		});
	}
}
