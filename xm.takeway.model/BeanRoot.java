package xm.takeway.model;

public class BeanRoot {
	public static BeanRoot currentLoginRoot = null;
	private String root_name;
	private String root_pwd;
	
	public String getRoot_pwd() {
		return root_pwd;
	}
	public void setRoot_pwd(String root_pwd) {
		this.root_pwd = root_pwd;
	}
	public String getRoot_name() {
		return root_name;
	}
	public void setRoot_name(String root_name) {
		this.root_name = root_name;
	}
	
}
