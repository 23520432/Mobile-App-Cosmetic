package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;

import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone;
    private Button btnSaveProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // 2. Ánh xạ ID từ file XML (activity_edit_profile.xml)
        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);

        // 3. Xử lý nút lưu
        btnSaveProfile.setOnClickListener(v -> {
            String newName = edtFullName.getText().toString();
            String newEmail = edtEmail.getText().toString();
            String newPhone = edtPhone.getText().toString();

            // Gọi API cập nhật ở đây (sẽ làm ở bước sau)
            Toast.makeText(this, "Đã gửi yêu cầu cập nhật cho: " + newName, Toast.LENGTH_SHORT).show();
            finish();
        });

        findViewById(R.id.btnBackInfo).setOnClickListener(v -> finish());

    }
    private void updateProfile(int userId, String name, String email, String phone) {
        String url = "http://10.0.2.2:3000/api/users/update/" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);

        // Tạo object dữ liệu để gửi đi
        JSONObject postData = new JSONObject();
        try {
            postData.put("fullname", name);
            postData.put("email", email);
            postData.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, postData,
                response -> {
                    Toast.makeText(this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng màn hình sửa
                },
                error -> Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show()
        );

        queue.add(putRequest);
    }
}