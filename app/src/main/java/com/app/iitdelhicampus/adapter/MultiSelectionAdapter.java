package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.StringUtils;

import java.util.ArrayList;


public class MultiSelectionAdapter extends RecyclerView.Adapter<MultiSelectionAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<CommonDropdownModel> details;

    public MultiSelectionAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_interest, parent, false);
        return new MultiSelectionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtSkill.setText(details.get(position).getName().trim());

//        if(details.get(position).getId().equalsIgnoreCase("high")){
//
//        }


        if (details.get(position).isSelected()) {
            holder.ivTickIcon.setVisibility(View.VISIBLE);
//            holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            if (!StringUtils.isNullOrEmpty(details.get(position).getSeverity())) {
                switch (details.get(position).getSeverity()) {
                    case "High":
                        holder.ivTickIcon.setImageResource(R.drawable.tick_icon_red);
                        holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                        break;
                    case "Moderate":
                        holder.ivTickIcon.setImageResource(R.drawable.tick_icon_yellow);
                        holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.intro_circle_active));
                        break;
                    case "Low":
                        holder.ivTickIcon.setImageResource(R.drawable.tick_icon_green);
                        holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.accept));
                        break;
                }
            } else {
                holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
            }

        } else {
            holder.ivTickIcon.setVisibility(View.GONE);
            holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.black));

        }

    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
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
        for (CommonDropdownModel skillModel : details) {
            if (skillModel.isSelected()) {
                listItem.add(skillModel);
            }
        }
        return listItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomTextView txtSkill;
        private final ImageView ivTickIcon;
        private final LinearLayout llParentView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSkill = itemView.findViewById(R.id.txtSkill);
            ivTickIcon = itemView.findViewById(R.id.ivTickIcon);
            llParentView = itemView.findViewById(R.id.llParentView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    details.get(getAdapterPosition()).setSelected(!details.get(getAdapterPosition()).isSelected());
                    notifyDataSetChanged();
                }
            });
        }
    }

}
