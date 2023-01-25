package com.example.toyproject;

import android.icu.text.DateFormat;

import java.time.LocalDate;

public interface InterWateringFrequency {
    LocalDate nowDate = LocalDate.now();

    void setCount(int count);
    int getCount();
    //물주는 횟수 계산하는 메소드
    int calcCount();
}
