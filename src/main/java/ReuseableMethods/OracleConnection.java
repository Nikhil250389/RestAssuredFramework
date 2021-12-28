package ReuseableMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import RestUtill.Utilities;

//import com.SetUp.BaseTest;

import java.sql.*;

public class OracleConnection {
	//FileOperations fileOperations = new FileOperations();
	public static String connection, database, server, port, dbname, dbusername, dbpassword = null;

	public static String configProperty = "./src/main/java/propertiesFiles/config.properties";
	public static RestUtill.Utilities utilities = new RestUtill.Utilities();
	public OracleConnection() {

		connection=	Utilities.getPropertyValue(configProperty, "connection");
		database=Utilities.getPropertyValue(configProperty, "database");
		server=Utilities.getPropertyValue(configProperty, "server");
		port=Utilities.getPropertyValue(configProperty, "port");
		dbname=Utilities.getPropertyValue(configProperty, "dbname");
		dbusername=Utilities.getPropertyValue(configProperty, "dbusername");
		dbpassword=Utilities.getPropertyValue(configProperty, "dbpassword");
		//connection = fileOperations.getConfigProperty("connection");
		//database = fileOperations.getConfigProperty("database");
		//server = fileOperations.getConfigProperty("server");
		//port = fileOperations.getConfigProperty("port");
	//	dbname = fileOperations.getConfigProperty("dbname");
		//dbusername = fileOperations.getConfigProperty("dbusername");
	//	dbpassword = fileOperations.getConfigProperty("dbpassword");
	}

	public Connection setUpConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found " + e);
		}
			conn = DriverManager.getConnection("jdbc:oracle:thin:@10.243.139.42:1521:DEVDB","bm_paylater","Password1");
		
		if (conn != null) {
			System.out.println("Connected to the database!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return conn;
		
	}
//		 conn =
//		 DriverManager.getConnection("jdbc:postgresql://192.168.2.110:5432/tenantOne",
//		 "postgres", "password");

	//	String str = "\"" + connection + ":" + database + "://" + server + ":" + port + "/" + dbname + "\"" + "," + "\""
	//			+ dbusername + "\"" + "," + "\"" + dbpassword + "\"";
	//	conn = DriverManager.getConnection(connection + ":" + database + "://" + server + ":" + port + "/" + dbname
		//		,dbusername, dbpassword);
		/*conn = DriverManager.getConnection(str);
		try {
			if (conn != null) {
				System.out.println("Connected to the database!");
			} else {
				System.out.println("Failed to make connection!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
*/
	public ArrayList<Object> getValueFromDBTable(String tableName, String fieldName) throws SQLException {
		ArrayList<Object> values = new ArrayList<Object>();
		String query = "SELECT " + fieldName + " FROM " + tableName;
		System.out.println(query);
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			values.add(rs.getString(fieldName));
		}
		System.out.println(values);
		return values;
	}

	public ArrayList<Object> getValuesFromDBQuery(String query, String fieldName) throws SQLException {
		ArrayList<Object> values = new ArrayList<Object>();
		System.out.println(query);
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			values.add(rs.getString(fieldName));
		}
		System.out.println(values);
		return values;

	}

	public ArrayList<String> getListFromDBQuery(String query, String fieldName, String queryKey, String queryValue)
			throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		System.out.println(query);
		String updatedQuery = query.replaceAll(queryKey, queryValue);
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(updatedQuery);
		while (rs.next()) {
			values.add(rs.getString(fieldName));
		}
		System.out.println(values);
		return values;

	}

	public ArrayList<String> getListFromDBQuery(String query, String fieldName, HashMap<String, String> keyValue)
			throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		StringBuilder mutableQuery = new StringBuilder();
		mutableQuery.append(query);
		for (Map.Entry<String, String> entry : keyValue.entrySet()) {
			this.replaceAll(mutableQuery, entry.getKey(), entry.getValue());
		}
		String updatedQuery = mutableQuery.toString();
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(updatedQuery);
		while (rs.next()) {
			values.add(rs.getString(fieldName));
		}
		System.out.println(values);
		return values;
	}
/*
	public ArrayList<String> getListFromDBQuery(String query, String fieldName) throws SQLException {
		return ObjToList(getValuesFromDBQuery(query, fieldName));
	}

	public ArrayList<String> ObjToList(ArrayList<Object> list) {
		ArrayList<String> strings = new ArrayList<>(list.size());
		for (Object object : list) {
			strings.add(Objects.toString(object, null));
		}
		return strings;
	}
*/
	public String getStringValuesFromDBQuery(String query, String fieldName) throws SQLException {
		String value = null;
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		while (rs.next()) {
			value = rs.getString(fieldName);
		}
		System.out.println(value);
		return value;
	}

	public String getStringValuesFromDBQuery(String query, String fieldName, String key, String keyValue)
			throws SQLException {
		String queryUpdated = query.replaceAll(key, keyValue);
		String value = null;
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryUpdated);
		while (rs.next()) {
			value = rs.getString(fieldName);
		}
		return value;
	}

	public String getStringValuesFromDBQuery(String query, String fieldName, HashMap<String, String> keyValue)
			throws SQLException {
		StringBuilder mutableQuery = new StringBuilder();
		mutableQuery.append(query);
		for (Map.Entry<String, String> entry : keyValue.entrySet()) {
			this.replaceAll(mutableQuery, entry.getKey(), entry.getValue());
		}
		String queryUpdated = mutableQuery.toString();
		String value = null;
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(queryUpdated);
		while (rs.next()) {
			value = rs.getString(fieldName);
		}
		return value;
	}

	private void replaceAll(StringBuilder sb, String find, String replace) {
		// compile pattern from find string
		Pattern p = Pattern.compile(find);
		// create new Matcher from StringBuilder object
		Matcher matcher = p.matcher(sb);
		// index of StringBuilder from where search should begin
		int startIndex = 0;

		while (matcher.find(startIndex)) {
			sb.replace(matcher.start(), matcher.end(), replace);
			// set next start index as start of the last match + length of replacement
			startIndex = matcher.start() + replace.length();
		}
	}

	public String getStringValuesFromDBQuery(String query) throws SQLException {
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		return rs.toString();
	}

	public ArrayList<Object> getColumnNamesFromTable(String query) throws SQLException {
		ArrayList<Object> coulumnNames = new ArrayList<Object>();
		System.out.println(query);
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);

		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		for (int i = 1; i <= columnCount; i++) {
			String name = rsmd.getColumnName(i);
			coulumnNames.add(name);
		}
		System.out.println(coulumnNames);
		return coulumnNames;

	}

	public void executeDBQuery(String query) throws SQLException {
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);
	}

	public Map<String, String> getRowDataFromDB(String query) throws SQLException {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData meta = rs.getMetaData();
		while (rs.next()) {

			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i);
				String value = rs.getString(key);
				map.put(key, value);
			}
		}
		return map;
	}

	public Map<String, Object> getRowObjectsFromDB(String query) throws SQLException {
		LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData meta = rs.getMetaData();
		while (rs.next()) {

			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i);
				Object value = rs.getObject(key);
				map.put(key, value);
			}
		}
		return map;
	}

	public ArrayList<String> getRowDataFromDBAsList(String query) throws SQLException {
		ArrayList<String> dbValuesList = new ArrayList<String>();
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData meta = rs.getMetaData();
		while (rs.next()) {

			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String key = meta.getColumnName(i);
				String value = rs.getString(key);
				dbValuesList.add(value);
			}
		}
		return dbValuesList;

	}

	public void cleanUp(String tableName, String key, String value) throws SQLException {
		String query = "DELETE FROM " + tableName + " WHERE " + key + " = '" + value + "'";
		this.executeDBQuery(query);
	}

	public String fetchData(String tableName, String columnName, String key, String value) throws SQLException {
		String query = "SELECT  * FROM " + tableName + " WHERE " + key + " = '" + value + "'";
		String valueFromDb = getStringValuesFromDBQuery(query, columnName);
		return valueFromDb;
	}
	
	public String fetchDBData(String tableName, String key, String value) throws SQLException {
		String query = "SELECT  * FROM " + tableName + " WHERE " + key + " = '" + value + "'";
		String valueFromDb = getStringValuesFromDBQuery(query);
		return valueFromDb;
	}

	public String countDataFromDb(String query) throws SQLException {
		String valueFromDb = getStringValuesFromDBQuery(query, "count");
		return valueFromDb;
	}

	public Boolean dataPresentInTable(String tableName, String columnName, String key, String value)
			throws SQLException {
		String query = "SELECT  count(*) FROM " + tableName + " WHERE " + key + " = '" + value + "'";
		String valueFromDb = getStringValuesFromDBQuery(query, columnName);
		return valueFromDb.contains("1");
	}
	/*
	public Object getBorrowerDatafromDB(String tableName, String key,String value) throws SQLException {
		Connection conn = setUpConnection();
		Statement stmt = conn.createStatement();
		String query = "SELECT * FROM " + tableName + " WHERE " + key + " = '" + value + "'";
		ResultSet rs = stmt.executeQuery(query);
		BorrowerDetails bd= new BorrowerDetails();
		bd.setCifNumber(rs.getString(key));
		bd.setCUSTOMERID(cUSTOMERID);
		while (rs.next()) {
			values.add(rs.getString(fieldName));
		}
		System.out.println(values);
		return values;
	}
*/
}
