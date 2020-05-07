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
	// ����ҳ���������
	EditText txtusername, txtlogin;
	Button btnlogin;
	CheckBox checkbox;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		// ����������ʾͼ��ͱ���
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowTitleEnabled(false);
		}
		// ��ȡҳ�����
		btnlogin = (Button) findViewById(R.id.btnLogin);
		txtlogin = (EditText) findViewById(R.id.txtLogin);
		txtusername = (EditText) findViewById(R.id.txtUserName);
		checkbox = (CheckBox) findViewById(R.id.checkBox);
		// ���˽�����͵�SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		String username = sp.getString("username", "");
		String rememberPassword = sp.getString("rememberPassword", "");
		txtusername.setText(username);
		if(rememberPassword.equals("true")) {
			checkbox.setChecked(true);
			String password = sp.getString("password", "password");
			txtlogin.setText(password);
		}
		// Ϊ��¼��ť���ü����¼�
		btnlogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String username = txtusername.getText().toString();
				String password = txtlogin.getText().toString();
				Intent intent = new Intent(Login.this, MainActivity.class);
				PwdDAO pwdDAO = new PwdDAO(Login.this);
				// �ж��Ƿ������뼰�ı����Ƿ�������
				if (pwdDAO.getCount() == 0) { 
					if(username.isEmpty() 
							&& password.isEmpty()){
						startActivity(intent);// ������Activity
					}else{
						Toast.makeText(Login.this, "�벻Ҫ����ֱ�ӵ�¼ϵͳ��",
								Toast.LENGTH_SHORT).show();
					}
				} else {
					if(username.isEmpty()) {
						Toast.makeText(Login.this, "�����������û�����",
								Toast.LENGTH_SHORT).show();
					} else {
						// �ж�����������Ƿ������ݿ��е�����һ��
						if(pwdDAO.find(username) == null) {
							Toast.makeText(Login.this, "�û������������",
									Toast.LENGTH_SHORT).show();
						} else {
							if (pwdDAO.find(username).getPassword()
								.equals(password)) {
								// ���˽�����͵�SharedPreferences
								SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
								Editor editor = sp.edit();// ���Editor����
								editor.putString("username", username);// �����û���
								editor.putString("password", password);// ��������
								// ����checkbox��Ϣ
								if(checkbox.isChecked()) { editor.putString("rememberPassword", "true"); }
								else { editor.putString("rememberPassword", "false"); }
								editor.commit();// ȷ���ύ
								// ������Activity
								startActivity(intent);
							} else {
								Toast.makeText(Login.this, "�û������������",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				}
				txtusername.setText("");// ����û����ı���
				txtlogin.setText("");// ��������ı���
			}
		});
	}
}
