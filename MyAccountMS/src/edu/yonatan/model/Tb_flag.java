package edu.yonatan.model;
/**
 * ��ǩ��Ϣʵ����
 * @author LIN
 *
 */
public class Tb_flag
{
	private int _id;// ��ǩ���
	private String flag;// ��ǩ��Ϣ
	// Ĭ�Ϲ��캯��
	public Tb_flag(){
		super();
	}
	// �����вι��캯��
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