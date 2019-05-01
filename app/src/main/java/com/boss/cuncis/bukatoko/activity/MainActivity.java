package com.boss.cuncis.bukatoko.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.adapter.ProductAdapter;
import com.boss.cuncis.bukatoko.data.model.Product;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.dialog.LoginDialog;
import com.boss.cuncis.bukatoko.utils.AuthState;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "_mainLog";

    MaterialSearchView searchView;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchProduct();
        floatingButton();
        navigationDrawer();
        initRecycler();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(null);
                getProducts();
            }
        });
    }

    private void getProducts() {
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<Product> call = apiInterface.getProducts();
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                Product product = response.body();
                List<Product.Data> products = product.getProducts();

                recyclerView.setAdapter(new ProductAdapter(products, MainActivity.this));

                for (int i = 0; i < products.size(); i++) {
                    Log.d(TAG, "onResponse: " + products.get(i).getProduct());
                }

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(MainActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void navigationDrawer() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        menu = navigationView.getMenu();
    }

    private void floatingButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void searchProduct() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, "" + query, Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    protected void onResume() {     // apabila ketika kita balik ke main, dan ada produk baru update
        super.onResume();           // maka kita akan mengambil semua produk tsb.
        getProducts();

        if (menu != null) {
            if (App.prefsManager.isLoggedIn()) {
                AuthState.isLoggedIn(menu);     // menampilkan drawer yg terhidden
            } else {
                AuthState.isLoggedOut(menu);    // menyembunyikan drawer
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.custom_menu, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_cart) {
            if (App.prefsManager.isLoggedIn()) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            } else {
                new LoginDialog().showLoginDialog(MainActivity.this, menu);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            startActivity(new Intent(MainActivity.this, SignupActivity.class));
        } else if (id == R.id.nav_notif) {
            startActivity(new Intent(MainActivity.this, NotifActivity.class));
        } else if (id == R.id.nav_transaski) {
            startActivity(new Intent(MainActivity.this, TransActivity.class));
        } else if (id == R.id.nav_profil) {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
        } else if (id == R.id.nav_logout) {
            App.prefsManager.logoutUser();
            AuthState.isLoggedOut(menu);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
