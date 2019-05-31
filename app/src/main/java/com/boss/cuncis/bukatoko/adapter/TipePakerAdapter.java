package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.PaketActivity;
import com.bumptech.glide.Glide;

public class TipePakerAdapter extends RecyclerView.Adapter<TipePakerAdapter.PaketHolder> {

    private Context context;

    public TipePakerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public PaketHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paket, parent, false);
        return new PaketHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaketHolder holder, int position) {
        switch (position) {
            case 0:
                holder.tvPaket.setText("Paket Rumput Sintetis");
                Glide.with(context)
                        .load(R.drawable.rumputsintetis)
                        .into(holder.imgPaket);
                break;
            case 1:
                holder.tvPaket.setText("Paket Interlock");
                Glide.with(context)
                        .load(R.drawable.interlock)
                        .into(holder.imgPaket);
                break;
            case 2:
                holder.tvPaket.setText("Paket Plester");
                Glide.with(context)
                        .load(R.drawable.plester)
                        .into(holder.imgPaket);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class PaketHolder extends RecyclerView.ViewHolder {
        ImageView imgPaket;
        TextView tvPaket;

        public PaketHolder(@NonNull View itemView) {
            super(itemView);
            imgPaket = itemView.findViewById(R.id.img_item);
            tvPaket = itemView.findViewById(R.id.tv_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, PaketActivity.class);
                    i.putExtra("NUMBER", getAdapterPosition() + 1);
                    context.startActivity(i);
                }
            });
        }
    }

}
