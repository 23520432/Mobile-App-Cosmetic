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
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.ProductDetailActivity;
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
//                Toast.makeText(getContext(), product.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), ProductDetailActivity.class);

                intent.putExtra("name", product.getName());
                intent.putExtra("brand", product.getBrand());
                intent.putExtra("price", product.getPrice());
                intent.putExtra("oldPrice", product.getOldPrice());
                intent.putExtra("badge", product.getBadge());
                intent.putExtra("bottleName", product.getBottleName());
                intent.putExtra("emoji", product.getEmoji());
                intent.putExtra("bgType", product.getBgType());
                intent.putExtra("description", product.getDescription());
                intent.putStringArrayListExtra("ingredients", product.getIngredients());

                startActivity(intent);
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

        // 1. Rose Lumière Sérum
        list.add(new Product("Rose Lumière Sérum", "LUMIÈRE PARIS", "490k", "700k",
                "-30%", "ROSE\nSÉRUM", "🌹", 1, "Dưỡng da",
                "Sérum dưỡng da cao cấp với chiết xuất hoa hồng giúp làm sáng và cấp ẩm sâu cho da.",
                new ArrayList<>(Arrays.asList("🌹 Rosa Extract", "💧 Hyaluronic", "✨ Niacinamide"))));

        // 2. Velvet Night Cream
        list.add(new Product("Velvet Night Cream", "ROSE ATELIER", "620k", "",
                "", "VELVET\nCREAM", "🌙", 2, "Dưỡng da",
                "Kem dưỡng ban đêm giúp phục hồi và tái tạo làn da mệt mỏi sau một ngày dài.",
                new ArrayList<>(Arrays.asList("🌙 Lavender Oil", "🌿 Retinol", "🌸 Vitamin E"))));

        // 3. Botanica Face Oil
        list.add(new Product("Botanica Face Oil", "BOTANICA LUXE", "850k", "",
                "Mới", "BOTANICA\nOIL", "🌿", 3, "Dưỡng da",
                "Dầu dưỡng da chiết xuất từ thảo mộc thiên nhiên, giúp da căng bóng và khỏe mạnh.",
                new ArrayList<>(Arrays.asList("🌿 Jojoba Oil", "🍵 Green Tea", "🌻 Seed Oil"))));

        // 4. Golden Glow Mist
        list.add(new Product("Golden Glow Mist", "PURE ÉCLAT", "380k", "",
                "", "GOLDEN\nMIST", "✨", 4, "Trang điểm",
                "Xịt khoáng giữ lớp nền căng bóng tự nhiên và bảo vệ da khỏi tác động môi trường.",
                new ArrayList<>(Arrays.asList("✨ Gold Flakes", "💧 Mineral Water", "🍊 Vitamin C"))));

        // 5. Eye Revive Serum
        list.add(new Product("Eye Revive Serum", "LUMIÈRE PARIS", "550k", "750k",
                "-27%", "EYE\nSERUM", "👁", 1, "Chăm sóc mắt",
                "Sérum đặc trị vùng mắt, giúp giảm quầng thâm và bọng mắt rõ rệt sau 2 tuần.",
                new ArrayList<>(Arrays.asList("☕ Caffeine", "🧊 Cooling Agent", "🧬 Peptides"))));

        // 6. Body Glow Oil
        list.add(new Product("Body Glow Oil", "BOTANICA LUXE", "420k", "",
                "Mới", "BODY\nOIL", "🌸", 3, "Body",
                "Dầu dưỡng thể mang lại làn da mịn màng cùng hương thơm dịu nhẹ quyến rũ.",
                new ArrayList<>(Arrays.asList("🌸 Jasmine", "🥥 Coconut Oil", "✨ Shimmer"))));

        // 7. Velvet Lip Color
        list.add(new Product("Velvet Lip Color", "ROSE ATELIER", "290k", "",
                "", "LIP\nCOLOR", "💄", 2, "Trang điểm",
                "Son môi lì với kết cấu mịn màng như nhung, không gây khô môi và bền màu cả ngày.",
                new ArrayList<>(Arrays.asList("💄 Pigment", "🍯 Beeswax", "🍓 Berry Fragrance"))));

        // 8. Golden Face Mask
        list.add(new Product("Golden Face Mask", "PURE ÉCLAT", "680k", "850k",
                "-20%", "FACE\nMASK", "🏅", 4, "Dưỡng da",
                "Mặt nạ vàng 24k giúp thải độc và mang lại vẻ rạng rễ tức thì cho làn da.",
                new ArrayList<>(Arrays.asList("🏅 24k Gold", "🧪 Collagen", "Aloe Vera"))));

        return list;
    }
}