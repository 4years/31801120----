package xm.takeway.model;

import java.util.Date;

public class BeanKnight {
	public static BeanKnight currentLoginKnight = null;
	private String knight_name;
	private String knight_pwd;
	private Date knight_hiredate;
	private String knight_rank;
	
	public String getKnight_name() {
		return knight_name;
	}
	public void setKnight_name(String knight_name) {
		this.knight_name = knight_name;
	}
	public Date getKnight_hiredate() {
		return knight_hiredate;
	}
	public void setKnight_hiredate(Date knight_hiredate) {
		this.knight_hiredate = knight_hiredate;
	}
	public String getKnight_rank() {
		return knight_rank;
	}
	public void setKnight_rank(String knight_rank) {
		this.knight_rank = knight_rank;
	}
	public String getKnight_pwd() {
		return knight_pwd;
	}
	public void setKnight_pwd(String knight_pwd) {
		this.knight_pwd = knight_pwd;
	}
	
}
