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

    public CampaignAdapter(Context context, List<Campaign> list) {
        this.context = context;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvUser, tvCaption, tvStats;
        ImageView imgCampaign;
        Button btnLike, btnComment, btnShare;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUser = itemView.findViewById(R.id.tvUser);
            tvCaption = itemView.findViewById(R.id.tvCaption);
            tvStats = itemView.findViewById(R.id.tvStats);

            imgCampaign = itemView.findViewById(R.id.imgCampaign);

            btnLike = itemView.findViewById(R.id.btnLike);
            btnComment = itemView.findViewById(R.id.btnComment);
            btnShare = itemView.findViewById(R.id.btnShare);
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

        // User (creator)
        h.tvUser.setText(c.getCreatedByName());

        // Caption
        h.tvCaption.setText(c.getDescription());

        // Stats
        h.tvStats.setText(
                c.getLikeCount() + " Like • " +
                        c.getCommentCount() + " Comment • " +
                        c.getShareCount() + " Share"
        );

        // Image
        Glide.with(context)
                .load(c.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(h.imgCampaign);

        // Like
        h.btnLike.setOnClickListener(v ->
                Toast.makeText(context, "Liked ❤️", Toast.LENGTH_SHORT).show()
        );

        // Comment
        h.btnComment.setOnClickListener(v ->
                Toast.makeText(context, "Comment 💬", Toast.LENGTH_SHORT).show()
        );

        // Share
        h.btnShare.setOnClickListener(v ->
                Toast.makeText(context, "Shared 🔄", Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}