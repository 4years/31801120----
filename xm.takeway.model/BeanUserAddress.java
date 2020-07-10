package xm.takeway.model;

public class BeanUserAddress {
	public static final String[] tableAddressTitles = {"序号","省","市","区","详细地址","联系方式"};
	private int address_id;
	private String province;
	private String city;
	private String block;
	private String address;
	private String user_name;
	private String user_tel;
	private int order_id;
	
	public int getAddress_id() {
		return address_id;
	}
	public void setAddress_id(int address_id) {
		this.address_id = address_id;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_tel() {
		return user_tel;
	}
	public void setUser_tel(String user_tel) {
		this.user_tel = user_tel;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	
	public String getCell(int col) {
		if(col == 0)
			return String.valueOf(this.order_id);
		else if(col == 1)
			return this.province;
		else if(col == 2)
			return this.city;
		else if(col == 3)
			return this.block;
		else if(col == 4)
			return this.address;
		else if(col == 5)
			return this.user_tel;
		else
			return "";
		
	}
	
	
}
