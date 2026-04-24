package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.*;
import com.android.volley.toolbox.*;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.MainActivity;
import com.example.marketingcosmetics.adapters.ProductAdapter;
import com.example.marketingcosmetics.models.Product;

import org.json.*;

import java.util.*;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    private Button btnMonthly, btnYearly;
    private TextView tvPrice1, tvPer1, tvPrice2, tvPer2;
    private boolean isMonthly = true;

    private final String API_URL = "http://10.0.2.2:3000/api/products"; // 👈 API Node.js

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerView();
        loadProductsFromAPI();
        setupMembership(view);
        setupHeroButtons(view);

        view.findViewById(R.id.tvSeeMore).setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).switchTab(1);
            }
        });

        return view;
    }

    private void initViews(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewProducts);

        btnMonthly = view.findViewById(R.id.btnMonthly);
        btnYearly = view.findViewById(R.id.btnYearly);
        tvPrice1 = view.findViewById(R.id.tvPrice1);
        tvPer1 = view.findViewById(R.id.tvPer1);
        tvPrice2 = view.findViewById(R.id.tvPrice2);
        tvPer2 = view.findViewById(R.id.tvPer2);
    }

    private void setupRecyclerView() {
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), productList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);
    }

    // ===== CALL API =====
    private void loadProductsFromAPI() {

        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                API_URL,
                null,
                response -> {
                    productList.clear();

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

                            // 👉 UI thêm
                            p.setBgType(new Random().nextInt(4) + 1);

                            if (p.getPrice() < 300000) {
                                p.setBadge("SALE");
                            } else {
                                p.setBadge("HOT");
                            }

                            productList.add(p);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    adapter.notifyDataSetChanged();
                },
                error -> Toast.makeText(getContext(), "Lỗi load API", Toast.LENGTH_SHORT).show()
        );

        queue.add(request);
    }

    // ===== MEMBERSHIP =====
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

    // ===== BUTTON =====
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


}