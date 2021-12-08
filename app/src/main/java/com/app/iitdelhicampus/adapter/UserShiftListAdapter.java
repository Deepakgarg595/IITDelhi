package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.MonthShiftDetailListModel;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.CustomTextViewBold;
import com.app.iitdelhicampus.utility.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class UserShiftListAdapter extends RecyclerView.Adapter<UserShiftListAdapter.ViewHolder> {
    private Context context;
    private List<MonthShiftDetailListModel> details;

    public UserShiftListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(List<MonthShiftDetailListModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_shift_emp, parent, false);
        return new UserShiftListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        int pos = position + 1;
        holder.tvEmpCode.setText(details.get(position).getPunchDate().substring(8));
        holder.tvDay.setText(parseDate(details.get(position).getPunchDate()));
        if (!StringUtils.isNullOrEmpty(details.get(position).getShiftSName())) {
            holder.tvEmp.setText(details.get(position).getShiftSName());
        }else
        {
            holder.tvEmp.setText("");
        }
    }
    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "EEEE";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextViewBold tvEmpCode;
        private CustomTextView tvEmp;
        private CustomTextView tvDay;
        private CardView cvCard;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEmpCode = (CustomTextViewBold) itemView.findViewById(R.id.tvEmpCode);
            tvDay = (CustomTextView) itemView.findViewById(R.id.tvDay);
            tvEmp = (CustomTextView) itemView.findViewById(R.id.tvEmp);
            cvCard = (CardView) itemView.findViewById(R.id.cvCard);
//            tvEmpCode.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ((EmployeeShiftActivity)context).getData(details.get(getAdapterPosition()).getPunchDate(),details.get(getAdapterPosition()));
//                }
//            });
//            tvDay.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ((EmployeeShiftActivity)context).getData(details.get(getAdapterPosition()).getPunchDate(),details.get(getAdapterPosition()));
//                }
//            });
//            tvEmp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    ((EmployeeShiftActivity)context).getData(details.get(getAdapterPosition()).getPunchDate(),details.get(getAdapterPosition()));
//                }
//            });
        }
    }

}
