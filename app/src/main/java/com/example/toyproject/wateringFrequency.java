package com.example.toyproject;

import java.time.LocalDate;
import java.util.ArrayList;

public class wateringFrequency implements InterWateringFrequency{

    private int count=0;
    Plant pla;
    ArrayList<Plant> date = new ArrayList<Plant>();

    public void putDate(LocalDate a) {
        pla = new Plant();
        pla.setPlantAddDate(a);
        date.add(pla);
    }

    public ArrayList<Plant> getDate() {
        return date;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int calcCount() {
        for(int i = 0; i<date.size();i++){
            if(nowDate.isAfter(date.get(i).getPlantWatering())){
                count++;
            }
        }


        return count;
    }
}
