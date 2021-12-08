package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ClientReportActivity;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.ClientReportModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.ArrayList;


public class ClientReportListAdapter extends RecyclerView.Adapter<ClientReportListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ClientReportModel> details;

    public ClientReportListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<ClientReportModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_client_report_, parent, false);
        return new ClientReportListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        holder.tvTitle.setText(details.get(position).getClientName());
        holder.tvDate.setText(details.get(position).getVisitDate());
        holder.tvRemark.setText("Remark: "+details.get(position).getRemark());
        holder.tvLoaction.setText("Address: "+details.get(position).getClientLocation());
//        StringBuilder stringBuilder=new StringBuilder();
//        for (CommonDropdownModel commonDropdownModel:details.get(position).getTypeOfIncident()){
//            stringBuilder.append(commonDropdownModel.getName()).append(", ") ;
//        }
        holder.tvTypeOfIncident.setText(details.get(position).getMetWhom());
        if(details.get(position).getImages().size()>0) {
            holder.ivProfile.setVisibility(View.VISIBLE);
            FileUtils.getProfilePicWithSize(context, details.get(position).getImages().get(0), holder.ivProfile, R.drawable.place_holder_default, "1111", false);
        }else {
            holder.ivProfile.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CustomTextView tvLoaction;
        private final CustomTextView tvTypeOfIncident;
        private CustomTextView tvTitle, tvDate,tvRemark;
        private ImageView ivProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (CustomTextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);
            tvRemark = (CustomTextView) itemView.findViewById(R.id.tvRemark);
            tvLoaction = (CustomTextView) itemView.findViewById(R.id.tvLoaction);
            tvTypeOfIncident = (CustomTextView) itemView.findViewById(R.id.tvTypeOfIncident);
            ivProfile=(ImageView)itemView.findViewById(R.id.ivProfile);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, ClientReportActivity.class);
                    intent.putExtra(Constants.NEED_TO_EDIT,true);
                    intent.putExtra(Constants.EXTRA_DATA,details.get(getAdapterPosition()));
//                    context.startActivity(intent);
                }
            });
        }
    }
}
