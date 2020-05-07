package edu.yonatan.activity;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import edu.yonatan.dao.InaccountDAO;
import edu.yonatan.model.Tb_inaccount;

public class AddInaccount extends Activity {
	protected static final int DATE_DIALOG_ID = 0;// �������ڶԻ�����
	// ����ҳ���������
	EditText txtInMoney, txtInTime, txtInHandler, txtInMark;
	Spinner spInType;
	Button btnInSaveButton;
	Button btnInCancelButton;
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
		setContentView(R.layout.addinaccount);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);
		txtInTime = (EditText) findViewById(R.id.txtInTime);
		txtInHandler = (EditText) findViewById(R.id.txtInHandler);
		txtInMark = (EditText) findViewById(R.id.txtInMark);
		spInType = (Spinner) findViewById(R.id.spInType);
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);
		// Ϊʱ���ı������õ��������¼�
		txtInTime.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showDialog(DATE_DIALOG_ID);// ��ʾ����ѡ��Ի���
					}
				});
		// Ϊ���水ť���ü����¼�
		btnInSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strInMoney = txtInMoney.getText().toString();
				if (!strInMoney.isEmpty()) {// �жϽ�Ϊ��
					InaccountDAO inaccountDAO = new InaccountDAO(
							AddInaccount.this);
					Tb_inaccount tb_inaccount = new Tb_inaccount(
							inaccountDAO.getMaxId() + 1, Double
									.parseDouble(strInMoney), txtInTime
									.getText().toString(), spInType
									.getSelectedItem().toString(),
							txtInHandler.getText().toString(),
							txtInMark.getText().toString());
					inaccountDAO.add(tb_inaccount);
					AddInaccount.this.finish();
					Toast.makeText(AddInaccount.this, "���������롽������ӳɹ���",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddInaccount.this, "�����������",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// Ϊȡ����ť���ü����¼�
		btnInCancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						AddInaccount.this.finish();
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
	private DatePickerDialog.OnDateSetListener mDateSetListener = 
			new DatePickerDialog.OnDateSetListener() {
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
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
