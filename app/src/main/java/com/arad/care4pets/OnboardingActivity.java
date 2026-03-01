package com.arad.care4pets;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private Button btnNext;
    private Button btnSkip;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewPager);
        btnNext = findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        tabLayout = findViewById(R.id.tabLayout);

        //FIX: Make sure Skip button is ALWAYS clickable
        btnSkip.bringToFront();
        btnSkip.invalidate();
        btnSkip.requestLayout();

        OnboardingAdapter adapter = new OnboardingAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager,
        (tab, position) -> {
        // no tab text/icons required
    }).attach();

        btnNext.setOnClickListener(v -> {
        int current = viewPager.getCurrentItem();
        if (current < 2) {
            viewPager.setCurrentItem(current + 1);
        } else {
            navigateToLogin();
        }
    });

        btnSkip.setOnClickListener(v -> navigateToLogin());

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                if (position == 2) {
                    btnNext.setText("Get Started");
                    btnNext.setBackground(getDrawable(R.drawable.bg_button_onboarding3));
                    btnSkip.setVisibility(Button.INVISIBLE);
                } else {
                    btnNext.setText("Next");
                    if (position == 0) {
                        btnNext.setBackground(getDrawable(R.drawable.bg_button_onboarding1));
                    } else {
                        btnNext.setBackground(getDrawable(R.drawable.bg_button_onboarding2));
                    }
                    btnSkip.setVisibility(Button.VISIBLE);
                }
            }
        });
    }

    private void navigateToLogin() {
        getSharedPreferences("care4pets_prefs", MODE_PRIVATE)
            .edit()
            .putBoolean("first_time", false)
            .apply();

        Intent intent = new Intent(OnboardingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
