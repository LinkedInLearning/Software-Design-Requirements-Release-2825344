package com.hplussport.red30.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Meal implements Comparable<Meal> {
	private int mealId;
	private List<Serving> mealServingList = new ArrayList<>();
	private Timestamp mealDateTime;
	private String mealType;
	private String username;

	public Meal() {	}
	
	public int getMealId() {
		return mealId;
	}

	public void setMealId(int mealId) {
		this.mealId = mealId;
	}

	public List<Serving> getMealServingList() {
		return mealServingList;
	}
	public void setMealServingList(List<Serving> mealServingList) {
		this.mealServingList = mealServingList;
	}

	public Timestamp getMealDateTime() {
		return mealDateTime;
	}
	public void setMealDateTime(Timestamp mealDateTime) {
		this.mealDateTime = mealDateTime;
	}


	public String getMealType() {
		return mealType;
	}


	public void setMealType(String mealType) {
		this.mealType = mealType;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + mealId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Meal other = (Meal) obj;
		if (mealId != other.mealId)
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public int compareTo(Meal o) {
		return mealDateTime.compareTo(o.mealDateTime); 
	}



}
