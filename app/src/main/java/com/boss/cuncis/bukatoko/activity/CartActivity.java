package com.boss.cuncis.bukatoko.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.CartAdapter;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.model.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    public static TextView tvPriceTotal;
    Button btnCheckout;
    RecyclerView recyclerView;

    public static List<Cart> cartList = new ArrayList<>();
    public static CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        cartList.clear();
        cartList.addAll(App.sqLiteHelper.myCart());

        tvPriceTotal = findViewById(R.id.tv_price_total);
        btnCheckout = findViewById(R.id.btn_checkout);
        recyclerView = findViewById(R.id.recyclerview_cart);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CartAdapter(cartList, this);
        recyclerView.setAdapter(adapter);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, OngkirActivity.class));
            }
        });

        getSupportActionBar().setTitle("Keranjang");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (Constant.SHOP_NOW) {
            startActivity(new Intent(this, OngkirActivity.class));
            Constant.SHOP_NOW = false;
        }
    }
}



















