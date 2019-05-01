package com.boss.cuncis.bukatoko.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.boss.cuncis.bukatoko.R;
import com.boss.cuncis.bukatoko.activity.DetailActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;

public class GlideImage {

    public static void get(Context context, String urlImage, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.img_placeholder2)
                .error(R.drawable.img_placeholder_error)
                .priority(Priority.HIGH);


        Glide.with(context)
                .load(urlImage)
                .apply(options)
                .into(imageView);
    }

}
