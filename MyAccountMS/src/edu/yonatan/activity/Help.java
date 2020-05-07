package edu.yonatan.activity;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Help extends Activity {
	// 定义页面组件对象
	EditText txtFlag;
	Button btnflagSaveButton;
	Button btnflagCancelButton;
	/**
	 * 重写onCreate方法
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		WebView webview=(WebView)findViewById(R.id.webView1);//获取布局管理器中添加的WebView组件
		//创建一个字符串构建器，将要显示的HTML内容放置在该构建器中
		StringBuilder sb=new StringBuilder();
		sb.append("<div style='background:#ff6b6b; color:#fff; width:100%; height:200px; text-align:center; padding: 80px 0 0 0;'>");
		sb.append("<h1>个人理财通</h1>");
		sb.append("<p>version: 1.0.1</p>");
		sb.append("<p>author: yonatan</p></div>");
		sb.append("<ul>");
		sb.append("<li>修改密码：点击“系统设置”修改登录密码。首次使用默认没有密码，请尽快更改用户名和密码。</li>");
		sb.append("<li>支出管理：点击“新增支出”添加支出信息；点击“我的支出”查看、修改或删除支出信息。</li>");
		sb.append("<li>收入管理：点击“新增收入”添加收入信息；点击“我的收入”查看、修改或删除收入信息。</li>");
		sb.append("<li>便签管理：点击“收支便签”添加便签信息；点击“数据管理”查看、修改或删除便签信息以及收支数据柱形图。</li>");
		sb.append("<li>退出系统：点击“退出系统”退出软件。</li>");
		sb.append("</ul>");
		webview.loadDataWithBaseURL(null, sb.toString(),"text/html","utf-8",null);//加载数据
	}
}
