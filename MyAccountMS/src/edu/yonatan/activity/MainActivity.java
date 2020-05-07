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
	// ����ҳ���������
	GridView gvInfo;
	TextView tvUserName, tvSignCount, tvFlagCount, tvAccountCount;
	ImageButton btnAvatar;
	Button btnSign;
	// �����ꡢ�¡���
	private int mYear;
	private int mMonth;
	private int mDay;
	// �����ַ������飬�洢ϵͳ����
	String[] titles = new String[] { "����֧��", "��������", "�ҵ�֧��", "�ҵ�����", "���ݹ���", "ϵͳ����", "��֧��ǩ", "����", "�˳���¼" };
	// ����int���飬�洢���ܶ�Ӧ��ͼ��
	int[] images = new int[] { R.drawable.addoutaccount, R.drawable.addinaccount, R.drawable.outaccountinfo,
			R.drawable.inaccountinfo, R.drawable.showinfo, R.drawable.sysset, R.drawable.accountflag, R.drawable.help,
			R.drawable.exit };

	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowHomeEnabled(false);
		}
		// ��ȡҳ�����
		btnAvatar = (ImageButton) findViewById(R.id.btnAvatar);
		btnSign = (Button) findViewById(R.id.btnSign);
		tvSignCount = (TextView) findViewById(R.id.tvSignCount);
		// ���ض����û���Ϣ
		loadUserInfo();
		// Ϊͷ��ť��Ӽ����¼�
		btnAvatar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(MainActivity.this, "�ݲ�֧���޸�ͷ��", Toast.LENGTH_SHORT).show();
			}
		});
		// Ϊ�򿨰�ť��Ӽ����¼�
		btnSign.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ���˽�����͵�SharedPreferences
				SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
				String signcountStr = sp.getString("signcount", "0");
				String latestSignDate = sp.getString("latestSignDate", "0");
				// ��ȡ��ǰϵͳ����
				final Calendar c = Calendar.getInstance();
				mYear = c.get(Calendar.YEAR);
				mMonth = c.get(Calendar.MONTH);
				mDay = c.get(Calendar.DAY_OF_MONTH);
				StringBuilder nowDate = new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-")
						.append(mDay);
				// �ж��Ƿ���ϴ�����
				if (nowDate.toString().equals(latestSignDate)) {
					Toast.makeText(MainActivity.this, "�������Ѿ���", Toast.LENGTH_SHORT).show();
				} else {
					int signcount = Integer.parseInt(signcountStr) + 1;
					Editor editor = sp.edit();// ���Editor����
					editor.putString("signcount", "" + signcount);
					editor.putString("latestSignDate", "" + nowDate);
					editor.commit();
					tvSignCount.setText("" + signcount);// ���´򿨴���
					btnSign.setText("��");// �޸Ĵ򿨰�ť����
					Toast.makeText(MainActivity.this, "ǩ���ɹ�", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// �Ź����ܼ�
		gvInfo = (GridView) findViewById(R.id.gvInfo);// ��ȡ�����ļ��е�gvInfo���
		pictureAdapter adapter = new pictureAdapter(titles, images, this);// ����pictureAdapter����
		gvInfo.setAdapter(adapter);// ΪGridView��������Դ
		// ΪGridView��������¼�
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
	 * ��дonResume����
	 */
	@Override
	protected void onResume() {
		super.onResume();
		loadUserInfo(); // ˢ�¶����û���Ϣ
	}

	/**
	 * ���ض����û���Ϣ
	 */
	public void loadUserInfo() {
		// ��ȡ�û���Ϣ�����
		tvUserName = (TextView) findViewById(R.id.tvUserName);
		tvSignCount = (TextView) findViewById(R.id.tvSignCount);
		tvFlagCount = (TextView) findViewById(R.id.tvFlagCount);
		tvAccountCount = (TextView) findViewById(R.id.tvAccountCount);
		btnSign = (Button) findViewById(R.id.btnSign);
		// ���˽�����͵�SharedPreferences
		SharedPreferences sp = getSharedPreferences("userinfo", MODE_PRIVATE);
		String username = sp.getString("username", "");// ����û���
		String signcount = sp.getString("signcount", "0");
		String latestSignDate = sp.getString("latestSignDate", "0");
		tvUserName.setText(username);
		tvSignCount.setText(signcount);
		// ��ѯ���ݿ�
		FlagDAO flagDAO = new FlagDAO(MainActivity.this);
		long flagCount = flagDAO.getCount();// ��ñ�ǩ��
		tvFlagCount.setText("" + flagCount);
		InaccountDAO inaccountDAO = new InaccountDAO(MainActivity.this);
		long inCount = inaccountDAO.getCount();// ������������
		OutaccountDAO outaccountDAO = new OutaccountDAO(MainActivity.this);
		long outCount = outaccountDAO.getCount();// ���֧��������
		long accountCount = inCount + outCount;// ��ӵõ��ܼ�����
		tvAccountCount.setText("" + accountCount);
		// ��ȡ��ǰϵͳ����
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		StringBuilder nowDate = new StringBuilder().append(mYear).append("-").append(mMonth + 1).append("-")
				.append(mDay);
		// �޸Ĵ򿨰�ť�ڵ�����
		if (nowDate.toString().equals(latestSignDate)) {
			btnSign.setText("��");// �޸Ĵ򿨰�ť����
		}
	}
}

/**
 * ��������BaseAdapter������
 * 
 * @author LIN
 *
 */
class pictureAdapter extends BaseAdapter {
	private LayoutInflater inflater;
	private List<Picture> pictures;

	// ���幹�캯��
	public pictureAdapter(String[] titles, int[] images, Context context) {
		super();
		pictures = new ArrayList<Picture>();
		inflater = LayoutInflater.from(context);
		for (int i = 0; i < images.length; i++) {// ����ͼ������
			Picture picture = new Picture(titles[i], images[i]);// ʹ�ñ����ͼ������Picture����
			pictures.add(picture);// ��Picture������ӵ����ͼ�����
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
 * ����ViewHolder��
 * 
 * @author LIN
 *
 */
class ViewHolder {
	public TextView title;
	public ImageView image;
}

/**
 * ����Picture��
 * 
 * @author LIN
 *
 */
class Picture {
	private String title;
	private int imageId;

	// Ĭ�Ϲ��캯��
	public Picture() {
		super();
	}

	// �����вι��캯��
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
