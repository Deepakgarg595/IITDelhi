package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.MarkAttendanceModel;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.StringUtils;
import com.app.iitdelhicampus.utility.Utility;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;


public class MarkAttendanceListAdapter extends RecyclerView.Adapter<MarkAttendanceListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MarkAttendanceModel> details;

    public MarkAttendanceListAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<MarkAttendanceModel> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_mark_attendance_, parent, false);
        return new MarkAttendanceListAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//        holder.tvAddMore.setVisibility(View.GONE);
        try {
            holder.tvTitle.setText(details.get(position).getUserName());
            if (!StringUtils.isNullOrEmptyOrZero(details.get(position).getStartTime() + ""))
                holder.tvDate.setText(Utility.getDateFromSecForEvents(details.get(position).getStartTime()));

            if (!StringUtils.isNullOrEmptyOrZero(details.get(position).getStartTime() + ""))
                holder.tvStartTime.setText(Utility.getEventTime(details.get(position).getStartTime()));
            holder.tvEndTime.setText("--");
            if (!StringUtils.isNullOrEmptyOrZero(details.get(position).getEndTime() + ""))
                holder.tvEndTime.setText(Utility.getEventTime(details.get(position).getEndTime()));


            holder.tvTotalTime.setText("--");
            if (!StringUtils.isNullOrEmptyOrZero(details.get(position).getStartTime() + "") && !StringUtils.isNullOrEmptyOrZero(details.get(position).getEndTime() + ""))
                getSeconds(holder.tvTotalTime, details.get(position).getStartTime(), details.get(position).getEndTime());
            holder.tvShift.setText(details.get(position).getShift());

            holder.tvEmpCode.setText(details.get(position).getEmpCode());
            holder.tvLocation.setText(details.get(position).getLocation());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getSeconds(CustomTextView textView, long start, long end) {
        long diffInMs = end - start;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diffInMs);

        int hours = (int) (seconds / 3600);
        int minutes = (int) ((seconds % 3600) / 60);
        int secs = (int) (seconds % 60);
        // Format the seconds into hours, minutes,
        // and seconds.
        String time
                = String
                .format(Locale.getDefault(),
                        "%2d:%02d", hours,
                        minutes);
        // Set the text view text.
        textView.setText(time + " min");
    }


    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView tvTitle, tvDate, tvStartTime, tvEndTime, tvTotalTime, tvShift, tvEmpCode, tvLocation;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (CustomTextView) itemView.findViewById(R.id.tvTitle);
            tvDate = (CustomTextView) itemView.findViewById(R.id.tvDate);
            tvStartTime = (CustomTextView) itemView.findViewById(R.id.tvStartTime);
            tvEndTime = (CustomTextView) itemView.findViewById(R.id.tvEndTime);
            tvTotalTime = (CustomTextView) itemView.findViewById(R.id.tvTotalTime);
            tvShift = (CustomTextView) itemView.findViewById(R.id.tvShift);

            tvEmpCode = (CustomTextView) itemView.findViewById(R.id.tvEmpCode);
            tvLocation = (CustomTextView) itemView.findViewById(R.id.tvLocation);


//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent=new Intent(context, ClientReportActivity.class);
//                    intent.putExtra(Constants.NEED_TO_EDIT,true);
//                    intent.putExtra(Constants.EXTRA_DATA,details.get(getAdapterPosition()));
////                    context.startActivity(intent);
//                }
//            });
        }
    }
}
