package edu.yonatan.model;
/**
 * �������ݱ�ʵ����
 * @author LIN
 *
 */
public class Tb_pwd
{
	private String username;// �û���
	private String password;// �û�����
	// Ĭ�Ϲ��캯��
	public Tb_pwd(){
		super();
	}
	// �����вι��캯��
	public Tb_pwd(String username, String password){
		super();
		this.username = username;
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setPassword(String password){
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
