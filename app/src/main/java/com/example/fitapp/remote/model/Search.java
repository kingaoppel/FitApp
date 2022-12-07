package com.example.fitapp.remote.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Search{

	@SerializedName("number")
	private int number;

	@SerializedName("totalResults")
	private int totalResults;

	@SerializedName("offset")
	private int offset;

	@SerializedName("results")
	private List<ResultsItem> results;

	public int getNumber(){
		return number;
	}

	public int getTotalResults(){
		return totalResults;
	}

	public int getOffset(){
		return offset;
	}

	public List<ResultsItem> getResults(){
		return results;
	}
}