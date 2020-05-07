package edu.yonatan.activity;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import edu.yonatan.dao.InaccountDAO;
import edu.yonatan.dao.OutaccountDAO;
import edu.yonatan.model.Tb_inaccount;
import edu.yonatan.model.Tb_outaccount;

public class InfoManage extends Activity {
	protected static final int DATE_DIALOG_ID = 0;// �������ڶԻ�����
	// ����ҳ���������
	TextView tvtitle, textView;
	EditText txtMoney, txtTime, txtHA, txtMark;
	Spinner spType;
	Button btnEdit, btnDel;
	String[] strInfos;// �����ַ�������
	String strid, strType;// ���������ַ����������ֱ�������¼��Ϣ��ź͹�������
	// �����ꡢ�¡���
	private int mYear;
	private int mMonth;
	private int mDay;
	// ���������֧����Dao����
	OutaccountDAO outaccountDAO;
	InaccountDAO inaccountDAO;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infomanage);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		tvtitle = (TextView) findViewById(R.id.inouttitle);
		textView = (TextView) findViewById(R.id.tvInOut);
		txtMoney = (EditText) findViewById(R.id.txtInOutMoney);
		txtTime = (EditText) findViewById(R.id.txtInOutTime);
		spType = (Spinner) findViewById(R.id.spInOutType);
		txtHA = (EditText) findViewById(R.id.txtInOut);
		txtMark = (EditText) findViewById(R.id.txtInOutMark);
		btnEdit = (Button) findViewById(R.id.btnInOutEdit);
		btnDel = (Button) findViewById(R.id.btnInOutDelete);
		// ��ȡ��ǰϵͳ����
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();// ��ʾ��ǰϵͳʱ��
		
		outaccountDAO = new OutaccountDAO(InfoManage.this);
		inaccountDAO = new InaccountDAO(InfoManage.this);

		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		strInfos = bundle.getStringArray(Showinfo.FLAG);
		strid = strInfos[0];
		strType = strInfos[1];
		if (strType.equals("btnoutinfo")){// ���������btnoutinfo
			tvtitle.setText("֧������");
			textView.setText("��  �㣺");
			Tb_outaccount tb_outaccount = outaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_outaccount.getMoney()));
			txtTime.setText(tb_outaccount.getTime());
			// �޸������б���
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
					this, R.array.outtype,android.R.layout.simple_dropdown_item_1line);
			spType.setAdapter(adapter); 
			setSpinnerDefaultValue(spType, tb_outaccount.getType());
			txtHA.setText(tb_outaccount.getAddress());
			txtMark.setText(tb_outaccount.getMark());
		} else if (strType.equals("btnininfo")){// ���������btnininfo
			tvtitle.setText("�������");
			textView.setText("�����");
			Tb_inaccount tb_inaccount = inaccountDAO.find(Integer
					.parseInt(strid));
			txtMoney.setText(String.valueOf(tb_inaccount.getMoney()));
			txtTime.setText(tb_inaccount.getTime());
			setSpinnerDefaultValue(spType, tb_inaccount.getType());
			txtHA.setText(tb_inaccount.getHandler());
			txtMark.setText(tb_inaccount.getMark());
		}
		// Ϊʱ���ı������õ��������¼�
		txtTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});
		// Ϊ�޸İ�ť���ü����¼�
		btnEdit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (strType.equals("btnoutinfo")){// �ж����������btnoutinfo
					Tb_outaccount tb_outaccount = new Tb_outaccount();
					tb_outaccount.setid(Integer.parseInt(strid));
					tb_outaccount.setMoney(Double.parseDouble(txtMoney
							.getText().toString()));
					tb_outaccount.setTime(txtTime.getText().toString());
					tb_outaccount.setType(spType.getSelectedItem().toString());
					tb_outaccount.setAddress(txtHA.getText().toString());
					tb_outaccount.setMark(txtMark.getText().toString());
					outaccountDAO.update(tb_outaccount);
				} else if (strType.equals("btnininfo")){// �ж����������btnininfo
					Tb_inaccount tb_inaccount = new Tb_inaccount();
					tb_inaccount.setid(Integer.parseInt(strid));
					tb_inaccount.setMoney(Double.parseDouble(txtMoney.getText()
							.toString()));
					tb_inaccount.setTime(txtTime.getText().toString());
					tb_inaccount.setType(spType.getSelectedItem().toString());
					tb_inaccount.setHandler(txtHA.getText().toString());
					tb_inaccount.setMark(txtMark.getText().toString());
					inaccountDAO.update(tb_inaccount);
				}
				InfoManage.this.finish();
				Toast.makeText(InfoManage.this, "�����ݡ��޸ĳɹ���", Toast.LENGTH_SHORT)
						.show();
			}
		});
		// Ϊɾ����ť���ü����¼�
		btnDel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (strType.equals("btnoutinfo")){// �ж����������btnoutinfo
					outaccountDAO.detele(Integer.parseInt(strid));
				} else if (strType.equals("btnininfo")){// �ж����������btnininfo
					inaccountDAO.detele(Integer.parseInt(strid));
				}
				InfoManage.this.finish();
				Toast.makeText(InfoManage.this, "�����ݡ�ɾ���ɹ���", Toast.LENGTH_SHORT)
						.show();
			}
		});
	}
	/**
	 * ��дonCreateDialog����
	 */
	@Override
	protected Dialog onCreateDialog(int id){
		switch (id) {
		case DATE_DIALOG_ID:// ��������ѡ��Ի���
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}
	/**
	 * ��ʾʱ��
	 */
	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDisplay();
		}
	};
	/**
	 * ��ʾ���õ�ʱ��
	 */
	private void updateDisplay() {
		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
	
    /**
     * spinner ����Ĭ��ֵ��Spinner
     * value ��Ҫ���õ�Ĭ��ֵ
     */
    private void setSpinnerDefaultValue(Spinner spinner, String value) {
        SpinnerAdapter apsAdapter = spinner.getAdapter();
        int size = apsAdapter.getCount();
        for (int i = 0; i < size; i++) {
            if (TextUtils.equals(value, apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i,true);
                break;
            }
        }
    }
}
