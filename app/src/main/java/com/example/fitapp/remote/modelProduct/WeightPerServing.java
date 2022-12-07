package com.example.fitapp.remote.modelProduct;

import com.google.gson.annotations.SerializedName;

public class WeightPerServing{

	@SerializedName("amount")
	private int amount;

	@SerializedName("unit")
	private String unit;

	public int getAmount(){
		return amount;
	}

	public String getUnit(){
		return unit;
	}
}