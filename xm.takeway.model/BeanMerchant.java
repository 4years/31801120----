package xm.takeway.model;

public class BeanMerchant {
	public static BeanMerchant currentLoginMerchant = null;
	public static final String[] tableMerchantTitles = {"序号","商家名","星级","人均消费","总销量"};
	private int merchant_id;
	private String merchant_name;
	private String merchant_pwd;
	private String merchant_rank;
	private double avg_consume;
	private int total_sales;
	
	public int getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(int merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_name() {
		return merchant_name;
	}
	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}
	public String getMerchant_rank() {
		return merchant_rank;
	}
	public void setMerchant_rank(String merchant_rank) {
		this.merchant_rank = merchant_rank;
	}
	public double getAvg_consume() {
		return avg_consume;
	}
	public void setAvg_consume(double avg_consume) {
		this.avg_consume = avg_consume;
	}
	public int getTotal_sales() {
		return total_sales;
	}
	public void setTotal_sales(int total_sales) {
		this.total_sales = total_sales;
	}
	public String getMerchant_pwd() {
		return merchant_pwd;
	}
	public void setMerchant_pwd(String merchant_pwd) {
		this.merchant_pwd = merchant_pwd;
	}
	
	public String getCell(int col) {
		if(col == 0)
			return String.valueOf(this.merchant_id);
		else if(col == 1)
			return this.merchant_name;
		else if(col == 2)
			return this.merchant_rank;
		else if(col == 3)	
			return String.valueOf(this.avg_consume);
		else if(col == 4)
			return String.valueOf(this.total_sales);
		else
			return "";
	}
}
