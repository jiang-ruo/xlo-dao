package xlo.database_old.util;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author XiaoLOrange
 * @time 2020.10.30
 * @title 辅助sql
 */

public class SqlUtil {

	//运算符
	public final static String EQUAL = "=";
	public final static String GREATER = ">";
	public final static String GREATER_EQUAL = ">=";
	public final static String LESS = "<";
	public final static String LESS_EQUAL = "<=";
	public final static String LEFT_LIKE = "%LIKE";
	public final static String RIGHT_LIKE = "LIKE%";
	public final static String BOTH_LIKE = "%LIKE%";

	/**
	 * 普通数据类型的初始值默认为0，Integer等类的初始值默认为null
	 * @param obj
	 * @param paramsContainer
	 * @param sdfs
	 * @return	返回(...) Values(...)
	 */
	public String insertSql(Object obj, List<Object> paramsContainer, SimpleDateFormat... sdfs){
		if(paramsContainer == null) throw new NullPointerException();
		String echo = "";
		if(obj == null) return echo;
		
		String[] preEcho = insertASql(obj,paramsContainer, sdfs);

		echo += "(" + preEcho[0] + ") VALUES(" + preEcho[1] + ")";

		return echo;
	}

	/**
	 * 普通数据类型的初始值默认为0，Integer等类的初始值默认为null
	 * @param obj
	 * @param paramContainer 必须传入，容器中直接放入参数
	 * @param sdfs
	 * @return 返回String[], table(...) Values(...); [0]为table后...中的内容，[1]为values后...中的内容(不包括括号)
	 */
	public String[] insertASql(Object obj, List<Object> paramContainer, SimpleDateFormat... sdfs){
		if(paramContainer == null) throw new NullPointerException();
		String[] echo = new String[2];
		if(obj == null) return echo;

		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		StringBuilder preField = new StringBuilder().append("");
		StringBuilder preValue = new StringBuilder().append("");
		Object value;
		for (int i = 0; i < fields.length; i++) {
			//不需要处理的数据类型
			if(!isInType(fields[i])) continue;
			
			

			//判断数据是否是初始值，初始值不处理
			value = getValue(fields[i], obj);
			if(value == null) continue;

			//
			sqlBuilder(preField, preValue, fields[i].getName(), paramContainer, value, sdfs);
		}
		String field = preField.toString();
		field = field.substring(0, field.length() - 2);
		String valueSql = preValue.toString();
		valueSql = valueSql.substring(0, valueSql.length() - 2);
		echo[0] = field;
		echo[1] = valueSql;

		return echo;
	}
	
	/**
	 * 
	 * @param obj
	 * @param paramsContainer
	 * @param sdfs
	 * @return
	 */
	public String setAsql(Object obj, List<Object> paramsContainer, SimpleDateFormat... sdfs) {
		if(paramsContainer == null) throw new NullPointerException();
		String echo = "";
		if(obj == null) return echo;

		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		StringBuilder precSql = new StringBuilder().append("\n");
		Object value;
		for (int i = 0; i < fields.length; i++) {
			//不需要处理的数据类型
			if(!isInType(fields[i])) continue;

			//判断数据是否是初始值，初始值不处理
			value = getValue(fields[i], obj);
			if(value == null) continue;

			//
			sqlBuilder(precSql, paramsContainer, fields[i].getName(), value, sdfs);
		}
		String sql = precSql.toString();
		sql = sql.substring(0, sql.length() - 2);
		return sql;
	}
	
	/**
	 * 
	 * @param tableName 表名
	 * @param paramsContainer 参数容器
	 * @param min 最小值，不包括自己 >
	 * @param minEqual 最小值，包括自己 >=
	 * @param max 最大值，不包括自己 <
	 * @param maxEqual 最大值，包括自己 <=
	 * @param like 模糊查询
	 * @param equal 精确查询
	 * @param sdfs 日期格式
	 * @return where sql语句
	 */
	public String whereSql(String tableName, List<Object> paramsContainer, Object min, Object minEqual, Object max, Object maxEqual, Object like, Object equal, SimpleDateFormat... sdfs) {
		if(paramsContainer == null) throw new NullPointerException();
		String echo = "";
		
		if(min != null) {
			echo += whereASql(tableName, min, GREATER, paramsContainer, sdfs);
		}
		
		if(minEqual != null) {
			echo += whereASql(tableName, minEqual, GREATER_EQUAL, paramsContainer, sdfs);
		}
		
		if(max != null) {
			echo += whereASql(tableName, max, LESS, paramsContainer, sdfs);
		}
		
		if(maxEqual != null) {
			echo += whereASql(tableName, maxEqual, LESS_EQUAL, paramsContainer, sdfs);
		}
		
		if(like != null) {
			echo += whereASql(tableName, like, BOTH_LIKE, paramsContainer, sdfs);
		}
		
		if(equal != null) {
			echo += whereASql(tableName, equal, EQUAL, paramsContainer, sdfs);
		}
		
		return echo;
	}

	/**
	 * 普通数据类型的初始值默认为0，Integer等类的初始值默认为null
	 * @param tableName 表或表的别名
	 * @param obj 对象
	 * @param operator 运算符
	 * @param paramsContainer 传入存放参数的容器，传入null则重新构建
	 * @param sdfs 传入日期格式，符合格式的全部判定为日期.
	 * @return
	 */
	public String whereASql(String tableName, Object obj, String operator, List<Object> paramsContainer, SimpleDateFormat... sdfs) {
		if(paramsContainer == null) throw new NullPointerException();
		String echo = "";
		if(obj == null) return echo;

		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();

		StringBuilder precSql = new StringBuilder().append("\n");
		Object value;
		tableName = formatTableName(tableName);
		for (int i = 0; i < fields.length; i++) {
			//不需要处理的数据类型
			if(!isInType(fields[i])) continue;

			//判断数据是否是初始值，初始值不处理
			value = getValue(fields[i], obj);
			if(value == null) continue;

			sqlBuilder(precSql, paramsContainer, tableName,
					fields[i].getName(), operator, value, sdfs);
		}
		echo = precSql.toString();

		return echo;
	}

	/**
	 * 格式化tableName
	 * @param tn tableName
	 * @return 	"", "[space|tab]", null - > ""
	 * 			"[content]" -> "[content]."
	 */
	private String formatTableName(String tn){
		if(tn == null) tn = "";
		tn = tn.trim();
		if(!tn.equals("")){
			tn = tn + ".";
		}
		return tn;
	}

	/**
	 * 判断是否是需要处理的数据类型。
	 * 除了String、基本数据类型及其实现类（不包括boolean和char）其它全部忽略.
	 */
	private boolean isInType(Field field){
		boolean result = true;
		Class type = field.getType();
		if(type.isPrimitive()){
			//是基本数据类型

			if(type.toString().equals("boolean") || type.toString().equals("char")){
				result = false;
			}

		}else if(type.isArray()){
			//是数组，直接抛弃
			result = false;
		}else{
			//不是数组和基本数据类型，就是其它类了。
			String[] allow = {
					"java.lang.Object",
					"java.lang.String",
					"java.lang.Byte",
					"java.lang.Short",
					"java.lang.Integer",
					"java.lang.Long",
					"java.lang.Float",
					"java.lang.Double"
			};
			result = false;
			for(int i = 0; i < allow.length; i++){
				if(allow[i].equals(type.getName())){
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * 判断变量是否是初始值
	 * 基本数据类型boolean, char则直接抛弃
	 * 其它基本数据类型的初始值默认为0，Integer等类的初始值默认为null
	 * @param field
	 * @param obj
	 * @return
	 */
	private Object getValue(Field field, Object obj){
		//获取属性的值。
		field.setAccessible(true);
		Object value = null;
		try {
			value = field.get(obj);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		Class type = field.getType();
		if(type.isPrimitive()){
			//是基本数据类型
			int bv = (int) value;
			if(bv == 0){
				return null;
			}
		}//不是基本数据类型，未赋值的属性就是null

		return value;
	}

	/**
	 * 构建sql语句
	 * @param fieldContainer sql容器 - 存放字段
	 * @param valueContainer sql容器 - 存放值
	 * @param fieldName 字段名
	 * @param paramsContainer param容器
	 * @param sdfs 时间格式
	 */
	private void sqlBuilder(StringBuilder fieldContainer, StringBuilder valueContainer,  String fieldName, List<Object> paramsContainer, Object value, SimpleDateFormat... sdfs){
		//判断该参数是否是时间
		String formatValue = value.toString();
		String dateFormat = getDate(formatValue, sdfs);

		if(dateFormat == null){
			fieldContainer.append(fieldName + ", ");
			valueContainer.append("?, ");
		}else{
			fieldContainer.append(fieldName + ", ");
			valueContainer.append("to_date( ?, '" + dateFormat + "'), ");
		}
		paramsContainer.add(formatValue);
	}
	
	/**
	 * 构建sql语句
	 * @param valueContainer
	 * @param paramsContainer
	 * @param fieldName
	 * @param value
	 * @param sdfs
	 */
	private void sqlBuilder(StringBuilder valueContainer, List<Object> paramsContainer, String fieldName, Object value, SimpleDateFormat... sdfs) {
		//判断该参数是否是时间
		String formatValue = value.toString();
		String dateFormat = getDate(formatValue, sdfs);

		if(dateFormat == null){
			valueContainer.append(fieldName + " = ?, ");
		}else{
			valueContainer.append(fieldName + " = to_date( ?, '" + dateFormat + "'), ");
		}
		paramsContainer.add(formatValue);
	}

	/**
	 * 构建sql语句
	 * @param sqlContainer sql容器
	 * @param paramsContainer param容器
	 * @param tableName 表明
	 * @param fieldName 字段名
	 * @param operator 运算符
	 * @param value 字段值
	 * @param sdfs 时间格式
	 */
	private void sqlBuilder(StringBuilder sqlContainer, List<Object> paramsContainer,
							String tableName, String fieldName, String operator, Object value,
							SimpleDateFormat... sdfs){

		//判断该参数是否是时间
		String formatValue = value.toString();
		String dateFormat = getDate(formatValue, sdfs);

		switch (operator){
			case LEFT_LIKE:
				operator = operator.substring(1);
				formatValue = "%" + formatValue;
				break;
			case RIGHT_LIKE:
				operator = operator.substring(0, operator.length() - 1);
				formatValue += "%";
				break;
			case BOTH_LIKE:
				operator = operator.substring(1, operator.length() - 1);
				formatValue = "%" + formatValue + "%";
				break;
		}

		sqlContainer.append("AND ");
		sqlContainer.append(tableName);
		sqlContainer.append(fieldName);
		if(dateFormat == null){
			sqlContainer.append(" " + operator + " ?\n");
		}else{
			sqlContainer.append(" " + operator + " ");
			sqlContainer.append("to_date(?, '" + dateFormat + "')\n");
		}
		paramsContainer.add(formatValue);
	}

	/**
	 * 获取时间
	 * @param value
	 * @param sdfs
	 * @return 如果value无法转化为时间则返回null,能则返回时间格式化方式
	 */
	private String getDate(String value, SimpleDateFormat... sdfs){
		String dateFormat = null;
		for (int i = 0; i < sdfs.length; i++){
			try{
				sdfs[i].parse(value);
				dateFormat = sdfs[i].toPattern();
				dateFormat = dateFormat.replace("mm", "MI");
				if((dateFormat.indexOf("HH") != -1) && (dateFormat.indexOf("HH24") == -1)){
					//字符串中存在HH,但不是HH24(24小时制)
					dateFormat = dateFormat.replace("HH", "HH24");
				}
			} catch (ParseException e) {
//				e.printStackTrace();
			}
		}
		return dateFormat;
	}

}
