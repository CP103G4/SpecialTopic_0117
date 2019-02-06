package com.chia.myrecycleview.goods;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chia.myrecycleview.R;
import com.chia.myrecycleview.TabOther;

public class HomeTabFragment extends Fragment {
    //主分類
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private static final String ARG_SECTION_NUMBER = "section_number";
    private ViewPager mViewPager;
    private static FragmentActivity activity;

    public HomeTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //取得主分類資料
        //主分類
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(activity.getSupportFragmentManager());

        View viewFragmentGoodMain = inflater.inflate(R.layout.fragment_home_tab, container, false);
        // Set up the ViewPager with the sections adapter.
        mViewPager = viewFragmentGoodMain.findViewById(R.id.vpGoodsMainClass);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //主分類的tab
        TabLayout tabLayout = viewFragmentGoodMain.findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        return viewFragmentGoodMain;
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

        }

        @Override
        public Fragment getItem(int position) {//tab換頁
            switch (position) {
                case 0:
                    return TabOther.newInstance();
                case 1:
                    return TabOther.newInstance();
                case 2:
//                    return GoodMainclassFragment.newInstance(String.valueOf(position),"getAll");
                    return TabOther.newInstance();
                case 3:
                    return GoodMainclassFragment.newInstance(String.valueOf(position),"getWoman");
                case 4:
                    return GoodMainclassFragment.newInstance(String.valueOf(position),"getMan");
                case 5:
                    return GoodMainclassFragment.newInstance(String.valueOf(position),"getKid");
                default:
                    return TabOther.newInstance();
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }
    }
}