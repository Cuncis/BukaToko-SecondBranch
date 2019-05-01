package com.boss.cuncis.bukatoko.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.CartActivity;
import com.boss.cuncis.bukatoko.data.Constant;

public class CartDialog {

    public void showCartDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_cart, null);
        builder.setView(view);

        view.findViewById(R.id.btn_cart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, CartActivity.class));
                ((Activity) context).finish();
            }
        });

        view.findViewById(R.id.btn_pay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.SHOP_NOW = true;
                context.startActivity(new Intent(context, CartActivity.class));
                ((Activity) context).finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
