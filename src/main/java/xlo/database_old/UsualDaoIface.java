package xlo.database_old;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author XiaoLOrange
 * @time 2020.11.02
 * @title 简单的sql语句封装
 */
public interface UsualDaoIface {
	
	/**
	 * 获取一个序列号
	 * @param sequence
	 * @return
	 */
	int getSequence(String sequence);

	/**
	 * 插入一条数据
	 * @param tableName
	 * @param obj
	 * @param sdfs
	 * @return
	 */
	boolean insertRow(String tableName, String key, String sequence, Object obj, SimpleDateFormat... sdfs);

	/**
	 * 修改表格的信息
	 * @param tableName
	 * @param set
	 * @param find
	 * @param sdfs
	 * @return
	 */
	boolean setRow(String tableName, Object set, Object find, SimpleDateFormat... sdfs);
	
	/**
	 * 删除表中的记录
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
	boolean delete(String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);
	
	/**
	 * 对符合条件的记录进行计数
	 * @param tableName 表名
	 * @return
	 */
	int count(String tableName);
	
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
	int count(String tableName, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);

	/**
	 * 对符合条件的记录进行计数
	 * @param tableName 表名
	 * @param min >
	 * @param minEqual >=
	 * @param max <
	 * @param maxEqual <=
	 * @param like 模糊查询
	 * @param equal 精确查询
	 * @param sdfs
	 * @return
	 */
	int count(String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);

	/**
	 * 返回选中的一条记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	<T> T getRow(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs);
	
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
	<T> T getRow(Class<T> clazz, String tableName, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);

	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	<T> ArrayList<T> getRows(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs);
	
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
	<T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object equal, SimpleDateFormat... sdfs);
	
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
	<T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);
	
	/**
	 * 返回符合条件的记录数
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
	 * @param <T>
	 * @return
	 */
	<T> ArrayList<T> getRows(Class<T> clazz, String tableName, Integer begin, Integer end, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);

	/**
	 * 返回符合条件的记录
	 * @param <T>
	 * @param clazz
	 * @param tableName
	 * @param equal
	 * @param sdfs
	 * @return
	 */
	<T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Object equal, SimpleDateFormat... sdfs);
	
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
	<T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object equal, SimpleDateFormat... sdfs);
	
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
	<T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object minEqual, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);
	
	/**
	 * 返回符合条件的记录数
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
	 * @param <T>
	 * @return
	 */
	<T> LinkedList<T> getRows_LL(Class<T> clazz, String tableName, Integer begin, Integer end, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs);

}
