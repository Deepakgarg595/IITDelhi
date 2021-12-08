package com.app.iitdelhicampus.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.file.FileUtils;
import com.app.iitdelhicampus.model.MediaDetails;
import com.app.iitdelhicampus.utility.CustomEditText;
import com.app.iitdelhicampus.utility.StringUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;


public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private Context context;
    private ArrayList<MediaDetails> details;

    public ImageAdapter(Context context) {
        this.context = context;

    }

    public void updateListData(ArrayList<MediaDetails> details) {
        this.details = details;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_image, parent, false);
        return new ImageAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        FileUtils.getProfilePicWithSize(context, details.get(position).getImageUrl(),holder.ivProfile , R.drawable.place_holder_default, "1111", false);


//        holder.tvAddMore.setVisibility(View.GONE);
//
//        if (StringUtils.isNullOrEmpty(details.get(position).getName())) {
//            holder.etCourseOfAction.getText().clear();
//            holder.etCourseOfAction.setCursorVisible(true);
//            holder.etCourseOfAction.requestFocus();
//            holder.etCourseOfAction.requestFocus(position);
//        } else {
//            holder.etCourseOfAction.setCursorVisible(false);
//            holder.etCourseOfAction.setText(details.get(position).getName());
//
//        }
//        if (position == details.size() - 1) {
//            holder.tvAddMore.setVisibility(View.VISIBLE);
//
//        }
    }

    @Override
    public int getItemCount() {
        return details == null ? 0 : details.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomEditText etCourseOfAction;
        private ImageView ivProfile;

        public ViewHolder(View itemView) {
            super(itemView);
            ivProfile = (ImageView) itemView.findViewById(R.id.ivProfile);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showAlertDialog(context,"","Do you want to delete?",getAdapterPosition());
                    return false;
                }
            });
//            tvAddMore = (CustomTextView) itemView.findViewById(R.id.tvAddMore);
//            tvAddMore.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    CommonDropdownModel commonDropdownModel = new CommonDropdownModel();
//                    details.add(commonDropdownModel);
//                    notifyDataSetChanged();
//                }
//            });
//
//
//            etCourseOfAction.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    details.get(getAdapterPosition()).setName(charSequence.toString().trim());
//                }
//
//                @Override
//                public void afterTextChanged(Editable editable) {
//
//                }
//            });
//
//
        }
    }


    public String getSelectedItemString() {
        StringBuilder skills = new StringBuilder();
        for (MediaDetails skillModel : details) {
            if (skillModel.isSelected()) {
                skills.append(", ").append(skillModel.getName());
            }
        }
        if (skills.length() > 0) {
            skills.deleteCharAt(0);
        }
        return skills.toString().trim();
    }

    public ArrayList<MediaDetails> getSelectedItemList() {

        ArrayList<MediaDetails> listItem = new ArrayList<>();
        for (MediaDetails skillModel : details) {
            if (skillModel.isSelected()) {
                listItem.add(skillModel);
            }
        }
        return listItem;
    }

    public void showAlertDialog(final Context context, String title, String message,final int position) {
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
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference photoRef = storage.getReferenceFromUrl(details.get(position).getImageUrl());
                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(context, "File deleted successfully.", Toast.LENGTH_SHORT).show(); // File deleted successfully
                                details.remove(position);
                                notifyDataSetChanged();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                            }
                        });



                        dialog.dismiss();
                    }
                });

        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        alertDialog.show();
    }


}
