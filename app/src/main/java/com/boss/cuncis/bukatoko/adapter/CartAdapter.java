package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.CartActivity;
import com.boss.cuncis.bukatoko.activity.MainActivity;
import com.boss.cuncis.bukatoko.data.model.Cart;
import com.boss.cuncis.bukatoko.utils.Converter;
import com.boss.cuncis.bukatoko.utils.GlideImage;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {

    private List<Cart> cartList;
    private Context context;

    public CartAdapter(List<Cart> cartList, Context context) {
        this.cartList = cartList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartHolder holder, final int position) {
//        holder.tvName.setText(cartList.get(position).getProduct());
        final Cart cart = cartList.get(position);
        cart.setQty(1);
        cart.setTotal(cart.getTotal());

        GlideImage.get(context, cart.getImage(), holder.imgProduct);
        holder.tvName.setText(cart.getProduct());
        holder.tvPrice.setText(Converter.rupiah(cart.getPrice()));

        holder.spinQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                int total = Integer.valueOf(parent.getItemAtPosition(i).toString()) * cart.getPrice();
                holder.tvTotal.setText(Converter.rupiah(total));

                cart.setQty(Integer.valueOf(parent.getItemAtPosition(i).toString()));
                cart.setTotal(total);

                getTotal();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(false);
                builder.setTitle("Konfirmasi");
                builder.setMessage("Yakin menghapus " + cart.getProduct() + " dari keranjang?");
                builder.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        App.sqLiteHelper.removeItem(String.valueOf(cart.getCart_id()));
                        cartList.remove(position);
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    class CartHolder extends RecyclerView.ViewHolder {
        ImageView imgProduct;
        TextView tvName, tvPrice, tvTotal;
        ImageButton btnDelete;
        Spinner spinQty;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.img_product);
            tvName = itemView.findViewById(R.id.tv_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            tvTotal = itemView.findViewById(R.id.tv_total);
            spinQty = itemView.findViewById(R.id.spin_qty);

            ArrayList<String> arrayList = new ArrayList<>();
            for (int i = 1; i <= 100; i++) {
                arrayList.add(String.valueOf(i));
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayList);
            spinQty.setAdapter(adapter);

        }
    }

    public int getTotal() {
        int priceTotal = 0;

        for (int i = 0; i < cartList.size(); i++) {
            priceTotal += cartList.get(i).getTotal();
        }
        CartActivity.tvPriceTotal.setText("TOTAL: Rp. " + Converter.rupiah(priceTotal));
        return priceTotal;
    }

}














