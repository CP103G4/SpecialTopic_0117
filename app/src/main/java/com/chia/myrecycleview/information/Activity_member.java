package com.chia.myrecycleview.information;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.chia.myrecycleview.R;

import co.ceryle.segmentedbutton.SegmentedButtonGroup;

public class Activity_member extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        SegmentedButtonGroup sbg = (SegmentedButtonGroup)findViewById(R.id.segmenttedButtonGroup);
        sbg.setOnClickedButtonPosition(new SegmentedButtonGroup.OnClickedButtonPosition() {
            @Override
            public void onClickedButtonPosition(int position) {
                if(position == 0)
                    Toast.makeText(Activity_member.this, "Order", Toast.LENGTH_SHORT).show();
                else  if(position == 1)
                    Toast.makeText(Activity_member.this, "Member", Toast.LENGTH_SHORT).show();
                else  if(position == 2)
                    Toast.makeText(Activity_member.this,"Setting", Toast.LENGTH_SHORT);
            }
        });

    }

}
