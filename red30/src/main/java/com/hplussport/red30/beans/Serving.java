package com.hplussport.red30.beans;

public class Serving {
	private int servingId;
	private Product product;
	private float quantity;
	
	public Serving(){}
	
	public Serving(Product product, float quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public int getServingId() {
		return servingId;
	}

	public void setServingId(int servingId) {
		this.servingId = servingId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
}
