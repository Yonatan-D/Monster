package edu.yonatan.model;
/**
 * 收入信息实体类
 * @author LIN
 *
 */
public class Tb_inaccount
{
	private int _id;// 收入编号
	private double money;// 收入金额
	private String time;// 收入时间
	private String type;// 收入类别
	private String handler;// 收入付款方
	private String mark;// 收入备注
	// 默认构造函数
	public Tb_inaccount(){
		super();
	}
	// 定义有参构造函数
	public Tb_inaccount(int id, double money, String time, String type,
			String handler, String mark) {
		super();
		this._id = id;
		this.money = money;
		this.time = time;
		this.type = type;
		this.handler = handler;
		this.mark = mark;
	}

	public int getid(){
		return _id;
	}

	public void setid(int id){
		this._id = id;
	}

	public double getMoney(){
		return money;
	}

	public void setMoney(double money){
		this.money = money;
	}

	public String getTime(){
		return time;
	}

	public void setTime(String time){
		this.time = time;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getHandler(){
		return handler;
	}

	public void setHandler(String handler){
		this.handler = handler;
	}

	public String getMark(){
		return mark;
	}

	public void setMark(String mark){
		this.mark = mark;
	}
}
