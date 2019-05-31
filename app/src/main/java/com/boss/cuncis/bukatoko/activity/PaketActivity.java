package com.boss.cuncis.bukatoko.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.R;
import com.bumptech.glide.Glide;

public class PaketActivity extends AppCompatActivity {

    TextView item1, item2, item3, item4, item5, item6, item7, item8;
    CardView cardView2, cardView3;
    Button btnBeli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paket);
        initView();
        initToolbar();

        initListener();
    }

    private void initListener() {
        btnBeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogInfoLebihLanjut();
            }
        });
    }

    private void dialogInfoLebihLanjut() {
        CharSequence[] items = {"Jl. Pd. Benowo Indah, Babat Jerawat, Pakal, Kota SBY, Jawa Timur 60197, Indonesia", "081357570064"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Info");
//        builder.setMessage("Dikarenakan ini adalah pembelian sekaligus pemasangan maka jika ingin info lebih lanjut silahkan hubungi");
        builder.setCancelable(true);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }

    private void initView() {
        item1 = findViewById(R.id.tv_item1);
        item2 = findViewById(R.id.tv_item2);
        item3 = findViewById(R.id.tv_item3);
        item4 = findViewById(R.id.tv_item4);
        item5 = findViewById(R.id.tv_item5);
        item6 = findViewById(R.id.tv_item6);
        item7 = findViewById(R.id.tv_item7);
        item8 = findViewById(R.id.tv_item8);

        cardView2 = findViewById(R.id.cardview2);
        cardView3 = findViewById(R.id.cardview3);
        btnBeli = findViewById(R.id.btn_beli);
    }

    private void initToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int number = getIntent().getIntExtra("NUMBER", 0);
        getImage(number);
        switch (number) {
            case 1:
                showDetailRumputSintetis();
                break;
            case 2:
                showDetailInterlock();
                break;
            case 3:
                showDetailPlester();
                break;
            default:
                break;
        }
    }

    private void showDetailPlester() {
        getSupportActionBar().setTitle("Paket Plester");
        item1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_plester, 0, 0, 0);
        item1.setText("Plester");

        item2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_pasirsilica, 0, 0, 0);
        item2.setText("Pasir Silica");
//        cardView2.setVisibility(View.INVISIBLE);
//        cardView3.setVisibility(View.INVISIBLE);
//
        item3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_rubbergranule, 0, 0, 0);
        item3.setText("Rubber Granule");

        item4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringlapangan, 0, 0, 0);
        item4.setText("Jaring Lapangan");

        item5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_seling, 0, 0, 0);
        item5.setText("Seling");

        item6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_gawang, 0, 0, 0);
        item6.setText("Gawang");

        item7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_papanskormanual, 0, 0, 0);
        item7.setText("Papan Skor");

        item8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringgawang, 0, 0, 0);
        item8.setText("Jaring Gawang");
    }

    private void showDetailInterlock() {
        getSupportActionBar().setTitle("Paket Interlock");
        item1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_interlock, 0, 0, 0);
        item1.setText("Interlock");

        item2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_pasirsilica, 0, 0, 0);
        item2.setText("Pasir Silica");

        item3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_rubbergranule, 0, 0, 0);
        item3.setText("Rubber Granule");

        item4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringlapangan, 0, 0, 0);
        item4.setText("Jaring Lapangan");

        item5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_seling, 0, 0, 0);
        item5.setText("Seling");

        item6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_gawang, 0, 0, 0);
        item6.setText("Gawang");

        item7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_papanskor, 0, 0, 0);
        item7.setText("Papan Skor");

        item8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringgawang, 0, 0, 0);
        item8.setText("Jaring Gawang");
    }

    private void showDetailRumputSintetis() {
        getSupportActionBar().setTitle("Paket Rumput Sintetis");
        item1.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_rumputsintetis, 0, 0, 0);
        item1.setText("Rumput Sintetis");

        item2.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_pasirsilica, 0, 0, 0);
        item2.setText("Pasir Silica");

        item3.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_rubbergranule, 0, 0, 0);
        item3.setText("Rubber Granule");

        item4.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringlapangan, 0, 0, 0);
        item4.setText("Jaring Lapangan");

        item5.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_seling, 0, 0, 0);
        item5.setText("Seling");

        item6.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_gawang, 0, 0, 0);
        item6.setText("Gawang");

        item7.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_papanskor, 0, 0, 0);
        item7.setText("Papan Skor");

        item8.setCompoundDrawablesWithIntrinsicBounds(R.drawable.size_of_jaringgawang, 0, 0, 0);
        item8.setText("Jaring Gawang");
    }

    private void getImage(int number) {
        ImageView imgPaket = findViewById(R.id.img_paket);
        switch (number) {
            case 1:
                Glide.with(this)
                        .load(R.drawable.rumputsintetis)
                        .into(imgPaket);
                break;
            case 2:
                Glide.with(this)
                        .load(R.drawable.interlock)
                        .into(imgPaket);
                break;
            case 3:
                Glide.with(this)
                        .load(R.drawable.plester)
                        .into(imgPaket);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
