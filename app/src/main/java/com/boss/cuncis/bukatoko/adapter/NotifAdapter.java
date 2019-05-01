package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.model.Notification;

import java.util.List;

public class NotifAdapter extends RecyclerView.Adapter<NotifAdapter.NotifHolder> {

    private List<Notification.Data> notifications;
    private Context context;

    public NotifAdapter(List<Notification.Data> notifications, Context context) {
        this.notifications = notifications;
        this.context = context;
    }

    @NonNull
    @Override
    public NotifHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_notif, parent, false);
        return new NotifHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifHolder holder, int i) {
        Notification.Data data = notifications.get(i);

        holder.tvMessage.setText(data.getMessage());
        holder.tvDate.setText(data.getDate());
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    class NotifHolder extends RecyclerView.ViewHolder {
        TextView tvMessage, tvDate;

        public NotifHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvDate = itemView.findViewById(R.id.tv_date);
        }
    }

}
