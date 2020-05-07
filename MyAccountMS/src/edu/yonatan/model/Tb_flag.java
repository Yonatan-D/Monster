package edu.yonatan.model;
/**
 * 便签信息实体类
 * @author LIN
 *
 */
public class Tb_flag
{
	private int _id;// 便签编号
	private String flag;// 便签信息
	// 默认构造函数
	public Tb_flag(){
		super();
	}
	// 定义有参构造函数
	public Tb_flag(int id, String flag) {
		super();
		this._id = id;
		this.flag = flag;
	}

	public int getid(){
		return _id;
	}

	public void setid(int id){
		this._id = id;
	}

	public String getFlag(){
		return flag;
	}

	public void setFlag(String flag){
		this.flag = flag;
	}
}