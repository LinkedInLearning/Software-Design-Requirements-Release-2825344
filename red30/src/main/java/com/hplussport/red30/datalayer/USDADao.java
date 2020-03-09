package com.hplussport.red30.datalayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;

/** USDADao handles all db queries related to 
 * data provided by USDA. 
 */
public class USDADao{

	public static Map<String, Product> productMap = new HashMap<>();  //populated by DataLoader
	public static Map<String, Nutrient> nutrientMap = new HashMap<>();  //populated by DataLoader
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


}
