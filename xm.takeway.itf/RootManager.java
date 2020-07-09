package xm.takeway.itf;

import xm.takeway.model.BeanRoot;
import xm.takeway.util.BaseException;

public interface RootManager {
	public BeanRoot login(String name,String pwd) throws BaseException;
}
