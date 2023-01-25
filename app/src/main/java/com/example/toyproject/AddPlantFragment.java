package com.example.toyproject;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPlantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlantFragment extends Fragment {
    EditText  plantSelect;
    AutoCompleteTextView addName;
    TextView recommand, addDate;
    ImageButton plantImage;
    Plant plant;
    Button add_btn;
    LocalDate setDate = LocalDate.now();
    ArrayList<Plant> plants;
    ArrayList<String> plantName;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPlantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlantFragment newInstance(String param1, String param2) {
        AddPlantFragment fragment = new AddPlantFragment();
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

//    EditText addName, addDate, plantSelect;
//    TextView recommand;
//    ImageButton plantImage;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_add_plant, container, false);
        addName = (AutoCompleteTextView) view.findViewById(R.id.plant_addname);
        addDate = (TextView)view.findViewById(R.id.plant_adddate);
        plantSelect =(EditText)view.findViewById(R.id.plant_select);
        recommand = (TextView)view.findViewById(R.id.plant_recommend);
        plantImage = (ImageButton)view.findViewById(R.id.image_add);
        add_btn =(Button)view.findViewById(R.id.insert_btn);
        LocalDate nowDate = LocalDate.now();
        String name[] = {"강아지풀", "민들레", "코스모스", "할미꽃","개나리", "다육이"};
//        addDate.setText(nowDate.toString());
        plantName = new ArrayList<>();
        plants = new ArrayList<>();
        for(int i = 0; i<name.length;i++) {
            plant = new Plant();
            plant.setName(name[i]);
            plants.add(plant);
        }
        for(Plant pla :plants){
            plantName.add(pla.getName());}

        addName.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,plantName));

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), datePicker, setDate.getYear(), setDate.getMonthValue(), setDate.getDayOfMonth()).show();
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
    DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            setDate=LocalDate.of(year,month,dayOfMonth);
            addDate.setText(setDate.toString());
        }
    };
}