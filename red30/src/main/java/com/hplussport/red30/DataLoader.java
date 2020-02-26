package com.hplussport.red30;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;


/**DataLoader is load-on-startup servlet 
 * It uses Dao to connect to datasource defined in context.xml,
 * and reads all data into Dao's product and nutrient maps
 */
@SuppressWarnings("serial")
@WebServlet (urlPatterns = "/dataloader")
public class DataLoader extends HttpServlet{

	private Dao dao;

	@Override
	public void init(ServletConfig config) throws ServletException {
		dao = Dao.getInstance();

		System.out.println("...loading products");
		loadProducts();
		System.out.println("...loading nutrients");
		loadNutrients();
		System.out.println("...loading productNutrients");
		loadProductNutrients();
		System.out.println("...data loading completed");
	}

	@Override
	public void destroy()  {
		Dao.nutrientMap = null;
		Dao.productMap = null;
		try {
			if (dao.connection != null)
				dao.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**loadProducts() first reads data from food table 
	 * to get fdc_id code and description. It creates Product objects and adds them 
	 * into Dao's productMap. 
	 * It then reads data from branded_food, and fills in rest of
	 * Product's properties in the map.
	 */
	private void loadProducts() {
		String query = "select fdc_id, description from food";
		try {
			ResultSet rs = dao.connection.prepareStatement(query).executeQuery();
			while (rs.next()) {
				Product product = new Product(rs.getString(1), rs.getString(2));
				Dao.productMap.put(rs.getString(1), product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		query = "select fdc_id, serving_size_unit, serving_size, household_serving_fulltext, brand_owner from  branded_food";

		try {
			ResultSet rs = dao.connection.prepareStatement(query).executeQuery();
			while (rs.next()) {
				if (Dao.productMap.containsKey(rs.getString(1))) {
					Product product = Dao.productMap.get(rs.getString(1));
					product.setServing_size_unit(rs.getString(2));
					product.setServing_size(rs.getFloat(3));
					product.setHousehold_serving_fulltext(rs.getString(4));
					product.setBrand_owner(rs.getString(5));
					Dao.productMap.put(rs.getString(1), product);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**loadNutrients() reads data from nutrient table, 
	 * creates Nutrient objects with id, name, and unit_name, 
	 * and then adds them to Dao's nutrientMap.
	 */
	private void loadNutrients() {
		String query = "select id, name, unit_name from nutrient";
		PreparedStatement ps;
		try {
			ps = dao.connection.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Nutrient nutrient = new  Nutrient(rs.getString(1), rs.getString(2), rs.getString(3));
				Dao.nutrientMap.put(rs.getString(1), nutrient);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/** loadProductNutrients() reads data from food_nutrient table 
	 * and stores each row of data temporarily in a list of TempItems. 
	 * Only those rows are stored where amount is not 0.
	 * Then for each temp-item in list, it finds the food-product in products map, 
	 * gets its productNutrientMap, and adds the nutrient and its amount in it.
	 */
	private void loadProductNutrients() {
		PreparedStatement ps;
		ResultSet rs;

		//to store row data 
		class TempItem{
			int fdc_id, nutrient_id;
			float amount;
			TempItem( int fdc_id, int nutrient_id, float amount) {
				this.fdc_id = fdc_id;
				this.nutrient_id = nutrient_id;
				this.amount = amount;
			}
		}
		List<TempItem> itemList = new ArrayList<>(); //will store all rows of data
		try {
			ps =  dao.connection.prepareStatement( "select fdc_id, nutrient_id, amount from food_nutrient where amount > 0");
			rs = ps.executeQuery();
			while (rs.next()) {
				itemList.add(new TempItem(rs.getInt(1), rs.getInt(2), rs.getFloat(3)));
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		}

		//use data in each item in itemList to update 
		//the productNutrientMap for each product in productMap 
		for (TempItem item : itemList) {
			if (Dao.productMap.containsKey(Integer.toString(item.fdc_id))) {
				Product product = Dao.productMap.get(Integer.toString(item.fdc_id));
				if (product.getProductNutrientMap() == null) {
					product.setProductNutrientMap();
				}
				product.getProductNutrientMap().put(Integer.toString(item.nutrient_id), (float)item.amount);
			}
		}
	}

}
