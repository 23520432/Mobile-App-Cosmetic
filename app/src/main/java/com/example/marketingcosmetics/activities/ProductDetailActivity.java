package com.example.marketingcosmetics.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;
import com.google.android.flexbox.FlexboxLayout;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // ===== GET DATA =====
        String name     = getIntent().getStringExtra("name");
        String brand    = getIntent().getStringExtra("brand");
        double price    = getIntent().getDoubleExtra("price", 0);
        String oldPrice = getIntent().getStringExtra("oldPrice");
        String badge    = getIntent().getStringExtra("badge");
        int bgType      = getIntent().getIntExtra("bgType", 1);
        String description = getIntent().getStringExtra("description");
        String imageUrl = getIntent().getStringExtra("image");
        ArrayList<String> ingredients =
                getIntent().getStringArrayListExtra("ingredients");

        // ===== BIND VIEW =====
        TextView tvName = findViewById(R.id.tvDetailName);
        TextView tvBrand = findViewById(R.id.tvDetailBrand);
        TextView tvPrice = findViewById(R.id.tvDetailPrice);
        TextView tvDesc = findViewById(R.id.tvDetailDescription);
        ImageView img = findViewById(R.id.imgDetailProduct);

        tvName.setText(name != null ? name : "");
        tvBrand.setText(brand != null ? brand : "");
        tvDesc.setText(description != null ? description : "");

        // ===== FORMAT PRICE =====
        DecimalFormat df = new DecimalFormat("#,###");
        tvPrice.setText(df.format(price) + "đ");

        // ===== LOAD IMAGE =====
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.bg_product_img_pink)
                .error(R.drawable.bg_product_img_pink)
                .into(img);

        // ===== INGREDIENTS DYNAMIC =====
        FlexboxLayout  layout = findViewById(R.id.layoutIngredients);
        layout.removeAllViews();

        if (ingredients != null && !ingredients.isEmpty()) {

            for (String item : ingredients) {

                if (item == null || item.trim().isEmpty()) continue;

                TextView tv = new TextView(this);
                tv.setText(item.trim());
                tv.setTextSize(12);

                tv.setPadding(24, 10, 24, 10);
                tv.setBackgroundResource(R.drawable.bg_hero_badge);

//                id="fix-flex"
                FlexboxLayout.LayoutParams params =
                        new FlexboxLayout.LayoutParams(
                                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                                FlexboxLayout.LayoutParams.WRAP_CONTENT
                        );

                params.setMargins(0, 0, 16, 25);
                tv.setLayoutParams(params);

                layout.addView(tv);
            }

        } else {
            Toast.makeText(this, "Không có ingredients", Toast.LENGTH_SHORT).show();
        }

        // ===== OLD PRICE =====
        TextView tvOld = findViewById(R.id.tvDetailOldPrice);
        if (oldPrice != null && !oldPrice.isEmpty()) {
            tvOld.setVisibility(View.VISIBLE);
            tvOld.setText(oldPrice);
            tvOld.setPaintFlags(tvOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvOld.setVisibility(View.GONE);
        }

        // ===== BADGE =====
        TextView tvBadge = findViewById(R.id.tvDetailBadge);
        if (badge != null && !badge.isEmpty()) {
            tvBadge.setVisibility(View.VISIBLE);
            tvBadge.setText(badge);
        } else {
            tvBadge.setVisibility(View.GONE);
        }

        // ===== BACKGROUND =====
        int bgRes;
        switch (bgType) {
            case 2: bgRes = R.drawable.bg_product_img_lilac; break;
            case 3: bgRes = R.drawable.bg_product_img_green; break;
            case 4: bgRes = R.drawable.bg_product_img_gold; break;
            default: bgRes = R.drawable.bg_product_img_pink; break;
        }
        findViewById(R.id.detailImgBg).setBackgroundResource(bgRes);

        // ===== BUTTONS =====
        findViewById(R.id.btnDetailBack).setOnClickListener(v -> finish());

        findViewById(R.id.btnDetailAddToCart).setOnClickListener(v ->
                Toast.makeText(this,
                        "Đã chuyển sang link mua " + name + "!",
                        Toast.LENGTH_SHORT).show()
        );
    }
}