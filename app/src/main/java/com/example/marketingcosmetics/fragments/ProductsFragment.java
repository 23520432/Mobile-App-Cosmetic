package com.example.marketingcosmetics.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.ProductDetailActivity;
import com.example.marketingcosmetics.adapters.ProductAdapter;
import com.example.marketingcosmetics.models.Product;

import org.json.*;

import java.util.*;

public class ProductsFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> allProducts = new ArrayList<>();
    private List<Product> filteredProducts = new ArrayList<>();
    private LinearLayout filterBar;

    private final String API_URL = "http://10.0.2.2:3000/api/products";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_products, container, false);

        recyclerView = view.findViewById(R.id.rvProducts);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        adapter = new ProductAdapter(getContext(), filteredProducts);
        recyclerView.setAdapter(adapter);

        adapter.setOnProductClickListener(new ProductAdapter.OnProductClickListener() {
            @Override
            public void onProductClick(Product product, int position) {

                Intent intent = new Intent(getContext(), ProductDetailActivity.class);

                intent.putExtra("name", product.getName());
                intent.putExtra("brand", product.getBrand());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("image", product.getImageUrl());
                intent.putExtra("description", product.getDescription());
                intent.putStringArrayListExtra(
                        "ingredients",
                        new ArrayList<>(Arrays.asList(product.getIngredientList()))
                );

                startActivity(intent);
            }

            @Override
            public void onAddToCart(Product product, int position) {
                Toast.makeText(getContext(), "Đã thêm: " + product.getName(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        setupFilters(view);
        loadProductsFromAPI();

        return view;
    }

    // ===== CALL API =====
    private void loadProductsFromAPI() {

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {

                    allProducts.clear();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);

                            Product p = new Product(
                                    obj.getInt("ID"),
                                    obj.getString("NAME"),
                                    obj.getString("BRAND"),
                                    obj.getDouble("PRICE"),
                                    obj.getString("IMAGE_URL"),
                                    obj.getString("DESCRIPTION"),
                                    obj.getString("INGREDIENTS"),
                                    obj.getInt("CATEGORY_ID"),
                                    obj.getString("BUY_LINK"),
                                    obj.getString("CREATED_AT")
                            );

                            // UI thêm
                            p.setBgType(new Random().nextInt(4) + 1);

                            if (p.getPrice() < 300000) {
                                p.setBadge("SALE");
                            } else {
                                p.setBadge("HOT");
                            }

                            allProducts.add(p);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // load mặc định
                    filteredProducts.clear();
                    filteredProducts.addAll(allProducts);
                    adapter.notifyDataSetChanged();

                },
                error -> Toast.makeText(getContext(), "Lỗi API", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    // ===== FILTER =====
    private void setupFilters(View view) {

        // map categoryId -> text
        String[] filters = {"Tất cả", "Skincare", "Makeup", "Perfume"};

        filterBar = view.findViewById(R.id.filterBar);

        for (int i = 0; i < filters.length; i++) {

            final String filter = filters[i];

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

                if (category.equals("Skincare") && p.getCategoryId() == 1) {
                    filteredProducts.add(p);
                } else if (category.equals("Makeup") && p.getCategoryId() == 2) {
                    filteredProducts.add(p);
                } else if (category.equals("Perfume") && p.getCategoryId() == 3) {
                    filteredProducts.add(p);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}