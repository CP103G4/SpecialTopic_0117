package com.chia.myrecycleview.goods;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chia.myrecycleview.R;
import com.chia.myrecycleview.GoodsTask.CommomTask;
import com.chia.myrecycleview.GoodsTask.Common;
import com.chia.myrecycleview.GoodsTask.ImageTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GoodMainclassFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String url = Common.URL + "/Spot_MySQL_Web";//提供服務的網頁名
    private static final String TAG = "GoodsListFragment";
    private CommomTask retrieveCategoryTask;
    private List<Goods> goodsList = null;
    private List<String> subClassificationList = null;
    private FragmentActivity activity;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mainClass;
    private SwipeRefreshLayout swipeRefreshLayout;

    public GoodMainclassFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GoodListManFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GoodMainclassFragment newInstance(String param1, String param2) {
        GoodMainclassFragment fragment = new GoodMainclassFragment();
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
            mainClass = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = getActivity();
        View viewFragmentGoodSub = inflater.inflate(R.layout.fragment_good_subclass, container, false);
        RecyclerView subClassification = viewFragmentGoodSub.findViewById(R.id.rvGoodsSubClass);
        subClassification.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        getGoods();
        getSubClassificationList();
        swipeRefreshLayout = viewFragmentGoodSub.findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                goodsList = getGoods();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        subClassification.setAdapter(new SubClassification(activity, subClassificationList));
        return viewFragmentGoodSub;
    }

    private class SubClassification extends RecyclerView.Adapter<SubClassification.MyViewHolder> {
        Context context;
        List<String> subClassificationList;

        public SubClassification(Context context, List<String> subClassificationList) {
            this.context = context;
            this.subClassificationList = subClassificationList;
        }

        @NonNull
        @Override
        public SubClassification.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.fragment_good_list, viewGroup, false);
//            RecyclerView rvGoods = itemView.findViewById(R.id.rvGoodsItem);
//            rvGoods.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
//            pagerSnapHelper(rvGoods);
//            getGoods();
//            List<Goods> subclassGoodsList = new ArrayList<>();//new ArrayList<String>();
//            for (int j = 0; j < goodsList.size(); j++){
//                if (goodsList.get(j).getSubClass() == i){
//                    subclassGoodsList.add(goodsList.get(j));
//                }
//            }
//            rvGoods.setAdapter(new GoodsAdapter(activity, subclassGoodsList));
            return new SubClassification.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull SubClassification.MyViewHolder myViewHolder, int i) {
            myViewHolder.tvGoodsSubClass.setText(subClassificationList.get(i));

            List<Goods> subclassGoodsList = new ArrayList<>();//new ArrayList<String>();
            for (int j = 0; j < goodsList.size(); j++){
                if (goodsList.get(j).getSubClass() == i){
                    subclassGoodsList.add(goodsList.get(j));
                }
            }
            myViewHolder.rvGoodsItem.setAdapter(new GoodsAdapter(activity, subclassGoodsList));
            myViewHolder.rvGoodsItem.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
            pagerSnapHelper(myViewHolder.rvGoodsItem);
        }

        @Override
        public int getItemCount() {
            return subClassificationList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvGoodsSubClass;
            RecyclerView rvGoodsItem;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                tvGoodsSubClass = itemView.findViewById(R.id.tvGoodsSubClass);
                rvGoodsItem = itemView.findViewById(R.id.rvGoodsItem);
            }
        }
    }

    private class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.MyViewHolder> {
        Context context;
        List<Goods> subclassGoodsList;

        public GoodsAdapter(Context context, List<Goods> subclassGoodsList) {
            this.context = context;
            this.subclassGoodsList = subclassGoodsList;
        }

        @Override
        public int getItemCount() {
            return subclassGoodsList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvName;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.imageView);
                tvName = itemView.findViewById(R.id.tvName);
            }
        }

        @NonNull
        @Override
        public GoodsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View itemView = layoutInflater.inflate(R.layout.item_view, viewGroup, false);
            return new GoodsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull GoodsAdapter.MyViewHolder viewHolder, int i) {
            final Goods subclass = subclassGoodsList.get(i);
            int id = subclass.getId();
            int imageSize = getResources().getDisplayMetrics().widthPixels / 4;//圖片縮小式螢幕幾倍

            ImageTask goodsImageTask = new ImageTask(url, id, imageSize, viewHolder.imageView);
            goodsImageTask.execute();
            viewHolder.tvName.setText(subclass.getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, GoodsDetailFragment.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subclassGoodsList", subclass);
                    intent.putExtras(bundle);
                    /* 呼叫startActivity()開啟新的頁面 */
                    startActivity(intent);
                }
            });
        }
    }

    private List<Goods> getGoods() {
        List<Goods> goodsListReceive = null;
        if (networkConnected(activity)) {//檢查是否連網
            url = Common.URL + "/GoodsServlet";//提供服務的網頁名
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("param", mainClass);
            retrieveCategoryTask = new CommomTask(url, jsonObject.toString());//透過contructor"呼叫",但還沒"啟動"
            try {
                String jsonIn = retrieveCategoryTask.execute().get();//"execute啟動傳輸",get取得結果來show
                Type listType = new TypeToken<List<Goods>>() {
                }.getType();
                goodsListReceive = new Gson().fromJson(jsonIn, listType);//Android CH05
            } catch (Exception e) {
                Log.e(TAG, e.toString());//log.e就是error
            }
            if (goodsListReceive == null || goodsListReceive.isEmpty()) {
                Toast.makeText(getActivity(), R.string.text_NoCategoriesFound, Toast.LENGTH_SHORT).show();
            } else {
                goodsList = goodsListReceive;
            }
        } else {
            Toast.makeText(getActivity(), R.string.text_NoNetwork, Toast.LENGTH_SHORT).show();
        }
        return goodsList;
    }

    // check if the device connect to the network
    private boolean networkConnected(Activity activity) {
        ConnectivityManager conManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    private void pagerSnapHelper(RecyclerView rvGoods) {//一次只翻一頁
        /* 不處理捲動事件所以監聽器設為null */
        rvGoods.setOnFlingListener(null);
        /* 如果希望一次滑動一頁資料，要加上PagerSnapHelper物件 */
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(rvGoods);
    }

    private List<String> getSubClassificationList() {
        List<String> subClassificationListReceive = new ArrayList<String>();
        subClassificationListReceive.add("clothes");
        subClassificationListReceive.add("pants");
        subClassificationListReceive.add("underwear");
        subClassificationListReceive.add("shoes");
        subClassificationListReceive.add("accessories");
        subClassificationList = subClassificationListReceive;
        Log.d(TAG, subClassificationList.toString());
        return subClassificationList;
    }

}
