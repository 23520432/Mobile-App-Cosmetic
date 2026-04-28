package com.example.marketingcosmetics.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.activities.LoginActivity;
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
                "menuSupport", "menuLogout"
        };

        String[] messages = {
                "Thông tin cá nhân", "Điểm thưởng & ưu đãi", "Thông báo",
                "Bảo mật & Quyền riêng tư", "Hỗ trợ & Trợ giúp", "Đăng xuất"
        };

        int[] ids = {
                R.id.menuPersonalInfo, R.id.menuPoints, R.id.menuNotif,
                R.id.menuSecurity, R.id.menuSupport, R.id.menuLogout
        };

        for (int i = 0; i < ids.length; i++) {
            final String msg = messages[i];
            View item = view.findViewById(ids[i]);
            if (item != null) {
                int finalI = i;
                item.setOnClickListener(v -> {
                    if (ids[finalI] == R.id.menuPersonalInfo) {
                        Intent intent = new Intent(getContext(), PersonalInfoActivity.class);
                        startActivity(intent);
                    }
                    else if (ids[finalI] == R.id.menuLogout) {
                        // Thực hiện đăng xuất
                        performLogout();
                    }else {
                        // Các menu khác tạm thời vẫn hiện Toast như cũ
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void performLogout() {
        com.example.marketingcosmetics.utils.SessionManager session = new com.example.marketingcosmetics.utils.SessionManager(getContext());
        session.logout(); // Xóa sạch dữ liệu đã lưu trong SharedPreferences

        // Chuyển về màn hình Login
        Intent intent = new Intent(getActivity(), LoginActivity.class);

        // Xóa toàn bộ lịch sử các màn hình trước đó để người dùng không bấm Back quay lại được
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        startActivity(intent);
        getActivity().finish();
    }
}