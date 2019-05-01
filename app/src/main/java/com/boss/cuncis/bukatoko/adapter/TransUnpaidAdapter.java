package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.TransDetailActivity;
import com.boss.cuncis.bukatoko.activity.UploadActivity;
import com.boss.cuncis.bukatoko.data.model.transaction.TransUser;

import java.util.List;

public class TransUnpaidAdapter extends RecyclerView.Adapter<TransUnpaidAdapter.TransHolder> {

    private List<TransUser.Data> transUnpaid;
    private Context context;

    public TransUnpaidAdapter(List<TransUser.Data> transUnpaid, Context context) {
        this.transUnpaid = transUnpaid;
        this.context = context;
    }

    @NonNull
    @Override
    public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_trans, parent, false);
        return new TransHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransHolder holder, int i) {
        final TransUser.Data data = transUnpaid.get(i);

        if (data.getStatus_transaction().equals("pending")) {
            holder.btnAction.setVisibility(View.GONE);
        } else {
            holder.btnAction.setText("Upload Bukti Transfer");
            holder.btnAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UploadActivity.class);
                    intent.putExtra("TRANSACTION_CODE", data.getTransaction_code());
                    context.startActivity(intent);
                }
            });
        }

        holder.tvTitle.setText(data.getTransaction_code());
        holder.tvStatus.setText(data.getStatus_transaction());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransDetailActivity.class);
                intent.putExtra("TRANSACTION_CODE", data.getTransaction_code());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return transUnpaid.size();
    }

    class TransHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStatus;
        Button btnAction;
        CardView cardView;

        public TransHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvStatus = itemView.findViewById(R.id.tv_status);
            btnAction = itemView.findViewById(R.id.btn_action);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

}
