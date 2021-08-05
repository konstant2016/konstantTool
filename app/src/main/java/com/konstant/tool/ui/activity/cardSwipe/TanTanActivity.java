package com.konstant.tool.ui.activity.cardSwipe;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.konstant.tool.R;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;
import com.mcxtzhang.commonadapter.rv.ViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;


public class TanTanActivity extends AppCompatActivity {
    RecyclerView mRv;
    List<SwipeCardBean> mDatas = SwipeCardBean.initDatas();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_card);
        mRv = (RecyclerView) findViewById(R.id.rv);
        mRv.setLayoutManager(new OverLayCardLayoutManager());

        RecyclerView.Adapter adapter = new RecyclerView.Adapter<MyViewHolder>() {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_swipe_card,parent,false);
                return new MyViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
                SwipeCardBean data = mDatas.get(position);
                holder.tvName.setText(data.getName());
                holder.tvName.setText(data.getPostition() + " /" + mDatas.size());
            }

            @Override
            public int getItemCount() {
                return mDatas.size();
            }
        };

        mRv.setAdapter(adapter);

        CardConfig.initConfig(this);

        final TanTanCallback callback = new TanTanCallback(mRv, adapter, mDatas);

        //测试竖直滑动是否已经不会被移除屏幕
        //callback.setHorizontalDeviation(Integer.MAX_VALUE);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(mRv);

    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        TextView tvPercent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvPercent = itemView.findViewById(R.id.tvPrecent);
        }
    }


}

