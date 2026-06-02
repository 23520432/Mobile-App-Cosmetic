package com.example.marketingcosmetics.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.models.Campaign;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private Context context;
    private List<Campaign> list;
    private OnEditClickListener editListener;
    private OnDeleteClickListener deleteListener;

    public CampaignAdapter(Context context, List<Campaign> list,
                           OnEditClickListener editListener,
                           OnDeleteClickListener deleteListener) {
        this.context = context;
        this.list = list;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUser, tvCaption, tvStats, tvDate;
        ImageView imgCampaign;
        Button btnLike, btnComment, btnShare;
        ImageButton btnEdit;
        ImageButton btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUser = itemView.findViewById(R.id.tvUser);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvStats = itemView.findViewById(R.id.tvStats);
            tvDate = itemView.findViewById(R.id.tvDate);

            imgCampaign = itemView.findViewById(R.id.imgCampaign);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.campaign_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        Campaign c = list.get(position);

        // USER
        h.tvUser.setText(c.getCreatedByName());

        // CAPTION
        h.tvCaption.setText(c.getDescription());

        // DATE
        try {
            String start = c.getStartDate().split("T")[0];
            String end = c.getEndDate().split("T")[0];
            h.tvDate.setText(start + " - " + end);
        } catch (Exception e) {
            h.tvDate.setText("N/A");
        }

        // STATS
        updateStatsUI(h.tvStats, c);

        // IMAGE
        Glide.with(context)
                .load(c.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(h.imgCampaign);

        // ACTIONS
        LinearLayout btnLike = h.itemView.findViewById(R.id.layoutLike);
        LinearLayout btnComment = h.itemView.findViewById(R.id.layoutComment);
        LinearLayout btnShare = h.itemView.findViewById(R.id.layoutShare);

        // NÚT LIKE
        btnLike.setOnClickListener(v -> {
            sendInteraction(c, "LIKE", h.tvStats);
            btnLike.setEnabled(false); // Khóa nút tạm thời để chống spam click
        });

        // NÚT COMMENT
        btnComment.setOnClickListener(v -> {
            sendInteraction(c, "COMMENT", h.tvStats);
            btnComment.setEnabled(false);
        });

        // NÚT SHARE
        btnShare.setOnClickListener(v -> {
            sendInteraction(c, "SHARE", h.tvStats);
            btnShare.setEnabled(false);
        });

        // EDIT BUTTON
        h.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEdit(c);
            }
        });

        h.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(c);
            }
        });
    }

    private void updateStatsUI(TextView tvStats, Campaign c) {
        tvStats.setText(
                c.getLikeCount() + " Like • " +
                        c.getCommentCount() + " Comment • " +
                        c.getShareCount() + " Share"
        );
    }

    // HÀM GỌI API: Ghi nhận Tương tác
    private void sendInteraction(Campaign c, String type, TextView tvStats) {
        // 1. Tăng số hiển thị trước
        if (type.equals("LIKE")) c.setLikeCount(c.getLikeCount() + 1);
        else if (type.equals("COMMENT")) c.setCommentCount(c.getCommentCount() + 1);
        else if (type.equals("SHARE")) c.setShareCount(c.getShareCount() + 1);

        updateStatsUI(tvStats, c);

        // 2. Gửi API chạy ngầm
        String url = "http://10.0.2.2:3000/api/campaigns/" + c.getId() + "/interact";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    //Toast.makeText(context, type + " thành công!", Toast.LENGTH_SHORT).show();
                },
                error -> {
                    // Nếu lỗi, trừ lại số đã cộng ảo ban nãy
                    if (type.equals("LIKE")) c.setLikeCount(c.getLikeCount() - 1);
                    else if (type.equals("COMMENT")) c.setCommentCount(c.getCommentCount() - 1);
                    else if (type.equals("SHARE")) c.setShareCount(c.getShareCount() - 1);

                    updateStatsUI(tvStats, c);
                    Toast.makeText(context, "Lỗi mạng, chưa ghi nhận được!", Toast.LENGTH_SHORT).show();
                }
        );

        Volley.newRequestQueue(context).add(request);
    }

    public interface OnEditClickListener {
        void onEdit(Campaign campaign);
    }

    public interface OnDeleteClickListener {
        void onDelete(Campaign campaign);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}