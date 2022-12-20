package com.example.fitapp.saveDataAboutMaeals;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DayWithMeals implements Serializable {

    private Date date;

    private Meal breakfast;
    private Meal dinner;
    private Meal lunch;
    private Meal snack;
    private Meal supper;

    private Double sumCallories;
    private Double sumProteins;
    private Double sumFats;
    private Double sumCarbo;
    // po dodaniu, usunieciu produktu z konkretnego meal, aktualizujemy tu dane

    public DayWithMeals(Date date, Meal breakfast, Meal dinner, Meal lunch, Meal snack, Meal supper, Double sumCallories, Double sumProteins, Double sumFats, Double sumCarbo) {
        this.date = date;
        this.breakfast = breakfast;
        this.dinner = dinner;
        this.lunch = lunch;
        this.snack = snack;
        this.supper = supper;
        this.sumCallories = sumCallories;
        this.sumProteins = sumProteins;
        this.sumFats = sumFats;
        this.sumCarbo = sumCarbo;
    }

    public DayWithMeals() {
        sumCallories = 0.0;
        sumProteins = 0.0;
        sumFats = 0.0;
        sumCarbo = 0.0;
    }

    public void setNutritions() {
        Double tempCalories = 0.0;
        Double tempProteins = 0.0;
        Double tempFats = 0.0;
        Double tempCarbo = 0.0;
        if (breakfast != null) {
            tempCalories += breakfast.getCalories();
            tempProteins += breakfast.getProteins();
            tempFats += breakfast.getFats();
            tempCarbo += breakfast.getCarbo();
        }
        if (dinner != null) {
            tempCalories += dinner.getCalories();
            tempProteins += dinner.getProteins();
            tempFats += dinner.getFats();
            tempCarbo += dinner.getCarbo();
        }
        if (lunch != null) {
            tempCalories += lunch.getCalories();
            tempProteins += lunch.getProteins();
            tempFats += lunch.getFats();
            tempCarbo += lunch.getCarbo();
        }
        if (snack != null) {
            tempCalories += snack.getCalories();
            tempProteins += snack.getProteins();
            tempFats += snack.getFats();
            tempCarbo += snack.getCarbo();
        }
        if (supper != null) {
            tempCalories += supper.getCalories();
            tempProteins += supper.getProteins();
            tempFats += supper.getFats();
            tempCarbo += supper.getCarbo();
        }
        this.sumCallories = tempCalories;
        this.sumProteins = tempProteins;
        this.sumFats = tempFats;
        this.sumCarbo = tempCarbo;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
        setNutritions();
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
        setNutritions();
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
        setNutritions();
    }

    public Meal getSnack() {
        return snack;
    }

    public void setSnack(Meal snack) {
        this.snack = snack;
        setNutritions();
    }

    public Meal getSupper() {
        return supper;
    }

    public void setSupper(Meal supper) {
        this.supper = supper;
        setNutritions();
    }

    public Double getSumCallories() {
        return sumCallories;
    }

    public void setSumCallories(Double sumCallories) {
        this.sumCallories = sumCallories;
    }

    public Double getSumProteins() {
        return sumProteins;
    }

    public void setSumProteins(Double sumProteins) {
        this.sumProteins = sumProteins;
    }

    public Double getSumFats() {
        return sumFats;
    }

    public void setSumFats(Double sumFats) {
        this.sumFats = sumFats;
    }

    public Double getSumCarbo() {
        return sumCarbo;
    }

    public void setSumCarbo(Double sumCarbo) {
        this.sumCarbo = sumCarbo;
    }


}
