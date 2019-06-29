package com.boss.cuncis.bukatoko.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.TransUnpaidAdapter;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.model.rajaongkir.Cost;
import com.boss.cuncis.bukatoko.data.model.transaction.TransPost;
import com.boss.cuncis.bukatoko.data.model.transaction.TransUser;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.utils.Converter;
import com.boss.cuncis.bukatoko.utils.ScreenshotType;
import com.boss.cuncis.bukatoko.utils.ScreenshotUtils;
import com.boss.cuncis.bukatoko.utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    // updated
    @BindView(R.id.et_namaPengirim)
    EditText etNamaPengirim;
    @BindView(R.id.et_emailPengirim)
    EditText etEmailPengirim;
    @BindView(R.id.et_tglTransfer)
    EditText etTglTransfer;
    @BindView(R.id.et_bank)
    EditText etBank;
    @BindView(R.id.et_nominal)
    EditText etNominal;
    @BindView(R.id.et_keterangan)
    EditText etKeterangan;

    @BindView(R.id.spnService)
    Spinner spinService;
    @BindView(R.id.txtOngkir)
    TextView tvOngkir;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.txtTotalPembayaran)
    TextView tvTotalPembayaran;

    @BindView(R.id.linear_noRekening)
    LinearLayout linearNoRekening;

    // jika ingin mengubah text/color di button pake BindView
    @OnClick(R.id.btnSave) void clickSave() {
//        linearNoRekening.setVisibility(View.VISIBLE);
//        linearTrans.setVisibility(View.VISIBLE);
//        linearSave.setVisibility(View.GONE);
        if (edtDestination.length()>0 && edtAddress.length()>0) {
            linearNoRekening.setVisibility(View.VISIBLE);
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
            transPost.setUser_id(Integer.parseInt(Objects.requireNonNull(App.prefsManager.getUserDetails().get(PrefsManager.SESS_ID))));
            transPost.setDestination(edtDestination.getText().toString() + " - " + edtAddress.getText().toString());
            transPost.setOngkir(ongkirValue);
            transPost.setGrandtotal(CartActivity.adapter.getTotal() + ongkirValue);
            transPost.setDetailList(arrayList);

            postTransaction(transPost);
        } else if (TextUtils.isEmpty(edtAddress.getText().toString().trim())) {
            Toast.makeText(this, "Lengkapi alamat pengiriman", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Lengkapi alamat pengiriman", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.btnTrans) void clickTrans() {
        if (TextUtils.isEmpty(etNamaPengirim.getText().toString().trim())) {
            etNamaPengirim.setError("Nama Pengirim tidak boleh kosong");
        } else if (TextUtils.isEmpty(etEmailPengirim.getText().toString().trim())) {
            etEmailPengirim.setError("Email Pengirim tidak boleh kosong");
        } else if (etTglTransfer.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Tanggal Transfer tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (etBank.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "Bank tidak boleh kosong", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(etNominal.getText().toString().trim())) {
            etNominal.setError("Nominal Transfer tidak boleh kosong");
        } else {
            takeScreenshot(ScreenshotType.CUSTOM);

//            Intent i = new Intent(this, TransActivity.class);
//            startActivity(i);
//            finish();
        }
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

        linearNoRekening.setVisibility(View.GONE);

        serviceList = new ArrayList<>();
        valueList = new ArrayList<>();

        initListener();

        getSupportActionBar().setTitle("Transaksi Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        switch (screenshotType) {
            case FULL:
                //If Screenshot type is FULL take full page screenshot i.e our root content.
                b = ScreenshotUtils.getScreenShot(linearNoRekening);
                break;
            case CUSTOM:
                //If Screenshot type is CUSTOM
                findViewById(R.id.linear1).setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of first button
                linearTrans.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of first button
//                relativeToolbar.setVisibility(View.INVISIBLE);//set the visibility to INVISIBLE of first button

                b = ScreenshotUtils.getScreenShot(linearNoRekening);

                //After taking screenshot reset the button and view again
                findViewById(R.id.linear1).setVisibility(View.VISIBLE);//set the visibility to VISIBLE of first button again
                linearTrans.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of first button again
//                relativeToolbar.setVisibility(View.VISIBLE);//set the visibility to VISIBLE of first button again

                //NOTE:  You need to use visibility INVISIBLE instead of GONE to remove the view from frame else it wont consider the view in frame and you will not get screenshot as you required.
                break;
        }

        //If bitmap is not null
        if (b != null) {
            showScreenShotImage(b);//show bitmap over imageview

            File saveFile = ScreenshotUtils.getMainDirectoryName(this);//get the path to save screenshot
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);//save the screenshot to selected path
        } else {
            //If bitmap is null show toast message
            Toast.makeText(this, "Error..!", Toast.LENGTH_SHORT).show();
        }
    }

    /*  Show screenshot Bitmap */
    private void showScreenShotImage(Bitmap b) {
//        imageView.setImageBitmap(b);
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            b.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            b.recycle();

            // get transaction code
            getTransactioncCode();

            //Pop intent
            Intent in1 = new Intent(this, UploadActivity.class);
            in1.putExtra("img_upload", filename);
            in1.putExtra("TRANSACTION_CODE", data.get(0).getTransaction_code());
            startActivity(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void initListener() {
        etTglTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c;
                DatePickerDialog datePickerDialog;

                c = Calendar.getInstance();
                final int year = c.get(Calendar.YEAR);
                final int month = c.get(Calendar.MONTH);
                final int day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(OngkirActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int mYear, int mMonth, int mDay) {
                        String date = mYear + "-" + (mMonth + 1) + "-" + mDay;
                        etTglTransfer.setText(Utils.getDateEvent(date));
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        etBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"BRI", "BCA", "BNI", "Permata", "Mandiri", "Lainnya"};

                AlertDialog.Builder builder = new AlertDialog.Builder(OngkirActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Pilih Bank");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        etBank.setText(items[which].toString());
                    }
                });
                builder.show();
            }
        });
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
//                    etKodeTransaksi.setText(results.get(i).getCode());

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

                        tvTotalPembayaran.setText("Rp " + Converter.rupiah(CartActivity.adapter.getTotal() + ongkirValue));
                        Log.d("_logGrandTotal", "onItemSelected: " +  "Rp " + Converter.rupiah(CartActivity.adapter.getTotal() + ongkirValue));
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
                Toast.makeText(OngkirActivity.this, "Erroraaa: " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    List<TransUser.Data> data;
    private void getTransactioncCode() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<TransUser> call = apiInterface.getTransUnpaid(
                App.prefsManager.getUserDetails().get(PrefsManager.SESS_ID)
        );
        call.enqueue(new Callback<TransUser>() {
            @Override
            public void onResponse(Call<TransUser> call, Response<TransUser> response) {
                if (response.isSuccessful()) {
                    TransUser transUser = response.body();
                    data = transUser.getData();
                    Collections.reverse(data);

                } else {
                    Toast.makeText(OngkirActivity.this, "Errorcccc: " + response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransUser> call, Throwable t) {
                Toast.makeText(OngkirActivity.this, "Errorbbb: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}













