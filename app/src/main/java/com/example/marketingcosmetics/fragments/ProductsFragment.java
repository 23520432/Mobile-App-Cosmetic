package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.adapters.ProductAdapter;
import com.example.marketingcosmetics.models.Product;
import java.util.*;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> allProducts;
    private List<Product> filteredProducts;
    private LinearLayout filterBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);

        allProducts = getAllProducts();
        filteredProducts = new ArrayList<>(allProducts);

        recyclerView = view.findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductAdapter(getContext(), filteredProducts);
        adapter.setOnProductClickListener(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product, int position) {
                Toast.makeText(getContext(), product.getName(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onAddToCart(Product product, int position) {
                Toast.makeText(getContext(), "Đã thêm: " + product.getName(),
                               Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        setupFilters(view);
        return view;
    }

    private void setupFilters(View view) {
        String[] filters = {"Tất cả", "Dưỡng da", "Trang điểm", "Chăm sóc mắt", "Body"};
        filterBar = view.findViewById(R.id.filterBar);

        for (int i = 0; i < filters.length; i++) {
            final String filter = filters[i];
            final int idx = i;
            Button chip = (Button) LayoutInflater.from(getContext())
                    .inflate(R.layout.item_filter_chip, filterBar, false);
            chip.setText(filter);
            chip.setSelected(i == 0);

            chip.setOnClickListener(v -> {
                for (int j = 0; j < filterBar.getChildCount(); j++) {
                    filterBar.getChildAt(j).setSelected(false);
                }
                chip.setSelected(true);
                filterProducts(filter);
            });
            filterBar.addView(chip);
        }
    }

    private void filterProducts(String category) {
        filteredProducts.clear();
        if (category.equals("Tất cả")) {
            filteredProducts.addAll(allProducts);
        } else {
            for (Product p : allProducts) {
                if (p.getCategory().equals(category)) {
                    filteredProducts.add(p);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    private List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        list.add(new Product("Rose Lumière Sérum", "LUMIÈRE PARIS", "490k", "700k",
                "-30%", "ROSE\nSÉRUM", "🌹", 1, "Dưỡng da"));
        list.add(new Product("Velvet Night Cream", "ROSE ATELIER", "620k", "",
                "", "VELVET\nCREAM", "🌙", 2, "Dưỡng da"));
        list.add(new Product("Botanica Face Oil", "BOTANICA LUXE", "850k", "",
                "Mới", "BOTANICA\nOIL", "🌿", 3, "Dưỡng da"));
        list.add(new Product("Golden Glow Mist", "PURE ÉCLAT", "380k", "",
                "", "GOLDEN\nMIST", "✨", 4, "Trang điểm"));
        list.add(new Product("Eye Revive Serum", "LUMIÈRE PARIS", "550k", "750k",
                "-27%", "EYE\nSERUM", "👁", 1, "Chăm sóc mắt"));
        list.add(new Product("Body Glow Oil", "BOTANICA LUXE", "420k", "",
                "Mới", "BODY\nOIL", "🌸", 3, "Body"));
        list.add(new Product("Velvet Lip Color", "ROSE ATELIER", "290k", "",
                "", "LIP\nCOLOR", "💄", 2, "Trang điểm"));
        list.add(new Product("Golden Face Mask", "PURE ÉCLAT", "680k", "850k",
                "-20%", "FACE\nMASK", "🏅", 4, "Dưỡng da"));
        return list;
    }
}