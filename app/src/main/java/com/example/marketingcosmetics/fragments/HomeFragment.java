package com.example.marketingcosmetics.fragments;

import android.graphics.Color;
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

// Thư viện vẽ biểu đồ
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.*;

import java.util.*;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;

    // Biến cho Biểu đồ
    private PieChart pieChartConversions;

    // Thêm các biến cho phần Phễu
    private Spinner spinnerCampaigns;

    // Thay thế biến barChartFunnel bằng các View tự chế
    private LinearLayout bgBarInteractions, bgBarConversions;
    private View barInteractions, barConversions;
    private TextView tvValInteractions, tvValConversions;

    private final String API_CAMPAIGN_LIST_URL = "http://10.0.2.2:3000/api/conversions/campaign-list";
    private final String API_FUNNEL_BASE_URL = "http://10.0.2.2:3000/api/conversions/funnel/";

    private BarChart barChartProductConversions;
    private LinearLayout layoutProductLegend;

    private final String API_STATS_URL = "http://10.0.2.2:3000/api/conversions/stats";
    private final String API_TOP_PRODUCTS_URL = "http://10.0.2.2:3000/api/conversions/top-products";

    private final String API_URL = "http://10.0.2.2:3000/api/products/top-converting";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initViews(view);
        setupRecyclerView();

        loadProductsFromAPI();
        loadConversionStats();
        loadCampaignListForSpinner();
        loadTopProductsStats();

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

        // chiến dịch
        spinnerCampaigns = view.findViewById(R.id.spinnerCampaigns);
        bgBarInteractions = view.findViewById(R.id.bgBarInteractions);
        bgBarConversions = view.findViewById(R.id.bgBarConversions);
        barInteractions = view.findViewById(R.id.barInteractions);
        barConversions = view.findViewById(R.id.barConversions);
        tvValInteractions = view.findViewById(R.id.tvValInteractions);
        tvValConversions = view.findViewById(R.id.tvValConversions);

        // Chart
        pieChartConversions = view.findViewById(R.id.pieChartConversions);
        barChartProductConversions = view.findViewById(R.id.barChartProductConversions);

        // Legend layout
        layoutProductLegend = view.findViewById(R.id.layoutProductLegend);
    }

    private void setupRecyclerView() {
        productList = new ArrayList<>();
        adapter = new ProductAdapter(getContext(), productList);

        recyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        recyclerView.setAdapter(adapter);
    }

    // CALL API
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

    // CALL API: THỐNG KÊ BIỂU ĐỒ
    private void loadConversionStats() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        // API trả về Object dạng { "APP": 10, "CAMPAIGN": 5 } nên dùng JsonObjectRequest
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                API_STATS_URL,
                null,
                response -> {
                    try {
                        int appConversions = response.getInt("APP");
                        int campaignConversions = response.getInt("CAMPAIGN");

                        // Gọi hàm vẽ biểu đồ sau khi có data
                        drawPieChart(appConversions, campaignConversions);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (isAdded() && getContext() != null) {
                        Toast.makeText(getContext(), "Lỗi load Thống kê", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }

    // VẼ BIỂU ĐỒ
    private void drawPieChart(int appData, int campaignData) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        if (appData > 0) entries.add(new PieEntry(appData, "Organic (App)"));
        if (campaignData > 0) entries.add(new PieEntry(campaignData, "Qua Chiến dịch"));

        // Nếu database trống chưa có ai mua
        if (entries.isEmpty()) {
            pieChartConversions.clear();
            pieChartConversions.setNoDataText("Chưa có dữ liệu chuyển đổi");
            return;
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        // Set màu sắc cho biểu đồ
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#B39DDB")); // Tím Lilac (Organic)
        colors.add(Color.parseColor("#F48FB1")); // Hồng Phấn (Qua Chiến dịch)
        dataSet.setColors(colors);

        dataSet.setValueTextSize(14f);
        dataSet.setValueTextColor(Color.WHITE);

        // Ép kiểu bỏ số .0 ở đuôi (Ví dụ: 10.0 -> 10)
        dataSet.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        PieData data = new PieData(dataSet);
        pieChartConversions.setData(data);
        // tắt chữ chỉ giữ số
        pieChartConversions.setDrawEntryLabels(false);

        // Làm đẹp biểu đồ
        pieChartConversions.getDescription().setEnabled(false); // Ẩn chữ description
        pieChartConversions.setCenterText("TỔNG QUAN\nCHUYỂN ĐỔI");
        pieChartConversions.setCenterTextSize(12f);
        pieChartConversions.setHoleRadius(40f); // Độ rộng của lỗ tròn ở giữa
        pieChartConversions.setTransparentCircleRadius(45f);

        // 👉 Tắt hoàn toàn chú thích mặc định (vì đã có XML lo)
        pieChartConversions.getLegend().setEnabled(false);

        pieChartConversions.animateY(1000); // Hiệu ứng xoay khi load
        pieChartConversions.invalidate(); // Vẽ lại
    }

    // Lớp phụ để lưu dữ liệu cho Spinner
    class CampaignItem {
        int id;
        String title;

        public CampaignItem(int id, String title) {
            this.id = id;
            this.title = title;
        }

        // Override toString để Spinner biết hiển thị cái chữ gì ra ngoài màn hình
        @Override
        public String toString() {
            return title;
        }
    }
    // ==========================================
    // 1. TẢI DANH SÁCH CHIẾN DỊCH CHO SPINNER
    // ==========================================
    private void loadCampaignListForSpinner() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                API_CAMPAIGN_LIST_URL,
                null,
                response -> {
                    ArrayList<CampaignItem> campaignList = new ArrayList<>();
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            campaignList.add(new CampaignItem(obj.getInt("ID"), obj.getString("TITLE")));
                        }

                        // Đổ dữ liệu vào Spinner
                        ArrayAdapter<CampaignItem> adapter = new ArrayAdapter<>(
                                requireContext(),
                                android.R.layout.simple_spinner_dropdown_item,
                                campaignList
                        );
                        spinnerCampaigns.setAdapter(adapter);

                        // Lắng nghe sự kiện người dùng chọn chiến dịch khác
                        spinnerCampaigns.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                CampaignItem selectedCampaign = (CampaignItem) parent.getSelectedItem();
                                // Khi chọn xong, gọi API lấy dữ liệu Phễu của chiến dịch đó
                                loadFunnelData(selectedCampaign.id);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {}
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (isAdded() && getContext() != null) {
                        Toast.makeText(getContext(), "Lỗi tải danh sách Spinner", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }

    // ==========================================
    // 2. GỌI API LẤY SỐ LIỆU PHỄU
    // ==========================================
    private void loadFunnelData(int campaignId) {
        String url = API_FUNNEL_BASE_URL + campaignId;
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        int interactions = response.getInt("total_interactions");
                        int conversions = response.getInt("total_conversions");

                        drawFunnelChart(interactions, conversions);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getContext(), "Lỗi tải dữ liệu Phễu", Toast.LENGTH_SHORT).show()
        );
        queue.add(request);
    }

    private void drawFunnelChart(int interactions, int conversions) {
        // Hiển thị số liệu ra TextView bên lề phải
        tvValInteractions.setText(String.valueOf(interactions));
        tvValConversions.setText(String.valueOf(conversions));

        // Tìm giá trị lớn nhất để làm mốc 100% độ rộng màn hình
        float maxVal = Math.max(interactions, conversions);
        if (maxVal == 0) maxVal = 1; // Chống lỗi chia cho 0

        // Set mốc 100% cho 2 khung chứa
        bgBarInteractions.setWeightSum(maxVal);
        bgBarConversions.setWeightSum(maxVal);

        // Kéo dài thanh Tương tác tương ứng với số điểm
        LinearLayout.LayoutParams paramInter = (LinearLayout.LayoutParams) barInteractions.getLayoutParams();
        paramInter.weight = interactions;
        barInteractions.setLayoutParams(paramInter);

        // Kéo dài thanh Chuyển đổi tương ứng với số điểm
        LinearLayout.LayoutParams paramConv = (LinearLayout.LayoutParams) barConversions.getLayoutParams();
        paramConv.weight = conversions;
        barConversions.setLayoutParams(paramConv);
    }

    // HÀM GỌI API TOP SẢN PHẨM
    private void loadTopProductsStats() {
        RequestQueue queue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                API_TOP_PRODUCTS_URL,
                null,
                response -> {
                    ArrayList<BarEntry> entries = new ArrayList<>();
                    String[] productNames = new String[response.length()];

                    try {
                        for (int i = 0; i < response.length(); i++) {
                            JSONObject obj = response.getJSONObject(i);
                            String title = obj.getString("TITLE");
                            int total = obj.getInt("TOTAL_CONVERSIONS");

                            productNames[i] = title;
                            entries.add(new BarEntry(i, total));
                        }
                        drawProductBarChart(entries, productNames);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    if (isAdded() && getContext() != null) {
                        Toast.makeText(getContext(), "Lỗi load Top Sản phẩm", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        queue.add(request);
    }


    // HÀM VẼ BAR CHART SẢN PHẨM
    private void drawProductBarChart(ArrayList<BarEntry> entries, String[] productNames) {
        if (entries.isEmpty()) {
            barChartProductConversions.clear();
            return;
        }

        // Đổi tone màu khác (Xanh ngọc, Hổ phách, Đỏ sậm...) để phân biệt với Chiến dịch
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#F48FB1"));
        colors.add(Color.parseColor("#CE93D8"));
        colors.add(Color.parseColor("#80CBC4"));
        colors.add(Color.parseColor("#FFE082"));
        colors.add(Color.parseColor("#90CAF9"));

        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);

        dataSet.setValueFormatter(new com.github.mikephil.charting.formatter.ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.valueOf((int) value);
            }
        });

        BarData data = new BarData(dataSet);
        data.setBarWidth(0.5f);
        barChartProductConversions.setData(data);

        // Cấu hình Trục X
        XAxis xAxis = barChartProductConversions.getXAxis();
        xAxis.setDrawLabels(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        // Cấu hình Trục Y
        barChartProductConversions.getAxisLeft().setAxisMinimum(0f);
        barChartProductConversions.getAxisLeft().setGranularity(1f);
        barChartProductConversions.getAxisRight().setEnabled(false);

        // Tắt Legend mặc định & cấu hình chung
        barChartProductConversions.getLegend().setEnabled(false);
        barChartProductConversions.getDescription().setEnabled(false);
        barChartProductConversions.setFitBars(true);
        barChartProductConversions.animateY(1500);
        barChartProductConversions.invalidate();

        // Vẽ chú thích
        renderProductLegend(productNames, colors);
    }

    // Hàm vẽ chú thích cho top 5 sản phẩm
    private void renderProductLegend(String[] productNames, ArrayList<Integer> colors) {
        layoutProductLegend.removeAllViews();

        for (int i = 0; i < productNames.length; i++) {
            LinearLayout itemLayout = new LinearLayout(requireContext());
            itemLayout.setOrientation(LinearLayout.HORIZONTAL);
            itemLayout.setPadding(0, 8, 0, 8);

            View colorBox = new View(requireContext());
            LinearLayout.LayoutParams colorParams = new LinearLayout.LayoutParams(30, 30);
            colorParams.setMargins(0, 0, 16, 0);
            colorBox.setLayoutParams(colorParams);
            colorBox.setBackgroundColor(colors.get(i % colors.size()));

            TextView tvName = new TextView(requireContext());
            tvName.setText(productNames[i]);
            tvName.setTextSize(14f);
            tvName.setTextColor(Color.BLACK);

            itemLayout.addView(colorBox);
            itemLayout.addView(tvName);
            layoutProductLegend.addView(itemLayout);
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