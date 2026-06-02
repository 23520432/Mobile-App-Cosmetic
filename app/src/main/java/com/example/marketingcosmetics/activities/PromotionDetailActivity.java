package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;

import com.example.marketingcosmetics.utils.SessionManager; // Import SessionManager
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONObject;

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

        int campaignId = getIntent().getIntExtra("campaignId", -1);
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
            // 1. GHI NHẬN CHUYỂN ĐỔI VÀO DATABASE
            sendCampaignConversionToBackend(campaignId);
            // 2. HIỆN VOUCHER
            showVoucherDialog(discount);
        });


        btnBack.setOnClickListener(v -> finish());
    }
    private void sendCampaignConversionToBackend(int campaignId) {
        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();

        if (userId == -1) {
            Toast.makeText(this, "Vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://10.0.2.2:3000/api/conversions";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user_id", userId);
            jsonBody.put("campaign_id", campaignId); // Gửi ID chiến dịch
            jsonBody.put("product_id", JSONObject.NULL);
            jsonBody.put("conversion_type", "CAMPAIGN"); // Loại chuyển đổi là CAMPAIGN
        } catch (Exception e) { e.printStackTrace(); }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> System.out.println("Đã ghi nhận lấy Voucher chiến dịch!"),
                error -> Toast.makeText(this, "Lỗi ghi nhận!", Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }

    // Tách logic dialog ra hàm riêng cho sạch code
    private void showVoucherDialog(int discount) {
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
    }
}
