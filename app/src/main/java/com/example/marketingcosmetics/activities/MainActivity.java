package com.example.marketingcosmetics.activities;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.example.marketingcosmetics.R;

import com.example.marketingcosmetics.fragments.*;

public class MainActivity extends AppCompatActivity {

    private LinearLayout tabHome, tabProducts, tabOffers, tabCampaigns, tabProfile;
    private FrameLayout tabHomeIcon, tabProductsIcon, tabOffersIcon, tabCampaignsIcon, tabProfileIcon;
    private TextView tabHomeLabel, tabProductsLabel, tabOffersLabel, tabCampaignsLabel, tabProfileLabel;

    private int currentTab = 0; // 0=home, 1=products, 2=offers, 3=campaigns, 4=profile

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setupTabListeners();
        loadFragment(new HomeFragment(), 0);
        updateTabUI(0);

        // Notification button
        findViewById(R.id.btnNotification).setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, NotificationActivity.class));
            overridePendingTransition(R.anim.slide_in_bottom, 0);
        });
    }

    private void initViews() {
        tabHome = findViewById(R.id.tabHome);
        tabProducts = findViewById(R.id.tabProducts);
        tabOffers = findViewById(R.id.tabOffers);
        tabCampaigns = findViewById(R.id.tabCampaigns);
        tabProfile = findViewById(R.id.tabProfile);

        tabHomeIcon = findViewById(R.id.tabHomeIcon);
        tabProductsIcon = findViewById(R.id.tabProductsIcon);
        tabOffersIcon = findViewById(R.id.tabOffersIcon);
        tabCampaignsIcon = findViewById(R.id.tabCampaignsIcon);
        tabProfileIcon = findViewById(R.id.tabProfileIcon);

        tabHomeLabel = findViewById(R.id.tabHomeLabel);
        tabProductsLabel = findViewById(R.id.tabProductsLabel);
        tabOffersLabel = findViewById(R.id.tabOffersLabel);
        tabCampaignsLabel = findViewById(R.id.tabCampaignsLabel);
        tabProfileLabel = findViewById(R.id.tabProfileLabel);
    }

    private void setupTabListeners() {
        tabHome.setOnClickListener(v -> switchTab(0));
        tabProducts.setOnClickListener(v -> switchTab(1));
        tabOffers.setOnClickListener(v -> switchTab(2));
        tabCampaigns.setOnClickListener(v -> switchTab(3));
        tabProfile.setOnClickListener(v -> switchTab(4));
    }

    public void switchTab(int tabIndex) {
        if (currentTab == tabIndex) return;
        currentTab = tabIndex;

        Fragment fragment;
        switch (tabIndex) {
            case 1: fragment = new ProductsFragment(); break;
            case 2: fragment = new OffersFragment(); break;
            case 3: fragment = new CampaignsFragment(); break;
            case 4: fragment = new ProfileFragment(); break;
            default: fragment = new HomeFragment(); break;
        }

        loadFragment(fragment, tabIndex);
        updateTabUI(tabIndex);
    }

    private void loadFragment(Fragment fragment, int tabIndex) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in, 0);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }

    private void updateTabUI(int activeTab) {
        // Reset all tabs
        int[] iconIds = {R.id.tabHomeIcon, R.id.tabProductsIcon, R.id.tabOffersIcon,
                R.id.tabCampaignsIcon, R.id.tabProfileIcon};
        TextView[] labels = {tabHomeLabel, tabProductsLabel, tabOffersLabel,
                tabCampaignsLabel, tabProfileLabel};
        FrameLayout[] icons = {tabHomeIcon, tabProductsIcon, tabOffersIcon,
                tabCampaignsIcon, tabProfileIcon};

        for (int i = 0; i < labels.length; i++) {
            if (i == activeTab) {
                labels[i].setTextColor(getColor(R.color.lilac3));
                labels[i].setTextSize(10f);
                android.util.TypedValue tv = new android.util.TypedValue();
                labels[i].setTypeface(null, android.graphics.Typeface.BOLD);
                icons[i].setBackgroundResource(R.drawable.bg_tab_active);
            } else {
                labels[i].setTextColor(getColor(R.color.mid));
                labels[i].setTypeface(null, android.graphics.Typeface.NORMAL);
                icons[i].setBackgroundResource(0);
            }
        }
    }
}