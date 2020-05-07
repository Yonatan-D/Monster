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
	protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
	// 定义页面组件对象
	EditText txtInMoney, txtInTime, txtInHandler, txtInMark;
	Spinner spInType;
	Button btnInSaveButton;
	Button btnInCancelButton;
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
		setContentView(R.layout.addinaccount);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);
		txtInTime = (EditText) findViewById(R.id.txtInTime);
		txtInHandler = (EditText) findViewById(R.id.txtInHandler);
		txtInMark = (EditText) findViewById(R.id.txtInMark);
		spInType = (Spinner) findViewById(R.id.spInType);
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);
		// 为时间文本框设置单击监听事件
		txtInTime.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
					}
				});
		// 为保存按钮设置监听事件
		btnInSaveButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String strInMoney = txtInMoney.getText().toString();
				if (!strInMoney.isEmpty()) {// 判断金额不为空
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
					Toast.makeText(AddInaccount.this, "〖新增收入〗数据添加成功！",
							Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(AddInaccount.this, "请输入收入金额！",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 为取消按钮设置监听事件
		btnInCancelButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						AddInaccount.this.finish();
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
	 * 显示设置的时间
	 */
	private void updateDisplay() {
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}
}
