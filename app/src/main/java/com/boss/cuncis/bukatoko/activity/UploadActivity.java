package com.boss.cuncis.bukatoko.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.model.Upload;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.utils.FileUtils;
import com.boss.cuncis.bukatoko.utils.ScreenshotUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    ImageView imgUpload;
    Button btnUpload;
    //    TextView tvGallery;
    ProgressBar progressBar;
    LinearLayout linearUpload;

    private static final int PICK_IMAGE = 1;    // nilai akhir yg gk bisa diubah2

    private Uri uri;        // tipe data resource

    Bundle bundle;

    Bitmap bmp = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        bundle = getIntent().getExtras();
        Log.d("_logCode", "onCreate: " + bundle.getString("TRANSACTION_CODE"));

        imgUpload = findViewById(R.id.img_upload);
        btnUpload = findViewById(R.id.btn_upload);
//        tvGallery = findViewById(R.id.tv_gallery);
        progressBar = findViewById(R.id.progressbar);
        linearUpload = findViewById(R.id.linear_upload);

//        tvGallery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                permissionGallery();
//            }
//        });
        final String filename = getIntent().getStringExtra("img_upload");
        try {
            FileInputStream is = this.openFileInput(filename);
            bmp = BitmapFactory.decodeStream(is);
            imgUpload.setImageBitmap(bmp);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnUpload.setVisibility(View.VISIBLE);
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File saveFile = ScreenshotUtils.getMainDirectoryName(UploadActivity.this);//get the path to save screenshot
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
                File file = ScreenshotUtils.store(bmp, "screenshot" + timeStamp + ".jpg", saveFile);//save the screenshot to selected path

                Uri uri = Uri.fromFile(file);

//                File file1 = new File(uri1.toString());
                File file1 = FileUtils.getFile(getApplicationContext(), uri);
                uploadImage(file1);
                Log.d("_logFileImage", "onClick: " + file1);


//                if (uri != null) {
//                    File file1 = FileUtils.getFile(getApplicationContext(), uri);    // Uri nya di convert menjadi sebuah file
//                    uploadImage(file1);
//
//                    linearUpload.setVisibility(View.GONE);
//                    progressBar.setVisibility(View.VISIBLE);
//                }

                Intent i = new Intent(UploadActivity.this, TransActivity.class);
                startActivity(i);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Upload Bukti Transfer");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

//    private void openGallery() {
//        Intent intent = new Intent();
//        intent.setType("image/*");      // * maksudnya dr mana terserah, yg penting masuk ke image
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE);
//    }

//    private void permissionGallery() {
//        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED
//                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            ActivityCompat.requestPermissions(this,
//                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
//
//        } else {
//            openGallery();
//        }
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
//            uri = data.getData();
////            imgUpload.setImageURI(uri);
//
//
////            tvGallery.setText("Ubah Data");
//            btnUpload.setVisibility(View.VISIBLE);
//        }
//    }

    private void uploadImage(File fileImage) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), fileImage);

        MultipartBody.Part image = MultipartBody.Part.createFormData("foto", fileImage.getName(), requestFile);

        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<Upload> call = apiInterface.uploadImage(bundle.getString("TRANSACTION_CODE"), image);
        call.enqueue(new Callback<Upload>() {
            @Override
            public void onResponse(Call<Upload> call, Response<Upload> response) {
                if (response.code() == 202) {
                    Toast.makeText(UploadActivity.this, "Bukti Transaksi berhasil di upload", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UploadActivity.this, "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }

                linearUpload.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Upload> call, Throwable t) {
                Toast.makeText(UploadActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


















