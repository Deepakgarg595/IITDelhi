package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.CommonDropdownModel;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.StringUtils;

import java.util.ArrayList;


public class CorrectionActionAdapter extends RecyclerView.Adapter<CorrectionActionAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CommonDropdownModel> details;
    private  boolean textEnabled;
    public CorrectionActionAdapter(Context context, boolean textEnabled) {
        this.context = context;
        this.textEnabled=textEnabled;

    }

    public void updateListData(ArrayList<CommonDropdownModel> details) {
        if(details==null){
            CommonDropdownModel commonDropdownModel=new CommonDropdownModel();
            details=new ArrayList<>();
            details.add(commonDropdownModel);
        }
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_row_, parent, false);
        return new CorrectionActionAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.ivAddMore.setVisibility(View.GONE);
        holder.etCourseOfAction.setEnabled(textEnabled);
        holder.tvCount.setEnabled(textEnabled);
        int count = position + 1;
        holder.tvCount.setText(count + ".");
        if (StringUtils.isNullOrEmpty(details.get(position).getName())) {
            holder.etCourseOfAction.getText().clear();
            holder.etCourseOfAction.setCursorVisible(true);
            holder.etCourseOfAction.requestFocus();
            holder.etCourseOfAction.requestFocus(position);
//            if(position!=0){
//                if(details.get(position).getId().equals("1")){
//                    holder.etCourseOfAction.setHint("Add another course of action");
//                }else if(details.get(position).getId().equals("2")){
//                    holder.etCourseOfAction.setHint("Add another observation");
//                }else if(details.get(position).getId().equals("3")){
//                    holder.etCourseOfAction.setHint("Add another correction");
//                }
//            }
        } else {
            holder.etCourseOfAction.setCursorVisible(false);
            holder.etCourseOfAction.setText(details.get(position).getName());

        }
        if (position == details.size() - 1) {
            holder.ivAddMore.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomEditText etCourseOfAction;
        private ImageView ivAddMore;
        private CustomTextView tvCount;

        public ViewHolder(View itemView) {
            super(itemView);
            etCourseOfAction = (CustomEditText) itemView.findViewById(R.id.etCourseOfAction);
            ivAddMore = (ImageView) itemView.findViewById(R.id.ivAddMore);
            tvCount=(CustomTextView)itemView.findViewById(R.id.tvCount);
            if(textEnabled)
                ivAddMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
                        details.add(commonDropdownModel);
                        notifyDataSetChanged();
                    }
                });


            etCourseOfAction.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    details.get(getAdapterPosition()).setName(charSequence.toString().trim());
                    details.get(getAdapterPosition()).setSelected(true);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        }
    }


    public String getSelectedItemString() {
        StringBuilder skills = new StringBuilder();
        if(details!=null)
            for (CommonDropdownModel skillModel : details) {
                if (skillModel.isSelected()) {
                    skills.append(", ").append(skillModel.getName());
                }
            }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        return skills.toString().trim();
    }

    public ArrayList<CommonDropdownModel> getSelectedItemList() {

        ArrayList<CommonDropdownModel> listItem = new ArrayList<>();
        if(details!=null)
            for (CommonDropdownModel skillModel : details) {
                if (skillModel.isSelected()) {
                    listItem.add(skillModel);
                }
            }
        return listItem;
    }

}