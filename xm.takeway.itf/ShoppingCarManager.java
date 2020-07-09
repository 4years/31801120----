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
	//删除购物车商品
	public void delGoodsFromShoppingCar(BeanShoppingCar shoppingCar) throws BaseException;
	//购物车结算
	public void settlementShoppingCar(int address_id) throws BaseException;
}
