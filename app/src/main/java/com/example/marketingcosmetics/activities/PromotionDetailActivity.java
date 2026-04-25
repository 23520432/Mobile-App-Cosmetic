package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;

public class PromotionDetailActivity extends AppCompatActivity {

    ImageView img;
    TextView tvTitle, tvDiscount, tvDate, tvDesc;
    Button btnVoucher;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);

        img = findViewById(R.id.imgPromotion);
        tvTitle = findViewById(R.id.tvTitle);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvDate = findViewById(R.id.tvDate);
        tvDesc = findViewById(R.id.tvDescription);
        btnVoucher = findViewById(R.id.btnGetVoucher);
        btnBack = findViewById(R.id.btnBack);

        // nhận dữ liệu
        String title = getIntent().getStringExtra("title");
        String desc = getIntent().getStringExtra("desc");
        int discount = getIntent().getIntExtra("discount", 0);
        String endDate = getIntent().getStringExtra("endDate");
        String image = getIntent().getStringExtra("image");

        tvTitle.setText(title);
        tvDiscount.setText("Giảm " + discount + "%");
        try {
            String raw = getIntent().getStringExtra("endDate");
            java.text.SimpleDateFormat inputFormat =
                    new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.getDefault());
            inputFormat.setTimeZone(java.util.TimeZone.getTimeZone("UTC"));

            java.text.SimpleDateFormat outputFormat =
                    new java.text.SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());

            java.util.Date date = inputFormat.parse(raw);
            String formattedDate = outputFormat.format(date);

            tvDate.setText("Hạn: " + formattedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tvDesc.setText(desc);

        Glide.with(this).load(image).into(img);

        btnVoucher.setOnClickListener(v -> {
            android.app.Dialog dialog = new android.app.Dialog(this);
            dialog.setContentView(R.layout.dialog_voucher);

            ImageView imgVoucher = dialog.findViewById(R.id.imgVoucher);
            Button btnClose = dialog.findViewById(R.id.btnClose);

            int voucherResId;

            if (discount >= 50) {
                voucherResId = R.drawable.voucher_50;
            } else if (discount >= 20) {
                voucherResId = R.drawable.voucher_20;
            } else {
                voucherResId = R.drawable.voucher_default;
            }

            imgVoucher.setImageResource(voucherResId);


            btnClose.setOnClickListener(v1 -> dialog.dismiss());

            dialog.show();
        });


        btnBack.setOnClickListener(v -> finish());
    }
}
