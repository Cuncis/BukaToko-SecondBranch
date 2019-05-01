package com.boss.cuncis.bukatoko.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.Waybill;

import java.util.List;

public class WaybillAdapter extends RecyclerView.Adapter<WaybillAdapter.WaybillHolder> {

    private List<Waybill.RajaOngkir.Result.Manifest> manifests;
    private Context context;

    public WaybillAdapter(List<Waybill.RajaOngkir.Result.Manifest> manifests, Context context) {
        this.manifests = manifests;
        this.context = context;
    }

    @NonNull
    @Override
    public WaybillHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_waybill, parent, false);
        return new WaybillHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaybillHolder holder, int i) {
        Waybill.RajaOngkir.Result.Manifest manifest = manifests.get(i);

        holder.tvDesc.setText(manifest.getManifest_description());
        holder.tvDate.setText(manifest.getManifest_date() + " " + manifest.getManifest_time());
    }

    @Override
    public int getItemCount() {
        return manifests.size();
    }

    class WaybillHolder extends RecyclerView.ViewHolder {
        TextView tvDesc, tvDate;

        public WaybillHolder(@NonNull View itemView) {
            super(itemView);
            tvDesc = itemView.findViewById(R.id.tv_description);
            tvDate = itemView.findViewById(R.id.tv_datetime);
        }
    }

}
