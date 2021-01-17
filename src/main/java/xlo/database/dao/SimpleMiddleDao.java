package xlo.database.dao;

import xlo.database.dbcpool.ConnPool;

import java.sql.Connection;

/**
 * @author XiaoLOrange
 * @time 2020.12.01 不支持单例
 * @title
 */

public abstract class SimpleMiddleDao<T> extends BaseDao<T> {

	private static ConnPool pool = null;
	public static void poolStart(String driver, String link, String user, String pwd){
		pool = new ConnPool(driver, link, user, pwd);
		pool.start();
	}

	public static void closePool(){
		pool.close();
	}

	/**
	 * 当调用不需要传入Connection的方法时，该方法会从getConn方法中获取一个连接
	 *
	 * @return
	 */
	@Override
	public Connection getConn() {
		return pool.getConn();
	}

	/**
	 * 调用完getConn方法后会调用closeConn方法关闭连接
	 *
	 * @param conn
	 * @return
	 */
	@Override
	public boolean closeConn(Connection conn) {
		pool.back(conn);
		return true;
	}

}
