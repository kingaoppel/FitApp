package com.example.fitapp.remote.modelProduct;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Product{

	@SerializedName("shoppingListUnits")
	private List<String> shoppingListUnits;

	@SerializedName("image")
	private String image;

	@SerializedName("amount")
	private double amount;

	@SerializedName("original")
	private String original;

	@SerializedName("categoryPath")
	private List<String> categoryPath;

	@SerializedName("unitLong")
	private String unitLong;

	@SerializedName("possibleUnits")
	private List<String> possibleUnits;

	@SerializedName("estimatedCost")
	private EstimatedCost estimatedCost;

	@SerializedName("aisle")
	private String aisle;

	@SerializedName("consistency")
	private String consistency;

	@SerializedName("originalName")
	private String originalName;

	@SerializedName("unit")
	private String unit;

	@SerializedName("nutrition")
	private Nutrition nutrition;

	@SerializedName("unitShort")
	private String unitShort;

	@SerializedName("meta")
	private List<Object> meta;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	public List<String> getShoppingListUnits(){
		return shoppingListUnits;
	}

	public String getImage(){
		return image;
	}

	public double getAmount(){
		return amount;
	}

	public String getOriginal(){
		return original;
	}

	public List<String> getCategoryPath(){
		return categoryPath;
	}

	public String getUnitLong(){
		return unitLong;
	}

	public List<String> getPossibleUnits(){
		return possibleUnits;
	}

	public EstimatedCost getEstimatedCost(){
		return estimatedCost;
	}

	public String getAisle(){
		return aisle;
	}

	public String getConsistency(){
		return consistency;
	}

	public String getOriginalName(){
		return originalName;
	}

	public String getUnit(){
		return unit;
	}

	public Nutrition getNutrition(){
		return nutrition;
	}

	public String getUnitShort(){
		return unitShort;
	}

	public List<Object> getMeta(){
		return meta;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}
}