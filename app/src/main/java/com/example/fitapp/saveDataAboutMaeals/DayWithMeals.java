package com.example.fitapp.saveDataAboutMaeals;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DayWithMeals implements Serializable {
    private Date date;

    private List<Meal> breakfast;
    private List<Meal> dinner;
    private List<Meal> lunch;
    private List<Meal> snack;
    private List<Meal> supper;

    private Double sumCallories;
    private Double sumProteins;
    private Double sumFats;
    private Double sumCarbo;
    // po dodaniu, usunieciu produktu z konkretnego meal, aktualizujemy tu dane

    public DayWithMeals(Date date, List<Meal> breakfast, List<Meal> dinner, List<Meal> lunch, List<Meal> snack, List<Meal> supper, Double sumCallories, Double sumProteins, Double sumFats, Double sumCarbo) {
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
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Meal> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(List<Meal> breakfast) {
        this.breakfast = breakfast;
    }

    public List<Meal> getDinner() {
        return dinner;
    }

    public void setDinner(List<Meal> dinner) {
        this.dinner = dinner;
    }

    public List<Meal> getLunch() {
        return lunch;
    }

    public void setLunch(List<Meal> lunch) {
        this.lunch = lunch;
    }

    public List<Meal> getSnack() {
        return snack;
    }

    public void setSnack(List<Meal> snack) {
        this.snack = snack;
    }

    public List<Meal> getSupper() {
        return supper;
    }

    public void setSupper(List<Meal> supper) {
        this.supper = supper;
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
