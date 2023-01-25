package com.example.toyproject;

import java.time.LocalDate;

public class Plant {
    private static int AutoNum = 0;
    int id; //식물 ID값
    String name;    //식물 이름
    int wateringCycle; // 물을 주는 주기s
    boolean checkWatering;
    LocalDate plantAddDate; //식물을 키우기 시작한 날
    LocalDate plantWatering;  //물을 줘야 하는 날
    LocalDate nowDate = LocalDate.now();
    Plant(){
        AutoNum ++;
        id = AutoNum;

        plantAddDate = nowDate;
        wateringCycle = 5;
        plantWatering = plantAddDate.plusDays(wateringCycle);
        checkWatering = false;
    }

//    while(today.isAfter(otherDay) ) {
//        otherDay=otherDay.plus(3, ChronoUnit.YEARS);
//    }

    public LocalDate getPlantWatering(){
        if(!checkWatering&&plantWatering.isAfter(nowDate)){

        }
        else if(!checkWatering){
            plantWatering = nowDate.minusDays(1);
        }
        else{
            while (nowDate.isAfter(plantWatering)) {
                plantWatering = plantWatering.plusDays(wateringCycle);
                checkWatering = false;
            }
        }
        return plantWatering;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setPlantAddDate(LocalDate date){
        plantAddDate = date;
        plantWatering = plantAddDate.plusDays(wateringCycle);
    }
    public void setWateringCycle(int wateringCycle){
        this.wateringCycle = wateringCycle;
    }
    public String getName(){
        return name;
    }
    public int getWateringCycle(){
        return wateringCycle;
    }
    public LocalDate getPlantAddDate(){
        return plantAddDate;
    }

}
