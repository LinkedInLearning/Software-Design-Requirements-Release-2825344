package red30;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.hplussport.red30.Dao;
import com.hplussport.red30.beans.Nutrient;
import com.hplussport.red30.beans.Product;

public class TestDao {
	
	static Dao dao;
	
	@BeforeClass 
	public static void setupClass(){
		Product product1 = new Product("123", "Apple pie");
		Product product2 = new Product("234", "Garlic bread");
		Product product3 = new Product("345", "Apple sauce");
		Dao.productMap = new HashMap<String, Product>();
		Dao.nutrientMap = new HashMap<String, Nutrient>();
		
		Dao.productMap.put("123", product1);
		Dao.productMap.put("234", product2);
		Dao.productMap.put("345", product3);
		
		for (Map.Entry<String, Product> entry: Dao.productMap.entrySet()) {
			entry.getValue().setProductNutrientMap();  //initialize 
		}
		
		Dao.nutrientMap.put("11", new Nutrient("11", "Calcium", "mg"));
		Dao.nutrientMap.put("22", new Nutrient("22", "Protein", "gm"));
		
		Dao.productMap.get("123").getProductNutrientMap().put("11", 3.5f);
		Dao.productMap.get("123").getProductNutrientMap().put("22", 20f);
		
		Dao.productMap.get("234").getProductNutrientMap().put("11", 5.5f);
		Dao.productMap.get("234").getProductNutrientMap().put("22", 10f);
		
		Dao.productMap.get("345").getProductNutrientMap().put("11", 0f);
		Dao.productMap.get("345").getProductNutrientMap().put("22", 10f);
		
	}

	
	//test that search returns correct number of products
	@Test
	public void testCountOfProductOnSearchOnName() {
		assertEquals(2, Dao.searchProductOnName("apple").size());
	}
	
	//test that search returns correct products
	@Test
	public void testNameOfProductOnSearchOnName() {
		List<Product> productList =  Dao.searchProductOnName("apple");
		Map<String, String> tempMap = new HashMap<>();
		for (Product product : productList) {
			tempMap.put(product.getFdc_id(), product.getDescription());
		}
		assertTrue(tempMap.containsKey("123"));
		assertTrue(tempMap.containsKey("345"));
	
	}

	//test that search on fdc_id returns correct product
//	@Test
//	public void testProductOnFdcIdSearch() {
//	}
	
	
	@AfterClass 
	public static void tearDownClass() {
		
	}
}
