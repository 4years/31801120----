package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsKind;
import xm.takeway.model.BeanMoneyOff;
import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;

public interface RootManager {
	//管理员登陆
	public BeanRoot login(String name,String pwd) throws BaseException;
	//管理员添加满减方案
	public BeanMoneyOff addMoneyOffWay(double moneyOff_much,double moneyOff_OffMuch,String moneyOff_overlay) throws BaseException;
	//加载满减方案
	public List<BeanMoneyOff> moneyOffWayloadAll() throws BaseException;
	//添加商品类别
	public void addGoodsKind(String kindName) throws BaseException;
	//加载商品类别
	public List<BeanGoodsKind> goodsKindloadAll() throws BaseException;
}
