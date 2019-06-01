package com.boss.cuncis.bukatoko.utils;

import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.model.User;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthState {

    public static void isLoggedIn(Menu menu) {
        menu.findItem(R.id.nav_notif).setVisible(true);
        menu.findItem(R.id.nav_transaski).setVisible(true);
        menu.findItem(R.id.nav_profil).setVisible(true);
        menu.findItem(R.id.nav_rekomkendasi).setVisible(true);
        menu.findItem(R.id.nav_logout).setVisible(true);

        menu.findItem(R.id.nav_login).setVisible(false);
    }

    public static void isLoggedOut(Menu menu) {
        menu.findItem(R.id.nav_notif).setVisible(false);
        menu.findItem(R.id.nav_transaski).setVisible(false);
        menu.findItem(R.id.nav_profil).setVisible(false);
        menu.findItem(R.id.nav_rekomkendasi).setVisible(false);
        menu.findItem(R.id.nav_logout).setVisible(false);

        menu.findItem(R.id.nav_login).setVisible(true);
    }

    public static void updateToken(final Context context, String token) {

        App.sessPref = App.prefsManager.getUserDetails();

        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<User> call = apiInterface.updateToken(App.sessPref.get(PrefsManager.SESS_ID), token);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("_FbResponse", response.toString() );

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Token Berhasil diperbarui", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updateHeaderUser(final Context context, String nama, String email) {

        App.sessPref = App.prefsManager.getUserDetails();

        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<User> call = apiInterface.updateHeaderUser(App.sessPref.get(App.prefsManager.SESS_ID), nama, email);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Log.d("_FbResponse", response.toString() );

                if (response.isSuccessful()) {
                    Toast.makeText(context, "Token Berhasil diperbarui", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}




















