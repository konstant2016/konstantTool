package com.konstant.tool.ui.activity.viewpager2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class FragmentAdapter extends FragmentStateAdapter {

    private int [] colors = new int[4];

    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        colors[0] = android.R.color.holo_purple;
        colors[1] = android.R.color.holo_green_light;
        colors[2] = android.R.color.holo_blue_light;
        colors[3] = android.R.color.holo_red_light;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return MyFragment.getInstance(colors[position],position);
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
