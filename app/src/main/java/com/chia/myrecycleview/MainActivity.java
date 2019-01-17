package com.chia.myrecycleview;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.chia.myrecycleview.goods.HomeTabFragment;
import com.chia.myrecycleview.myFavorite.MyFavoriteFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_myfavotite:
                    fragment = new MyFavoriteFragment();
                    break;
                case R.id.navigation_shoppingCar:
                    fragment = new MyFavoriteFragment();
                    break;
                case R.id.navigation_information:
                    fragment = new MyFavoriteFragment();
                    break;
                default:
                case R.id.navigation_home:
                    fragment = new HomeTabFragment();//暫定首頁到男裝
                    break;
            }
            item.setChecked(true);
            changeFragment(fragment);
            setTitle(item.getTitle());
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        initContent();
    }

    private void initContent() {
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }

    private void changeFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);//取代
        fragmentTransaction.commit();
    }
}
