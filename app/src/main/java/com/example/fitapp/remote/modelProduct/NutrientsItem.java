package com.example.fitapp.remote.modelProduct;

import com.google.gson.annotations.SerializedName;

public class NutrientsItem{

	@SerializedName("amount")
	private double amount;

	@SerializedName("unit")
	private String unit;

	@SerializedName("percentOfDailyNeeds")
	private double percentOfDailyNeeds;

	@SerializedName("name")
	private String name;

	public double getAmount(){
		return amount;
	}

	public String getUnit(){
		return unit;
	}

	public double getPercentOfDailyNeeds(){
		return percentOfDailyNeeds;
	}

	public String getName(){
		return name;
	}
}