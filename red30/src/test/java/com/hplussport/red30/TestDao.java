package com.hplussport.red30;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;
import com.hplussport.red30.datalayer.Dao;
import com.hplussport.red30.datalayer.USDADao;

public class TestDao {
	
	@BeforeClass 
	public static void setupClass(){
		//setup fake product and nutrient maps
		USDADao.productMap = new HashMap<String, Product>();
		USDADao.nutrientMap = new HashMap<String, Nutrient>();
		
		//create fake products
		Product product1 = new Product("123", "Apple pie");
		Product product2 = new Product("234", "Garlic bread");
		Product product3 = new Product("345", "Apple sauce");
		
		//add products to map
		USDADao.productMap.put("123", product1);
		USDADao.productMap.put("234", product2);
		USDADao.productMap.put("345", product3);
		
		//initialize productNutrientMap in each product to empty maps
		for (Map.Entry<String, Product> entry: USDADao.productMap.entrySet()) {
			entry.getValue().setProductNutrientMap();   
		}
		
		//create fake nutrientMap with fake nutrients
		USDADao.nutrientMap.put("11", new Nutrient("11", "Calcium", "mg"));
		USDADao.nutrientMap.put("22", new Nutrient("22", "Protein", "gm"));
		USDADao.nutrientMap.put("33", new Nutrient("33", "Energy", "KCAL"));
		USDADao.nutrientMap.put("44", new Nutrient("44", "Fiber", "gm"));
		
		//put nutrients in product - Apple pie
		USDADao.productMap.get("123").getProductNutrientMap().put("11", 3.5f);
		USDADao.productMap.get("123").getProductNutrientMap().put("22", 20f);
		USDADao.productMap.get("123").getProductNutrientMap().put("33", 100f);
		
	}
	
	//test that search returns correct number of products
	@Test
	public void testCountOfProductOnSearchOnName() {
		assertEquals(2, USDADao.searchProductOnName("apple").size());
	}
	
	//test that search returns correct products
	@Test
	public void testNameOfProductOnSearchOnName() {
		List<Product> productList =  USDADao.searchProductOnName("apple");
		Map<String, String> tempMap = new HashMap<>();
		for (Product product : productList) {
			tempMap.put(product.getFdc_id(), product.getDescription());
		}
		assertTrue(tempMap.containsKey("123"));
		assertTrue(tempMap.containsKey("345"));
	
	}

	//test that search on a products' nutrients returns correct number of nutrients
	@Test
	public void testCountOfNutrientInNutrientSearch() {
		assertEquals(3, USDADao.searchNutrientsForProduct("123").size());
	}
	
	//test that search on a products' nutrients returns correct nutrient objects
	@Test
	public void testNutrientIdInNutrientSearch() {
		List<Nutrient> nutrientsList =  USDADao.searchNutrientsForProduct("123");
		Map<String, String> tempMap = new HashMap<>();
		for (Nutrient nutrient : nutrientsList) {
			tempMap.put(nutrient.getId(), nutrient.getName());
		}
		assertTrue(tempMap.containsKey("11"));
		assertTrue(tempMap.containsKey("22"));
		assertTrue(tempMap.containsKey("33"));
		assertTrue(!tempMap.containsKey("44"));
	}
	
	@AfterClass
	public static void tearDownClass() {
		USDADao.productMap = null;
		USDADao.nutrientMap = null;
	}
}
