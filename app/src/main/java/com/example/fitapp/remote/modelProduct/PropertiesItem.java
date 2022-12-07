package com.example.fitapp.remote.modelProduct;

import com.google.gson.annotations.SerializedName;

public class PropertiesItem{

	@SerializedName("amount")
	private double amount;

	@SerializedName("unit")
	private String unit;

	@SerializedName("name")
	private String name;

	public double getAmount(){
		return amount;
	}

	public String getUnit(){
		return unit;
	}

	public String getName(){
		return name;
	}
}