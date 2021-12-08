package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.ExperienceActivity;
import com.app.iitdelhicampus.model.MetaDataDetailModel;

import java.util.ArrayList;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {
    Context context;
    ArrayList<MetaDataDetailModel> result;


    public ExperienceAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public ExperienceAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_gender, parent, false);
        return new ExperienceAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExperienceAdapter.MyViewHolder holder, final int position) {
        holder.txtOption.setText(result.get(position).getExperience());

        holder.rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ExperienceActivity) context).list(result.get(position).getExperience());
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void updateList(ArrayList<MetaDataDetailModel> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOption;
        private RelativeLayout rlAll;

        public MyViewHolder(View itemView) {
            super(itemView);
            rlAll = (RelativeLayout) itemView.findViewById(R.id.rlAll);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
        }
    }
}
