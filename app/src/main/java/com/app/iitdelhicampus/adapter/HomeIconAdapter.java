package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.MarkedAttendanceListActivity;
import com.app.iitdelhicampus.activity.ui.home.HomeFragment;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.HomeImageDataModel;

import java.util.ArrayList;

public class HomeIconAdapter extends RecyclerView.Adapter<HomeIconAdapter.MyViewHolder> {
    ArrayList<HomeImageDataModel> detailModelNew;
    private Context context;
    private String type;

    public HomeIconAdapter(Context context) {
        detailModelNew = new ArrayList<>();
        this.context = context;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_home_images, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ivHomeIcon.setImageResource(detailModelNew.get(position).getImage());
        holder.tvName.setText(detailModelNew.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return detailModelNew.size();
    }

    public void updateList(ArrayList<HomeImageDataModel> menuDetail) {
        detailModelNew = menuDetail;
        notifyDataSetChanged();
    }

    public void onClickPopupMenu(View v) {
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(context, v);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.sims_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().toString().equalsIgnoreCase("Scan QR Code")) {
//                    if(HomeFragment.getInstance()!=null)
//                        HomeFragment.getInstance().scanQrCode();

                } else {
                    if (HomeFragment.getInstance() != null)
                        HomeFragment.getInstance().takePhoto();
                }
//                Toast.makeText(context,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivHomeIcon;
        private ImageView imgBackground;
        private TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivHomeIcon = (ImageView) itemView.findViewById(R.id.ivHomeIcon);
            imgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HomeFragment.getInstance() == null) return;
                    if (getAdapterPosition() == 4) {
                        HomeFragment.getInstance().scanQrCode(Constants.REQUEST_CODE_IN_OUT_OTHERS,true);
                    } else if (getAdapterPosition() == 5) {
                        Intent intent = new Intent(context, MarkedAttendanceListActivity.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}