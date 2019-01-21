package com.chia.myrecycleview.ShoppingCar;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

//import idv.ron.easygo.R;
//import idv.ron.easygo.order.CartActivity;
//import idv.ron.easygo.product.ProductListActivity;
//import idv.ron.easygo.user.UserActivity;

public class Common {
    // 模擬器連Tomcat
//    public static String URL = "http://10.1.33.202:8080/EasyGo_MySQL_Web/";
    public static String URL = "http://10.0.2.2:8080/EasyGo_MySQL_Web";
//    public static String URL = "http://54.65.214.19:8080/EasyGo_MySQL_Web/";

    // 偏好設定檔案名稱
    public final static String PREF_FILE = "preference";

    // 要讓商品在購物車內順序能夠一定，且使用RecyclerView顯示時需要一定順序，List較佳
    public static List<OrderProduct> CART = new ArrayList<>();
//
//    // 功能分類
//    public final static Category[] CATEGORIES = {
//            new Category(0, "User", R.drawable.user, UserActivity.class),
//            new Category(1, "Products", R.drawable.product, ProductListActivity.class),
//            new Category(2, "Shopping cart", R.drawable.cart_empty, CartActivity.class),
//            new Category(3, "Settings", R.drawable.setting, ChangeUrlActivity.class)};

    // check if the device connect to the network
    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager != null ? conManager.getActiveNetworkInfo() : null;
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
