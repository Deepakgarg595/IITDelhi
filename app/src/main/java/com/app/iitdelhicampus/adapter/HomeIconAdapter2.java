package com.app.iitdelhicampus.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.MarkedAttendanceListActivity;
import com.app.iitdelhicampus.activity.POReportIncidentActivity;
import com.app.iitdelhicampus.activity.ReviewIncidentActivity;
import com.app.iitdelhicampus.activity.SiteObservationListActivity;
import com.app.iitdelhicampus.activity.ui.home.HomeFragment2;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.HomeImageDataModel;
import com.app.iitdelhicampus.preference.AppPreferences;
import com.app.iitdelhicampus.utility.StringUtils;

import java.util.ArrayList;

public class HomeIconAdapter2 extends RecyclerView.Adapter<HomeIconAdapter2.MyViewHolder> {
    ArrayList<HomeImageDataModel> detailModelNew;
    private Context context;
    private String type;
    HomeFragment2 fragment2;
    public HomeIconAdapter2(Context context,HomeFragment2 fragment2) {
        detailModelNew = new ArrayList<>();
        this.context = context;
        this.fragment2=fragment2;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_home_images2, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ivHomeIcon.setImageResource(detailModelNew.get(position).getImage());
        holder.tvName.setText(detailModelNew.get(position).getName());
//        holder.frameLayout.setVisibility(View.GONE);
//        if (position == 4 || position == 5) {
//            holder.frameLayout.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return detailModelNew.size();
    }

    public void updateList(ArrayList<HomeImageDataModel> menuDetail) {
        detailModelNew = menuDetail;
        notifyDataSetChanged();
    }

    public void showAlertDialog(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        FrameLayout frameLayout;
        private ImageView ivHomeIcon;
        //        private ImageView imgBackground;
        private TextView tvName;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivHomeIcon = (ImageView) itemView.findViewById(R.id.ivHomeIcon);
//            imgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frameLayout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (HomeFragment2.getInstance() == null) return;
                    if (AppPreferences.getInstance(context).getUserType().equalsIgnoreCase("client")) {
                        if (getAdapterPosition() == 4) {
                            if (!AppPreferences.getInstance(context).getInOut().equalsIgnoreCase("I")) {
                                try {
                                    fragment2.mActivity.showAlertDialogCustom(true, "Please mark IN your attendance to mark attendance for others.", R.color.red, R.drawable.cross_red, "red");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return;
                            }
                            HomeFragment2.getInstance().scanQrCode(Constants.REQUEST_CODE_IN_OUT_OTHERS, true);
                        } else if (getAdapterPosition() == 5) {
                            Intent intent = new Intent(context, MarkedAttendanceListActivity.class);
                            context.startActivity(intent);
                        }
                    } else if (AppPreferences.getInstance(context).getUserType().equalsIgnoreCase("tl")) {
                        Class clz = null;

                        switch (getAdapterPosition()) {
                            case 0:
                                showAlertDialog(context, "", "This feature is under process.");
                                break;
                            case 1:
                                clz = ReviewIncidentActivity.class;
                                Intent intent = new Intent(context, clz);
                                context.startActivity(intent);
                                break;
                            case 2:
                                clz = SiteObservationListActivity.class;
                                intent = new Intent(context, clz);
                                context.startActivity(intent);
                                break;
                            case 3:
                                showAlertDialog(context, "", "This feature is under process.");
                                break;
                            case 4:
                                showAlertDialog(context, "", "This feature is under process.");
                                break;
                            case 5:
                                showAlertDialog(context, "", "This feature is under process.");

                                break;

                        }

                    } else {//PO
                        switch (getAdapterPosition()) {
                            case 0:
                                Intent intent = new Intent(context, POReportIncidentActivity.class);
                                context.startActivity(intent);
                                break;
                            case 1:
                                showAlertDialog(context, "", "This feature is under process.");
                                break;
                            case 2:
                                showAlertDialog(context, "", "This feature is under process.");
                                break;
                        }
                    }
                }
            });
        }
    }
}