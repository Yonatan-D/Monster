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
	// ����ҳ���������
	EditText txtusrname, txtpwd;
	Button btnSet, btnsetCancel;
	// ��¼�޸ĵ��û���������
	String username, password;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sysset);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		txtusrname = (EditText) findViewById(R.id.txtUsrname);
		txtpwd = (EditText) findViewById(R.id.txtPwd);
		btnSet = (Button) findViewById(R.id.btnSet);
		btnsetCancel = (Button) findViewById(R.id.btnsetCancel);
		// ���˽�����͵�SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		txtusrname.setText(username);
		txtpwd.setText(password);
		// Ϊ���ð�ť��Ӽ����¼�
		btnSet.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(txtusrname.getText().toString().isEmpty() || txtpwd.getText().toString().isEmpty()) {
					Toast.makeText(Sysset.this, "�û��������벻��Ϊ�գ�",
							Toast.LENGTH_SHORT).show();
					return;
				}
				PwdDAO pwdDAO = new PwdDAO(Sysset.this);
				Tb_pwd tb_pwd = new Tb_pwd(txtusrname.getText().toString(), txtpwd.getText().toString());
				if (pwdDAO.getCount() == 0) {// �ж����ݿ����Ƿ��Ѿ�����������
					pwdDAO.add(tb_pwd);// ����û�����
					SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("username", txtusrname.getText().toString());// �����û���
					editor.putString("password", txtpwd.getText().toString());// ��������
					editor.commit();// ȷ���ύ
				} else {
					pwdDAO.update(tb_pwd);// �޸��û�����
					SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
					Editor editor = sp.edit();
					editor.putString("username", txtusrname.getText().toString());
					editor.putString("password", txtpwd.getText().toString());
					editor.commit();
				}
				Toast.makeText(Sysset.this, "�����롽���óɹ���", Toast.LENGTH_SHORT)
						.show();
			}
		});
		// Ϊ���ذ�ť���ü����¼�
		btnsetCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Sysset.this.finish();
			}
		});
	}
}
