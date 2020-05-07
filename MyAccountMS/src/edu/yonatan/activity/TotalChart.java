package edu.yonatan.activity;
import java.util.Map;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import edu.yonatan.dao.InaccountDAO;
import edu.yonatan.dao.OutaccountDAO;

public class TotalChart extends Activity {
	private float[] money=new float[]{600,1000,600,300,1500};	//�������Ĭ��ֵ
	private int[] color=new int[]{Color.rgb(95, 184, 120)
								,Color.rgb(255, 184, 0)
								,Color.rgb(255, 87, 34)
								,Color.rgb(1, 170, 237)
								,Color.rgb(47, 64, 86)};	//������ɫ
	
	private final int WIDTH = 90;	//���͵Ŀ��
	private final int OFFSET = 90;	//���
	private int x =120;	//���x
	private int y =1200;	//�յ�y
	private int height=800;	//�߶�
	String[] type=null;		//��������
	private String passType="";	//��¼��������Ϣ����֧����Ϣ
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chart);
		// ����������ʾͼ��
		ActionBar actionBar = getActionBar();
		if(actionBar!=null){
			actionBar.setDisplayShowHomeEnabled(false);
		}
		
		Intent intent=getIntent();
		Bundle bundle=intent.getExtras();
		passType=bundle.getString("passType");
		Resources res=getResources();
		if("outinfo".equals(passType)){
			type=res.getStringArray(R.array.outtype);	//��ȡ֧����������
			
		}else if("ininfo".equals(passType)){
			type=res.getStringArray(R.array.intype);	//��ȡ������������
		}
		FrameLayout ll=(FrameLayout)findViewById(R.id.canvas);//��ȡ�����ļ�����ӵ�֡���ֹ�����
		ll.addView(new MyView(this));				//���Զ����MyView��ͼ��ӵ�֡���ֹ�������
		
	}
	public class MyView extends View{
		public MyView(Context context) {
			super(context);
		}
		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			canvas.drawColor(Color.WHITE);					//ָ�������ı���ɫΪ��ɫ
			Paint paint=new Paint();						//��������Ĭ�����õĻ���
			paint.setAntiAlias(true);						//ʹ�ÿ���ݹ���
			/***********����������**********************/
			paint.setStrokeWidth(1);						//���ñʴ��Ŀ��
			paint.setColor(Color.BLACK);		//���ñʴ�����ɫ
			canvas.drawLine(100, 1200, 1000, 1200, paint);	//��
			canvas.drawLine(100, 250, 100, 1200, paint);	//��
			/******************************************/
			/***********��������**********************/
			paint.setStyle(Style.FILL);					//���������ʽΪ���
			int left=0;	//ÿ�����͵����X����
			money=getMoney(passType);						//�»�ȡ�Ľ��******************
			float max=maxMoney(money);
			for(int i=0;i<money.length;i++){
				paint.setColor(color[i]);		//���ñʴ�����ɫ
				left=x+i*(OFFSET+WIDTH);	//����ÿ���������X����
				canvas.drawRect(left, y-height/max*money[i], left+WIDTH, y, paint);	
			}
			/******************************************/
			/***********��������Ŀ̶�**********************/
			paint.setColor(Color.BLACK);		//���ñʴ�����ɫ
			int tempY=0;
			for(int i=0;i<11;i++){
				tempY=y-height+height/10*i+1;
				canvas.drawLine(47,tempY , 50, tempY, paint);
				paint.setTextSize(32);	//���������С
				canvas.drawText(String.valueOf((int)(max/10*(10-i))), 15,tempY+5, paint);	//����������ע	
			}
			/******************************************/
			/***********����˵������**********************/
			paint.setColor(Color.BLACK);		//���ñʴ�����ɫ
			paint.setTextSize(40);	//���������С
			/******************���Ʊ���*********************************/
			if("outinfo".equals(passType)){
				canvas.drawText("�������ͨ��֧��ͳ��ͼ", 40,85, paint);	//���Ʊ���
			}else if("ininfo".equals(passType)){
				canvas.drawText("�������ͨ������ͳ��ͼ", 40,85, paint);	//���Ʊ���
			}	
			/***********************************************************/
			paint.setTextSize(32);	//���������С

			String str_type="";
			for(int i=0;i<type.length;i++){
				str_type+=type[i]+"               ";
			}
			canvas.drawText(str_type, 120,1232, paint);	//���ƺ�����ע
		}
	}
	//���������
	float maxMoney(float[] money){
		float max=money[0];	//����һ������Ԫ�ظ�ֵ������max
		for(int i=0;i<money.length-1;i++){
			if(max<money[i+1]){
				max=money[i+1];		//����max
			}
		}
		return max;
	}
	//��ȡ��֧����
	float[] getMoney(String flagType){
		Map mapMoney=null;
		System.out.println(flagType);
		if("ininfo".equals(flagType)){
			InaccountDAO inaccountinfo = new InaccountDAO(TotalChart.this);// ����TotalChart����
			mapMoney=inaccountinfo.getTotal();  //��ȡ���������Ϣ			
		}else if("outinfo".equals(flagType)){
			OutaccountDAO outaccountinfo = new OutaccountDAO(TotalChart.this);// ����TotalChart����
			mapMoney=outaccountinfo.getTotal();	//��ȡ֧��������Ϣ
		}	
		int size=type.length;
		float[] money1=new float[size];
		for(int i=0;i<size;i++){
			money1[i]=( mapMoney.get(type[i])!=null?((Float) mapMoney.get(type[i])):0);
		}
		return money1;
	}
}
