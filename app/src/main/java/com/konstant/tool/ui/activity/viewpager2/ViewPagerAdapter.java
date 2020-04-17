package com.konstant.tool.ui.activity.viewpager2;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.Holder> {

    private int [] colors = new int[4];

    public ViewPagerAdapter() {
        colors[0] = android.R.color.holo_purple;
        colors[1] = android.R.color.holo_green_light;
        colors[2] = android.R.color.holo_blue_light;
        colors[3] = android.R.color.holo_red_light;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = new View(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.setColor(colors[position]);
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class Holder extends RecyclerView.ViewHolder{

        public Holder(@NonNull View itemView) {
            super(itemView);
        }

        void setColor(int color){
            itemView.setBackgroundResource(color);
        }
    }

}
