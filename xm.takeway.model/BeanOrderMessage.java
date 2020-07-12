package xm.takeway.model;

public class BeanOrderMessage {
	public static final String[] tableOrderMessageTitles = {"�������","�̼���","������","������","����״̬"};
	private int order_id;
	private String merchant_name;
	private String knight_name;
	private double origin_money;
	private double real_money;
	private String order_state;
	
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getKnight_name() {
		return knight_name;
	}
	public void setKnight_name(String knight_name) {
		this.knight_name = knight_name;
	}
	public double getOrigin_money() {
		return origin_money;
	}
	public void setOrigin_money(double origin_money) {
		this.origin_money = origin_money;
	}
	public double getReal_money() {
		return real_money;
	}
	public void setReal_money(double real_money) {
		this.real_money = real_money;
	}
	public String getOrder_state() {
		return order_state;
	}
	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}
	
	public String getCell(int col) {
		if(col == 0)
			return String.valueOf(this.order_id);
		else if(col == 1)
			return this.merchant_name;
		else if(col == 2)
			return this.knight_name;
		else if(col == 3)
			return String.valueOf(this.real_money);
		else if(col == 4)
			return this.order_state;
		else
			return "";
	}
	
}
