package com.boss.cuncis.bukatoko.dialog;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.CartActivity;
import com.boss.cuncis.bukatoko.activity.SignupActivity;
import com.boss.cuncis.bukatoko.data.model.User;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.utils.AuthState;
import com.boss.cuncis.bukatoko.utils.Converter;
import com.xwray.passwordview.PasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginDialog {

    public void showLoginDialog(final Context context, final Menu menu) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_login, null);
        builder.setView(view);

        final EditText etEmail = view.findViewById(R.id.et_email);
        final PasswordView etPassword = view.findViewById(R.id.et_password);

        final AlertDialog dialog = builder.create();

        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.length()>0 && etPassword.length()>0) {
                    String email = etEmail.getText().toString();
                    final String password = etPassword.getText().toString();

                    if (Converter.isValidEmailId(email)) {
                        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
                        Call<User> call = apiInterface.authLogin(email, password);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    User.Data data = response.body().getData();

                                    AuthState.isLoggedIn(menu);
                                    App.prefsManager.createLoginSession(String.valueOf(data.getId()), data.getName(),
                                            data.getEmail(), password);

                                    dialog.dismiss();
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
            }
        });

        view.findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, SignupActivity.class));
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
