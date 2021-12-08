package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.model.HomeImageDataModel;

import java.util.ArrayList;

public class IncidentAdapter extends RecyclerView.Adapter<IncidentAdapter.MyViewHolder> {
    Context context;
    ArrayList<HomeImageDataModel> result;


    public IncidentAdapter(Context context) {
        this.context = context;
        result = new ArrayList<>();
    }

    @NonNull
    @Override
    public IncidentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_incident, parent, false);
        return new IncidentAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncidentAdapter.MyViewHolder holder, final int position) {
        holder.txtOption.setText(result.get(position).getName());
        holder.imgTick.setImageResource(result.get(position).getImage());
        holder.imgTick.setBackground(null);
    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public void updateList(ArrayList<HomeImageDataModel> result) {
        this.result = result;
        notifyDataSetChanged();
    }

    public ArrayList<HomeImageDataModel> getList() {
        return result;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtOption;
        private RelativeLayout rlAll;
        private ImageView imgTick;

        public MyViewHolder(View itemView) {
            super(itemView);
            imgTick = (ImageView) itemView.findViewById(R.id.imgTick);
            rlAll = (RelativeLayout) itemView.findViewById(R.id.rlAll);
            txtOption = (TextView) itemView.findViewById(R.id.txtOption);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switch (getAdapterPosition()) {
                        case 0:
                            if (result.get(getAdapterPosition()).isSelected()) {
                                result.get(getAdapterPosition()).setSelected(false);
                                imgTick.setBackground(null);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.fire));
                            } else {
                                result.get(getAdapterPosition()).setSelected(true);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.fire_));
                                imgTick.setBackground(context.getResources().getDrawable(R.drawable.blue_bubble_color));
                            }
                            break;
                        case 1:
                            if (result.get(getAdapterPosition()).isSelected()) {
                                result.get(getAdapterPosition()).setSelected(false);
                                imgTick.setBackground(null);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.revolver_));
                            } else {
                                result.get(getAdapterPosition()).setSelected(true);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.revolver));
                                imgTick.setBackground(context.getResources().getDrawable(R.drawable.blue_bubble_color));
                            }
                            break;
                        case 2:
                            if (result.get(getAdapterPosition()).isSelected()) {
                                result.get(getAdapterPosition()).setSelected(false);
                                imgTick.setBackground(null);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.car_collision));
                            } else {
                                result.get(getAdapterPosition()).setSelected(true);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.car_collision_));
                                imgTick.setBackground(context.getResources().getDrawable(R.drawable.blue_bubble_color));
                            }
                            break;
                        case 3:
                            if (result.get(getAdapterPosition()).isSelected()) {
                                result.get(getAdapterPosition()).setSelected(false);
                                imgTick.setBackground(null);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.earthquake));
                            } else {
                                result.get(getAdapterPosition()).setSelected(true);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.earthquake_));
                                imgTick.setBackground(context.getResources().getDrawable(R.drawable.blue_bubble_color));
                            }
                            break;
                        case 4:
                            if (result.get(getAdapterPosition()).isSelected()) {
                                result.get(getAdapterPosition()).setSelected(false);
                                imgTick.setBackground(null);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.theft));
                            } else {
                                result.get(getAdapterPosition()).setSelected(true);
                                imgTick.setImageDrawable(context.getResources().getDrawable(R.drawable.theft1));
                                imgTick.setBackground(context.getResources().getDrawable(R.drawable.blue_bubble_color));
                            }
                            break;
                    }
                }
            });
        }
    }
}
