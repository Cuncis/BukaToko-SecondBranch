package com.boss.cuncis.bukatoko.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.City;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityHolder> implements Filterable {

    private List<City.RajaOngkir.Results> results;
    private List<City.RajaOngkir.Results> resultsFilter;
    private Context context;

    public CityAdapter(List<City.RajaOngkir.Results> results, Context context) {
        this.results = results;
        this.context = context;
        this.resultsFilter = results;
    }

    @NonNull
    @Override
    public CityHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_city, parent, false);
        return new CityHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityHolder holder, final int position) {
        holder.tvCity.setText(resultsFilter.get(position).getCity_name());
        holder.tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.DESTINATION = resultsFilter.get(position).getCity_id();
                Constant.DESTINATION_NAME = resultsFilter.get(position).getCity_name();
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsFilter.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    resultsFilter = results;
                } else {
                    List<City.RajaOngkir.Results> resultsList = new ArrayList<>();
                    for (City.RajaOngkir.Results row: results) {
                        if (row.getCity_name().toLowerCase().contains(charString.toLowerCase())) {
                            resultsList.add(row);
                        }
                    }

                    resultsFilter = resultsList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = resultsFilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                resultsFilter = (List<City.RajaOngkir.Results>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class CityHolder extends RecyclerView.ViewHolder {
        TextView tvCity;

        public CityHolder(@NonNull View itemView) {
            super(itemView);
            tvCity = itemView.findViewById(R.id.tv_city);
        }
    }

}
