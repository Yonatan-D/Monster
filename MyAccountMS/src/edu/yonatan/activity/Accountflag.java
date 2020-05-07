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
	// ����ҳ���������
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
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		txtFlag = (EditText) findViewById(R.id.txtFlag);
		btnflagSaveButton = (Button) findViewById(R.id.btnflagSave);
		btnflagCancelButton = (Button) findViewById(R.id.btnflagCancel);
		// Ϊ���水ť���ü����¼�
		btnflagSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strFlag = txtFlag.getText().toString();
				if (!strFlag.isEmpty()) {// �жϻ�ȡ��ֵ��Ϊ��
					FlagDAO flagDAO = new FlagDAO(Accountflag.this);
					Tb_flag tb_flag = new Tb_flag(flagDAO.getMaxId() + 1, strFlag);
					flagDAO.add(tb_flag);
					Accountflag.this.finish();
					Toast.makeText(Accountflag.this, "��������ǩ��������ӳɹ���",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(Accountflag.this, "�������ǩ��",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// Ϊȡ����ť���ü����¼�
		btnflagCancelButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Accountflag.this.finish();
			}
		});
	}
}
