package com.example.toyproject;

import java.time.LocalDate;



public class Plant implements Comparable<Plant> {
	private static int autoNum = 0;
	private String plantSpecies;
	
	private int plantId;
	private String plantName;
	private LocalDate addDate;
	private LocalDate waterDate;
	private int waterFrequency;
	private boolean waterCheck;
	private LocalDate nowDate = LocalDate.now();
	
	CalcWaterDate calcWaterDate = (n,w) -> (n.plusDays(w));
	CalcWaterCheck todayWater = (w,n)->{if(n.isAfter(w)) {
		return true;} else return false;
	};

	Plant(){
		autoNum++;
		plantId = 10000 + autoNum;
//		waterDate = calcWaterDate.calcWaterDate(nowDate, waterFrequency);
		waterCheck = false;
	}
	//새로 만든 식물
	Plant(String plantSpecies, String plantName, LocalDate addDate, int waterFrequency){
		autoNum++;
		plantId = 10000 + autoNum;
		waterDate = calcWaterDate.calcWaterDate(nowDate, waterFrequency);
		
		this.plantSpecies=plantSpecies;
		this.plantName =plantName;
		this.addDate=addDate;
		this.waterFrequency = waterFrequency;
		waterCheck = todayWater.todayWater(waterDate,nowDate);
		
	}
	//만들어진 식물.
	Plant(String plantSpecies, String plantName, LocalDate addDate, LocalDate waterDate, int waterFrequency){
		autoNum++;
		plantId = 10000 + autoNum;
		
		this.waterDate = waterDate;
		this.plantSpecies=plantSpecies;
		this.plantName =plantName;
		this.addDate=addDate;
		this.waterFrequency = waterFrequency;
		waterCheck = todayWater.todayWater(this.waterDate,nowDate);
		
	}
	//물을 줘야하는지 확인
	public boolean countWater() {
		return waterCheck;
		
	}
	//물을 줌.
	public boolean getWater() {
		if(waterCheck) {
		waterCheck = false;
		waterDate = calcWaterDate.calcWaterDate(nowDate, waterFrequency);
		return true;
		}
		else return false;
	}
	
	public void setPlantName(String plantName) {
		this.plantName = plantName;
	}

	public int getPlantId() {
		return plantId;
	}


	public LocalDate getAddDate() {
		return addDate;
	}

	public void setAddDate(LocalDate addDate) {
		this.addDate = addDate;
	}

	public LocalDate getWaterDate() {
		return waterDate;
	}

	public void setWaterDate(LocalDate waterDate) {
		this.waterDate = waterDate;
	}

	public int getWaterFrequency() {
		return waterFrequency;
	}

	public void setWaterFrequency(int waterFrequency) {
		this.waterFrequency = waterFrequency;
	}

	public boolean isWaterCheck() {
		return waterCheck;
	}

	public void setWaterCheck(boolean waterCheck) {
		this.waterCheck = waterCheck;
	}

	public String getPlantName() {
		return plantName;
	}
	public String getPlantSpecies() {
		return plantSpecies;
	}

	public void setPlantSpecies(String plantSpecies) {
		this.plantSpecies = plantSpecies;
	}
	
	@Override
	public int hashCode() {
		return plantId;
	}
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Plant) {
			Plant plant = (Plant) obj;
			if(this.plantId == plant.plantId) {
				return true;
			}
			else return false;
		}
		else return false;
	}
	@Override
	public String toString() {
		return plantName + " Id: " +plantId; 
	}
	@Override
	public int compareTo(Plant o) {
		// TODO Auto-generated method stub
		return this.plantId - o.plantId;
	}
	
	

}
