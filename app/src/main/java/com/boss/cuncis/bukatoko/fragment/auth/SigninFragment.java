package com.boss.cuncis.bukatoko.fragment.auth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
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
public class SigninFragment extends Fragment {

    EditText etEmail;
    PasswordView etPassword;
    Button btnLogin;

    public SigninFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        etEmail = view.findViewById(R.id.et_email);
        etPassword = view.findViewById(R.id.et_password);
        btnLogin = view.findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etEmail.length()>0 && etPassword.length()>0) {
                    if (Converter.isValidEmailId(etEmail.getText().toString())) {
                        auth(etEmail.getText().toString(), etPassword.getText().toString());
                    } else {
                        Toast.makeText(getContext(), "Isi format email dengan benar!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Isi data dengan benar!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void auth(String email, final String password) {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<User> call = apiInterface.authLogin(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User.Data data = response.body().getData();
                    Toast.makeText(getContext(), "" + data.getName(), Toast.LENGTH_SHORT).show();

                    App.prefsManager.createLoginSession(String.valueOf(data.getId()), data.getName(),
                            data.getEmail(), password);

                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}















