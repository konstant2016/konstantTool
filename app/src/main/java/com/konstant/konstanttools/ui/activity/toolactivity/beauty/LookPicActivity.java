package com.konstant.konstanttools.ui.activity.toolactivity.beauty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.konstant.konstanttools.R;
import com.konstant.konstanttools.base.BaseActivity;
import com.konstant.konstanttools.util.NetworkUtil;
import com.konstant.konstanttools.util.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LookPicActivity extends BaseActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        getSwipeBackLayout().setEnableGesture(false);
        initBaseViews();
    }

    @Override
    protected void initBaseViews() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        Intent intent = getIntent();
        ArrayList<String> urlList = intent.getStringArrayListExtra("urlList");
        Log.i("连接集合", urlList.toString());

        List<ImageView> imageViewList = new ArrayList<>();
        for (int i = 0; i < urlList.size(); i++) {
            PhotoView photoView = new PhotoView(this);
            photoView.enable();
            photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            Picasso.with(this).load(urlList.get(i)).into(photoView);
            imageViewList.add(photoView);
        }

        ViewPagerAdapter adapter = new ViewPagerAdapter(imageViewList, urlList, this);
        mViewPager.setAdapter(adapter);

        int index = intent.getIntExtra("index", 0);
        mViewPager.setCurrentItem(index);
    }


    // viewpager的适配器
    static class ViewPagerAdapter extends PagerAdapter {

        private List<ImageView> imageViewList;
        private List<String> urlList;
        private Context context;

        public ViewPagerAdapter(List<ImageView> imageViewList, List<String> urlList, Context context) {
            this.imageViewList = imageViewList;
            this.urlList = urlList;
            this.context = context;
        }

        @Override
        public int getCount() {
            return imageViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = imageViewList.get(position);

            container.addView(imageView);

            imageView.setOnClickListener((v) -> ((Activity) context).finish());

            imageView.setOnLongClickListener(v -> {
                showDialog(urlList.get(position));
                return true;
            });

            return imageView;

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private void showDialog(String urlString) {
            new AlertDialog.Builder(context).setMessage("是否要保存到本地?")
                    .setNegativeButton("取消", (dialog, whitch) -> {
                        dialog.dismiss();
                    })
                    .setPositiveButton("确定", (dialog, whitch) -> {
                        dialog.dismiss();
                        writeToStroage(urlString);
                    })
                    .create().show();
        }


        //写出文件到本地
        public void writeToStroage(String urlString) {
            new Thread(() -> {
                byte[] byteArray = Utils.getByteArray(urlString);
                File fileParent = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String name = urlString.substring(urlString.lastIndexOf("/") + 1);
                File file = new File(fileParent, name);
                try {
                    FileOutputStream outputStream = new FileOutputStream(file);
                    outputStream.write(byteArray, 0, byteArray.length);
                    outputStream.flush();
                    outputStream.close();
                    shoeToast("保存成功");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        // 更新界面提示
        private void shoeToast(String text) {
            ((Activity) context).runOnUiThread(() -> {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            });
        }
    }

}
