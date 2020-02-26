package com.hplussport.red30.beans;

import java.util.HashMap;
import java.util.Map;
 
/* Bean with getters and setters as needed*/
public class Product {
	private String fdc_id;
	private String description;
	private String ingredients;
	private String serving_size_unit;
	private float serving_size;
	private String household_serving_fulltext;
	private String brand_owner;
	private Map<String, Float > productNutrientMap; //nutrient-id is key and its amoung in product is value
	
	public Product() {}
	
	public Product(String fdc_id, String description) {
		this.fdc_id = fdc_id;
		this.description = description;
	}
	
	//set through constructor
	public String getFdc_id() { return fdc_id;}
	//set through constructor
	public String getDescription() { return description;}
	
	
	public String getIngredients() { return ingredients;}
	public void setIngredients(String ingredients) {	this.ingredients = ingredients;	}
	
	public String getServing_size_unit() { return serving_size_unit;	}
	public void setServing_size_unit(String serving_size_unit) { this.serving_size_unit = serving_size_unit; }
	
	public float getServing_size() { return serving_size; }
	public void setServing_size(float serving_size) { this.serving_size = serving_size; }

	public String getHousehold_serving_fulltext() { return household_serving_fulltext; }
	public void setHousehold_serving_fulltext(String household_serving_fulltext) { this.household_serving_fulltext = household_serving_fulltext; }
	
	public String getBrand_owner() {return brand_owner; }
	public void setBrand_owner(String brand_owner) { this.brand_owner = brand_owner; }
	
	public Map<String, Float> getProductNutrientMap() { return this.productNutrientMap; }
	public void setProductNutrientMap() { this.productNutrientMap = new HashMap<>(); }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fdc_id == null) ? 0 : fdc_id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (fdc_id == null) {
			if (other.fdc_id != null)
				return false;
		} else if (!fdc_id.equals(other.fdc_id))
			return false;
		return true;
	}


}