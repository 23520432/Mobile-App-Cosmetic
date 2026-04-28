package com.example.marketingcosmetics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegisterLink;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Khởi tạo Session
        session = new SessionManager(this);

        // Ánh xạ View
        edtUsername = findViewById(R.id.etUsername);
        edtPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegisterLink = findViewById(R.id.tvGoToRegister);

        // Click Đăng nhập
        btnLogin.setOnClickListener(v -> {
            String user = edtUsername.getText().toString().trim();
            String pass = edtPassword.getText().toString().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
            } else {
                performLogin(user, pass);
            }
        });

        // Click chuyển sang Đăng ký
        tvRegisterLink.setOnClickListener(v -> {
            // startActivity(new Intent(this, RegisterActivity.class));
            Toast.makeText(this, "Chức năng đăng ký đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void performLogin(String username, String password) {
        String url = "http://10.0.2.2:3000/api/users/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject params = new JSONObject();
        try {
            params.put("username", username);
            params.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                response -> {
                    try {
                        JSONObject userObj = response.getJSONObject("user");
                        int id = userObj.getInt("ID");
                        String name = userObj.getString("FULLNAME");

                        // Lưu vào Session để các trang sau dùng
                        session.createLoginSession(id, name);

                        Toast.makeText(this, "Chào mừng " + name, Toast.LENGTH_SHORT).show();

                        // Chuyển vào trang chủ
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(this, "Sai tài khoản hoặc mật khẩu!", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }
}