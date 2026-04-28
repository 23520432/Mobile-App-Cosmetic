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
import com.example.marketingcosmetics.utils.SessionManager;
import org.json.JSONException;
import org.json.JSONObject;

public class EditProfileActivity extends AppCompatActivity {

    private EditText edtFullName, edtEmail, edtPhone;
    private Button btnSave;
    private SessionManager session;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Lấy ID từ Session
        session = new SessionManager(this);
        userId = session.getUserId();

        edtFullName = findViewById(R.id.edtFullName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSaveProfile);

        // Tải dữ liệu cũ lên để người dùng sửa
        loadCurrentData();

        // 4. Sự kiện Lưu
        btnSave.setOnClickListener(v -> performUpdate());

        // Nút quay lại
        findViewById(R.id.btnBackEdit).setOnClickListener(v -> finish());
    }

    private void loadCurrentData() {
        String url = "http://10.0.2.2:3000/api/users/" + userId;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        edtFullName.setText(response.getString("FULLNAME"));
                        edtEmail.setText(response.getString("EMAIL"));
                        edtPhone.setText(response.getString("PHONE"));
                    } catch (JSONException e) { e.printStackTrace(); }
                }, null);
        Volley.newRequestQueue(this).add(request);
    }

    private void performUpdate() {
        String url = "http://10.0.2.2:3000/api/users/update/" + userId;

        JSONObject postData = new JSONObject();
        try {
            postData.put("fullname", edtFullName.getText().toString());
            postData.put("email", edtEmail.getText().toString());
            postData.put("phone", edtPhone.getText().toString());
        } catch (JSONException e) { e.printStackTrace(); }

        JsonObjectRequest putRequest = new JsonObjectRequest(Request.Method.PUT, url, postData,
                response -> {
                    Toast.makeText(this, "Đã lưu thay đổi!", Toast.LENGTH_SHORT).show();
                    finish(); // Đóng màn hình chỉnh sửa, quay về trang thông tin
                },
                error -> Toast.makeText(this, "Không thể lưu dữ liệu", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(this).add(putRequest);
    }
}