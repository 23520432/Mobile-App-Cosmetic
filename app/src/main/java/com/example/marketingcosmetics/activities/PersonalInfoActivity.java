package com.example.marketingcosmetics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;

import org.json.JSONException;
import org.json.JSONObject;

public class PersonalInfoActivity extends AppCompatActivity {

    private TextView tvShowFullName, tvShowEmail, tvShowPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        // 2. Ánh xạ ID từ XML sang Java (Sau dòng setContentView)
        tvShowFullName = findViewById(R.id.tvShowFullName);
        tvShowEmail = findViewById(R.id.tvShowEmail);
        tvShowPhone = findViewById(R.id.tvShowPhone);

        // Đừng quên gọi hàm này để nó bắt đầu tải dữ liệu nhé
        loadUserData();

        // Xử lý nút bấm chuyển trang Edit (đoạn code cũ của bạn)
        findViewById(R.id.btnGoToEdit).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnBackInfo).setOnClickListener(v -> finish());
    }

    private void loadUserData() {
        // Thay IP máy tính của bạn vào đây (ví dụ: 192.168.1.5)
        String url = "http://10.0.2.2:3000/api/users/login"; // 10.0.2.2 là localhost của máy ảo Android

        RequestQueue queue = Volley.newRequestQueue(this);

        // Ở đây mình giả sử bạn dùng một API GET, nếu chưa có hãy dùng tạm Login để lấy data
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Phân tích cú pháp JSON từ server gửi về
                        JSONObject user = response.getJSONObject("user");
                        tvShowFullName.setText(user.getString("FULLNAME"));
                        tvShowEmail.setText(user.getString("EMAIL"));
                        tvShowPhone.setText(user.getString("PHONE"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Lỗi kết nối server", Toast.LENGTH_SHORT).show()
        );

        queue.add(jsonObjectRequest);
    }

}