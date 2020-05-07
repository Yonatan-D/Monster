package edu.yonatan.model;
/**
 * 支出信息实体类
 * @author LIN
 *
 */
public class Tb_outaccount
{
	private int _id;// 支出编号
	private double money;// 支出金额
	private String time;// 支出时间
	private String type;// 支出类别
	private String address;// 支出地点
	private String mark;// 支出备注
	// 默认构造函数
	public Tb_outaccount(){
		super();
	}
	// 定义有参构造函数
	public Tb_outaccount(int id, double money, String time, String type,
			String address, String mark) {
		super();
		this._id = id;
		this.money = money;
		this.time = time;
		this.type = type;
		this.address = address;
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

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getMark(){
		return mark;
	}

	public void setMark(String mark){
		this.mark = mark;
	}
}
