package com.example.marketingcosmetics.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.PersonalInfoActivity;

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
                "menuPersonalInfo", "menuPoints", "menuNotif", "menuSecurity",
                "menuSupport"
        };

        String[] messages = {
                "Thông tin cá nhân", "Điểm thưởng & ưu đãi",
                "Thông báo", "Bảo mật & Quyền riêng tư", "Hỗ trợ & Trợ giúp"
        };

        int[] ids = {
                R.id.menuPersonalInfo, R.id.menuPoints, R.id.menuNotif, R.id.menuSecurity, R.id.menuSupport
        };

        for (int i = 0; i < ids.length; i++) {
            final String msg = messages[i];
            View item = view.findViewById(ids[i]);
            if (item != null) {
                int finalI = i;
                item.setOnClickListener(v -> {
                    if (ids[finalI] == R.id.menuPersonalInfo) {
                        // Nếu bấm vào "Thông tin cá nhân", mở Activity mới
                        Intent intent = new Intent(getContext(), PersonalInfoActivity.class);
                        startActivity(intent);
                    } else {
                        // Các menu khác tạm thời vẫn hiện Toast như cũ
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
}