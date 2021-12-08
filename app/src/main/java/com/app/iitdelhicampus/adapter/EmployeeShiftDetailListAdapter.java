package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.MusterRoleActivity;
import com.app.iitdelhicampus.model.Employee;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;

import java.util.List;


public class EmployeeShiftDetailListAdapter extends RecyclerView.Adapter<EmployeeShiftDetailListAdapter.ViewHolder> {
    private Context context;
    private List<Employee> details;

    public EmployeeShiftDetailListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(List<Employee> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_shift_, parent, false);
        return new EmployeeShiftDetailListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.tvEmpCode.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        holder.tvEmpName.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        holder.tvEmpMobile.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        holder.tvEmpShift.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

        holder.tvEmpCode.setText("Emp Code: "+details.get(position).getEmployeeCode());
        holder.tvEmpName.setText("Name: "+details.get(position).getEmployeeName());
        holder.tvEmpMobile.setText("Mobile: "+details.get(position).getPhone());
        holder.tvEmpShift.setText("Shift: "+details.get(position).getShiftName());

    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextViewBold tvEmpCode;
        private CustomTextView tvEmpName;
        private CustomTextView tvEmpMobile;
        private CustomTextView tvEmpShift;
        private TextView txtViewDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpCode = (CustomTextViewBold) itemView.findViewById(R.id.tvEmpCode);
            tvEmpName = (CustomTextView) itemView.findViewById(R.id.tvEmpName);
            tvEmpMobile = (CustomTextView) itemView.findViewById(R.id.tvEmpMobile);
            tvEmpShift = (CustomTextView) itemView.findViewById(R.id.tvEmpShift);
            txtViewDetails = (TextView) itemView.findViewById(R.id.txtViewDetails);
            txtViewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent=new Intent(context, EmployeeShiftActivity.class);
                    Intent intent=new Intent(context, MusterRoleActivity.class);
                    intent.putExtra("data",details.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }

}
