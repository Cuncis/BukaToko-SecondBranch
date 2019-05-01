package com.boss.cuncis.bukatoko.fragment.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.SignupActivity;
import com.boss.cuncis.bukatoko.data.model.User;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.utils.Converter;
import com.xwray.passwordview.PasswordView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragment extends Fragment {

    EditText etName, etEmail;
    PasswordView etPassword, etRePassword;
    Button btnSignup;

    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        etName = view.findViewById(R.id.et_name);
        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        etRePassword = view.findViewById(R.id.et_re_password);
        btnSignup = view.findViewById(R.id.btn_signup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.length()>0 && etEmail.length()>0 && etPassword.length()>0 && etRePassword.length()>0) {
                    if (!etPassword.getText().toString().equals(etRePassword.getText().toString())) {
                        Toast.makeText(getContext(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
                    } else if (!Converter.isValidEmailId(etEmail.getText().toString())) {
                        Toast.makeText(getContext(), "Isi format email dengan benar!", Toast.LENGTH_SHORT).show();
                    } else {
                        auth();
                    }
                } else {
                    Toast.makeText(getContext(), "Isi Data dengan benar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void auth() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<User> call = apiInterface.authRegister(etName.getText().toString(),
                etEmail.getText().toString(), etPassword.getText().toString());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User.Status status = response.body().getStatus();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "" + status.getDescription(), Toast.LENGTH_SHORT).show();
                    SignupActivity.tabLayout.getTabAt(1).select();
                } else {
                    Toast.makeText(getContext(), "Error: " + status.getCode() + "\n" + status.getDescription(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
