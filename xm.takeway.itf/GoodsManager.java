package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public interface GoodsManager {
	//添加商品
	public BeanGoodsDetails addGoods(int kind_id,String goods_name,double goods_price,double goods_sales,int goods_num) throws BaseException;
	//加载商品
	public List<BeanGoodsDetails> loadAll() throws BaseException;
	//用户按商家加载商品
	public List<BeanGoodsDetails> loadAll(BeanMerchant merchant) throws BaseException;
	//删除商品
	public void delGoods(BeanGoodsDetails goodsdetails) throws BaseException;
}
