package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.marketingcosmetics.R;

public class ProfileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        setupMenuItems(view);
        return view;
    }

    private void setupMenuItems(View view) {
        String[] menuItemIds = {
                "menuPersonalInfo", "menuPoints", "menuOrders",
                "menuPayment", "menuNotif", "menuSecurity",
                "menuLanguage", "menuSupport"
        };

        String[] messages = {
                "Thông tin cá nhân", "Điểm thưởng & ưu đãi",
                "Lịch sử đơn hàng", "Phương thức thanh toán",
                "Thông báo", "Bảo mật & Quyền riêng tư",
                "Ngôn ngữ & Khu vực", "Hỗ trợ & Trợ giúp"
        };

        int[] ids = {
                R.id.menuPersonalInfo, R.id.menuPoints, R.id.menuOrders,
                R.id.menuPayment, R.id.menuNotif, R.id.menuSecurity,
                R.id.menuLanguage, R.id.menuSupport
        };

        for (int i = 0; i < ids.length; i++) {
            final String msg = messages[i];
            View item = view.findViewById(ids[i]);
            if (item != null) {
                item.setOnClickListener(v ->
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show()
                );
            }
        }
    }
}