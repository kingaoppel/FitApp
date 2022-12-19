package com.example.fitapp.saveDataAboutMaeals;

import com.example.fitapp.MyProduct;
import com.example.fitapp.remote.modelProduct.Product;

import java.util.List;
import java.util.Map;

public class Meal {

    private List<String> itemsName; // maslo, mleko, miodek
    private List<Double> itemsAmount; //100, 80, 90
    private Double calories;
    private Double proteins;
    private Double fats;
    private Double carbo;
    private String note;

    public Meal() {
    }

    public Meal(List<String> itemsName, List<Double> itemsAmount, Double calories, Double proteins, Double fats, Double carbo, String note) {
        this.itemsName = itemsName;
        this.itemsAmount = itemsAmount;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbo = carbo;
        this.note = note;
    }

    public List<String> getItemsName() {
        return itemsName;
    }

    public void setItemsName(List<String> itemsName) {
        this.itemsName = itemsName;
    }

    public List<Double> getItemsAmount() {
        return itemsAmount;
    }

    public void setItemsAmount(List<Double> itemsAmount) {
        this.itemsAmount = itemsAmount;
    }

    public Double getCalories() {
        return calories;
    }

    public void setCalories(Double calories) {
        this.calories = calories;
    }

    public Double getProteins() {
        return proteins;
    }

    public void setProteins(Double proteins) {
        this.proteins = proteins;
    }

    public Double getFats() {
        return fats;
    }

    public void setFats(Double fats) {
        this.fats = fats;
    }

    public Double getCarbo() {
        return carbo;
    }

    public void setCarbo(Double carbo) {
        this.carbo = carbo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
