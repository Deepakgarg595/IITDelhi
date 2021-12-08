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
import com.app.iitdelhicampus.activity.VisitTimingsActivity;
import com.app.iitdelhicampus.model.VisitTimingModel;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import java.util.ArrayList;

public class VisitTimingsAdapter extends RecyclerView.Adapter<VisitTimingsAdapter.MyViewHolder> {
    Context context;
    ArrayList<VisitTimingModel> result;


    public VisitTimingsAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public VisitTimingsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_visit_timing, parent, false);
        return new VisitTimingsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final VisitTimingsAdapter.MyViewHolder holder, final int position) {
        holder.txtDays.setText(result.get(position).getDay());

//        if (result.get(position).isSelected()) {
//            holder.imgTick.setVisibility(View.VISIBLE);
//            holder.llTime.setVisibility(View.VISIBLE);
//        } else {
//            holder.imgTick.setVisibility(View.GONE);
//            holder.llTime.setVisibility(View.GONE);
//        }

        holder.rlDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result.get(position).getDay().equalsIgnoreCase("Preferred Time Slot")) {
                    if (result.get(position).isSelected()) {
                        holder.imgTick.setVisibility(View.GONE);
                        holder.llTime.setVisibility(View.GONE);
                        result.get(position).setSelected(false);

                    } else {
                        result.get(position).setSelected(true);
                        holder.imgTick.setVisibility(View.VISIBLE);
                        holder.llTime.setVisibility(View.GONE);
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

        holder.llMorningFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((VisitTimingsActivity) context).timepickerFrom(holder.txtMorningFrom, position, "MorningFrom");
            }
        });


        holder.llMorningTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isNullOrEmpty(holder.txtMorningFrom.getText().toString())) {
                    ((VisitTimingsActivity) context).timepickerTo(holder.txtMorningTo, position, "MorningTo");
                } else {
                    Utility.showToast(context, "Please enter from time.");
                }
            }
        });

        holder.llEveningFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((VisitTimingsActivity) context).timepickerFrom(holder.txtEveningFrom, position, "EveningFrom");
            }
        });


        holder.llEveningTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!StringUtils.isNullOrEmpty(holder.txtEveningFrom.getText().toString())) {
                    ((VisitTimingsActivity) context).timepickerTo(holder.txtEveningTo, position, "EveningTo");
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

    public void updateList(ArrayList<VisitTimingModel> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    public ArrayList<VisitTimingModel> getList() {
        return result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtDays;
        private TextView txtMorningFrom, txtMorningTo;
        private TextView txtEveningFrom, txtEveningTo;
        private ImageView imgTick;
        private LinearLayout llTime;
        private LinearLayout llMorningTime, llMorningFrom, llMorningTo;
        private LinearLayout llEveningTime, llEveningFrom, llEveningTo;

        private RelativeLayout rlDay;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);
            txtDays = (TextView) itemView.findViewById(R.id.txtDays);
            rlDay = (RelativeLayout) itemView.findViewById(R.id.rlDay);
            llTime = (LinearLayout) itemView.findViewById(R.id.llTime);

            txtMorningFrom = (TextView) itemView.findViewById(R.id.txtMorningFrom);
            txtMorningTo = (TextView) itemView.findViewById(R.id.txtMorningTo);
            txtEveningFrom = (TextView) itemView.findViewById(R.id.txtEveningFrom);
            txtEveningTo = (TextView) itemView.findViewById(R.id.txtEveningTo);

            llMorningTime = (LinearLayout) itemView.findViewById(R.id.llMorningTime);
            llMorningFrom = (LinearLayout) itemView.findViewById(R.id.llMorningFrom);
            llMorningTo = (LinearLayout) itemView.findViewById(R.id.llMorningTo);

            llEveningTime = (LinearLayout) itemView.findViewById(R.id.llEveningTime);
            llEveningFrom = (LinearLayout) itemView.findViewById(R.id.llEveningFrom);
            llEveningTo = (LinearLayout) itemView.findViewById(R.id.llEveningTo);


        }
    }
}
