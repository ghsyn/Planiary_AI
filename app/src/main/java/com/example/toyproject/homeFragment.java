package com.example.toyproject;

import static com.example.toyproject.Define.NOWDATE;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.time.LocalDate;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homeFragment extends Fragment {
    private TextView waterCount;
    PlantHashMap plantHashMap;
    ImageButton btn_waterCount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static homeFragment newInstance(String param1, String param2) {
        homeFragment fragment = new homeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.i("SeongWon","OnCreateView homeFragment");
        View view = null;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        LocalDate date[] = new LocalDate[6];
        for(int i =0; i<6; i++) {
            date[i] = LocalDate.of(2023, 01, 13).plusDays(i);
        }
        waterCount = (TextView)view.findViewById(R.id.TV_count);
        btn_waterCount = (ImageButton) view.findViewById(R.id.water_btn);
        plantHashMap = PlantHashMap.getInstance();
        testPlant(plantHashMap);
        waterCount.setText(String.valueOf(plantHashMap.waterCount()));


        btn_waterCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PlantWaterCountActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public View onResume(LayoutInflater inflater, ViewGroup container,
                         Bundle savedInstanceState) {
        Log.i("SeongWon","OnResume");
        super.onResume();
        View view = null;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        LocalDate date[] = new LocalDate[6];
        for(int i =0; i<6; i++) {
            date[i] = LocalDate.of(2023, 01, 13).plusDays(i);
        }
        waterCount = (TextView)view.findViewById(R.id.TV_count);
        plantHashMap = PlantHashMap.getInstance();
        waterCount.setText(String.valueOf(plantHashMap.waterCount()));
        return view;
    }

    public void testPlant(PlantHashMap plantHashMap){
        plantHashMap.addPlant(new Plant.Builder("a","a",NOWDATE.minusDays(5), 6).build());
        plantHashMap.addPlant(new Plant.Builder("b","b",NOWDATE.minusDays(6), 6).build());
        plantHashMap.addPlant(new Plant.Builder("c","c",NOWDATE.minusDays(7), 6).build());
        plantHashMap.addPlant(new Plant.Builder("d","d",NOWDATE.minusDays(8), 6).build());
    }




}