package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.*;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketingcosmetics.R;
import com.example.marketingcosmetics.adapters.CampaignAdapter;
import com.example.marketingcosmetics.models.Campaign;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CampaignsFragment extends Fragment {

    RecyclerView rvCampaigns;
    CampaignAdapter adapter;
    List<Campaign> campaignList = new ArrayList<>();

    Button btnRunning, btnUpcoming, btnEnded;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_campaigns, container, false);

        initView(view);
        setupRecyclerView();
        setupTabs();
        loadCampaigns("ACTIVE");

        return view;
    }

    private void initView(View view) {
        rvCampaigns = view.findViewById(R.id.rvCampaigns);

        btnRunning = view.findViewById(R.id.btnCampRunning);
        btnUpcoming = view.findViewById(R.id.btnCampUpcoming);
        btnEnded = view.findViewById(R.id.btnCampEnded);
    }

    private void setupRecyclerView() {
        rvCampaigns.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CampaignAdapter(getContext(), campaignList);
        rvCampaigns.setAdapter(adapter);
    }

    private void setupTabs() {

        btnRunning.setOnClickListener(v -> {
            setActiveTab(btnRunning, btnUpcoming, btnEnded);
            loadCampaigns("ACTIVE");
        });

        btnUpcoming.setOnClickListener(v -> {
            setActiveTab(btnUpcoming, btnRunning, btnEnded);
            loadCampaigns("UPCOMING");
        });

        btnEnded.setOnClickListener(v -> {
            setActiveTab(btnEnded, btnRunning, btnUpcoming);
            loadCampaigns("ENDED");
        });
    }

    private void setActiveTab(Button active, Button... others) {

        active.setBackgroundResource(R.drawable.bg_btn_primary);
        active.setTextColor(requireContext().getColor(R.color.white));

        for (Button b : others) {
            b.setBackgroundResource(0);
            b.setTextColor(requireContext().getColor(R.color.mid));
        }
    }

    // ================= API =================
    private void loadCampaigns(String status) {

        String url = "http://10.0.2.2:3000/api/campaigns?status=" + status;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {

                    campaignList.clear();

                    try {

                        for (int i = 0; i < response.length(); i++) {

                            JSONObject obj = response.getJSONObject(i);

                            Campaign c = new Campaign();

                            c.setId(obj.getInt("ID")); // nếu backend dùng ID viết hoa
                            c.setTitle(obj.getString("TITLE"));
                            c.setDescription(obj.getString("DESCRIPTION"));
                            c.setImageUrl(obj.getString("IMAGE_URL"));
                            c.setStatus(obj.getString("STATUS"));

                            c.setCreatedByName(obj.getString("CREATOR"));

                            c.setLikeCount(obj.getInt("LIKE_COUNT"));
                            c.setCommentCount(obj.getInt("COMMENT_COUNT"));
                            c.setShareCount(obj.getInt("SHARE_COUNT"));

                            campaignList.add(c);
                        }

                        adapter.notifyDataSetChanged();

                        Log.d("SIZE", "list=" + campaignList.size());

                    } catch (Exception e) {
                        Log.e("PARSE_ERROR", e.toString());
                    }

                },
                error -> {
                    Log.e("API_ERROR", error.toString());
                    Toast.makeText(getContext(), "Load lỗi", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(getContext()).add(request);
    }
}