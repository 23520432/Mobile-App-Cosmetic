package com.example.marketingcosmetics.adapters;

import android.content.Context;
import android.view.*;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.models.NotificationItem;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.VH> {

    private final Context context;
    private final List<NotificationItem> items;

    public NotificationAdapter(Context context, List<NotificationItem> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_notification, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        NotificationItem item = items.get(position);
        holder.tvEmoji.setText(item.getEmoji());
        holder.tvTitle.setText(item.getTitle());
        holder.tvTime.setText(item.getTime());

        // Background color by type
        int bgRes;
        switch (item.getType()) {
            case 2: bgRes = R.drawable.bg_card_pink; break;
            case 3: bgRes = R.drawable.bg_card_sage; break;
            default: bgRes = R.drawable.bg_card_lilac; break;
        }
        holder.tvEmoji.setBackgroundResource(bgRes);

        // Show unread dot only for first 2 items
        holder.unreadDot.setVisibility(position < 2 ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvTitle, tvTime;
        View unreadDot;
        VH(@NonNull View v) {
            super(v);
            tvEmoji = v.findViewById(R.id.tvNotifEmoji);
            tvTitle = v.findViewById(R.id.tvNotifTitle);
            tvTime = v.findViewById(R.id.tvNotifTime);
            unreadDot = v.findViewById(R.id.unreadDot);
        }
    }
}