package com.boss.cuncis.bukatoko.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.model.User;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.xwray.passwordview.PasswordView;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    LinearLayout linearEdit, linearData;
    TextView tvName, tvEmail, tvPassword;
    Button btnSave;
    EditText etName, etEmail;
    PasswordView etCurrentPassword, etNewPassword, etRePassword;
    FloatingActionButton fab;

    boolean edit = false;
    String userId;

    HashMap<String, String> sessPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initView();

        // get session user
        sessPrefs = App.prefsManager.getUserDetails();

        userId = sessPrefs.get(App.prefsManager.SESS_ID);
        tvName.setText(sessPrefs.get(App.prefsManager.SESS_NAME));
        tvEmail.setText(sessPrefs.get(App.prefsManager.SESS_EMAIL));
        tvPassword.setText(sessPrefs.get(App.prefsManager.SESS_PASS));

        etName.setText(sessPrefs.get(App.prefsManager.SESS_NAME));
        etEmail.setText(sessPrefs.get(App.prefsManager.SESS_EMAIL));

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!etCurrentPassword.getText().toString().equals(sessPrefs.get(App.prefsManager.SESS_PASS))) {
                    Toast.makeText(ProfileActivity.this, "Password saat ini salah", Toast.LENGTH_SHORT).show();
                } else if (!etNewPassword.getText().toString().equals(etRePassword.getText().toString())) {
                    Toast.makeText(ProfileActivity.this, "Konfirmasi password baru dengan benar", Toast.LENGTH_SHORT).show();
                } else if (etNewPassword.length() <= 0) {
                    Toast.makeText(ProfileActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                } else {
                    setUpdate();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edit) {
                    editTrue();
                } else {
                    editFalse();
                }
            }
        });
    }

    private void setUpdate() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<User> call = apiInterface.updateUser(sessPrefs.get(App.prefsManager.SESS_ID),
                etName.getText().toString(), etEmail.getText().toString(), etNewPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    App.prefsManager.logoutUser();
                    App.prefsManager.createLoginSession(userId, etName.getText().toString(),
                            etEmail.getText().toString(), etNewPassword.getText().toString());

                    editTrue();
                } else {
                    Toast.makeText(ProfileActivity.this, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        fab = findViewById(R.id.fab);

        linearEdit = findViewById(R.id.linear_edit);
        linearData = findViewById(R.id.linear_data);

        tvName = findViewById(R.id.tv_name);
        tvEmail = findViewById(R.id.tv_email);
        tvPassword = findViewById(R.id.tv_password);

        btnSave = findViewById(R.id.btn_save);

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etCurrentPassword = findViewById(R.id.et_current_password);
        etNewPassword = findViewById(R.id.et_new_password);
        etRePassword = findViewById(R.id.et_re_password);
    }

    private void editTrue() {
        linearData.setVisibility(View.VISIBLE);
        linearEdit.setVisibility(View.GONE);
        fab.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_fab_edit));
        edit = false;
    }

    private void editFalse() {
        linearData.setVisibility(View.GONE);
        linearEdit.setVisibility(View.VISIBLE);
        fab.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_fab_close));
        edit = true;
    }
}
