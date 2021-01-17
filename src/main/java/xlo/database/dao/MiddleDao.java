package xlo.database.dao;

import xlo.database.dbcpool.ConnPool;

import java.sql.Connection;

/**
 * @author XiaoLOrange
 * @time 2020.10.16 BaseDao是可以广泛运用与各个场景的Dao,但是因此只具有一些基础功能，
 * 					因此在BaseDao的基础上进行了一次封装，但是降低了用户的自由度，连接只能选择连接池进行连接
 * @time 2020.12.01 改进了一些方法，使MiddleDao适应单例模式
 * @title
 */

public abstract class MiddleDao<T> extends PreBaseDao<T> {

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
