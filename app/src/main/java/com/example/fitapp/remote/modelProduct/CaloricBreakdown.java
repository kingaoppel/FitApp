package com.example.fitapp.remote.modelProduct;

import com.google.gson.annotations.SerializedName;

public class CaloricBreakdown{

	@SerializedName("percentCarbs")
	private double percentCarbs;

	@SerializedName("percentProtein")
	private double percentProtein;

	@SerializedName("percentFat")
	private double percentFat;

	public double getPercentCarbs(){
		return percentCarbs;
	}

	public double getPercentProtein(){
		return percentProtein;
	}

	public double getPercentFat(){
		return percentFat;
	}
}