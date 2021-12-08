package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.SiteObservationActivity;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.SiteVisitMode;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class SiteObservationListAdapter extends RecyclerView.Adapter<SiteObservationListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<SiteVisitMode> details;

    public SiteObservationListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<SiteVisitMode> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_site_observation_, parent, false);
        return new SiteObservationListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        holder.tvTitle.setText(details.get(position).getClientName());
        holder.tvDate.setText(details.get(position).getCreatedDate());
        holder.tvLoaction.setText(details.get(position).getDeploymentLocation());
//        StringBuilder stringBuilder=new StringBuilder();
//        for (CommonDropdownModel commonDropdownModel:details.get(position).getTypeOfIncident()){
//            stringBuilder.append(commonDropdownModel.getName()).append(", ") ;
//        }
        holder.tvTypeOfIncident.setText(details.get(position).getClientName());
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomTextView tvLoaction;
        private final CustomTextView tvTypeOfIncident;
        private CustomTextView tvTitle, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (CustomTextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);
            tvLoaction = (CustomTextView) itemView.findViewById(R.id.tvLoaction);
            tvTypeOfIncident = (CustomTextView) itemView.findViewById(R.id.tvTypeOfIncident);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, SiteObservationActivity.class);
                    intent.putExtra(Constants.NEED_TO_EDIT,true);
                    intent.putExtra(Constants.EXTRA_DATA,details.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}