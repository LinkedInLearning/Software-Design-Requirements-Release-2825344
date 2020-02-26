package com.hplussport.red30;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;

/**Dao is Red30's Data Access Object class to connect with
 * Red30DB through the data source defined in tomcat's context.xml.
 * It uses Singleton pattern to ensure single instance. 
 */
public class Dao {
	private static Dao dao;
	Connection connection;

	public static Map<String, Product> productMap = new HashMap<>();  //populated by DataLoader
	public static Map<String, Nutrient> nutrientMap = new HashMap<>();  //populated by DataLoader
	
	//connect to data source as defined in context.xml
	private Dao() {
		InitialContext ic;
		try {
			ic = new InitialContext();
			Context xmlContext = (Context) ic.lookup("java:comp/env");
			DataSource datasource = (DataSource) xmlContext.lookup("datasource"); //defined in context.xml
			connection = datasource.getConnection();
		} catch (NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	//singleton pattern
	public static Dao getInstance() {
		if (dao == null) {
			dao = new Dao();
		}
		return dao;
	}
}
