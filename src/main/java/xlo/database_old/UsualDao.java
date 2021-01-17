package xlo.database_old;

import xlo.adapter.MiddleDaoAdapter;
import xlo.database_old.util.SqlUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author XiaoLOrange
 * @time 2020.11.02
 * @title
 */

public class UsualDao extends MiddleDaoAdapter implements UsualDaoIface{
	
	/**
	 * 获取一个序列号
	 * @param sequence
	 * @return
	 */
	public int getSequence(String sequence) {
		String sql = "SELECT " + sequence + ".NEXTVAL FROM DUAL";
		return Integer.valueOf(super.SingleValueQuery(sql).toString());
	}

	/**
	 * 插入一条数据
	 *
	 * @param tableName 表名
	 * @param key 需要手动自增的主键
	 * @param sequence 序列名
	 * @param obj
	 * @param sdfs
	 * @return
	 */
	@Override
	public boolean insertRow(String tableName, String key, String sequence, Object obj, SimpleDateFormat... sdfs) {
		if(obj == null) throw new NullPointerException();

		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("INSERT INTO ");
		sqlContainer.append(tableName);

		SqlUtil su = new SqlUtil();
		if(key == null){
			sqlContainer.append(su.insertSql(obj, paramsContainer, sdfs));
		}else{
			//有传入主键
			String[] sqlAssemble = su.insertASql(obj, paramsContainer, sdfs);
			sqlContainer.append("(" + key + "," + sqlAssemble[0] + ") ");
			sqlContainer.append("VALUES(" + sequence + ".NEXTVAL, " + sqlAssemble[1] + ") ");
		}

		String sql = sqlContainer.toString();
		Object[] params = paramsContainer.toArray();

		return super.Update(sql, params) == 1;
	}
	
	/**
	 * 修改表格的信息
	 * @param tableName
	 * @param set
	 * @param find
	 * @param sdfs
	 * @return
	 */
	public boolean setRow(String tableName, Object set, Object find, SimpleDateFormat... sdfs) {
		if(set == null) throw new NullPointerException();
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("UPDATE ");
		sqlContainer.append(tableName);
		sqlContainer.append(" SET ");
		
		SqlUtil su = new SqlUtil();
		
		sqlContainer.append(su.setAsql(set, paramsContainer, sdfs));
		sqlContainer.append(" WHERE 1 = 1 ");
		sqlContainer.append(su.whereASql(tableName, find, su.EQUAL, paramsContainer, sdfs));
		
		String sql = sqlContainer.toString();
		Object[] params = paramsContainer.toArray();
		return super.Update(sql, params) == 1;
	}

	/**
	 * 删除表中的记录
	 *
	 * @param tableName
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	@Override
	public boolean delete(String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("DELETE FROM ");
		sqlContainer.append(tableName);
		sqlContainer.append(" WHERE 1 = 1");

		SqlUtil su = new SqlUtil();
		sqlContainer.append(su.whereSql(tableName, paramsContainer, min, minEqual, max, maxEqual, like, equal, sdfs));

		String sql = sqlContainer.toString();
		Object[] params = paramsContainer.toArray();

		return super.Update(sql, params) > 0;
	}
	
	/**
	 * 对符合条件的记录进行计数
	 * @param tableName 表名
	 * @return
	 */
	public int count(String tableName) {
		return count(tableName, null, null, null, null, null, null);
	}
	
	/**
	 * 对符合条件的记录进行计数
	 * @param tableName 表名
	 * @param minEqual >=
	 * @param maxEqual <=
	 * @param like 模糊查询
	 * @param equal 精确查询
	 * @param sdfs
	 * @return
	 */
	public int count(String tableName, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		return count(tableName, null, minEqual, null, maxEqual, like, equal, sdfs);
	}
	
	/**
	 * 对符合条件的记录进行计数
	 *
	 * @param tableName 表名
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like      模糊查询
	 * @param equal     精确查询
	 * @param sdfs
	 * @return
	 */
	@Override
	public int count(String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("SELECT COUNT(*) FROM ");
		sqlContainer.append(tableName);
		sqlContainer.append(" WHERE 1 = 1");

		SqlUtil su = new SqlUtil();
		sqlContainer.append(su.whereSql(tableName, paramsContainer, min, minEqual, max, maxEqual, like, equal, sdfs));

		String sql = sqlContainer.toString();
		Object[] params = paramsContainer.toArray();

		return Integer.valueOf(super.SingleValueQuery(sql, params).toString());
	}

	/**
	 * 返回选中的一条记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal 精确查询
	 * @param sdfs
	 * @return
	 */
	public <T> T getRow(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs) {
		return getRow(clazz, tableName, null, null, null, null, null, equal, sdfs);
	}
	
	/**
	 * 返回选中的一条记录
	 * @param clazz
	 * @param tableName
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @param <T>
	 * @return
	 */
	@Override
	public <T> T getRow(Class<T> clazz, String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("SELECT * FROM ");
		sqlContainer.append(tableName);
		sqlContainer.append(" WHERE 1 = 1");

		SqlUtil su = new SqlUtil();
		sqlContainer.append(su.whereSql(tableName, paramsContainer, min, minEqual, max, maxEqual, like, equal, sdfs));

		String sql = sqlContainer.toString();
		Object[] params = paramsContainer.toArray();

		ArrayList<T> resultContainer = super.QueryT(clazz, sql, params);

		return resultContainer.size() > 0 ? resultContainer.get(0) : null;
	}
	
	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	public <T> ArrayList<T> getRows(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs){
		return getRows(clazz, tableName, null, null, equal, sdfs);
	}
	
	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	public <T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object equal, SimpleDateFormat... sdfs){
		return getRows(clazz, tableName, begin, end, null, null, null, equal, sdfs);
	}
	
	/**
	 * 返回符合条件的记录数
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param minEqual >=
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @param <T>
	 * @return
	 */
	public <T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs){
		return getRows(clazz, tableName, begin, end, null, minEqual, null, maxEqual, like, equal, sdfs);
	}

	/**
	 * 返回符合条件的记录数
	 *
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	@Override
	public <T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("SELECT * FROM ");
		sqlContainer.append(tableName);
		sqlContainer.append(" WHERE 1 = 1");

		SqlUtil su = new SqlUtil();
		sqlContainer.append(su.whereSql(tableName, paramsContainer, min, minEqual, max, maxEqual, like, equal, sdfs));

		String preSql = sqlContainer.toString();
		String sql;
		if(begin != null && end != null){
			sql = "SELECT * FROM (\n" +
					"SELECT ROWNUM AS RN, t.* FROM (" + preSql + ") t\n" +
					") t WHERE t.RN BETWEEN ? AND  ?";
			paramsContainer.add(begin);
			paramsContainer.add(end);
		}else{
			sql = preSql;
		}
		Object[] params = paramsContainer.toArray();

		return super.QueryT(clazz, sql, params);
	}
	
	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	public <T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs){
		return getRows_LL(clazz, tableName, null, null, equal, sdfs);
	}
	
	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	public <T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object equal, SimpleDateFormat... sdfs){
		return getRows_LL(clazz, tableName, begin, end, null, null, null, equal, sdfs);
	}
	
	
	/**
	 * 返回符合条件的记录数
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param minEqual >=
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @param <T>
	 * @return
	 */
	public <T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs){
		return getRows_LL(clazz, tableName, begin, end, null, minEqual, null, maxEqual, like, equal, sdfs);
	}
	
	/**
	 * 返回符合条件的记录数
	 *
	 * @param clazz
	 * @param tableName
	 * @param begin
	 * @param end
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	@Override
	public <T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		ArrayList<Object> paramsContainer = new ArrayList<>();
		StringBuilder sqlContainer = new StringBuilder("SELECT * FROM ");
		sqlContainer.append(tableName);
		sqlContainer.append(" WHERE 1 = 1");

		SqlUtil su = new SqlUtil();
		sqlContainer.append(su.whereSql(tableName, paramsContainer, min, minEqual, max, maxEqual, like, equal, sdfs));

		String preSql = sqlContainer.toString();
		String sql;
		if(begin != null && end != null){
			sql = "SELECT * FROM (\n" +
					"SELECT ROWNUM AS RN, t.* FROM (" + preSql + ") t\n" +
					") t WHERE t.RN BETWEEN ? AND  ?";
			paramsContainer.add(begin);
			paramsContainer.add(end);
		}else{
			sql = preSql;
		}
		Object[] params = paramsContainer.toArray();

		return super.QueryT_LL(clazz, sql, params);
	}
}
