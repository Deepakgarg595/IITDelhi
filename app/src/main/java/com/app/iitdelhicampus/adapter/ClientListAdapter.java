package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ClientsListActivity;
import com.app.iitdelhicampus.model.ClientListModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<ClientListModel.Data> details;
    private int selectedPosition = -1;

    public ClientListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<ClientListModel.Data> details) {
        this.details=new ArrayList<>();
        for (ClientListModel.Data data : details) {
            if (data.getUnit_name().contains("AXIS")) {
                this.details.add(data);
            }
        }
//        this.details = details;// uncomment it and comment above for loop and ew object of detail list
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_client, parent, false);
        return new ClientListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvTitle.setText(details.get(position).getUnit_name().trim());
        holder.tvLoaction.setText(details.get(position).getLocation());
        holder.rlView.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        details.get(position).setSelected(false);
        if (position == selectedPosition) {
            details.get(position).setSelected(true);

            holder.rlView.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
        }
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }

    public String getSelectedItemString() {
        StringBuilder skills = new StringBuilder();
        if (details != null)
            for (ClientListModel.Data skillModel : details) {
                if (skillModel.isSelected()) {
                    skills.append(", ").append(skillModel.getUnit_name());
                }
            }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        return skills.toString().trim();
    }

    public ClientListModel.Data getSelectedItemData() {
        if (details != null)
            for (ClientListModel.Data skillModel : details) {
                if (skillModel.isSelected()) {
                    return skillModel;
                }
            }
        return null;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //        private final ImageView ivTickIcon;
        final LinearLayout rlView;
        private CustomTextView tvTitle;
        private CustomTextView tvLoaction;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvLoaction = itemView.findViewById(R.id.tvLoaction);
//            ivTickIcon = itemView.findViewById(R.id.ivTickIcon);
            rlView = itemView.findViewById(R.id.rlView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ClientsListActivity)context).setResultData(details.get(getAdapterPosition()));


//                    selectedPosition = getAdapterPosition();
//                    notifyDataSetChanged();
//                    details.get(getAdapterPosition()).setSelected(!details.get(getAdapterPosition()).isSelected());
//                    notifyDataSetChanged();
                }
            });
        }
    }

}
