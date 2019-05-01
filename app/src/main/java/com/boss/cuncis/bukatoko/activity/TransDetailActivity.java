package com.boss.cuncis.bukatoko.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.TransDetailAdapter;
import com.boss.cuncis.bukatoko.data.model.transaction.TransDetail;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TransDetailActivity extends AppCompatActivity {

    TextView tvCode, tvPrice, tvName, tvAddress, tvStatus;

    Switch switchTagihan, switchPengiriman;
    LinearLayout linearTagihan, linearPengiriman;

    Button btnTrack;

    ProgressBar progressBar;
    RecyclerView recyclerView;

    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_detail);

        bundle = getIntent().getExtras();

        tvCode = findViewById(R.id.txtCode);
        tvPrice = findViewById(R.id.txtPrice);
        tvName = findViewById(R.id.txtName);
        tvAddress = findViewById(R.id.txtAddress);
        tvStatus = findViewById(R.id.txtStatus);

        btnTrack = findViewById(R.id.btnTrack);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerview_transDetail);

        switchTagihan = findViewById(R.id.switchTagihan);
        switchPengiriman = findViewById(R.id.switchPengiriman);

        linearTagihan = findViewById(R.id.linearTagihan);
        linearPengiriman = findViewById(R.id.linearPengiriman);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        switchTagihan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {    // default: true -> dlm kondisi di klik
                    linearTagihan.setVisibility(View.VISIBLE);
                } else {
                    linearTagihan.setVisibility(View.GONE);
                }
            }
        });

        switchPengiriman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {    // default: true -> dlm kondisi di klik
                    linearPengiriman.setVisibility(View.VISIBLE);
                } else {
                    linearPengiriman.setVisibility(View.GONE);
                }
            }
        });

        tvCode.setText(bundle.getString("TRANSACTION_CODE"));
        getDetailTransaksi();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Transaksi");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getDetailTransaksi() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<TransDetail> call = apiInterface.getTransDetail(bundle.getString("TRANSACTION_CODE"));
        call.enqueue(new Callback<TransDetail>() {
            @Override
            public void onResponse(Call<TransDetail> call, Response<TransDetail> response) {
                Log.d("_LogTransDetail", "onResponse: " + bundle.getString("TRANSACTION_CODE"));    // OK
                if (response.isSuccessful()) {
                    final TransDetail.Data data = response.body().getData();

                    tvPrice.setText(data.getGrandtotal());
                    tvName.setText(data.getUser());
                    tvAddress.setText(data.getDestination());
                    tvStatus.setText(data.getStatus_transaction());

                    if (data.getStatus_transaction().equals("sent")) {
                        btnTrack.setVisibility(View.VISIBLE);
                        btnTrack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(TransDetailActivity.this, WaybillActivity.class);
                                intent.putExtra("WAYBILL", data.getResi_code());
                                startActivity(intent);
                            }
                        });
                    } else {
                        btnTrack.setVisibility(View.GONE);
                    }

                    List<TransDetail.Data.DetailTransaction> transactions = data.getDetail_transaction();
                    TransDetailAdapter adapter = new TransDetailAdapter(transactions, TransDetailActivity.this);
                    recyclerView.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(TransDetailActivity.this, "Error TransDetail: " + response.message(), Toast.LENGTH_SHORT).show();
                    Log.d("_LogTransDetail", "onResponse: " + response.message() + " - " + response.isSuccessful());
                }
            }

            @Override
            public void onFailure(Call<TransDetail> call, Throwable t) {
                Toast.makeText(TransDetailActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}





















