package com.example.marketingcosmetics.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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

public class PersonalInfoActivity extends AppCompatActivity {

    private TextView tvShowFullName, tvShowEmail, tvShowPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        tvShowFullName = findViewById(R.id.tvShowFullName);
        tvShowEmail = findViewById(R.id.tvShowEmail);
        tvShowPhone = findViewById(R.id.tvShowPhone);

        findViewById(R.id.btnGoToEdit).setOnClickListener(v -> {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnBackInfo).setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume(){
        super.onResume();
        loadUserData();
    }

    private void loadUserData() {
        SessionManager session = new SessionManager(this);
        int userId = session.getUserId();
        Log.d("DEBUG_API", "Đang gọi URL: http://10.0.2.2:3000/api/users/" + userId);

        String url = "http://10.0.2.2:3000/api/users/" + userId;

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d("JSON_DATA", response.toString());
                    try {
                        String fullName = response.getString("FULLNAME");
                        String email = response.getString("EMAIL");
                        String phone = response.getString("PHONE");
                        tvShowFullName.setText(fullName);
                        tvShowEmail.setText(email);
                        tvShowPhone.setText(phone);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    String message = "Lỗi kết nối";
                    if (error.networkResponse != null) {
                        message = "Mã lỗi: " + error.networkResponse.statusCode; // Hiện 404 hoặc 405
                    }
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );

        queue.add(jsonObjectRequest);
    }

}