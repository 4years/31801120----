package xm.takeway.model;

import java.util.Date;

public class BeanUser {
	public static BeanUser currentLoginUser = null;
	public static Boolean isVip = false;
	private int user_id;
	private String user_name;
	private String user_sex;
	private String user_pwd;
	private String user_tel;
	private String user_email;
	private String user_city;
	private Date user_regdate;
	private String user_vip;
	private Date user_vipDeadLine;
	
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_sex() {
		return user_sex;
	}
	public void setUser_sex(String user_sex) {
		this.user_sex = user_sex;
	}
	public String getUser_pwd() {
		return user_pwd;
	}
	public void setUser_pwd(String user_pwd) {
		this.user_pwd = user_pwd;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_city() {
		return user_city;
	}
	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}
	public Date getUser_regdate() {
		return user_regdate;
	}
	public void setUser_regdate(Date user_regdate) {
		this.user_regdate = user_regdate;
	}
	public String getUser_vip() {
		return user_vip;
	}
	public void setUser_vip(String user_vip) {
		this.user_vip = user_vip;
	}
	public Date getUser_vipDeadLine() {
		return user_vipDeadLine;
	}
	public void setUser_vipDeadLine(Date user_vipDeadLine) {
		this.user_vipDeadLine = user_vipDeadLine;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
}
