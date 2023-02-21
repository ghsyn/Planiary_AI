package com.example.toyproject;

import java.util.HashMap;
import java.util.Iterator;


public class PlantHashMap {
	private HashMap<Integer ,Plant> hashMap;

	
	public PlantHashMap() {
		hashMap = new HashMap<>();
	}
	public void addPlant(Plant plant) {
		hashMap.put(plant.getPlantID(), plant);
	}
	public boolean removePlant(int plantId) {
		if(hashMap.containsKey(plantId)) {
			hashMap.remove(plantId);
			return true;
		}
		return false;
	}
	//물 줘야하는 식물 수
	public int waterCount() {
		Iterator<Integer> ir = hashMap.keySet().iterator();
		int count = 0;
		while(ir.hasNext()) {
			int key = ir.next();
			Plant plant = hashMap.get(key);
			if(plant.getCount()) {
				count ++;
			}
		}
		return count;
	}
	public Plant getPlant(int plantId) {
		if(hashMap.containsKey(plantId)) {
			return hashMap.get(plantId);
		}
		return null;
	}

	//물을 줌
	public boolean getWater(int plantId) {
		if(hashMap.containsKey(plantId)) {
			Plant plant = hashMap.get(plantId);
			plant.getWater();
			return true;
		}
		return false;
	}
	
	public int getSize() {
		return hashMap.size();
	}

	public String getWaterDate(int plantId) {
		if(hashMap.containsKey(plantId)) {
			Plant plant = hashMap.get(plantId);
			
			return plant.getWaterDate().toString();
		}
		return null;
	}
	public void showAllPlant() {
		Iterator<Integer> ir = hashMap.keySet().iterator();
		while(ir.hasNext()) {
			int key = ir.next();
			Plant plant = hashMap.get(key);
			System.out.println(plant);
		}
		System.out.println();
	}
}
