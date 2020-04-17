package com.konstant.tool.ui.activity.viewpager2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MyFragment extends Fragment {

    public static MyFragment getInstance(int color, int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("color",color);
        bundle.putInt("position",position);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    private int mPosition;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = new View(getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        view.setLayoutParams(params);
        int color = getArguments().getInt("color");
        mPosition = getArguments().getInt("position") +1;
        view.setBackgroundResource(color);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("MyFragment","onResume :"+ mPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("MyFragment","onPause :"+ mPosition);
    }
}
