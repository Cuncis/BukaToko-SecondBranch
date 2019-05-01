package com.boss.cuncis.bukatoko.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.CityAdapter;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.City;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends AppCompatActivity {

    @BindView(R.id.recyclerview_city)
    RecyclerView recyclerView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.editText)
    EditText editText;

    CityAdapter adapter;

    @OnClick(R.id.img_cancel) void clickCancel() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getCity();
    }

    private void getCity() {
        ApiInterface apiInterface = new ApiClient().getClientRajaOngkir(Constant.BASE_URL_RAJAONGKIR_STARTER).create(ApiInterface.class);
        Call<City> call = apiInterface.getCities(Constant.KEY_RAJAONGKIR);
        call.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                if (response.isSuccessful()) {
                    City.RajaOngkir rajaOngkir = response.body().getRajaOngkir();
                    List<City.RajaOngkir.Results> results = rajaOngkir.getResults();

                    adapter = new CityAdapter(results, CityActivity.this);
                    recyclerView.setAdapter(adapter);

                    progressBar.setVisibility(View.GONE);

                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            String search = editable.toString();

                            adapter.getFilter().filter(search);
                        }
                    });
                } else {
                    Toast.makeText(CityActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(CityActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
