package com.example.marketingcosmetics.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.marketingcosmetics.R;
import android.view.*;

public class SupportHelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_help);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());


        // Xử lý đóng/mở FAQ 1
        View layoutFaq1 = findViewById(R.id.layoutFaq1);
        TextView tvAnswer1 = findViewById(R.id.tvAnswer1);
        layoutFaq1.setOnClickListener(v -> toggleFaq(tvAnswer1));

        // Xử lý đóng/mở FAQ 2
        View layoutFaq2 = findViewById(R.id.layoutFaq2);
        TextView tvAnswer2 = findViewById(R.id.tvAnswer2);
        layoutFaq2.setOnClickListener(v -> toggleFaq(tvAnswer2));

        // xử lý gửi Feedback
        Button btnSend = findViewById(R.id.btnSendFeedback);
        EditText edtFeedback = findViewById(R.id.edtFeedback);

        btnSend.setOnClickListener(v -> {
            String text = edtFeedback.getText().toString().trim();
            if (text.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung góp ý!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Cảm ơn bạn! Góp ý đã được gửi.", Toast.LENGTH_SHORT).show();
                edtFeedback.setText("");
            }
        });

        // Gọi Hotline
        findViewById(R.id.cardCall).setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            intent.setData(Uri.parse("tel:0123456789"));
//            startActivity(intent);
            Toast.makeText(this, "sdt: 0123456789", Toast.LENGTH_SHORT).show();
        });

        // Gửi Email
        findViewById(R.id.cardEmail).setOnClickListener(v -> {
//            Intent intent = new Intent(Intent.ACTION_SENDTO);
//            intent.setData(Uri.parse("mailto:support@luxe.com"));
//            intent.putExtra(Intent.EXTRA_SUBJECT, "Hỗ trợ khách hàng Luxe");
//            startActivity(intent);
            Toast.makeText(this, "email: support@luxe.com", Toast.LENGTH_SHORT).show();
        });

    }

    // Hàm hỗ trợ ẩn hiện mượt mà
    private void toggleFaq(TextView answer) {
        if (answer.getVisibility() == View.VISIBLE) {
            answer.setVisibility(View.GONE);
        } else {
            answer.setVisibility(View.VISIBLE);
        }
    }
}