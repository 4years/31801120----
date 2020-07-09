package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsDetails;
import xm.takeway.model.BeanShoppingCar;
import xm.takeway.util.BaseException;

public interface ShoppingCarManager {
	//���ع��ﳵ��Ϣ
	public List<BeanShoppingCar> loadAll() throws BaseException;
	//���ﳵ������Ʒ
	public BeanShoppingCar addGoods(BeanGoodsDetails BGD,int num) throws BaseException;
	//ɾ�����ﳵ��Ʒ
	public void delGoodsFromShoppingCar(BeanShoppingCar shoppingCar) throws BaseException;
	//���ﳵ����
	public void settlementShoppingCar(int address_id) throws BaseException;
}
