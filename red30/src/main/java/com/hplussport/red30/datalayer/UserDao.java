package com.hplussport.red30.datalayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hplussport.red30.beans.User;

/** UserDao handles all db queries related to User authentication data
 */
public class UserDao {
	
	//validates user credentials from user table in red30db
	public static User validateUser(String username, String password) {
		User user = null;
		try {
			PreparedStatement ps = Dao.getInstance().connection.prepareStatement("select username, password from user "
					+ "where username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User(rs.getString(1), rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
