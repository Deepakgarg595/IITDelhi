package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CommonDropdownModel;

import java.util.ArrayList;


public class SingleSelectionAdapter extends RecyclerView.Adapter<SingleSelectionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonDropdownModel> details;
    private TextView textView;
    private String clickType;
    private TextView date;
    private TextView dateEnd;

    public SingleSelectionAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details, TextView textView, String clickType) {
        this.details = details;
        this.textView = textView;
        this.clickType = clickType;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_interest, parent, false);
        return new SingleSelectionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.txtSkill.setText(details.get(position).getName().trim());


        if (details.get(position).isSelected()) {
            holder.ivTickIcon.setVisibility(View.VISIBLE);
            textView.setText(details.get(position).getName().trim());
            holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));

        } else {
            holder.ivTickIcon.setVisibility(View.GONE);
            holder.txtSkill.setTextColor(ContextCompat.getColor(context, R.color.black));

        }

    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtSkill;
        private ImageView ivTickIcon;
        private LinearLayout llParentView;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSkill = (TextView) itemView.findViewById(R.id.txtSkill);
            ivTickIcon = (ImageView) itemView.findViewById(R.id.ivTickIcon);
            llParentView = (LinearLayout) itemView.findViewById(R.id.llParentView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (int i = 0; i < details.size(); i++) {
                        if (i == getAdapterPosition()) {
                            details.get(i).setSelected(true);
                        } else {
                            details.get(i).setSelected(false);
                        }
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }


    public void setSelectedItems() {
        StringBuilder skills = new StringBuilder();
        for (CommonDropdownModel skillModel : details) {
            if (skillModel.isSelected()) {
                skills.append(", ").append(skillModel.getName());
            }
        }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        textView.setText(skills.toString().trim());
    }

    public CommonDropdownModel getSelectedItem() {
        CommonDropdownModel model = null;
        for (CommonDropdownModel skillModel : details) {
            if (skillModel.isSelected()) {
                model = skillModel;
                break;
            }
        }
        return model;
    }

}
