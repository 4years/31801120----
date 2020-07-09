package xm.takeway.model;

import java.util.Date;

public class BeanCoupon {
	public static final String[] tableMerchantCouponTitles = {"优惠金额","需要集单数","开始生效日期","失效日期","使用范围","发放数量"};
	public static final String[] tableUserCouponTitles = {"优惠金额","需要集单数","开始生效日期","失效日期","使用范围","未领取数量"};
	private double moneyOff_much;
	private int consume_count;
	private Date sales_begin_date;
	private Date sales_end_date;
	private String useArea;
	private int order_id;
	private int coupon_num;
	private String add_user;
	private int coupon_id;
	private int recevied;
	private int unrecevied;
	
	public int getCoupon_num() {
		return coupon_num;
	}
	public void setCoupon_num(int coupon_num) {
		this.coupon_num = coupon_num;
	}
	public String getAdd_user() {
		return add_user;
	}
	public void setAdd_user(String add_user) {
		this.add_user = add_user;
	}
	public double getMoneyOff_much() {
		return moneyOff_much;
	}
	public void setMoneyOff_much(double moneyOff_much) {
		this.moneyOff_much = moneyOff_much;
	}
	public int getConsume_count() {
		return consume_count;
	}
	public void setConsume_count(int consume_count) {
		this.consume_count = consume_count;
	}
	public Date getSales_begin_date() {
		return sales_begin_date;
	}
	public void setSales_begin_date(Date sales_begin_date) {
		this.sales_begin_date = sales_begin_date;
	}
	public Date getSales_end_date() {
		return sales_end_date;
	}
	public void setSales_end_date(Date sales_end_date) {
		this.sales_end_date = sales_end_date;
	}
	public String getUseArea() {
		return useArea;
	}
	public void setUseArea(String useArea) {
		this.useArea = useArea;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getCoupon_id() {
		return coupon_id;
	}
	public void setCoupon_id(int coupon_id) {
		this.coupon_id = coupon_id;
	}
	public int getRecevied() {
		return recevied;
	}
	public void setRecevied(int recevied) {
		this.recevied = recevied;
	}
	
	public String getCell(int col) {
		if(col == 0)
			return String.valueOf(this.moneyOff_much);
		else if(col == 1)
			return String.valueOf(this.consume_count);
		else if(col == 2)
			return String.valueOf(this.sales_begin_date);
		else if(col == 3)
			return String.valueOf(this.sales_end_date);
		else if(col == 4)
			return this.useArea;
		else if(col == 5)
			return String.valueOf(this.coupon_num);
		else
			return "";
	}
	
	public String UsergetCell(int col) {
		this.unrecevied = this.coupon_num - this.recevied;
		if(col == 0)
			return String.valueOf(this.moneyOff_much);
		else if(col == 1)
			return String.valueOf(this.consume_count);
		else if(col == 2)
			return String.valueOf(this.sales_begin_date);
		else if(col == 3)
			return String.valueOf(this.sales_end_date);
		else if(col == 4)
			return this.useArea;
		else if(col == 5)
			return String.valueOf(this.unrecevied);
		else
			return "";
	}

	
}
