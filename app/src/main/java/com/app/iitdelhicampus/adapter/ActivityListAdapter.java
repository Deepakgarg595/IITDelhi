package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonDropdownModel> details;

    public ActivityListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_activity_, parent, false);
        return new ActivityListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        holder.tvEmpName.setText(details.get(position).getName());
        holder.tvDesignation.setText(details.get(position).getSeverity());


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
        private CustomTextView tvEmpName, tvDesignation;
        private LinearLayout llExpandView;
        private RelativeLayout rlView;
        private ImageView ivSideArrow;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpName = (CustomTextView) itemView.findViewById(R.id.tvEmpName);
            tvDesignation = (CustomTextView) itemView.findViewById(R.id.tvDesignation);
            llExpandView=(LinearLayout)itemView.findViewById(R.id.llExpandView);
            rlView=(RelativeLayout)itemView.findViewById(R.id.rlView);
            ivSideArrow=(ImageView)itemView.findViewById(R.id.ivSideArrow);
            ivSideArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(llExpandView.getVisibility()==View.VISIBLE){
                        llExpandView.setVisibility(View.GONE);
                        ivSideArrow.setImageResource(R.mipmap.side_arrow_black);
                    }else {
                        llExpandView.setVisibility(View.VISIBLE);
                        ivSideArrow.setImageResource(R.mipmap.drop_down_arrow);
                    }

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
