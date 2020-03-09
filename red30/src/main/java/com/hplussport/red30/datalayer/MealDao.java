package com.hplussport.red30.datalayer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.hplussport.red30.beans.Meal;
import com.hplussport.red30.beans.Serving;

/** MealDao handles all db queries related to meal and servings data
 * created by user
 */
public class MealDao {

	//saves meal and its servings in red30 db in meal and serving tables
	public static boolean saveMeal(Meal meal) {
		try {
			PreparedStatement ps = Dao.getInstance().connection.prepareStatement("insert into meal (mealDateTime, mealType, username) values (?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			ps.setTimestamp(1, meal.getMealDateTime());
			ps.setString(2, meal.getMealType());
			ps.setString(3, meal.getUsername());
			ps.execute();
			ResultSet result = ps.getGeneratedKeys();
			result.next();
			meal.setMealId(result.getInt(1));
			for (Serving serving: meal.getMealServingList()) {
				ps = Dao.getInstance().connection.prepareStatement("insert into serving (mealId, fdc_id, quantity) values (?, ?, ?)");
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
	public static List<Meal> findMeals(String username, String fromDate, String toDate) {
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

			PreparedStatement ps = Dao.getInstance().connection.prepareStatement("select * from meal where username = ? and CAST(mealDateTime AS DATE) between ? and ? "
					+ "order by mealDateTime");
			ps.setString(1, username);
			ps.setTimestamp(2, fromDateTime);
			ps.setTimestamp(3, toDateTime);
			ResultSet mealResult = ps.executeQuery();
			while (mealResult.next()) {
				Meal meal = new Meal();
				meal.setMealDateTime(Timestamp.valueOf(mealResult.getString(2)));
				meal.setUsername(username);
				meal.setMealType(mealResult.getString(3));
				String mealId = mealResult.getString(1);
				meal.setMealId(Integer.parseInt(mealId));
				ps = Dao.getInstance().connection.prepareStatement("select * from serving where mealId = ?");
				ps.setString(1, mealId);
				ResultSet servingResult = ps.executeQuery();
				List<Serving> servings = new ArrayList<>();
				while (servingResult.next()) {
					//create serving with product and quantity and add to list
					servings.add(new Serving( USDADao.productMap.get(servingResult.getString(3)), servingResult.getFloat(4)));
				}
				meal.setMealServingList(servings);
				result.add(meal);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Map<Calendar, Map<String, Float>> findDailyNutrients(List<Meal> meals, String nutrientId, String username, String fromDate, String toDate) {

		Map<Calendar, Map<String, Float>> dailyNutrientMap = new TreeMap<>();

		//for each meal, get each serving
		//for each serving, get each product
		//for each product, find the nutrient being tracked
		//from food_nutrient, find how much nutrient is there in 100 units of product
		//divide it by 100 to get nutrient per unit of product
		//multiply it by the quantity consumed in the meal

		for (Meal meal: meals) {
			Calendar cal = Calendar.getInstance();
			cal.setTimeInMillis(meal.getMealDateTime().getTime());
			//remove time of day component to enable nutrient quantity to be added
			//only based on date. This will become the key for dailyNutrientMap.  
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);

			for (Serving serving: meal.getMealServingList()) {
				String fdc_id = serving.getProduct().getFdc_id();
				if (USDADao.productMap.get(fdc_id).getProductNutrientMap().containsKey(nutrientId)) {
					float amount = USDADao.productMap.get(fdc_id).getProductNutrientMap().get(nutrientId);
					//populate dailyNutrientMap
					//check if date already added. Add if it is not.
					if (!dailyNutrientMap.containsKey(cal)) {
						dailyNutrientMap.put(cal, new HashMap<String, Float>());
					}
					//check if nutrient already added. Add if it is not. 
					if (!dailyNutrientMap.get(cal).containsKey(nutrientId)) {
						dailyNutrientMap.get(cal).put(nutrientId, 0f);
					}
					float newQuantity = dailyNutrientMap.get(cal).get(nutrientId) + (amount / 100) * serving.getQuantity();
					dailyNutrientMap.get(cal).put(nutrientId, newQuantity);
				}
			}
		}		
		return dailyNutrientMap;
	}
}
