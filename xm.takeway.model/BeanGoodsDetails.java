package xm.takeway.model;

public class BeanGoodsDetails {
	public static final String[] tableGoodsTitles={"序号","商品名","所属商家","商品类别","商品价格","会员价格","数量"};
	
	private int goods_id;
	private int kind_id;
	private String merchant_Name;
	private String goods_name;
	private double goods_price;
	private double goods_sales;
	private int goods_num;
	private int order_id;
	
	public int getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(int goods_id) {
		this.goods_id = goods_id;
	}
	public int getKind_id() {
		return kind_id;
	}
	public void setKind_id(int kind_id) {
		this.kind_id = kind_id;
	}
	public String getGoods_name() {
		return goods_name;
	}
	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}
	public double getGoods_price() {
		return goods_price;
	}
	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}
	public double getGoods_sales() {
		return goods_sales;
	}
	public void setGoods_sales(double goods_sales) {
		this.goods_sales = goods_sales;
	}
	public String getMerchant_Name() {
		return merchant_Name;
	}
	public void setMerchant_Name(String merchant_Name) {
		this.merchant_Name = merchant_Name;
	}
	public int getGoods_num() {
		return goods_num;
	}
	public void setGoods_num(int goods_num) {
		this.goods_num = goods_num;
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
			return this.goods_name;
		else if(col == 2)
			return this.merchant_Name;
		else if(col == 3)	
			return String.valueOf(this.kind_id);
		else if(col == 4)
			return String.valueOf(this.goods_price);
		else if(col == 5)
			return String.valueOf(this.goods_sales);
		else if(col == 6)
			return String.valueOf(this.goods_num);
		else
			return "";
	}
	

	
}
