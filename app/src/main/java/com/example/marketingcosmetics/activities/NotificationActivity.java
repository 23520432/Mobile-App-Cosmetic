package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.adapters.NotificationAdapter;
import com.example.marketingcosmetics.models.NotificationItem;
import java.util.*;

public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
            overridePendingTransition(0, android.R.anim.slide_out_right);
        });

        TextView tvMarkAll = findViewById(R.id.tvMarkAllRead);
        tvMarkAll.setOnClickListener(v ->
                Toast.makeText(this, "Đã đọc tất cả thông báo", Toast.LENGTH_SHORT).show()
        );

        RecyclerView rv = findViewById(R.id.rvNotifications);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new NotificationAdapter(this, getNotifications()));
    }

    private List<NotificationItem> getNotifications() {
        List<NotificationItem> list = new ArrayList<>();
        list.add(new NotificationItem("👑", "Bạn nhận được ưu đãi VIP tháng này!", "Vừa xong", 1));
        list.add(new NotificationItem("🌸", "Flash Sale — Sérum Rose Lumière -40%", "2 phút trước", 2));
        list.add(new NotificationItem("✨", "Chiến dịch \"Golden Hour\" vừa ra mắt", "1 giờ trước", 1));
        list.add(new NotificationItem("📦", "Đơn hàng #4821 đang được giao", "3 giờ trước", 3));
        list.add(new NotificationItem("🎁", "Quà sinh nhật của bạn đang chờ!", "1 ngày trước", 2));
        list.add(new NotificationItem("💎", "Bạn vừa đạt hạng Gold Member", "2 ngày trước", 1));
        list.add(new NotificationItem("🌿", "Sản phẩm mới: Botanica Oil ra mắt", "3 ngày trước", 3));
        return list;
    }
}