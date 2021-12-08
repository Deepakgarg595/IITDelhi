package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class AttendanceListAdapter extends RecyclerView.Adapter<AttendanceListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonDropdownModel> details;

    public AttendanceListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_attendance_, parent, false);
        return new AttendanceListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        if (details.get(position).getSeverity().equalsIgnoreCase("0")) {
            holder.tvStatus.setText("Absent");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        } else {
            holder.tvStatus.setText("Present");
            holder.tvStatus.setTextColor(context.getResources().getColor(R.color.black));
        }


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
        private CustomTextView tvStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            tvStatus = (CustomTextView) itemView.findViewById(R.id.tvStatus);
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
