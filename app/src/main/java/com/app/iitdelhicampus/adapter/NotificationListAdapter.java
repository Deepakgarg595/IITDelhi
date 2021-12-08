package com.app.iitdelhicampus.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewholder> {
    Context context;

    public NotificationListAdapter(Context context) {
        this.context = context;
    }


    @Override
    public NotificationListAdapter.MyViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_notifications, parent, false);
        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationListAdapter.MyViewholder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        private ImageView imgProfile;
        private TextView txtNotification;
        private LinearLayout llButtons;
        private Button btn_decline, btn_accept;


        public MyViewholder(View itemView) {
            super(itemView);

            imgProfile = (ImageView) itemView.findViewById(R.id.imgProfile);
            txtNotification = (TextView) itemView.findViewById(R.id.txtNotification);
            llButtons = (LinearLayout) itemView.findViewById(R.id.llButtons);
            btn_decline = (Button) itemView.findViewById(R.id.btn_decline);
            btn_accept = (Button) itemView.findViewById(R.id.btn_accept);

        }
    }
}
