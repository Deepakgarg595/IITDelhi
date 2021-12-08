package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ReportIncidentActivity;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.model.ReportIncidentModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class ReviewIncidentListAdapter extends RecyclerView.Adapter<ReviewIncidentListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ReportIncidentModel> details;

    public ReviewIncidentListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<ReportIncidentModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_review_incident_, parent, false);
        return new ReviewIncidentListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        holder.tvTitle.setText(details.get(position).getName());
        holder.tvDate.setText(details.get(position).getCreatedDate()+", "+details.get(position).getTimeOfReporting());
        holder.tvLoaction.setText(details.get(position).getPlaceOfIncident());
        StringBuilder stringBuilder=new StringBuilder();
        for (CommonDropdownModel commonDropdownModel:details.get(position).getTypeOfIncident()){
            stringBuilder.append(commonDropdownModel.getName()).append(", ") ;
        }
        holder.tvTypeOfIncident.setText(stringBuilder.toString());
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
                    Intent intent=new Intent(context, ReportIncidentActivity.class);
                    intent.putExtra(Constants.NEED_TO_EDIT,true);
                    intent.putExtra(Constants.EXTRA_DATA,details.get(getAdapterPosition()));                    context.startActivity(intent);
                }
            });
        }
    }
}
