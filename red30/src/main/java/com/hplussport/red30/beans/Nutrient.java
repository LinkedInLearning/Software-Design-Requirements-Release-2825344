package com.hplussport.red30.beans;

/* Bean with getters and setters as needed*/
public class Nutrient {
	
	private String id;
	private String name;
	private String unit_name;
	
	public Nutrient() {}
	public Nutrient(String id, String name, String unit_name) {
		this.id = id;
		this.name = name;
		this.unit_name = unit_name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnit_name() {
		return unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name = unit_name;
	}
}
