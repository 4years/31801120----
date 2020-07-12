package xm.takeway.itf;

import java.util.List;

import xm.takeway.model.BeanOrderMessage;
import xm.takeway.util.BaseException;

public interface OrderManager {
	//���ص�ǰ����
	public List<BeanOrderMessage> NowloadAll() throws BaseException;
	//ɾ������
	public void DelOrder(BeanOrderMessage curOrder) throws BaseException;
	//������ʷ����
	public List<BeanOrderMessage> HistoryloadAll() throws BaseException;
}
