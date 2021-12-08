package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.WeeklyOffActivity;
import com.app.iitdelhicampus.model.WeeklyOffModel;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import java.util.ArrayList;

public class WeeklyOffAdapter extends RecyclerView.Adapter<WeeklyOffAdapter.MyViewHolder> {
    Context context;
    ArrayList<WeeklyOffModel> result;


    public WeeklyOffAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public WeeklyOffAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_week_off, parent, false);
        return new WeeklyOffAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final WeeklyOffAdapter.MyViewHolder holder, final int position) {
        holder.txtDays.setText(result.get(position).getDay());

        if (result.get(position).isSelected()) {
            holder.imgTick.setVisibility(View.VISIBLE);
            holder.llTime.setVisibility(View.VISIBLE);
        } else {
            holder.imgTick.setVisibility(View.GONE);
            holder.llTime.setVisibility(View.GONE);
        }

        holder.rlDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtDays.getText().toString().equalsIgnoreCase("All 7 days Working")) {
                    if (result.get(position).isSelected()) {
                        holder.imgTick.setVisibility(View.GONE);
                        holder.llTime.setVisibility(View.GONE);
                        result.get(position).setSelected(false);

                    } else {
                        result.get(position).setSelected(true);
                        holder.llTime.setVisibility(View.GONE);
                        holder.imgTick.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (result.get(position).isSelected()) {
                        holder.imgTick.setVisibility(View.GONE);
                        holder.llTime.setVisibility(View.GONE);
                        result.get(position).setSelected(false);

                    } else {
                        result.get(position).setSelected(true);
                        holder.imgTick.setVisibility(View.VISIBLE);
                        holder.llTime.setVisibility(View.VISIBLE);
                    }
                }


            }
        });

        holder.txtTimingsFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((WeeklyOffActivity) context).timepickerFrom(holder.txtTimingsFrom, position);
            }
        });


        holder.txtTimingsTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isNullOrEmpty(holder.txtTimingsFrom.getText().toString())) {
                    ((WeeklyOffActivity) context).timepickerTo(holder.txtTimingsTo, position);
                } else {
                    Utility.showToast(context, "Please enter from time.");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void updateList(ArrayList<WeeklyOffModel> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    public ArrayList<WeeklyOffModel> getList() {
        return result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDays, txtTimingsFrom, txtTimingsTo;
        private ImageView imgTick;
        private LinearLayout llTime, llTimingFrom, llTimingTo;
        private RelativeLayout rlDay;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);

            rlDay = (RelativeLayout) itemView.findViewById(R.id.rlDay);

            llTime = (LinearLayout) itemView.findViewById(R.id.llTime);
            llTimingFrom = (LinearLayout) itemView.findViewById(R.id.llTimingFrom);
            llTimingTo = (LinearLayout) itemView.findViewById(R.id.llTimingTo);

            txtDays = (TextView) itemView.findViewById(R.id.txtDays);
            txtTimingsFrom = (TextView) itemView.findViewById(R.id.txtTimingsFrom);
            txtTimingsTo = (TextView) itemView.findViewById(R.id.txtTimingsTo);


        }
    }
}
