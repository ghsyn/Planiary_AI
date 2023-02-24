package com.example.toyproject;

import static com.example.toyproject.Define.NOWDATE;

import java.time.LocalDate;



public class Plant {
	private final int plantID;
	private final int waterFrequency;
	private final String plantSpecies, plantName;
	private final LocalDate addDate;
	private LocalDate waterDate;


	static CalcWaterDate calcWaterDate = (n, w) -> (n.plusDays(w));
	CalcWaterCheck todayWater = (w,n)->{if(n.isAfter(w)) {
		return true;} else return false;
	};

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Plant)) return false;

		Plant pl = (Plant) o;

		return pl.plantID == plantID && pl.plantName == plantName && pl.plantSpecies == plantSpecies && pl.addDate == addDate;
	}

	@Override
	public int hashCode() {
		return plantID;
	}

	public static class Builder{
		private final String plantSpecies, plantName;
		private final LocalDate addDate;
		private final int waterFrequency;
		private int plantID=0;
		private LocalDate waterDate = null;



		public Builder(String plantSpecies, String plantName, LocalDate addDate, int waterFrequency){
			this.plantSpecies = plantSpecies;
			this.plantName = plantName;
			this.addDate = addDate;
			this.waterFrequency = waterFrequency;
			waterDate = calcWaterDate.calcWaterDate(addDate, waterFrequency);

		}
		public Builder plantID(int val){
			plantID = val;
			return this;
		}
		public Builder waterDate(LocalDate val){
			waterDate = val;
			return this;
		}
		public Plant build(){
			return new Plant(this);
		}

	}
	private Plant(Builder builder){
		addDate = builder.addDate;
		waterDate = builder.waterDate;
		waterFrequency = builder.waterFrequency;
		plantName = builder.plantName;
		plantSpecies = builder.plantName;
		plantID = makePlantID(builder.plantID);
	}

	public int makePlantID(int plantID){
		if(plantID ==0){
			int result = plantSpecies != null ? plantSpecies.hashCode() : 0;
			result = 31 * result + (plantName != null ? plantName.hashCode() : 0);
			result = 31 * result + (addDate != null ? addDate.hashCode() : 0);
			return result;
		}
		else return plantID;
	}

	//오늘 물을 주어야할 식물 수
	public boolean getCount(){
		return todayWater.todayWater(waterDate,NOWDATE);
	}

	//물을 줌
	public boolean getWater() {

		waterDate = calcWaterDate.calcWaterDate(NOWDATE, waterFrequency);
		return true;


	}
	public int getPlantID(){return plantID;}

	public int getWaterFrequency() {
		return waterFrequency;
	}

	public String getPlantSpecies() {
		return plantSpecies;
	}

	public String getPlantName() {
		return plantName;
	}

	public LocalDate getAddDate() {
		return addDate;
	}

	public LocalDate getWaterDate() {
		return waterDate;
	}

}

