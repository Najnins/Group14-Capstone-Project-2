package com.arad.care4pets;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OnboardingAdapter extends FragmentStateAdapter {

    public OnboardingAdapter(@NonNull FragmentActivity activity) {
        super(activity);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
            return OnboardingFragment.newInstance(R.layout.fragment_onboarding1);
            case 1:
            return OnboardingFragment.newInstance(R.layout.fragment_onboarding2);
            default:
            return OnboardingFragment.newInstance(R.layout.fragment_onboarding3);
        }
    }
}
