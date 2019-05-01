package com.boss.cuncis.bukatoko.fragment.trans;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.TransUnpaidAdapter;
import com.boss.cuncis.bukatoko.data.db.PrefsManager;
import com.boss.cuncis.bukatoko.data.model.transaction.TransUser;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnpaidFragment extends Fragment {

    RecyclerView recyclerView;
    TextView textView;

    public UnpaidFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_unpaid, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_transUnpaid);
        textView = view.findViewById(R.id.textView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        getTrans();

        return view;
    }

    private void getTrans() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<TransUser> call = apiInterface.getTransUnpaid(
                App.prefsManager.getUserDetails().get(PrefsManager.SESS_ID)
        );
        call.enqueue(new Callback<TransUser>() {
            @Override
            public void onResponse(Call<TransUser> call, Response<TransUser> response) {
                if (response.isSuccessful()) {
                    TransUser transUser = response.body();
                    List<TransUser.Data> data = transUser.getData();

                    recyclerView.setAdapter(new TransUnpaidAdapter(data, getActivity()));
                } else {
                    textView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<TransUser> call, Throwable t) {

            }
        });
    }

}






















