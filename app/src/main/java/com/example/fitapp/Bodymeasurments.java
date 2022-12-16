package com.example.fitapp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bodymeasurments {

    String uid;
    List<Date> date;
    List<Double> circumference_arm;
    List<Double> circumference_calf;
    List<Double> circumference_chest;
    List<Double> circumference_hip;
    List<Double> circumference_thigh;
    List<Double> circumference_waist;
    List<Double> weight;

    public Bodymeasurments() {
    }

    public Bodymeasurments(String uid, List<Date> date, List<Double> circumference_arm, List<Double> circumference_calf, List<Double> circumference_chest, List<Double> circumference_hip, List<Double> circumference_thigh, List<Double> circumference_waist, List<Double> weight) {
        this.uid = uid;
        this.date = date;
        this.circumference_arm = circumference_arm;
        this.circumference_calf = circumference_calf;
        this.circumference_chest = circumference_chest;
        this.circumference_hip = circumference_hip;
        this.circumference_thigh = circumference_thigh;
        this.circumference_waist = circumference_waist;
        this.weight = weight;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid",uid);
        result.put("date",date);
        result.put("circumference_arm",circumference_arm);
        result.put("circumference_calf",circumference_calf);
        result.put("circumference_chest",circumference_chest);
        result.put("circumference_hip",circumference_hip);
        result.put("circumference_thigh",circumference_thigh);
        result.put("circumference_waist",circumference_waist);
        result.put("weight",weight);
        return result;
    }

}
