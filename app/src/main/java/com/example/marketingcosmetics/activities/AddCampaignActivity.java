package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;

import org.json.JSONObject;

public class AddCampaignActivity extends AppCompatActivity {
    EditText edtTitle, edtDesc, edtImage, edtStart, edtEnd;
    Button btnSave;

    boolean isEdit = false;
    int campaignId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_campaign);

        edtTitle = findViewById(R.id.edtTitle);
        edtDesc = findViewById(R.id.edtDesc);
        edtImage = findViewById(R.id.edtImage);
        edtStart = findViewById(R.id.edtStart);
        edtEnd = findViewById(R.id.edtEnd);
        btnSave = findViewById(R.id.btnSave);

        // 👉 nhận dữ liệu edit
        isEdit = getIntent().getBooleanExtra("isEdit", false);
        campaignId = getIntent().getIntExtra("id", -1);

        if (isEdit) {
            edtTitle.setText(getIntent().getStringExtra("title"));
            edtDesc.setText(getIntent().getStringExtra("desc"));
            edtImage.setText(getIntent().getStringExtra("image"));
            edtStart.setText(getIntent().getStringExtra("start"));
            edtEnd.setText(getIntent().getStringExtra("end"));

            btnSave.setText("Cập nhật chiến dịch");
        }

        btnSave.setOnClickListener(v -> saveCampaign());

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }

    private void saveCampaign() {

        String title = edtTitle.getText().toString().trim();
        String desc = edtDesc.getText().toString().trim();
        String image = edtImage.getText().toString().trim();
        String start = edtStart.getText().toString().trim();
        String end = edtEnd.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Nhập title!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url;
        int method;

        if (isEdit) {
            url = "http://10.0.2.2:3000/api/campaigns/" + campaignId;
            method = Request.Method.PUT;
        } else {
            url = "http://10.0.2.2:3000/api/campaigns";
            method = Request.Method.POST;
        }

        try {
            JSONObject json = new JSONObject();
            json.put("title", title);
            json.put("description", desc);
            json.put("image_url", image);
            json.put("start_date", start);
            json.put("end_date", end);

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
