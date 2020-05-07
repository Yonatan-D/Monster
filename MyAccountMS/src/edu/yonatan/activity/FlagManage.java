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
	// ����ҳ���������
	EditText txtFlag;
	Button btnEdit, btnDel;
	// �����ǩid
	String strid;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flagmanage);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		txtFlag = (EditText) findViewById(R.id.txtFlagManage);
		btnEdit = (Button) findViewById(R.id.btnFlagManageEdit);
		btnDel = (Button) findViewById(R.id.btnFlagManageDelete);
		
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strid = bundle.getString(Showinfo.FLAG);
		final FlagDAO flagDAO = new FlagDAO(FlagManage.this);
		txtFlag.setText(flagDAO.find(Integer.parseInt(strid)).getFlag());
		// Ϊ�޸İ�ť���ü����¼�
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Tb_flag tb_flag = new Tb_flag();
				tb_flag.setid(Integer.parseInt(strid));
				tb_flag.setFlag(txtFlag.getText().toString());
				flagDAO.update(tb_flag);
				FlagManage.this.finish();
				Toast.makeText(FlagManage.this, "����ǩ���ݡ��޸ĳɹ���",
						Toast.LENGTH_SHORT).show();
			}
		});
		// Ϊɾ����ť���ü����¼�
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				flagDAO.detele(Integer.parseInt(strid));
				FlagManage.this.finish();
				Toast.makeText(FlagManage.this, "����ǩ���ݡ�ɾ���ɹ���",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
