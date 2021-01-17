package xlo.database.dao;

import java.sql.Connection;

public interface MiddleDaoIface {
	
	/**
	 * 获取一个连接
	 * @return
	 */
	Connection getConn();

	/**
	 * 关闭连接
	 */
	boolean closeConn(Connection conn);
	
}
