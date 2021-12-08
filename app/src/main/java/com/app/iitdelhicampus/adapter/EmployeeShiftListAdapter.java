package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.SuccessModel;
import com.app.iitdelhicampus.utility.CustomTextViewBold;

import java.util.List;


public class EmployeeShiftListAdapter extends RecyclerView.Adapter<EmployeeShiftListAdapter.ViewHolder> {
    private Context context;
    private List<SuccessModel> details;

    public EmployeeShiftListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(List<SuccessModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_shift, parent, false);
        return new EmployeeShiftListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvEmpName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        holder.tvEmpName.setText(details.get(position).getWarehouseName());
        holder.recEmp.setHasFixedSize(true);
        holder.recEmp.setNestedScrollingEnabled(false);
        holder.recEmp.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        EmployeeShiftDetailListAdapter adapter = new EmployeeShiftDetailListAdapter(context);
        holder.recEmp.setAdapter(adapter);
        adapter.updateListData(details.get(position).getEmployees());

    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextViewBold tvEmpName;
        private RecyclerView recEmp;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpName = (CustomTextViewBold) itemView.findViewById(R.id.tvEmpName);
            recEmp = (RecyclerView) itemView.findViewById(R.id.recEmp);

        }
    }

}
