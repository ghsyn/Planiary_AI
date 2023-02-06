package com.example.toyproject;

import java.time.LocalDate;

//오늘 물줘야하는지 check
//true가 나오면 +, false가 나오면 0 해서 계산.

@FunctionalInterface
public interface CalcWaterCheck {
	boolean todayWater(LocalDate waterDate, LocalDate nowDate);
}
