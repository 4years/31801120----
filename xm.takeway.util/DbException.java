package xm.takeway.util;

public class DbException extends BaseException {
	public DbException(java.lang.Throwable e) {
		super("���ݿ��������" + e.getMessage());
	}
}
