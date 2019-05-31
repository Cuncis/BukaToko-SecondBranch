package com.boss.cuncis.bukatoko.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.TipePakerAdapter;

public class RekomendasiActivity extends AppCompatActivity {

    TipePakerAdapter adapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomendasi);

        adapter = new TipePakerAdapter(this);

        recyclerView = findViewById(R.id.recyclerview_rekomendasi);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Rekomendasi Lapangan");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
