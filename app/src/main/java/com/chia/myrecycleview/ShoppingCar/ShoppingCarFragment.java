package com.chia.myrecycleview.ShoppingCar;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.chia.myrecycleview.R;
import com.chia.myrecycleview.myFavorite.MyFavoriteViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import static com.chia.myrecycleview.ShoppingCar.Common.CART;


public class ShoppingCarFragment extends Fragment {

    private FragmentActivity activity;
    private Gson gson;
    private RecyclerView rvItems;
    private LinearLayout layoutEmpty;
    private TextView tvTotal;
    private MyFavoriteViewModel mViewModel;
    private ImageTask productImageTask;


    public static ShoppingCarFragment newInstance() {
        return new ShoppingCarFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        activity = getActivity();
        View viewFragmentShoppingCar = inflater.inflate(R.layout.fragment_shoppingcart, container, false);

        findViews(viewFragmentShoppingCar);
        // 訂單帶有日期時間，最好指定轉換成JSON時的格式
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return viewFragmentShoppingCar;
    }

    private void findViews(View view) {
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        tvTotal = view.findViewById(R.id.tvTotal);
        rvItems = view.findViewById(R.id.rvItems);
        rvItems.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));

        showTotal(getOrderProduct());
        rvItems.setAdapter(new CartRecyclerViewAdapter(activity, CART));
    }

    private class CartRecyclerViewAdapter extends RecyclerView.Adapter<CartRecyclerViewAdapter.MyViewHolder> {
        private Context context;
        private LayoutInflater layoutInflater;
        private List<OrderProduct> orderProducts;
        private int imageSize;

        CartRecyclerViewAdapter(Context context, List<OrderProduct> orderProducts) {
            this.context = context;
            layoutInflater = LayoutInflater.from(context);
            this.orderProducts = orderProducts;
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            View itemView;
            ImageView ivProduct;
            ImageView ivRemove;
            TextView tvName;
            TextView tvPrice;
            Spinner spQty;

            MyViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;
                ivProduct = itemView.findViewById(R.id.ivProduct);
                ivRemove = itemView.findViewById(R.id.ivRemove);
                tvName = itemView.findViewById(R.id.tvName);
                tvPrice = itemView.findViewById(R.id.tvPrice);
                spQty = itemView.findViewById(R.id.spQty);
            }
        }

        @Override
        public int getItemCount() {
            if (CART.size() <= 0) {
                layoutEmpty.setVisibility(View.VISIBLE);
            } else {
                layoutEmpty.setVisibility(View.GONE);
            }
            return orderProducts.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.cart_recyclerview_item, parent, false);
            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
            final OrderProduct orderProduct = orderProducts.get(position);

            String url = Common.URL + "/ProductServlet";
            int id = orderProduct.getId();
            productImageTask = new ImageTask(url, id, imageSize, myViewHolder.ivProduct);
            productImageTask.execute();
            myViewHolder.tvName.setText(orderProduct.getName());
            myViewHolder.tvPrice.setText(String.valueOf(orderProduct.getPrice()));
            myViewHolder.spQty.setSelection(orderProduct.getQuantity() - 1, true);
            myViewHolder.spQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent,
                                           View view, int position, long id) {
                    int quantity = Integer.parseInt(parent
                            .getItemAtPosition(position).toString());
                    orderProduct.setQuantity(quantity);
                    showTotal(CART);
                    Common.showToast(context,
                            getString(R.string.msg_NewQty) + " " +
                                    orderProduct.getQuantity());
                }

                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });
            myViewHolder.ivRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String message = getString(R.string.cartRemove) + "「"
                            + orderProduct.getName() + "」?";
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.cart)
                            .setTitle(R.string.cartRemove)
                            .setMessage(message)
                            .setPositiveButton(R.string.text_btYes,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            CART.remove(orderProduct);
                                            showTotal(CART);
                                            CartRecyclerViewAdapter.this
                                                    .notifyDataSetChanged();
                                        }
                                    })
                            .setNegativeButton(R.string.text_btNo,
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(
                                                DialogInterface dialog,
                                                int which) {
                                            dialog.cancel();
                                        }
                                    }).setCancelable(false).show();
                }
            });
        }
    }

    private void showTotal(List<OrderProduct> orderProductList) {
        double total = 0;
        for (OrderProduct orderProduct : orderProductList) {
            total += orderProduct.getPrice() * orderProduct.getQuantity();
        }
        String text = "Total: " + total;
        tvTotal.setText(text);
    }

    private List<OrderProduct> getOrderProduct() {
        List<OrderProduct> orderProductList = new ArrayList<>();
        orderProductList.add(new OrderProduct(1, "ProductA", 150.0, "this is a underwear", 1));
        orderProductList.add(new OrderProduct(1, "ProductB", 250.0, "this is a underwear", 1));
        orderProductList.add(new OrderProduct(2, "ProductB", 950.0, "this is a dress", 3));
        CART = orderProductList;
        return CART;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MyFavoriteViewModel.class);
        // TODO: Use the ViewModel
    }
}
