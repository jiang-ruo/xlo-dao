package xlo.adapter;

import xlo.database.dao.MiddleDao;
import xlo.database.dao.SimpleMiddleDao;
import xlo.database_old.MiddleDaoIface;

import java.sql.Connection;

/**
 * @author XiaoLOrange
 * @time 2020.12.03
 * @title 为了兼容旧版本的UsualDao而暂时使用的适配器，等到全部更新为新版本后可以取消
 */

public class MiddleDaoAdapter extends MiddleDao {

	private Connection conn;

	public void setConn(Connection conn){
		this.conn = conn;
	}

	/**
	 * 当调用不需要传入Connection的方法时，该方法会从getConn方法中获取一个连接
	 *
	 * @return
	 */
	@Override
	public Connection getConn() {
		return conn == null ? super.getConn() : conn;
	}

	/**
	 * 调用完getConn方法后会调用closeConn方法关闭连接
	 *
	 * @param conn
	 * @return
	 */
	@Override
	public boolean closeConn(Connection conn) {
		if(this.conn == null) super.closeConn(conn);
		conn = null;
		return true;
	}
}
