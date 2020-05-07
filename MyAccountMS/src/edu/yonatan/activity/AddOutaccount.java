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
	protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
	// 定义页面组件对象
	EditText txtMoney, txtTime, txtAddress, txtMark;
	Spinner spType;
	Button btnSaveButton;
	Button btnCancelButton;
	// 定义年、月、日
	private int mYear;
	private int mMonth;
	private int mDay;
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addoutaccount);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		txtMoney = (EditText) findViewById(R.id.txtMoney);
		txtTime = (EditText) findViewById(R.id.txtTime);
		txtAddress = (EditText) findViewById(R.id.txtAddress);
		txtMark = (EditText) findViewById(R.id.txtMark);
		spType = (Spinner) findViewById(R.id.spType);
		btnSaveButton = (Button) findViewById(R.id.btnSave);
		btnCancelButton = (Button) findViewById(R.id.btnCancel);
		// 为时间文本框设置单击监听事件
		txtTime.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
			}
		});
		// 为保存按钮设置监听事件
		btnSaveButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						String strMoney = txtMoney.getText().toString();
						if (!strMoney.isEmpty()) {// 判断金额不为空
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
							Toast.makeText(AddOutaccount.this, "〖新增支出〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(AddOutaccount.this, "请输入支出金额！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		// 为取消按钮设置监听事件
		btnCancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						AddOutaccount.this.finish();
					}
				});
		// 获取当前系统日期
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDisplay();// 显示当前系统时间
	}
	/**
	 * 重写onCreateDialog方法
	 */
	@Override
	protected Dialog onCreateDialog(int id){
		switch (id) {
		case DATE_DIALOG_ID:// 弹出日期选择对话框
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
	 * 显示设置的时间
	 */
	private void updateDisplay() {
		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
