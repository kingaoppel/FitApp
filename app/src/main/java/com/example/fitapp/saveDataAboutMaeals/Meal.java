package com.example.fitapp.saveDataAboutMaeals;

import com.example.fitapp.MyProduct;
import com.example.fitapp.remote.modelProduct.Product;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Meal implements Serializable {

    private List<MyProduct> items;
    private Double calories;
    private Double proteins;
    private Double fats;
    private Double carbo;
    private String note;

    public Meal() {
    }

    public Meal(List<MyProduct> items, Double calories, Double proteins, Double fats, Double carbo, String note) {
        this.items = items;
        this.calories = calories;
        this.proteins = proteins;
        this.fats = fats;
        this.carbo = carbo;
        this.note = note;
    }

    public List<MyProduct> getItems() {
        return items;
    }

    public void setItems(List<MyProduct> items) {
        this.items = items;
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
