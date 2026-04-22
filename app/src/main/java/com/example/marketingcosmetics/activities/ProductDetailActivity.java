package com.example.marketingcosmetics.activities;

import android.graphics.Paint;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.marketingcosmetics.R;

import java.util.ArrayList;

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
        String description = getIntent().getStringExtra("description");
        ArrayList<String> ingredients = getIntent().getStringArrayListExtra("ingredients");

        // Bind views
        ((TextView) findViewById(R.id.tvDetailName)).setText(name != null ? name : "");
        ((TextView) findViewById(R.id.tvDetailBrand)).setText(brand != null ? brand : "");
        ((TextView) findViewById(R.id.tvDetailPrice)).setText(price != null ? price : "");
        ((TextView) findViewById(R.id.tvDetailBottleName)).setText(bottle != null ? bottle : "");
        ((TextView) findViewById(R.id.tvDetailEmoji)).setText(emoji != null ? emoji : "");
        ((TextView) findViewById(R.id.tvDetailDescription)).setText(description != null ? description : "");

        TextView tvIng1 = findViewById(R.id.tvIngredient1);
        TextView tvIng2 = findViewById(R.id.tvIngredient2);
        TextView tvIng3 = findViewById(R.id.tvIngredient3);

        if (ingredients != null) {
            if (ingredients.size() > 0) { tvIng1.setText(ingredients.get(0));
                tvIng1.setVisibility(android.view.View.VISIBLE); } else { tvIng1.setVisibility(android.view.View.GONE); }
            if (ingredients.size() > 1) { tvIng2.setText(ingredients.get(1));
                tvIng2.setVisibility(android.view.View.VISIBLE); } else { tvIng2.setVisibility(android.view.View.GONE); }
            if (ingredients.size() > 2) { tvIng3.setText(ingredients.get(2));
                tvIng3.setVisibility(android.view.View.VISIBLE); } else { tvIng3.setVisibility(android.view.View.GONE); }
        }

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