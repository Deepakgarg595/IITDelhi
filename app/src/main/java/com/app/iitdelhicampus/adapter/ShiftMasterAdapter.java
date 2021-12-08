package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.ShiftMasterDetailModel;
import com.app.iitdelhicampus.utility.CustomTextView;

import java.util.List;


public class ShiftMasterAdapter extends RecyclerView.Adapter<ShiftMasterAdapter.ViewHolder> {
    private final Context context;
    private List<ShiftMasterDetailModel> details;
    private int selectedPosition = -1;

    public ShiftMasterAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(List<ShiftMasterDetailModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_shift_guard, parent, false);
        return new ShiftMasterAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvEmpCode.setText(details.get(position).getShiftName()+" ("+details.get(position).getShiftSName()+")");
//        if (details.get(position).isSelected())
//        {
//            holder.imgSelected.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            holder.imgSelected.setVisibility(View.GONE);
//        }

    }

    private void setData(int position) {
        for (int i=0;i<details.size();i++)
        {
            details.get(i).setSelected(false);
        }
        details.get(position).setSelected(true);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvEmpCode;
        private ImageView imgSelected;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpCode = itemView.findViewById(R.id.tvEmpCode);
            imgSelected = itemView.findViewById(R.id.imgSelected);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    setData(getAdapterPosition());
                }
            });
        }
    }

}
