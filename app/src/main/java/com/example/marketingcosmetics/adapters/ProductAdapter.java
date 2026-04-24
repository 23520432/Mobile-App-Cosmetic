package com.example.marketingcosmetics.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.models.Product;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final Context context;
    private final List<Product> products;
    private OnProductClickListener listener;

    public interface OnProductClickListener {
        void onProductClick(Product product, int position);
        void onAddToCart(Product product, int position);
    }

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public void setOnProductClickListener(OnProductClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, @SuppressLint("RecyclerView") int position) {

        Product product = products.get(position);

        // ===== TEXT =====
        holder.tvProductName.setText(product.getName());
        holder.tvBrandName.setText(product.getBrand());

        DecimalFormat df = new DecimalFormat("#,###");
        holder.tvPrice.setText(df.format(product.getPrice()) + "đ");

        // ===== IMAGE -> BACKGROUND =====
        Glide.with(context)
                .load(product.getImageUrl())
                .centerCrop()
                .placeholder(R.drawable.bg_product_img_pink)
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource,
                                                @Nullable Transition<? super Drawable> transition) {
                        holder.prodImgBg.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        holder.prodImgBg.setBackgroundResource(R.drawable.bg_product_img_pink);
                    }
                });

        // ===== BADGE =====
        if (product.getBadge() != null && !product.getBadge().isEmpty()) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(product.getBadge());
        } else {
            holder.tvBadge.setVisibility(View.GONE);
        }

        // ===== OLD PRICE (fake) =====
        double oldPrice = product.getPrice() * 1.3;
        holder.tvOldPrice.setVisibility(View.VISIBLE);
        holder.tvOldPrice.setText(df.format(oldPrice) + "đ");
        holder.tvOldPrice.setPaintFlags(
                holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG
        );

        // ===== CLICK =====
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onProductClick(product, position);
        });

        holder.btnAdd.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAddToCart(product, position);
            } else {
                Toast.makeText(context, "Đã thêm " + product.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    // ===== VIEW HOLDER =====
    static class ProductViewHolder extends RecyclerView.ViewHolder {

        FrameLayout prodImgBg;
        TextView tvProductName, tvBrandName, tvPrice, tvOldPrice, tvBadge;
        Button btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            prodImgBg = itemView.findViewById(R.id.prodImgBg);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}