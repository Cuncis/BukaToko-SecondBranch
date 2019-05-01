package com.boss.cuncis.bukatoko.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.TabAdapter;
import com.boss.cuncis.bukatoko.fragment.auth.SigninFragment;
import com.boss.cuncis.bukatoko.fragment.auth.SignupFragment;

public class SignupActivity extends AppCompatActivity {

    public static TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        addTab(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Pengguna Baru");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void addTab(ViewPager viewPager) {
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignupFragment(), "Daftar");
        adapter.addFragment(new SigninFragment(), "Masuk");
        viewPager.setAdapter(adapter);
    }
}
