package edu.yonatan.activity;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class Help extends Activity {
	// ����ҳ���������
	EditText txtFlag;
	Button btnflagSaveButton;
	Button btnflagCancelButton;
	/**
	 * ��дonCreate����
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		WebView webview=(WebView)findViewById(R.id.webView1);//��ȡ���ֹ���������ӵ�WebView���
		//����һ���ַ�������������Ҫ��ʾ��HTML���ݷ����ڸù�������
		StringBuilder sb=new StringBuilder();
		sb.append("<div style='background:#ff6b6b; color:#fff; width:100%; height:200px; text-align:center; padding: 80px 0 0 0;'>");
		sb.append("<h1>�������ͨ</h1>");
		sb.append("<p>version: 1.0.1</p>");
		sb.append("<p>author: yonatan</p></div>");
		sb.append("<ul>");
		sb.append("<li>�޸����룺�����ϵͳ���á��޸ĵ�¼���롣�״�ʹ��Ĭ��û�����룬�뾡������û��������롣</li>");
		sb.append("<li>֧���������������֧�������֧����Ϣ��������ҵ�֧�����鿴���޸Ļ�ɾ��֧����Ϣ��</li>");
		sb.append("<li>�������������������롱���������Ϣ��������ҵ����롱�鿴���޸Ļ�ɾ��������Ϣ��</li>");
		sb.append("<li>��ǩ�����������֧��ǩ����ӱ�ǩ��Ϣ����������ݹ����鿴���޸Ļ�ɾ����ǩ��Ϣ�Լ���֧��������ͼ��</li>");
		sb.append("<li>�˳�ϵͳ��������˳�ϵͳ���˳������</li>");
		sb.append("</ul>");
		webview.loadDataWithBaseURL(null, sb.toString(),"text/html","utf-8",null);//��������
	}
}
