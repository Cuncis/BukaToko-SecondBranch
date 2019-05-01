package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.model.transaction.TransDetail;

import java.util.List;

public class TransDetailAdapter extends RecyclerView.Adapter<TransDetailAdapter.TransDetailHolder> {

    private List<TransDetail.Data.DetailTransaction> transactions;
    private Context context;

    public TransDetailAdapter(List<TransDetail.Data.DetailTransaction> transactions, Context context) {
        this.transactions = transactions;
        this.context = context;
    }

    @NonNull
    @Override
    public TransDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trans_detail, parent, false);
        return new TransDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransDetailHolder holder, int i) {
        final TransDetail.Data.DetailTransaction transaction = transactions.get(i);

        holder.tvTitle.setText(transaction.getProduct());
        holder.tvDesc.setText(
                "Harga: " + transaction.getPrice() + ", " +
                        "Jumlah: " + transaction.getQty()
        );
        holder.tvTotal.setText(transaction.getTotal());
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransDetailHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc, tvTotal;

        public TransDetailHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDesc = itemView.findViewById(R.id.tv_description);
            tvTotal = itemView.findViewById(R.id.tv_total);
        }
    }

}
