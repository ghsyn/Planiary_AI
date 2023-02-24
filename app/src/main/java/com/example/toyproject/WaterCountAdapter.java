package com.example.toyproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class WaterCountAdapter extends BaseAdapter {
    PlantHashMap plantHashMap = PlantHashMap.getInstance();
    ArrayList<Plant> list = new ArrayList<Plant>();
    ArrayList<Boolean> checkList = new ArrayList<Boolean>();
    boolean checkboxState;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {

        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getPlantID();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Context context = viewGroup.getContext();

        //리스트뷰에 아이템이 인플레이트 되어있는지 확인한후
        //아이템이 없다면 아래처럼 아이템 레이아웃을 인플레이트 하고 view객체에 담는다.
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_homewater,viewGroup,false);
        }

        //이제 아이템에 존재하는 텍스트뷰 객체들을 view객체에서 찾아 가져온다
        TextView tv_plantName = (TextView)view.findViewById(R.id.item_plantname);
        TextView tv_plantAddDate = (TextView)view.findViewById(R.id.item_plantdate);
        CheckBox cb_plantCheck = (CheckBox)view.findViewById(R.id.item_checkbox);
        //현재 포지션에 해당하는 아이템에 글자를 적용하기 위해 list배열에서 객체를 가져온다.
        Plant plant = list.get(i);
        checkboxState = checkList.get(i);

        //가져온 객체안에 있는 글자들을 각 뷰에 적용한다
        tv_plantName.setText(plant.getPlantName()); //원래 int형이라면 String으로 형 변환
        tv_plantAddDate.setText(plant.getAddDate().toString());

        if(cb_plantCheck != null) {
            // 체크박스의 상태 변화를 체크한다.
            cb_plantCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    checkboxState = isChecked;
                }
            });
        }
        cb_plantCheck.setChecked(checkboxState);
        checkList.set(i, checkboxState);

        return view;
    }
    public void addItemToList(){
        Iterator<Integer> ir = plantHashMap.getHashMap().keySet().iterator();

        while(ir.hasNext()) {
            int key = ir.next();
            Plant plant = plantHashMap.getHashMap().get(key);
            if(plant.getCount()) {
                list.add(plant);
                checkList.add(false);
            }
        }
    }
    public boolean isCheckboxState(int index){
        return checkList.get(index);
    }
    public void getWater(){
        int index = 0;
        for(boolean boo : checkList){
            if(boo){
                plantHashMap.getWater(list.get(index).getPlantID());
                list.remove(index);
                checkList.remove(index);
            }
            index ++;
        }
    }

}
