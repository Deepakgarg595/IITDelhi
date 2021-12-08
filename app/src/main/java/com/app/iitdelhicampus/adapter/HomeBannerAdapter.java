package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.viewpager.widget.PagerAdapter;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ui.home.HomeFragment;
import com.app.iitdelhicampus.model.FcmDataModel;

import java.util.ArrayList;

public class HomeBannerAdapter extends PagerAdapter {

    private Context mContext;
    private HomeFragment homeFragmentGate;
    ArrayList<FcmDataModel> bannerImages;
    public HomeBannerAdapter(Context context, HomeFragment homeFragmentGate) {
        mContext = context;
        bannerImages=new ArrayList<>();
        this.homeFragmentGate=homeFragmentGate;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.home_banner, collection, false);
        ProgressBar progressBar=(ProgressBar)layout.findViewById(R.id.progressBar);
        ImageView img_banner=(ImageView)layout.findViewById(R.id.img_banner);
        progressBar.setVisibility(View.GONE);
        img_banner.setImageResource(bannerImages.get(position).getUrl());
//        FileUtils.getFullPic(mContext,bannerImages.get(position).getUrl(),img_banner,0,"1232",false,progressBar);
        homeFragmentGate.currentPage = position;
        homeFragmentGate.DELAY_MS = 500;
        homeFragmentGate.PERIOD_MS = 4000;
        collection.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return bannerImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void updateList(ArrayList<FcmDataModel> bannerImages) {
        this.bannerImages=bannerImages;
        notifyDataSetChanged();
    }
}