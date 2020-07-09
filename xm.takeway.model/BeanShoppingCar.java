package xm.takeway.model;

public class BeanShoppingCar {
	public static final String[] tableShoppingCarTitles = {"序号","商品名","商家名","单价","数量"};
	private int order_id;
	private String goods_name;
	private String merchant_name;
	private double goods_price;
	private int num;
	
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getGoods_name() {
		return goods_name;
	}

	public void setGoods_name(String goods_name) {
		this.goods_name = goods_name;
	}

	public String getMerchant_name() {
		return merchant_name;
	}

	public void setMerchant_name(String merchant_name) {
		this.merchant_name = merchant_name;
	}

	public double getGoods_price() {
		return goods_price;
	}

	public void setGoods_price(double goods_price) {
		this.goods_price = goods_price;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getCell(int col) {
		if(col == 0)
			return String.valueOf(this.order_id);
		else if(col == 1)
			return this.goods_name;
		else if(col == 2)
			return this.merchant_name;
		else if(col == 3)
			return String.valueOf(this.goods_price);
		else if(col == 4)
			return String.valueOf(this.num);
		else
			return "";
	}
}
