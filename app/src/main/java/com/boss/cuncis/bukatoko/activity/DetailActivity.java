package com.boss.cuncis.bukatoko.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.boss.cuncis.bukatoko.App;
import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.data.Constant;
import com.boss.cuncis.bukatoko.data.model.Detail;
import com.boss.cuncis.bukatoko.data.retrofit.ApiClient;
import com.boss.cuncis.bukatoko.data.retrofit.ApiInterface;
import com.boss.cuncis.bukatoko.dialog.CartDialog;
import com.boss.cuncis.bukatoko.utils.Converter;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.DefaultSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    SliderLayout sliderLayout;
    TextView tvName, tvPrice, tvDescription;
    ImageButton btnAddCart;
    Button btnCheckout;

    Bundle bundle;

    int detailPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bundle = getIntent().getExtras();

        tvName = findViewById(R.id.tv_name);
        tvPrice = findViewById(R.id.tv_price);
        tvDescription = findViewById(R.id.tv_description);
        btnAddCart = findViewById(R.id.btn_add_cart);
        btnCheckout = findViewById(R.id.btn_checkout);



        btnAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();
                new CartDialog().showCartDialog(DetailActivity.this);
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToCart();

                Constant.SHOP_NOW = true;
                startActivity(new Intent(DetailActivity.this, CartActivity.class));
                finish();
            }
        });


        getDetails();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Detail Barang");
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private void addToCart() {
        if (App.sqLiteHelper.checkExists(bundle.getInt("PRODUCT_ID")) == 0) {
            Long cartId = App.sqLiteHelper.addToCart(bundle.getInt("PRODUCT_ID"), tvName.getText().toString(),
                    bundle.getString("PRODUCT_IMAGE"), detailPrice);
            Log.d("_logCartId", "onClick: " + String.valueOf(cartId));
        }
    }

    private void getDetails() {
        ApiInterface apiInterface = new ApiClient().getClient().create(ApiInterface.class);
        Call<Detail> call = apiInterface.getProducts(bundle.getInt("PRODUCT_ID"));
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail.Data data = response.body().getData();
                tvName.setText(data.getProduct());

                detailPrice = data.getPrice();
                tvPrice.setText("Rp " + Converter.rupiah(data.getPrice()));

                if (data.getDescription() != null) {
                    tvDescription.setText(data.getDescription());
                }

                Detail detail = response.body();
                List<Detail.Data.Images> images = detail.getData().getImages();

                List<String> arrayList = new ArrayList<>();     //buat nampung data
                for (Detail.Data.Images img: images) {
                    arrayList.add(img.getImage());
                    Log.d("_logListImage", "onResponse: " + img.getImage());
                }

                setSlide(arrayList);

            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });
    }

    private void setSlide(List<String> urlImage) {
        sliderLayout = findViewById(R.id.slider);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop()
            .placeholder(R.drawable.img_placeholder2)
            .error(R.drawable.img_placeholder_error);

        for (int i = 0; i < urlImage.size(); i++) {
            DefaultSliderView sliderView = new DefaultSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(urlImage.get(i))
                    .setRequestOption(requestOptions)
                    .setBackgroundColor(Color.TRANSPARENT)
                    .setProgressBarVisible(false)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderLayout.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(4000);
        sliderLayout.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onSliderClick(BaseSliderView baseSliderView) {

    }
}
