package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.MetaDataDetailModel;

import java.util.ArrayList;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.MyViewHolder> {
    ArrayList<MetaDataDetailModel> detailModelNew;
    private Context context;
    private String type;

    public SpecialitiesAdapter(Context context) {
        detailModelNew = new ArrayList<>();
        this.context = context;

    }


    @Override
    public SpecialitiesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_gender, parent, false);
        return new SpecialitiesAdapter.MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final SpecialitiesAdapter.MyViewHolder holder, final int position) {
        if (detailModelNew.get(position).isSelected()) {
            holder.imgTick.setVisibility(View.VISIBLE);
        } else {
            holder.imgTick.setVisibility(View.GONE);
        }

        holder.txtOption.setText(detailModelNew.get(position).getSpecialities());


        holder.rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (detailModelNew.get(position).isSelected()) {
                    detailModelNew.get(position).setSelected(false);
                    holder.imgTick.setVisibility(View.GONE);

                } else {
                    detailModelNew.get(position).setSelected(true);
                    holder.imgTick.setVisibility(View.VISIBLE);

                }

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
