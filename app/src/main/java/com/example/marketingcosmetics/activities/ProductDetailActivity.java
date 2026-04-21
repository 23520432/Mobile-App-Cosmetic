package com.example.marketingcosmetics.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.marketingcosmetics.R;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Get data from Intent
        String name     = getIntent().getStringExtra("name");
        String brand    = getIntent().getStringExtra("brand");
        String price    = getIntent().getStringExtra("price");
        String oldPrice = getIntent().getStringExtra("oldPrice");
        String badge    = getIntent().getStringExtra("badge");
        String bottle   = getIntent().getStringExtra("bottleName");
        String emoji    = getIntent().getStringExtra("emoji");
        int    bgType   = getIntent().getIntExtra("bgType", 1);

        // Bind views
        ((TextView) findViewById(R.id.tvDetailName)).setText(name != null ? name : "");
        ((TextView) findViewById(R.id.tvDetailBrand)).setText(brand != null ? brand : "");
        ((TextView) findViewById(R.id.tvDetailPrice)).setText(price != null ? price : "");
        ((TextView) findViewById(R.id.tvDetailBottleName)).setText(bottle != null ? bottle : "");
        ((TextView) findViewById(R.id.tvDetailEmoji)).setText(emoji != null ? emoji : "");

        // Old price
        TextView tvOld = findViewById(R.id.tvDetailOldPrice);
        if (oldPrice != null && !oldPrice.isEmpty()) {
            tvOld.setVisibility(android.view.View.VISIBLE);
            tvOld.setText(oldPrice);
            tvOld.setPaintFlags(tvOld.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        // Badge
        TextView tvBadge = findViewById(R.id.tvDetailBadge);
        if (badge != null && !badge.isEmpty()) {
            tvBadge.setVisibility(android.view.View.VISIBLE);
            tvBadge.setText(badge);
        }

        // Background
        int bgRes;
        switch (bgType) {
            case 2: bgRes = R.drawable.bg_product_img_lilac; break;
            case 3: bgRes = R.drawable.bg_product_img_green; break;
            case 4: bgRes = R.drawable.bg_product_img_gold; break;
            default: bgRes = R.drawable.bg_product_img_pink; break;
        }
        findViewById(R.id.detailImgBg).setBackgroundResource(bgRes);

        // Buttons
        findViewById(R.id.btnDetailBack).setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, android.R.anim.slide_out_right);
        });

        findViewById(R.id.btnDetailAddToCart).setOnClickListener(v ->
                Toast.makeText(this, "Đã thêm " + name + " vào giỏ hàng!", Toast.LENGTH_SHORT).show()
        );
    }
}