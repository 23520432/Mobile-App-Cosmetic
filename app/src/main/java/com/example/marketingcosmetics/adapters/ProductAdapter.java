package com.example.marketingcosmetics.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.models.Product;
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
        View view = LayoutInflater.from(context).inflate(R.layout.item_product_card, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);

        holder.tvProductName.setText(product.getName());
        holder.tvBrandName.setText(product.getBrand());
        holder.tvPrice.setText(product.getPrice());
        holder.tvBottleName.setText(product.getBottleName());
        holder.tvBottleEmoji.setText(product.getEmoji());

        // Badge
        if (product.getBadge() != null && !product.getBadge().isEmpty()) {
            holder.tvBadge.setVisibility(View.VISIBLE);
            holder.tvBadge.setText(product.getBadge());
        } else {
            holder.tvBadge.setVisibility(View.GONE);
        }

        // Old price with strikethrough
        if (product.getOldPrice() != null && !product.getOldPrice().isEmpty()) {
            holder.tvOldPrice.setVisibility(View.VISIBLE);
            holder.tvOldPrice.setText(product.getOldPrice());
            holder.tvOldPrice.setPaintFlags(holder.tvOldPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tvOldPrice.setVisibility(View.GONE);
        }

        // Background type
        int bgRes;
        switch (product.getBgType()) {
            case 2: bgRes = R.drawable.bg_product_img_lilac; break;
            case 3: bgRes = R.drawable.bg_product_img_green; break;
            case 4: bgRes = R.drawable.bg_product_img_gold; break;
            default: bgRes = R.drawable.bg_product_img_pink; break;
        }
        holder.prodImgBg.setBackgroundResource(bgRes);

        // Clicks
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
    public int getItemCount() { return products.size(); }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        LinearLayout prodImgBg;
        TextView tvProductName, tvBrandName, tvPrice, tvOldPrice, tvBadge, tvBottleName, tvBottleEmoji;
        Button btnAdd;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            prodImgBg = itemView.findViewById(R.id.prodImgBg);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvOldPrice = itemView.findViewById(R.id.tvOldPrice);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            tvBottleName = itemView.findViewById(R.id.tvBottleName);
            tvBottleEmoji = itemView.findViewById(R.id.tvBottleEmoji);
            btnAdd = itemView.findViewById(R.id.btnAdd);
        }
    }
}