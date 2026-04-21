package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.marketingcosmetics.R;

public class CampaignsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_campaigns, container, false);

        setupCampTabs(view);
        return view;
    }

    private void setupCampTabs(View view) {
        Button btnRunning = view.findViewById(R.id.btnCampRunning);
        Button btnUpcoming = view.findViewById(R.id.btnCampUpcoming);
        Button btnEnded = view.findViewById(R.id.btnCampEnded);

        btnRunning.setOnClickListener(v -> {
            setActiveTab(btnRunning, btnUpcoming, btnEnded);
        });
        btnUpcoming.setOnClickListener(v -> {
            setActiveTab(btnUpcoming, btnRunning, btnEnded);
            Toast.makeText(getContext(), "Sắp ra mắt", Toast.LENGTH_SHORT).show();
        });
        btnEnded.setOnClickListener(v -> {
            setActiveTab(btnEnded, btnRunning, btnUpcoming);
            Toast.makeText(getContext(), "Đã kết thúc", Toast.LENGTH_SHORT).show();
        });
    }

    private void setActiveTab(Button active, Button... inactives) {
        active.setBackgroundResource(R.drawable.bg_btn_primary);
        active.setTextColor(requireContext().getColor(R.color.white));
        for (Button b : inactives) {
            b.setBackgroundResource(0);
            b.setTextColor(requireContext().getColor(R.color.mid));
        }
    }
}