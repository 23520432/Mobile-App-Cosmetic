package com.example.marketingcosmetics.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.marketingcosmetics.R;

public class OffersFragment extends Fragment {

    private TextView tvHours, tvMinutes, tvSeconds;
    private CountDownTimer countDownTimer;

    // 8h 24m 37s in millis
    private long timeLeftMs = (8 * 3600 + 24 * 60 + 37) * 1000L;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers, container, false);

        tvHours = view.findViewById(R.id.tvHours);
        tvMinutes = view.findViewById(R.id.tvMinutes);
        tvSeconds = view.findViewById(R.id.tvSeconds);

        startCountdown();
        setupOfferChips(view);

        return view;
    }

    private void startCountdown() {
        countDownTimer = new CountDownTimer(timeLeftMs, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftMs = millisUntilFinished;
                long hours = millisUntilFinished / (1000 * 3600);
                long minutes = (millisUntilFinished % (1000 * 3600)) / (1000 * 60);
                long seconds = (millisUntilFinished % (1000 * 60)) / 1000;
                tvHours.setText(String.format("%02d", hours));
                tvMinutes.setText(String.format("%02d", minutes));
                tvSeconds.setText(String.format("%02d", seconds));
            }

            @Override
            public void onFinish() {
                tvHours.setText("00");
                tvMinutes.setText("00");
                tvSeconds.setText("00");
            }
        }.start();
    }

    private void setupOfferChips(View view) {
        // Offer chip click handlers
        String[] chipIds = {"🌸 Mua 2 tặng 1", "🎟️ Voucher", "✨ Quà sinh nhật"};
        LinearLayout chipsContainer = view.findViewById(R.id.offerChipsContainer);

        for (String chip : chipIds) {
            TextView tv = new TextView(getContext());
            tv.setText(chip);
            tv.setTextSize(13f);
            tv.setTextColor(requireContext().getColor(R.color.dark2));
            tv.setBackground(requireContext().getDrawable(R.drawable.bg_hero_badge));
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, 0, 8, 8);
            tv.setLayoutParams(lp);
            tv.setPadding(28, 14, 28, 14);
            tv.setClickable(true);
            tv.setFocusable(true);
            tv.setOnClickListener(v ->
                    Toast.makeText(getContext(), chip, Toast.LENGTH_SHORT).show()
            );
            chipsContainer.addView(tv);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (countDownTimer != null) countDownTimer.cancel();
    }
}