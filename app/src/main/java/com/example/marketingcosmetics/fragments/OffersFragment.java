package com.example.marketingcosmetics.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.AddCampaignActivity;
import com.example.marketingcosmetics.activities.AddPromotionActivity;
import com.example.marketingcosmetics.activities.PromotionDetailActivity;
import com.example.marketingcosmetics.models.Promotion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OffersFragment extends Fragment {

    private TextView tvDays, tvHours, tvMinutes, tvSeconds;
    private LinearLayout flashSaleContainer;
    private CountDownTimer countDownTimer;

    private final String API_URL = "http://10.0.2.2:3000/api/campaigns/promotions/all";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        tvDays = view.findViewById(R.id.tvDays);
        tvHours = view.findViewById(R.id.tvHours);
        tvMinutes = view.findViewById(R.id.tvMinutes);
        tvSeconds = view.findViewById(R.id.tvSeconds);

        FloatingActionButton fab = view.findViewById(R.id.fabAdd);

        fab.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), AddPromotionActivity.class));
        });

        flashSaleContainer = view.findViewById(R.id.flashSaleContainer);

        loadPromotions();

        return view;
    }

    // ================= LOAD DATA =================
    private void loadPromotions() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest request = new JsonArrayRequest(API_URL,
                response -> {
                    flashSaleContainer.removeAllViews();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            Promotion p = new Promotion();
                            p.setId(obj.getInt("ID"));
                            p.setCampaignId(obj.getInt("CAMPAIGN_ID"));
                            p.setTitle(obj.getString("TITLE"));
                            p.setDescription(obj.getString("DESCRIPTION"));
                            p.setImageUrl(obj.getString("IMAGE_URL"));
                            p.setDiscountPercent(obj.getInt("DISCOUNT_PERCENT"));
                            p.setStartDate(obj.getString("START_DATE"));
                            p.setEndDate(obj.getString("END_DATE"));
                            p.setStatus(obj.getString("STATUS"));

                            addPromotionView(p);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                error -> Toast.makeText(getContext(), "Lỗi load ưu đãi", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    // ================= UI ITEM =================
    private void addPromotionView(Promotion p) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View item = inflater.inflate(R.layout.item_promotion, flashSaleContainer, false);

        TextView tvTitle = item.findViewById(R.id.tvTitle);
        TextView tvDesc = item.findViewById(R.id.tvDesc);
        TextView tvDiscount = item.findViewById(R.id.tvDiscount);
        ImageView img = item.findViewById(R.id.imgPromo);
        LinearLayout badge = item.findViewById(R.id.badgeContainer);
        ImageButton btnEdit = item.findViewById(R.id.btnEdit);
        ImageButton btnDelete = item.findViewById(R.id.btnDelete);

        tvTitle.setText(p.getTitle());
        tvDesc.setText(p.getDescription());
        tvDiscount.setText("-" + p.getDiscountPercent() + "%");

        // 👉 Load ảnh
        com.bumptech.glide.Glide.with(getContext())
                .load(p.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(img);

        // 👉 Click badge → chạy countdown
        badge.setOnClickListener(v -> startCountdownFromDate(p));

        // 👉 Click item → mở DETAIL (đúng UX)
        item.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), PromotionDetailActivity.class);

            intent.putExtra("title", p.getTitle());
            intent.putExtra("desc", p.getDescription());
            intent.putExtra("image", p.getImageUrl());
            intent.putExtra("discount", p.getDiscountPercent());
            intent.putExtra("endDate", p.getEndDate());

            startActivity(intent);
        });

        // 👉 NÚT EDIT
        btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), AddPromotionActivity.class);

            intent.putExtra("isEdit", true);
            intent.putExtra("id", p.getId());
            intent.putExtra("title", p.getTitle());
            intent.putExtra("desc", p.getDescription());
            intent.putExtra("image", p.getImageUrl());
            intent.putExtra("start", p.getStartDate());
            intent.putExtra("end", p.getEndDate());
            intent.putExtra("discount", p.getDiscountPercent());
            intent.putExtra("campaignId", p.getCampaignId());

            startActivity(intent);
        });

        // DELETE
        btnDelete.setOnClickListener(v -> showDeleteDialog(p));

        flashSaleContainer.addView(item);
    }

    // ================= DELETE =================
    private void showDeleteDialog(Promotion p) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa ưu đãi")
                .setMessage("Bạn chắc chắn muốn xóa?")
                .setPositiveButton("Xóa", (d, w) -> deletePromotion(p.getId()))
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deletePromotion(int id) {

        String url = "http://10.0.2.2:3000/api/campaigns/promotions/" + id;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.DELETE,
                url,
                null,
                response -> {
                    Toast.makeText(getContext(), "Đã xóa", Toast.LENGTH_SHORT).show();
                    loadPromotions();
                },
                error -> Toast.makeText(getContext(), "Xóa lỗi", Toast.LENGTH_SHORT).show()
        );

        Volley.newRequestQueue(getContext()).add(request);
    }


    // ================= COUNTDOWN =================
    private void startCountdownFromDate(Promotion p) {
        try {
            String raw = p.getEndDate().split("T")[0]; // bỏ giờ

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date endDate = sdf.parse(raw);

            long now = System.currentTimeMillis();
            long end = endDate.getTime();

            if (end < now) {
                tvDays.setText("00");
                tvHours.setText("00");
                tvMinutes.setText("00");
                tvSeconds.setText("00");
                Toast.makeText(getContext(), "Ưu đãi đã hết", Toast.LENGTH_SHORT).show();
                return;
            }

            long timeLeft = end - now;

            if (countDownTimer != null) countDownTimer.cancel();

            countDownTimer = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long ms) {

                    long days = ms / (1000 * 60 * 60 * 24);
                    long hours = (ms % (1000 * 60 * 60 * 24)) / (1000 * 3600);
                    long minutes = (ms % (1000 * 3600)) / (1000 * 60);
                    long seconds = (ms % (1000 * 60)) / 1000;

                    tvDays.setText(String.format("%02d", days));
                    tvHours.setText(String.format("%02d", hours));
                    tvMinutes.setText(String.format("%02d", minutes));
                    tvSeconds.setText(String.format("%02d", seconds));
                }

                @Override
                public void onFinish() {
                    tvDays.setText("00");
                    tvHours.setText("00");
                    tvMinutes.setText("00");
                    tvSeconds.setText("00");
                }
            }.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}
