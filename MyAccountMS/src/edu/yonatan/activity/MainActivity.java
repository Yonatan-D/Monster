package edu.yonatan.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.yonatan.dao.FlagDAO;
import edu.yonatan.dao.InaccountDAO;
import edu.yonatan.dao.OutaccountDAO;

public class MainActivity extends Activity {
	// 定义页面组件对象
	GridView gvInfo;
	TextView tvUserName, tvSignCount, tvFlagCount, tvAccountCount;
	ImageButton btnAvatar;
	Button btnSign;
	// 定义年、月、日
	private int mYear;
	private int mMonth;
	private int mDay;
	// 定义字符串数组，存储系统功能
	String[] titles = new String[] { "新增支出", "新增收入", "我的支出", "我的收入", "数据管理", "系统设置", "收支便签", "帮助", "退出登录" };
	// 定义int数组，存储功能对应的图标
	int[] images = new int[] { R.drawable.addoutaccount, R.drawable.addinaccount, R.drawable.outaccountinfo,
			R.drawable.inaccountinfo, R.drawable.showinfo, R.drawable.sysset, R.drawable.accountflag, R.drawable.help,
			R.drawable.exit };

	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 导航栏不显示图标
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// 获取页面组件
		btnAvatar = (ImageButton) findViewById(R.id.btnAvatar);
		btnSign = (Button) findViewById(R.id.btnSign);
		tvSignCount = (TextView) findViewById(R.id.tvSignCount);
		// 加载顶栏用户信息
		loadUserInfo();
		// 为头像按钮添加监听事件
		btnAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "暂不支持修改头像", Toast.LENGTH_SHORT).show();
			}
		});
		// 为打卡按钮添加监听事件
		btnSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 获得私有类型的SharedPreferences
				SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
				String signcountStr = sp.getString("signcount", "0");
				String latestSignDate = sp.getString("latestSignDate", "0");
				// 获取当前系统日期
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);
				StringBuilder nowDate = new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-")
						.append(mDay);
				// 判断是否符合打卡条件
				if (nowDate.toString().equals(latestSignDate)) {
					Toast.makeText(MainActivity.this, "您今天已经打卡", Toast.LENGTH_SHORT).show();
				} else {
					int signcount = Integer.parseInt(signcountStr) + 1;
					Editor editor = sp.edit();// 获得Editor对象
					editor.putString("signcount", "" + signcount);
					editor.putString("latestSignDate", "" + nowDate);
					editor.commit();
					tvSignCount.setText("" + signcount);// 更新打卡次数
					btnSign.setText("√");// 修改打卡按钮文字
					Toast.makeText(MainActivity.this, "签到成功", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 九宫格功能键
		gvInfo = (GridView) findViewById(R.id.gvInfo);// 获取布局文件中的gvInfo组件
		pictureAdapter adapter = new pictureAdapter(titles, images, this);// 创建pictureAdapter对象
		gvInfo.setAdapter(adapter);// 为GridView设置数据源
		// 为GridView设置项单击事件
		gvInfo.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				Intent intent = null;
				switch (arg2) {
				case 0:
					intent = new Intent(MainActivity.this, AddOutaccount.class);
					startActivity(intent);
					break;
				case 1:
					intent = new Intent(MainActivity.this, AddInaccount.class);
					startActivity(intent);
					break;
				case 2:
					intent = new Intent(MainActivity.this, Outaccountinfo.class);
					startActivity(intent);
					break;
				case 3:
					intent = new Intent(MainActivity.this, Inaccountinfo.class);
					startActivity(intent);
					break;
				case 4:
					intent = new Intent(MainActivity.this, Showinfo.class);
					startActivity(intent);
					break;
				case 5:
					intent = new Intent(MainActivity.this, Sysset.class);
					startActivity(intent);
					break;
				case 6:
					intent = new Intent(MainActivity.this, Accountflag.class);
					startActivity(intent);
					break;
				case 7:
					intent = new Intent(MainActivity.this, Help.class);
					startActivity(intent);
					break;
				case 8:
					finish();
				}
			}
		});
	}

	/**
	 * 重写onResume方法
	 */
	@Override
	protected void onResume() {
		super.onResume();
		loadUserInfo(); // 刷新顶栏用户信息
	}

	/**
	 * 加载顶栏用户信息
	 */
	public void loadUserInfo() {
		// 获取用户信息栏组件
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvSignCount = (TextView) findViewById(R.id.tvSignCount);
		tvFlagCount = (TextView) findViewById(R.id.tvFlagCount);
		tvAccountCount = (TextView) findViewById(R.id.tvAccountCount);
		btnSign = (Button) findViewById(R.id.btnSign);
		// 获得私有类型的SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		String username = sp.getString("username", "");// 获得用户名
		String signcount = sp.getString("signcount", "0");
		String latestSignDate = sp.getString("latestSignDate", "0");
		tvUserName.setText(username);
		tvSignCount.setText(signcount);
		// 查询数据库
		FlagDAO flagDAO = new FlagDAO(MainActivity.this);
		long flagCount = flagDAO.getCount();// 获得便签数
		tvFlagCount.setText("" + flagCount);
		InaccountDAO inaccountDAO = new InaccountDAO(MainActivity.this);
		long inCount = inaccountDAO.getCount();// 获得收入记账数
		OutaccountDAO outaccountDAO = new OutaccountDAO(MainActivity.this);
		long outCount = outaccountDAO.getCount();// 获得支出记账数
		long accountCount = inCount + outCount;// 相加得到总记账数
		tvAccountCount.setText("" + accountCount);
		// 获取当前系统日期
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		StringBuilder nowDate = new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-")
				.append(mDay);
		// 修改打卡按钮内的文字
		if (nowDate.toString().equals(latestSignDate)) {
			btnSign.setText("√");// 修改打卡按钮文字
		}
	}
}

/**
 * 创建基于BaseAdapter的子类
 * 
 * @author LIN
 *
 */
class pictureAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Picture> pictures;

	// 定义构造函数
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {// 遍历图像数组
			Picture picture = new Picture(titles[i], images[i]);// 使用标题和图像生成Picture对象
			pictures.add(picture);// 将Picture对象添加到泛型集合中
		}
	}

	@Override
	public int getCount() {
		if (null != pictures) {
			return pictures.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int arg0) {
		return pictures.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ViewHolder viewHolder;
		if (arg1 == null) {
			arg1 = inflater.inflate(R.layout.gvitem, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) arg1.findViewById(R.id.ItemTitle);
			viewHolder.image = (ImageView) arg1.findViewById(R.id.ItemImage);
			arg1.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) arg1.getTag();
		}
		viewHolder.title.setText(pictures.get(arg0).getTitle());
		viewHolder.image.setImageResource(pictures.get(arg0).getImageId());
		return arg1;
	}
}

/**
 * 创建ViewHolder类
 * 
 * @author LIN
 *
 */
class ViewHolder {
	public TextView title;
	public ImageView image;
}

/**
 * 创建Picture类
 * 
 * @author LIN
 *
 */
class Picture {
	private String title;
	private int imageId;

	// 默认构造函数
	public Picture() {
		super();
	}

	// 定义有参构造函数
	public Picture(String title, int imageId) {
		super();
		this.title = title;
		this.imageId = imageId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getImageId() {
		return imageId;
	}

	public void setimageId(int imageId) {
		this.imageId = imageId;
	}
}
