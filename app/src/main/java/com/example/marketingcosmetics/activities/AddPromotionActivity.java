package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;

import org.json.JSONObject;

public class AddPromotionActivity extends AppCompatActivity {

    EditText edtTitle, edtDesc, edtImage, edtStart, edtEnd, edtDiscount, edtCampaignId;
    Button btnSave;

    boolean isEdit = false;
    int promotionId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_promotion);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtImage = findViewById(R.id.edtImage);
        edtStart = findViewById(R.id.edtStart);
        edtEnd = findViewById(R.id.edtEnd);
        edtDiscount = findViewById(R.id.edtDiscount);
        edtCampaignId = findViewById(R.id.edtCampaignId);
        btnSave = findViewById(R.id.btnSave);

        // nhận dữ liệu edit
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        promotionId = getIntent().getIntExtra("id", -1);

        if (isEdit) {
            edtTitle.setText(getIntent().getStringExtra("title"));
            edtDesc.setText(getIntent().getStringExtra("desc"));
            edtImage.setText(getIntent().getStringExtra("image"));
            edtStart.setText(getIntent().getStringExtra("start"));
            edtEnd.setText(getIntent().getStringExtra("end"));
            edtDiscount.setText(String.valueOf(getIntent().getIntExtra("discount", 0)));
            edtCampaignId.setText(String.valueOf(getIntent().getIntExtra("campaignId", 1)));

            btnSave.setText("Cập nhật ưu đãi");
        }

        btnSave.setOnClickListener(v -> savePromotion());

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void savePromotion() {

        String title = edtTitle.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        String image = edtImage.getText().toString().trim();
        String start = edtStart.getText().toString().trim();
        String end = edtEnd.getText().toString().trim();
        String discountStr = edtDiscount.getText().toString().trim();
        String campaignIdStr = edtCampaignId.getText().toString().trim();

        if (title.isEmpty() || discountStr.isEmpty()) {
            Toast.makeText(this, "Nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        int discount = Integer.parseInt(discountStr);
        int campaignId = Integer.parseInt(campaignIdStr);

        String url;
        int method;

        if (isEdit) {
            url = "http://10.0.2.2:3000/api/campaigns/promotions/" + promotionId;
            method = Request.Method.PUT;
        } else {
            url = "http://10.0.2.2:3000/api/campaigns/" + campaignId + "/promotions";
            method = Request.Method.POST;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("title", title);
            json.put("description", desc);
            json.put("image_url", image);
            json.put("start_date", start);
            json.put("end_date", end);
            json.put("discount_percent", discount);

            JsonObjectRequest request = new JsonObjectRequest(
                    method,
                    url,
                    json,
                    response -> {
                        Toast.makeText(this, "Thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    },
                    error -> Toast.makeText(this, "Lỗi API!", Toast.LENGTH_SHORT).show()
            );

            Volley.newRequestQueue(this).add(request);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
