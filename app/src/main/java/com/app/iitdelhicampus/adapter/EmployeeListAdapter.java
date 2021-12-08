package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ActivityListActivity;
import com.app.iitdelhicampus.activity.AttendanceListActivity;
import com.app.iitdelhicampus.activity.LeaveListActivity;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonDropdownModel> details;

    public EmployeeListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_employee_, parent, false);
        return new EmployeeListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvEmpName.setText(details.get(position).getName());
        holder.tvDesignation.setText(details.get(position).getSeverity());


//        holder.tvAddMore.setVisibility(View.GONE);

//        if (StringUtils.isNullOrEmpty(details.get(position).getName())) {
//            holder.etCourseOfAction.setCursorVisible(true);
//            holder.etCourseOfAction.getText().clear();
//        } else {
//            holder.etCourseOfAction.setCursorVisible(false);
//            holder.etCourseOfAction.setText(details.get(position).getName());
//
//        }
//        if (position == details.size() - 1) {
//            holder.tvAddMore.setVisibility(View.VISIBLE);
//
//        }
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView ivLeave;
        private final ImageView ivActivityList;
        private CustomTextView tvEmpName, tvDesignation;
        ImageView ivSideArrow;
        private LinearLayout llExpandableView;
        private RelativeLayout rlEmpList;
        private ImageView ivAttendance;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpName = (CustomTextView) itemView.findViewById(R.id.tvEmpName);
            tvDesignation = (CustomTextView) itemView.findViewById(R.id.tvDesignation);

            ivSideArrow=(ImageView)itemView.findViewById(R.id.ivSideArrow);
            llExpandableView=(LinearLayout)itemView.findViewById(R.id.llExpandableView);
            rlEmpList=(RelativeLayout)itemView.findViewById(R.id.rlEmpList);
            ivSideArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(llExpandableView.getVisibility()==View.VISIBLE){
                        llExpandableView.setVisibility(View.GONE);
                        ivSideArrow.setImageResource(R.mipmap.side_arrow_black);
                    }else {
                        llExpandableView.setVisibility(View.VISIBLE);
                        ivSideArrow.setImageResource(R.mipmap.drop_down_arrow);
                    }
                }
            });

            ivAttendance=(ImageView)itemView.findViewById(R.id.ivAttendance);
            ivAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent intent=new Intent(context, AttendanceListActivity.class);
                   context.startActivity(intent);
                }
            });

            ivLeave=(ImageView)itemView.findViewById(R.id.ivLeave);
            ivLeave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, LeaveListActivity.class);
                    context.startActivity(intent);
                }
            });

            ivActivityList=(ImageView)itemView.findViewById(R.id.ivActivityList);
            ivActivityList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ActivityListActivity.class);
                    context.startActivity(intent);
                }
            });







        }
    }


    public String getSelectedItemString() {
        StringBuilder skills = new StringBuilder();
        for (CommonDropdownModel skillModel : details) {
            if (skillModel.isSelected()) {
                skills.append(", ").append(skillModel.getName());
            }
        }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        return skills.toString().trim();
    }

    public ArrayList<CommonDropdownModel> getSelectedItemList() {

        ArrayList<CommonDropdownModel> listItem = new ArrayList<>();
        if(details!=null)
        for (CommonDropdownModel skillModel : details) {
            if (skillModel.isSelected()) {
                listItem.add(skillModel);
            }
        }
        return listItem;
    }

}
