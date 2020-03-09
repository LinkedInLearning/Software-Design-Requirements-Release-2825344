package com.hplussport.red30.datalayer;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**Dao is Red30's Data Access Object class to connect with
 * Red30DB through the data source defined in tomcat's context.xml.
 * It uses Singleton pattern to ensure single instance. 
 */
public class Dao {
	private static Dao dao;
	Connection connection;

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
