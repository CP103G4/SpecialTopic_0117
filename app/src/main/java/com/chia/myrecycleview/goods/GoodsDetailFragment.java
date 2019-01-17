package com.chia.myrecycleview.goods;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chia.myrecycleview.Task.Common;
import com.chia.myrecycleview.R;
import com.chia.myrecycleview.Task.ImageTask;

import java.util.concurrent.ExecutionException;

public class GoodsDetailFragment extends AppCompatActivity {
    ImageView imageView;
    TextView tvName;
    TextView tvPhone;
    private ImageTask spotImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_good_detail);
        showResults();
    }

    private void showResults() {
        ImageView imageView = findViewById(R.id.imageView);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvPhone = findViewById(R.id.tvPhone);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Goods goods = (Goods) bundle.getSerializable("spot");
            if (goods != null) {
                String url = Common.URL + "/SpotServlet";
                int id = goods.getId();
                int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
                spotImageTask = new ImageTask(url, id, imageSize);
                Bitmap bitmap = null;

                try {
                    bitmap = spotImageTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                } else {
                    imageView.setImageResource(R.drawable.marry);
                }
//                imageView.setImageResource(friend.getImageid());
                tvName.setText(goods.getName());
                tvPhone.setText(goods.getPhoneNo());
            }
        }
    }
}
