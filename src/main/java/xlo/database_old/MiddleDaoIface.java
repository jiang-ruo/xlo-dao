package xlo.database_old;

import java.sql.Connection;

public interface MiddleDaoIface {
	
	/**
	 * 获取一个连接
	 * @return
	 */
	public Connection getConn();

	/**
	 * 设置该Dao手动提交
	 * 但不会对传入的连接对象做任何操作
	 * @param conn
	 */
	public void AutoCommitFalse(Connection conn);

	/**
	 * 设置该Dao自动提交
	 */
	public void AutoCommitTrue();
	
}
