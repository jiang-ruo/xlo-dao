package xlo.database.dao;

import xlo.util.math.NumUtil;

/**
 * @author XiaoLOrange
 * @time 2020.12.02
 * @title 能够使用单例的usualDao
 */

public class UsualDao extends MiddleDao{

	/**
	 * 获取一个序列号
	 * @param seq
	 * @return
	 */
	public int getSeq(String seq){
		String sql = "SELECT " + seq + ".NEXTVAL FROM DUAL";
		return NumUtil.toInt(super.SingleValueQuery(sql));
	}

}
