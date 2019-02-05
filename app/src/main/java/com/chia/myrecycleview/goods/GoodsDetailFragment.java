package com.chia.myrecycleview.goods;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.chia.myrecycleview.GoodsTask.Common;
import com.chia.myrecycleview.R;
import com.chia.myrecycleview.GoodsTask.ImageTask;

import java.util.concurrent.ExecutionException;

public class GoodsDetailFragment extends AppCompatActivity {
    private ImageTask goodsImageTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_good_detail);
        showResults();
    }

    private void showResults() {
        ImageView imageView = findViewById(R.id.imageView);
        TextView tvName = findViewById(R.id.tvName);
        TextView tvDescript = findViewById(R.id.tvDescript);
        TextView tvPrice = findViewById(R.id.tvPrice);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Goods goods = (Goods) bundle.getSerializable("goods");
            if (goods != null) {
                String url = Common.URL + "/GoodsServlet";
                int id = goods.getId();
                int imageSize = getResources().getDisplayMetrics().widthPixels / 3;
                goodsImageTask = new ImageTask(url, id, imageSize);
                Bitmap bitmap = null;

                try {
                    bitmap = goodsImageTask.execute().get();
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
                tvName.setText(goods.getName());
                tvDescript.setText(goods.getDescription());
                tvPrice.setText(String.valueOf(goods.getPrices()));
            }
        }
    }
}
