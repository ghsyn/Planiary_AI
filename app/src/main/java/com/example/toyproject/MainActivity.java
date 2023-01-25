package com.example.toyproject;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    homeFragment homeFrag;
    FragmentManager manager;
    LinearLayout home_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        init(); //객체 정의
        SettingListener(); //리스너 등록


        //맨 처음 시작할 탭 설정
        bottomNavigationView.setSelectedItemId(R.id.item_home);

        manager = getSupportFragmentManager();
        homeFrag = (homeFragment)manager.findFragmentByTag("home");
//        homeFrag.setWaterCount();



    }

    private void init() {
        home_ly = findViewById(R.id.home_ly);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedListener());
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.item_home: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new homeFragment(),"tag")
                            .commit();
                    return true;
                }
                case R.id.item_addPlants: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new AddPlantFragment())
                            .commit();
                    return true;
                }
                case R.id.item_encyclopedia: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new EncyclopediaFragment())
                            .commit();
                    return true;
                }
                case R.id.item_tips: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new TipsFragment())
                            .commit();
                    return true;
                }
                case R.id.item_aiCamera: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_ly, new AiCameraFragment())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }
}