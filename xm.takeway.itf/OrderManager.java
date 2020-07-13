package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;

public interface OrderManager {
	//加载当前订单
	public List<BeanOrderMessage> NowloadAll() throws BaseException;
	//删除订单
	public void DelOrder(BeanOrderMessage curOrder) throws BaseException;
	//加载历史订单
	public List<BeanOrderMessage> HistoryloadAll() throws BaseException;
	//骑手接单界面显示
	public List<BeanOrderMessage> KnightloadAll() throws BaseException;
	//骑手已接到订单显示
	public List<BeanOrderMessage> KnightGetloadAll() throws BaseException;
}
