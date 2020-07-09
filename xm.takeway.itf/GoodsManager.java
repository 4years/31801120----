package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanMerchant;
import xm.takeway.util.BaseException;

public interface GoodsManager {
	//�����Ʒ
	public BeanGoodsDetails addGoods(int kind_id,String goods_name,double goods_price,double goods_sales,int goods_num) throws BaseException;
	//�̼Ҽ�����Ʒ
	public List<BeanGoodsDetails> loadAll() throws BaseException;
	//�û����̼Ҽ�����Ʒ
	public List<BeanGoodsDetails> loadAll(BeanMerchant merchant) throws BaseException;
	//ɾ����Ʒ
	public void delGoods(BeanGoodsDetails goodsdetails) throws BaseException;
}
