package com.example.marketingcosmetics.adapters;

import android.content.Context;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.models.Campaign;

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

//            btnLike = itemView.findViewById(R.id.btnLike);
//            btnComment = itemView.findViewById(R.id.btnComment);
//            btnShare = itemView.findViewById(R.id.btnShare);

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
        h.tvStats.setText(
                c.getLikeCount() + " Like • " +
                        c.getCommentCount() + " Comment • " +
                        c.getShareCount() + " Share"
        );

        // IMAGE
        Glide.with(context)
                .load(c.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(h.imgCampaign);

        // ACTIONS
        LinearLayout btnLike = h.itemView.findViewById(R.id.layoutLike);
        LinearLayout btnComment = h.itemView.findViewById(R.id.layoutComment);
        LinearLayout btnShare = h.itemView.findViewById(R.id.layoutShare);

        btnLike.setOnClickListener(v ->
                Toast.makeText(context, "Liked ❤️", Toast.LENGTH_SHORT).show()
        );

        btnComment.setOnClickListener(v ->
                Toast.makeText(context, "Comment 💬", Toast.LENGTH_SHORT).show()
        );

        btnShare.setOnClickListener(v ->
                Toast.makeText(context, "Shared 🔄", Toast.LENGTH_SHORT).show()
        );

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