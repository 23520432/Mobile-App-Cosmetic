package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.marketingcosmetics.R;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtOldPass, edtNewPass, edtConfirmPass;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtOldPass = findViewById(R.id.edtOldPassword);
        edtNewPass = findViewById(R.id.edtNewPassword);
        edtConfirmPass = findViewById(R.id.edtConfirmPassword);
        btnUpdate = findViewById(R.id.btnUpdatePassword);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        btnUpdate.setOnClickListener(v -> {
            String oldP = edtOldPass.getText().toString().trim();
            String newP = edtNewPass.getText().toString().trim();
            String confirmP = edtConfirmPass.getText().toString().trim();

            if (oldP.isEmpty() || newP.isEmpty() || confirmP.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newP.equals(confirmP)) {
                Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            if (newP.length() < 6) {
                Toast.makeText(this, "Mật khẩu mới phải có 6 ký tự!", Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}