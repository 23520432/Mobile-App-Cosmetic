package com.example.marketingcosmetics.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.PromotionDetailActivity;
import com.example.marketingcosmetics.models.Promotion;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.ViewHolder> {

    private Context context;
    private List<Promotion> list;

    private OnDeleteClickListener deleteListener;
    private OnEditClickListener editListener;

    // 👉 constructor mới
    public PromotionAdapter(Context context, List<Promotion> list,
                            OnEditClickListener editListener,
                            OnDeleteClickListener deleteListener) {
        this.context = context;
        this.list = list;
        this.editListener = editListener;
        this.deleteListener = deleteListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle, tvDesc, tvDiscount, tvDate;
        ImageView imgPromo;
        ImageButton btnDelete, btnEdit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvDiscount = itemView.findViewById(R.id.tvDiscount);
            tvDate = itemView.findViewById(R.id.tvDate);
            imgPromo = itemView.findViewById(R.id.imgPromo);

            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);
        }
    }

    @NonNull
    @Override
    public PromotionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_promotion, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {

        Promotion p = list.get(position);

        // TITLE
        h.tvTitle.setText(p.getTitle());

        // DESCRIPTION
        h.tvDesc.setText(p.getDescription());

        // DISCOUNT
        h.tvDiscount.setText("-" + p.getDiscountPercent() + "%");

        // DATE
        try {
            String start = p.getStartDate().split("T")[0];
            String end = p.getEndDate().split("T")[0];
            h.tvDate.setText(start + " - " + end);
        } catch (Exception e) {
            h.tvDate.setText("N/A");
        }

        // IMAGE
        Glide.with(context)
                .load(p.getImageUrl())
                .placeholder(R.drawable.placeholder)
                .into(h.imgPromo);

        // 👉 CLICK mở detail
        h.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PromotionDetailActivity.class);

            intent.putExtra("title", p.getTitle());
            intent.putExtra("desc", p.getDescription());
            intent.putExtra("discount", p.getDiscountPercent());
            intent.putExtra("image", p.getImageUrl());
            intent.putExtra("endDate", p.getEndDate());

            context.startActivity(intent);
        });

        // ✏️ EDIT
        h.btnEdit.setOnClickListener(v -> {
            if (editListener != null) {
                editListener.onEdit(p);
            }
        });

        // 🗑 DELETE
        h.btnDelete.setOnClickListener(v -> {
            if (deleteListener != null) {
                deleteListener.onDelete(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // ================= INTERFACE =================

    public interface OnDeleteClickListener {
        void onDelete(Promotion promotion);
    }

    public interface OnEditClickListener {
        void onEdit(Promotion promotion);
    }
}