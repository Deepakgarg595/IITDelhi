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
import com.app.iitdelhicampus.activity.GenderListActivity;

import java.util.ArrayList;

public class GenderListAdapter extends RecyclerView.Adapter<GenderListAdapter.MyViewHolder> {
    Context context;
    ArrayList<String> result;


    public GenderListAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_gender, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.txtOption.setText(result.get(position));

        holder.rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((GenderListActivity) context).list(result.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void updateList(ArrayList<String> result) {
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
