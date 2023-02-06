package com.example.toyproject;

import java.time.LocalDate;

//다음 물주는 날 계산


@FunctionalInterface
public interface CalcWaterDate {
	LocalDate calcWaterDate(LocalDate nowDate, int waterFrequency);
}
