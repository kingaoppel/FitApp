package com.example.fitapp.remote.modelProduct;

import com.google.gson.annotations.SerializedName;

public class EstimatedCost{

	@SerializedName("unit")
	private String unit;

	@SerializedName("value")
	private double value;

	public String getUnit(){
		return unit;
	}

	public double getValue(){
		return value;
	}
}