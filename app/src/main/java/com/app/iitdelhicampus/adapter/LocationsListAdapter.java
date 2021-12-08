package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.FourSquareNearbyPlacesResponseModel;

import java.util.ArrayList;
import java.util.Locale;

public class LocationsListAdapter extends BaseAdapter {

    private ArrayList<FourSquareNearbyPlacesResponseModel.Venues> mVenuesList;
    private Context context;

    public LocationsListAdapter(Context context) {
        this.context = context;
    }

    public void setListData(ArrayList<FourSquareNearbyPlacesResponseModel.Venues> venuesList) {
        mVenuesList = venuesList;
    }

    @Override
    public int getCount() {
        return mVenuesList == null ? 0 : mVenuesList.size();
    }

    @Override
    public FourSquareNearbyPlacesResponseModel.Venues getItem(int position) {
        return mVenuesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_locations, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        FourSquareNearbyPlacesResponseModel.Venues venues = getItem(position);

        String distance = String.format(Locale.getDefault(), "%s mtrs", venues.getLocation().getDistance());

        holder.txvLocationName.setText(venues.getName());
        holder.txvDistance.setText(distance);
        return view;
    }

    public static class ViewHolder {
        TextView txvLocationName;
        TextView txvDistance;

        public ViewHolder(View view) {
            txvLocationName = (TextView) view.findViewById(R.id.txvLocationName);
            txvDistance = (TextView) view.findViewById(R.id.txvDistance);
        }
    }

}
