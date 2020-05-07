package edu.yonatan.model;
/**
 * 密码数据表实体类
 * @author LIN
 *
 */
public class Tb_pwd
{
	private String username;// 用户名
	private String password;// 用户密码
	// 默认构造函数
	public Tb_pwd(){
		super();
	}
	// 定义有参构造函数
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
