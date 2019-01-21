package com.chia.myrecycleview;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chia.myrecycleview.Login.Common;
import com.chia.myrecycleview.Login.Login_Activity;
import com.chia.myrecycleview.ShoppingCar.ShoppingCarFragment;
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
                    fragment = new ShoppingCarFragment();
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

    @Override
    protected void onResume() {
        super.onResume();
        // 從偏好設定檔中取得登入狀態來決定是否顯示「登出」
        SharedPreferences pref = getSharedPreferences(Common.PREF_FILE,
                MODE_PRIVATE);
        boolean login = pref.getBoolean("login", false);
        Intent intent = new Intent(this, Login_Activity.class);
        if (login) {

        } else {
            if (getIntent().getExtras() == null) {
                /* 呼叫startActivity()開啟新的頁面 */
                startActivity(intent);
            } else {
                Bundle bundle = getIntent().getExtras();
                int onIgnore = bundle.getInt("onIgnore");
            }
        }
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
