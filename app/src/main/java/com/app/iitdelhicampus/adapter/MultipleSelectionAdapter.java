package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.activity.MultipleSelectionActivity;
import com.app.iitdelhicampus.activity.SpecialitiesActivity;
import com.app.iitdelhicampus.model.MetaDataDetailModel;
import com.app.iitdelhicampus.utility.StringUtils;

import java.util.ArrayList;

public class MultipleSelectionAdapter extends RecyclerView.Adapter<MultipleSelectionAdapter.MyViewHolder> {
    ArrayList<MetaDataDetailModel> detailModelNew;
    private Context context;
    private String type;

    public MultipleSelectionAdapter(Context context) {
        detailModelNew = new ArrayList<>();
        this.context = context;

    }


    @Override
    public MultipleSelectionAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_gender, parent, false);
        return new MultipleSelectionAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MultipleSelectionAdapter.MyViewHolder holder, final int position) {
//        if (detailModelNew.get(position).isSelected()) {
//            holder.imgTick.setVisibility(View.VISIBLE);
//        } else {
//            holder.imgTick.setVisibility(View.GONE);
//        }

        if (!StringUtils.isNullOrEmpty(type)) {
            switch (type) {
                case "Qualification":
                    holder.txtOption.setText(detailModelNew.get(position).getQualification());
                    break;

                case "Pet":
                    holder.txtOption.setText(detailModelNew.get(position).getSpeciesSpecialisation());
                    break;

                case "Services":
                    holder.txtOption.setText(detailModelNew.get(position).getServices());
                    break;

                case "Speciality":
                    holder.txtOption.setText(detailModelNew.get(position).getSpecialities());
                    break;
            }

        }


        holder.rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.txtOption.getText().toString().equalsIgnoreCase("Other Pets")) {
                    if (detailModelNew.get(position).isSelected()) {
                        holder.imgTick.setVisibility(View.GONE);
                        detailModelNew.get(position).setSelected(false);
                        holder.edtPet.setVisibility(View.GONE);

                    } else {
                        holder.imgTick.setVisibility(View.VISIBLE);
                        detailModelNew.get(position).setSelected(true);
                        holder.edtPet.setVisibility(View.VISIBLE);

                    }

                } else if (holder.txtOption.getText().toString().equalsIgnoreCase("Specialisation")) {
                    detailModelNew.get(position).setSelected(true);
                    holder.imgTick.setVisibility(View.VISIBLE);

                    Intent intent6 = new Intent(context, SpecialitiesActivity.class);
                    intent6.putExtra("title", "Select Speciality");
                    intent6.putExtra("type", "Speciality");
                    ((MultipleSelectionActivity) context).startActivityForResult(intent6, 1010);
//                    ((MultipleSelectionActivity)context).finish();


                } else {
                    if (detailModelNew.get(position).isSelected()) {
                        detailModelNew.get(position).setSelected(false);
                        holder.imgTick.setVisibility(View.GONE);

                    } else {
                        detailModelNew.get(position).setSelected(true);
                        holder.imgTick.setVisibility(View.VISIBLE);

                    }
                }


            }
        });


        holder.edtPet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                detailModelNew.get(position).setOtherName(holder.edtPet.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return detailModelNew.size();
    }

    public void updateList(ArrayList<MetaDataDetailModel> menuDetail, String type) {
        detailModelNew = menuDetail;
        this.type = type;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgTick;
        private TextView txtOption;
        private RelativeLayout rlAll;
        private EditText edtPet;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);
            rlAll = (RelativeLayout) itemView.findViewById(R.id.rlAll);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
            edtPet = (EditText) itemView.findViewById(R.id.edtPet);
        }
    }

    public ArrayList<MetaDataDetailModel> getList() {
        return detailModelNew;
    }
}