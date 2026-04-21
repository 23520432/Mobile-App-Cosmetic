package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.MainActivity;
import com.example.marketingcosmetics.adapters.ProductAdapter;
import com.example.marketingcosmetics.models.Product;
import java.util.*;

public class HomeFragment extends Fragment {

    private LinearLayout llProductsRow;
    private Button btnMonthly, btnYearly;
    private TextView tvPrice1, tvPer1, tvPrice2, tvPer2;
    private boolean isMonthly = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        loadProducts(view);
        setupMembership(view);
        setupHeroButtons(view);

        return view;
    }

    private void initViews(View view) {
        llProductsRow = view.findViewById(R.id.llProductsRow);
        btnMonthly = view.findViewById(R.id.btnMonthly);
        btnYearly = view.findViewById(R.id.btnYearly);
        tvPrice1 = view.findViewById(R.id.tvPrice1);
        tvPer1 = view.findViewById(R.id.tvPer1);
        tvPrice2 = view.findViewById(R.id.tvPrice2);
        tvPer2 = view.findViewById(R.id.tvPer2);
    }

    private void loadProducts(View view) {
        List<Product> products = getHomeProducts();
        LayoutInflater inflater = LayoutInflater.from(getContext());

        for (Product p : products) {
            View card = inflater.inflate(R.layout.item_product_card,
                    llProductsRow, false);
            bindProductCard(card, p);
            llProductsRow.addView(card);
        }

        view.findViewById(R.id.tvSeeMore).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchTab(1);
            }
        });
    }

    private void bindProductCard(View card, Product p) {
        ((TextView) card.findViewById(R.id.tvProductName)).setText(p.getName());
        ((TextView) card.findViewById(R.id.tvBrandName)).setText(p.getBrand());
        ((TextView) card.findViewById(R.id.tvPrice)).setText(p.getPrice());
        ((TextView) card.findViewById(R.id.tvBottleName)).setText(p.getBottleName());
        ((TextView) card.findViewById(R.id.tvBottleEmoji)).setText(p.getEmoji());

        TextView tvBadge = card.findViewById(R.id.tvBadge);
        if (p.getBadge() != null && !p.getBadge().isEmpty()) {
            tvBadge.setVisibility(View.VISIBLE);
            tvBadge.setText(p.getBadge());
        } else {
            tvBadge.setVisibility(View.GONE);
        }

        TextView tvOldPrice = card.findViewById(R.id.tvOldPrice);
        if (p.getOldPrice() != null && !p.getOldPrice().isEmpty()) {
            tvOldPrice.setVisibility(View.VISIBLE);
            tvOldPrice.setText(p.getOldPrice());
            tvOldPrice.setPaintFlags(tvOldPrice.getPaintFlags() |
                    android.graphics.Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            tvOldPrice.setVisibility(View.GONE);
        }

        int bgRes;
        switch (p.getBgType()) {
            case 2: bgRes = R.drawable.bg_product_img_lilac; break;
            case 3: bgRes = R.drawable.bg_product_img_green; break;
            case 4: bgRes = R.drawable.bg_product_img_gold; break;
            default: bgRes = R.drawable.bg_product_img_pink; break;
        }
        card.findViewById(R.id.prodImgBg).setBackgroundResource(bgRes);

        card.findViewById(R.id.btnAdd).setOnClickListener(v ->
                Toast.makeText(getContext(), "Đã thêm: " + p.getName(), Toast.LENGTH_SHORT).show()
        );
    }

    private void setupMembership(View view) {
        btnMonthly.setOnClickListener(v -> {
            isMonthly = true;
            updatePricing();
            btnMonthly.setBackgroundResource(R.drawable.bg_btn_primary);
            btnMonthly.setTextColor(requireContext().getColor(R.color.white));
            btnYearly.setBackgroundResource(android.R.color.transparent);
            btnYearly.setTextColor(requireContext().getColor(R.color.dark2));
        });

        btnYearly.setOnClickListener(v -> {
            isMonthly = false;
            updatePricing();
            btnYearly.setBackgroundResource(R.drawable.bg_btn_primary);
            btnYearly.setTextColor(requireContext().getColor(R.color.white));
            btnMonthly.setBackgroundResource(android.R.color.transparent);
            btnMonthly.setTextColor(requireContext().getColor(R.color.dark2));
        });
    }

    private void updatePricing() {
        if (isMonthly) {
            tvPrice1.setText("149k");
            tvPer1.setText("/tháng");
            tvPrice2.setText("299k");
            tvPer2.setText("/tháng");
        } else {
            tvPrice1.setText("1.2tr");
            tvPer1.setText("/năm (-33%)");
            tvPrice2.setText("2.4tr");
            tvPer2.setText("/năm (-33%)");
        }
    }

    private void setupHeroButtons(View view) {
        view.findViewById(R.id.btnExplore).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchTab(1);
            }
        });
        view.findViewById(R.id.btnCampaigns).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchTab(3);
            }
        });
    }

    private List<Product> getHomeProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("Rose Lumière Sérum", "LUMIÈRE PARIS", "490k", "700k",
                "-30%", "ROSE\nSÉRUM", "🌹", 1, "Dưỡng da"));
        list.add(new Product("Velvet Night Cream", "ROSE ATELIER", "620k", "",
                "", "VELVET\nCREAM", "🌙", 2, "Dưỡng da"));
        list.add(new Product("Botanica Face Oil", "BOTANICA LUXE", "850k", "",
                "Mới", "BOTANICA\nOIL", "🌿", 3, "Dưỡng da"));
        list.add(new Product("Golden Glow Mist", "PURE ÉCLAT", "380k", "",
                "", "GOLDEN\nMIST", "✨", 4, "Trang điểm"));
        return list;
    }
}