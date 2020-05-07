package edu.yonatan.activity;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import edu.yonatan.dao.OutaccountDAO;
import edu.yonatan.model.Tb_outaccount;

public class AddOutaccount extends Activity {
	protected static final int DATE_DIALOG_ID = 0;// �������ڶԻ�����
	// ����ҳ���������
	EditText txtMoney, txtTime, txtAddress, txtMark;
	Spinner spType;
	Button btnSaveButton;
	Button btnCancelButton;
	// �����ꡢ�¡���
	private int mYear;
	private int mMonth;
	private int mDay;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addoutaccount);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		txtTime = (EditText) findViewById(R.id.txtTime);
		txtAddress = (EditText) findViewById(R.id.txtAddress);
		txtMark = (EditText) findViewById(R.id.txtMark);
		spType = (Spinner) findViewById(R.id.spType);
		btnSaveButton = (Button) findViewById(R.id.btnSave);
		btnCancelButton = (Button) findViewById(R.id.btnCancel);
		// Ϊʱ���ı������õ��������¼�
		txtTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
			}
		});
		// Ϊ���水ť���ü����¼�
		btnSaveButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String strMoney = txtMoney.getText().toString();
						if (!strMoney.isEmpty()) {// �жϽ�Ϊ��
							OutaccountDAO outaccountDAO = new OutaccountDAO(
									AddOutaccount.this);
							Tb_outaccount tb_outaccount = new Tb_outaccount(
									outaccountDAO.getMaxId() + 1, Double
											.parseDouble(strMoney), txtTime
											.getText().toString(), spType
											.getSelectedItem().toString(),
									txtAddress.getText().toString(), txtMark
											.getText().toString());
							outaccountDAO.add(tb_outaccount);
							AddOutaccount.this.finish();
							Toast.makeText(AddOutaccount.this, "������֧����������ӳɹ���",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddOutaccount.this, "������֧����",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		// Ϊȡ����ť���ü����¼�
		btnCancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						AddOutaccount.this.finish();
					}
				});
		// ��ȡ��ǰϵͳ����
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();// ��ʾ��ǰϵͳʱ��
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
	 * 
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
}
