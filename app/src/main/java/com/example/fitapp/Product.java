package com.example.fitapp;

import java.util.HashMap;
import java.util.Map;

public class Product {

    public String name;
    public String uid;
    public Double calories;
    public Double protein;
    public Double fats;
    public Double carbs;
    public Double quantity;

    public Product() {
    }

    public Product(String name, String owner, Double calories, Double protein, Double fats, Double carbs, Double quantity) {
        this.name = name;
        this.uid = owner;
        this.calories = calories;
        this.protein = protein;
        this.fats = fats;
        this.carbs = carbs;
        this.quantity = quantity;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("name",name);
        if(uid != null){
            result.put("owner",uid);
        }
        result.put("calories",calories);
        result.put("protein",protein);
        result.put("fats",fats);
        result.put("carbs",carbs);
        result.put("quantity",quantity);
        return result;
    }
}
