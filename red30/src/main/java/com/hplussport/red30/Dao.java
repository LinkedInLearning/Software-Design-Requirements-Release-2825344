package com.hplussport.red30;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;
import com.hplussport.red30.beans.Serving;
import com.hplussport.red30.beans.User;

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
	
	//returns a list of product objects whose description
	//contains the searchString
	public static List<Product> searchProductOnName(String searchString) {
		List<Product> products = new ArrayList<>();
		for (Map.Entry<String, Product> entry : productMap.entrySet()) {
			if (entry.getValue().getDescription().toLowerCase().contains(searchString.toLowerCase())) {
				products.add(entry.getValue());
			}
		}
		return products;
	}

	//returns a list of Nutrient objects for a given 
	//product fdc_id
	public static List<Nutrient> searchNutrientsForProduct(String fdc_id) {
		List<Nutrient> nutrientsList = new ArrayList<>();
		Product product = productMap.get(fdc_id);
		for (String nutrientId: product.getProductNutrientMap().keySet()) {
			nutrientsList.add(nutrientMap.get(nutrientId));
		}
		return nutrientsList;
	}
	
	//validates user credentials from user table in red30db
	public User validateUser(String username, String password) {
		User user = null;
		try {
			PreparedStatement ps = connection.prepareStatement("select username, password from user "
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
	
	//saves meal and its servings in red30 db in meal and serving tables
	public boolean saveMeal(Meal meal) {
		try {
			PreparedStatement ps = connection.prepareStatement("insert into meal (mealDateTime, mealType, username) values (?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, meal.getMealDateTime());
			ps.setString(2, meal.getMealType());
			ps.setString(3, meal.getUsername());
			ps.execute();
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			meal.setMealId(result.getInt(1));
			for (Serving serving: meal.getMealServingList()) {
				ps = connection.prepareStatement("insert into serving (mealId, fdc_id, quantity) values (?, ?, ?)");
				ps.setInt(1,  meal.getMealId());
				ps.setString(2, serving.getProduct().getFdc_id());
				ps.setFloat(3, serving.getQuantity());
				ps.execute();
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	//finds meals for a user within a given date range fromDate to toDate
	public List<Meal> findMeals(String userId, String fromDate, String toDate) {
		List<Meal> result = new ArrayList<>();
		try {
			Timestamp fromDateTime, toDateTime;
			if (fromDate == null || fromDate.isEmpty()) {
				fromDateTime = Timestamp.valueOf(LocalDateTime.MIN);
			} else {
				fromDateTime = Timestamp.valueOf(fromDate + " 00:00:00.00");
			}
			if (toDate == null || toDate.isEmpty()) {
				toDateTime = Timestamp.valueOf(LocalDateTime.now());
			} else {
				toDateTime = Timestamp.valueOf(toDate + " 00:00:00.00");
			}
			
			PreparedStatement ps = connection.prepareStatement("select * from meal where username = ? and CAST(mealDateTime AS DATE) between ? and ? "
					+ "order by mealDateTime");
			ps.setString(1, userId);
			ps.setTimestamp(2, fromDateTime);
			ps.setTimestamp(3, toDateTime);
			ResultSet mealResult = ps.executeQuery();
			while (mealResult.next()) {
				Meal meal = new Meal();
				meal.setMealDateTime(Timestamp.valueOf(mealResult.getString(2)));
				meal.setUsername(userId);
				meal.setMealType(mealResult.getString(3));
				String mealId = mealResult.getString(1);
				meal.setMealId(Integer.parseInt(mealId));
				ps = connection.prepareStatement("select * from serving where mealId = ?");
				ps.setString(1, mealId);
				ResultSet servingResult = ps.executeQuery();
				List<Serving> servings = new ArrayList<>();
				while (servingResult.next()) {
					//create serving with product and quantity and add to list
					servings.add(new Serving( Dao.productMap.get(servingResult.getString(3)), servingResult.getFloat(4)));
				}
				meal.setMealServingList(servings);
				result.add(meal);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

}
