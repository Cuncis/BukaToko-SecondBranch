package com.boss.cuncis.bukatoko.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.NotifAdapter;
import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.model.Notification;
import com.boss.cuncis.bukatoko.data.model.transaction.TransUser;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotifActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Notification.Data> data;
    NotifAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif);

        recyclerView = findViewById(R.id.recyclerview_notif);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data = new ArrayList<>();
        adapter = new NotifAdapter(data, NotifActivity.this);



        getNotif();

        getSupportActionBar().setTitle("Notifikasi");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void getNotif() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<Notification> call = apiInterface.myNotifications(
                App.prefsManager.getUserDetails().get(PrefsManager.SESS_ID)
        );

        call.enqueue(new Callback<Notification>() {
            @Override
            public void onResponse(Call<Notification> call, Response<Notification> response) {
                data.addAll(response.body().getData());
                adapter = new NotifAdapter(data, NotifActivity.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<Notification> call, Throwable t) {
                Toast.makeText(NotifActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
