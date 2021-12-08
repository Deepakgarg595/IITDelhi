package com.app.iitdelhicampus.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
//import com.app.punjabwarehouse.activity.ApproveUserListActivity;
//import com.app.punjabwarehouse.activity.DefaultActivity;
//import com.app.punjabwarehouse.activity.IncidentReportListActivity;
//import com.app.punjabwarehouse.activity.LoginActivityZinc;
//import com.app.punjabwarehouse.activity.RoutesActivity;
//import com.app.punjabwarehouse.database.DatabaseHelper;
//import com.app.punjabwarehouse.database.Patrolling.PatrollingDatabaseHelper;
//import com.app.punjabwarehouse.database.StartEndPatrolling.StartEndPatrollingDatabaseHelper;
//import com.app.punjabwarehouse.database.categories.CategoriesDatabaseHelper;
//import com.app.punjabwarehouse.database.locationUnit.LocationUnitDatabaseHelper;
//import com.app.punjabwarehouse.database.observation.ObservationDatabaseHelper;
//import com.app.punjabwarehouse.database.observation_category.ObservationCategoryDatabaseHelper;
import com.app.iitdelhicampus.model.HomeImageDataModel;
import com.app.iitdelhicampus.utility.CustomTextView;
import com.app.iitdelhicampus.utility.StringUtils;

import java.util.ArrayList;

public class ProfileListAdapter extends RecyclerView.Adapter<ProfileListAdapter.MyViewHolder> {
    ArrayList<HomeImageDataModel> detailModelNew;
    private Context context;
    private String type;
    private String idd;

    public ProfileListAdapter(Context context) {
        detailModelNew = new ArrayList<>();
        this.context = context;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_profile_images, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.tvName.setText(detailModelNew.get(position).getName());
        holder.tvQty.setText(detailModelNew.get(position).getContent());
        holder.ivPof.setImageResource(detailModelNew.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return detailModelNew.size();
    }

    public void updateList(ArrayList<HomeImageDataModel> menuDetail) {
        detailModelNew = menuDetail;
        this.type = type;
        notifyDataSetChanged();
    }
    public void showAlertDialogForLogoutApp(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        if (StringUtils.isNullOrEmpty(title)) {
            alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            alertDialog.setTitle(title);

        } else {
            alertDialog.setTitle(title);
        }

        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        logout();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }

    private void logout() {
//        `DatabaseHelper.getInstance(context).deleteOfflineTableData();
//        CategoriesDatabaseHelper.getInstance(context).deleteOfflineTableData();
//        LocationUnitDatabaseHelper.getInstance(context).deleteOfflineTableData();
//        ObservationDatabaseHelper.getInstance(context).deleteOfflineTableData();
//        ObservationCategoryDatabaseHelper.getInstance(context).deleteOfflineTableData();
//        PatrollingDatabaseHelper.getInstance(context).deleteOfflineTableData();
//        StartEndPatrollingDatabaseHelper.getInstance(context).deleteOfflineTableData();
//
////        idd=AppPreferences.getInstance(context).getDocumentId();
//        AppPreferences.getInstance(context).clear();
//        AppPreferences.getInstance(context).setClearData(true);
////        AppPreferences.getInstance(context).setDocumentId(idd);
////        FirebaseAuth.getInstance().signOut();
//        AppPreferences.getInstance(context).setLoggedIn(false);
//        Intent intent =new Intent(context, LoginActivityZinc.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(intent);
////        Intent intent = new Intent(context, LoginActivity.class);
////        context.startActivity(intent);
////        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
////        AppPreferences.getInstance(context).setLoggedIn(false);
////        FirebaseAuth.getInstance().signOut();
////        ((DashBoardActivity)context).finish();`
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPof;
        private CustomTextView tvName;
        private CustomTextView tvQty;

        public MyViewHolder(View itemView) {
            super(itemView);
            ivPof = (ImageView) itemView.findViewById(R.id.ivPof);
            tvName = (CustomTextView) itemView.findViewById(R.id.tvName);
            tvQty = (CustomTextView) itemView.findViewById(R.id.tvQty);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("Notification Settings")) {
//                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//                        intent.setData(uri);
//                        context.startActivity(intent);
//                    }
//                    else if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("App Version")) {
//
//                    }
//                    else if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("Patrolling Routes")) {
//                        Intent intent = new Intent(context, RoutesActivity.class);
//                        context.startActivity(intent);
//                    }
//                    else if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("Incident Report")) {
//                        Intent intent = new Intent(context, IncidentReportListActivity.class);
//                        context.startActivity(intent);
//                    }
//                    else if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("Logout")) {
//                     showAlertDialogForLogoutApp(context,"","Do you want to logout?");
//                    }
//                    else if (detailModelNew.get(getAdapterPosition()).getName().equalsIgnoreCase("Approve User"))
//                    {
//                        Intent intent = new Intent(context, ApproveUserListActivity.class);
//                        context.startActivity(intent);
//                    }
//                    else {
//                        Intent intent = new Intent(context, DefaultActivity.class);
//                        intent.putExtra("header", detailModelNew.get(getAdapterPosition()).getName());
//                        context.startActivity(intent);
////                    ToastUtils.showToast(context,"Clicked on: "+detailModelNew.get(getAdapterPosition()).getName());
//                    }
                }
            });
        }
    }


}
