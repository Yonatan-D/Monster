package edu.yonatan.activity;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import edu.yonatan.dao.PwdDAO;

public class Login extends Activity {
	// 定义页面组件对象
	EditText txtusername, txtlogin;
	Button btnlogin;
	CheckBox checkbox;
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// 导航栏不显示图标和标题
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
		}
		// 获取页面组件
		btnlogin = (Button) findViewById(R.id.btnLogin);
		txtlogin = (EditText) findViewById(R.id.txtLogin);
		txtusername = (EditText) findViewById(R.id.txtUserName);
		checkbox = (CheckBox) findViewById(R.id.checkBox);
		// 获得私有类型的SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		String username = sp.getString("username", "");
		String rememberPassword = sp.getString("rememberPassword", "");
		txtusername.setText(username);
		if(rememberPassword.equals("true")) {
			checkbox.setChecked(true);
			String password = sp.getString("password", "password");
			txtlogin.setText(password);
		}
		// 为登录按钮设置监听事件
		btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String username = txtusername.getText().toString();
				String password = txtlogin.getText().toString();
				Intent intent = new Intent(Login.this, MainActivity.class);
				PwdDAO pwdDAO = new PwdDAO(Login.this);
				// 判断是否有密码及文本框是否有输入
				if (pwdDAO.getCount() == 0) { 
					if(username.isEmpty() 
							&& password.isEmpty()){
						startActivity(intent);// 启动主Activity
					}else{
						Toast.makeText(Login.this, "请不要输入直接登录系统！",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					if(username.isEmpty()) {
						Toast.makeText(Login.this, "请输入您的用户名！",
								Toast.LENGTH_SHORT).show();
					} else {
						// 判断输入的密码是否与数据库中的密码一致
						if(pwdDAO.find(username) == null) {
							Toast.makeText(Login.this, "用户名或密码错误！",
									Toast.LENGTH_SHORT).show();
						} else {
							if (pwdDAO.find(username).getPassword()
								.equals(password)) {
								// 获得私有类型的SharedPreferences
								SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
								Editor editor = sp.edit();// 获得Editor对象
								editor.putString("username", username);// 增加用户名
								editor.putString("password", password);// 增加密码
								// 更新checkbox信息
								if(checkbox.isChecked()) { editor.putString("rememberPassword", "true"); }
								else { editor.putString("rememberPassword", "false"); }
								editor.commit();// 确认提交
								// 启动主Activity
								startActivity(intent);
							} else {
								Toast.makeText(Login.this, "用户名或密码错误！",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
				txtusername.setText("");// 清空用户名文本框
				txtlogin.setText("");// 清空密码文本框
			}
		});
	}
}
