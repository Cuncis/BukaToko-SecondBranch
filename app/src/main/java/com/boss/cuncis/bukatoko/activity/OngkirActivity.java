package com.boss.cuncis.bukatoko.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.Cost;
import com.boss.cuncis.bukatoko.data.model.transaction.TransPost;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.utils.Converter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OngkirActivity extends AppCompatActivity {

    @BindView(R.id.linearSave)
    LinearLayout linearSave;
    @BindView(R.id.linearTrans)
    LinearLayout linearTrans;
    @BindView(R.id.edtDestination)
    EditText edtDestination;
    @BindView(R.id.edtAddress)
    EditText edtAddress;

    @BindView(R.id.spnService)
    Spinner spinService;
    @BindView(R.id.txtOngkir)
    TextView tvOngkir;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    // jika ingin mengubah text/color di button pake BindView
    @OnClick(R.id.btnSave) void clickSave() {
        if (edtDestination.length()>0 || edtAddress.length()>0) {
            linearTrans.setVisibility(View.VISIBLE);
            linearSave.setVisibility(View.GONE);

            ArrayList<TransPost.Detail> arrayList = new ArrayList<>();
            for (int i = 0; i < CartActivity.cartList.size(); i++) {
                TransPost.Detail detail = new TransPost().new Detail();
                detail.setProduct_id(CartActivity.cartList.get(i).getProduct_id());
                detail.setQty(CartActivity.cartList.get(i).getQty());
                detail.setPrice(CartActivity.cartList.get(i).getPrice());
                detail.setTotal(CartActivity.cartList.get(i).getTotal());

                arrayList.add(detail);
            }

            TransPost transPost = new TransPost();
            transPost.setUser_id(Integer.parseInt(App.prefsManager.getUserDetails().get(PrefsManager.SESS_ID)));
            transPost.setDestination(edtDestination.getText().toString() + " - " + edtAddress.getText().toString());
            transPost.setOngkir(ongkirValue);
            transPost.setGrandtotal(CartActivity.adapter.getTotal() + ongkirValue);
            transPost.setDetailList(arrayList);

            postTransaction(transPost);
        } else {
            Toast.makeText(this, "Lengkapi alamat pengiriman", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnTrans) void clickTrans() {
        startActivity(new Intent(this, TransActivity.class));
        finish();
    }

    @OnClick(R.id.txtDismiss) void clickDismiss() {
        finish();
    }

    @OnClick(R.id.txtCancel) void clickCancel() {
        finish();
    }

    @OnClick(R.id.edtDestination) void clickDestination() {
        startActivity(new Intent(this, CityActivity.class));
    }

    List<String> serviceList;
    List<Integer> valueList;

    private int ongkirValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongkir);
        ButterKnife.bind(this);

        serviceList = new ArrayList<>();
        valueList = new ArrayList<>();

        getSupportActionBar().setTitle("Cek Ongkos Kirim");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (!Constant.DESTINATION.equals("")) {
            edtDestination.setText(Constant.DESTINATION_NAME);

            getServices();
        }
    }

    private void getServices() {
        valueList.clear();
        serviceList.clear();

        ApiInterface apiInterface = new ApiClient().getClientRajaOngkir(Constant.BASE_URL_RAJAONGKIR_STARTER).create(ApiInterface.class);
        Call<Cost> call = apiInterface.getCost(Constant.KEY_RAJAONGKIR,
                "444",      // toko = tempat drmana asal barang yg punya tokoonline mengirimkan
                Constant.DESTINATION,     // tujuan
                "1000",     // dlm hitungan gram, bisa dicustom dgn edittext.getText().toString()
                "jne");
        call.enqueue(new Callback<Cost>() {
            @Override
            public void onResponse(Call<Cost> call, Response<Cost> response) {
                Cost.RajaOngkir ongkir = response.body().getRajaOngkir();

                List<Cost.RajaOngkir.Results> results = ongkir.getResults();
                for (int i = 0; i < results.size(); i++) {
                    Log.d("_logServiceCode", "onResponse: " + results.get(i).getCode());

                    List<Cost.RajaOngkir.Results.Costs> costs = results.get(i).getCosts();
                    for (int j = 0; j < costs.size(); j++) {
                        Log.d("_logServiceDesc", "onResponse: " + costs.get(j).getDescription());

                        List<Cost.RajaOngkir.Results.Costs.Data> data = costs.get(j).getCost();
                        for (int k = 0; k < data.size(); k++) {
                            Log.d("_logServiceCost", "onResponse: " + data.get(k).getValue());

                            serviceList.add("Rp " + Converter.rupiah(data.get(k).getValue()) + " (JNE " + costs.get(j).getService() + ")");
                            valueList.add(data.get(k).getValue());
                        }
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(OngkirActivity.this,
                        android.R.layout.simple_list_item_1, serviceList);
                spinService.setAdapter(arrayAdapter);

                spinService.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                        tvOngkir.setText("Rp " + Converter.rupiah(valueList.get(position)));
                        ongkirValue = valueList.get(position);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<Cost> call, Throwable t) {
                Toast.makeText(OngkirActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void postTransaction(TransPost transPost) {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<TransPost> call = apiInterface.insertTrans(transPost);
        call.enqueue(new Callback<TransPost>() {
            @Override
            public void onResponse(Call<TransPost> call, Response<TransPost> response) {
                if (response.isSuccessful()) {
                    linearTrans.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(OngkirActivity.this, "Transaksi telah dibuat", Toast.LENGTH_SHORT).show();

                    App.sqLiteHelper.clearTable();
                } else {
                    Toast.makeText(OngkirActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransPost> call, Throwable t) {
                Toast.makeText(OngkirActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}













