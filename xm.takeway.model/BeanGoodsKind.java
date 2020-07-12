package xm.takeway.model;

public class BeanGoodsKind {
	public static final String[] tableGoodsKindTitle = {"类别序号","类别名称","货种数量"};
	private int kind_id;
	private String kind_name;
	private int good_num;
	private int order_id;
	
	public int getKind_id() {
		return kind_id;
	}
	public void setKind_id(int kind_id) {
		this.kind_id = kind_id;
	}
	public String getKind_name() {
		return kind_name;
	}
	public void setKind_name(String kind_name) {
		this.kind_name = kind_name;
	}
	public int getGood_num() {
		return good_num;
	}
	public void setGood_num(int good_num) {
		this.good_num = good_num;
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
			return this.kind_name;
		else if(col == 2)
			return String.valueOf(this.good_num);
		else
			return "";
	}

}
