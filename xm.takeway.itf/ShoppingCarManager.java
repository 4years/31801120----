package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanShoppingCar;
import xm.takeway.util.BaseException;

public interface ShoppingCarManager {
	//加载购物车信息
	public List<BeanShoppingCar> loadAll() throws BaseException;
	//向购物车加入商品
	public BeanShoppingCar addGoods(BeanGoodsDetails BGD,int num) throws BaseException;
}
