package com.boss.cuncis.bukatoko.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.WaybillAdapter;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.Waybill;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WaybillActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    TextView tvReceiver, tvStatus;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waybill);

        bundle = getIntent().getExtras();

        tvStatus = findViewById(R.id.tv_status);
        tvReceiver = findViewById(R.id.tv_receiver);
        progressBar = findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview_waybill);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getWaybill(bundle.getString("WAYBILL"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Riwayat Pengiriman");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getWaybill(String NO_RESI) {
        ApiInterface apiInterface = new ApiClient().getClientRajaOngkir(Constant.BASE_URL_RAJAONGKIR_PRO).create(ApiInterface.class);
        Call<Waybill> call = apiInterface.getWaybill(Constant.KEY_RAJAONGKIR, NO_RESI, "jne");
        call.enqueue(new Callback<Waybill>() {
            @Override
            public void onResponse(Call<Waybill> call, Response<Waybill> response) {
                Waybill.RajaOngkir.Result result = response.body().getRajaOngkir().getResult();
                Waybill.RajaOngkir.Result.DeliveryStatus status = result.getDeliveryStatus();
                tvReceiver.setText(status.getPod_receiver());
                tvStatus.setText(status.getStatus());

                List<Waybill.RajaOngkir.Result.Manifest> manifests = result.getManifest();
                recyclerView.setAdapter(new WaybillAdapter(manifests, WaybillActivity.this));

                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Waybill> call, Throwable t) {

            }
        });
    }
}

























