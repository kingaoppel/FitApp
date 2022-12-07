package com.example.fitapp.remote.modelProduct;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Nutrition{

	@SerializedName("caloricBreakdown")
	private CaloricBreakdown caloricBreakdown;

	@SerializedName("weightPerServing")
	private WeightPerServing weightPerServing;

	@SerializedName("flavonoids")
	private List<FlavonoidsItem> flavonoids;

	@SerializedName("properties")
	private List<PropertiesItem> properties;

	@SerializedName("nutrients")
	private List<NutrientsItem> nutrients;

	public CaloricBreakdown getCaloricBreakdown(){
		return caloricBreakdown;
	}

	public WeightPerServing getWeightPerServing(){
		return weightPerServing;
	}

	public List<FlavonoidsItem> getFlavonoids(){
		return flavonoids;
	}

	public List<PropertiesItem> getProperties(){
		return properties;
	}

	public List<NutrientsItem> getNutrients(){
		return nutrients;
	}
}