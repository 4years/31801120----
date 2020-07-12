package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanGoodsKind;
import xm.takeway.model.BeanMoneyOff;
import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;

public interface RootManager {
	//����Ա��½
	public BeanRoot login(String name,String pwd) throws BaseException;
	//����Ա�����������
	public BeanMoneyOff addMoneyOffWay(double moneyOff_much,double moneyOff_OffMuch,String moneyOff_overlay) throws BaseException;
	//������������
	public List<BeanMoneyOff> moneyOffWayloadAll() throws BaseException;
	//�����Ʒ���
	public void addGoodsKind(String kindName) throws BaseException;
	//������Ʒ���
	public List<BeanGoodsKind> goodsKindloadAll() throws BaseException;
}
