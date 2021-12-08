package com.app.iitdelhicampus.fragment;

import static com.app.iitdelhicampus.constants.Constants.SOS;
import static com.app.iitdelhicampus.constants.Constants.UPDATE_PROFILE;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.model.SOSModel;
import com.app.iitdelhicampus.network.LoopJRequestHandler;
import com.app.iitdelhicampus.network.OnUpdateResponse;
import com.app.iitdelhicampus.utility.ConnectivityUtils;
import com.app.iitdelhicampus.utility.ToastUtils;

import org.json.JSONObject;
import org.w3c.dom.Text;

public class ProfileFragmentGate extends BaseFragment implements OnUpdateResponse {
    private static ProfileFragmentGate instance;
    View itemView;
    private ImageView imgEdit;
    private TextView txtMobile,txtId,txtname,txtgender;
    private Dialog dialog;
    private String name;
    private LinearLayout llName,llGender;
    private String gender;

    public static ProfileFragmentGate getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        itemView = inflater.inflate(R.layout.fragment_profile_gate, container, false);
   init();
    return itemView;
    }

    private void init() {
        llGender=itemView.findViewById(R.id.llGender);
        llName=itemView.findViewById(R.id.llName);
        txtId=itemView.findViewById(R.id.txtId);
        txtname=itemView.findViewById(R.id.txtName);
        txtgender=itemView.findViewById(R.id.txtGender);
        txtMobile=itemView.findViewById(R.id.txtMobile);
        txtMobile.setText(preferences.getMobile());
        txtId.setText(preferences.getUserId());
        imgEdit=itemView.findViewById(R.id.imgEdit);
        if (preferences.getGender()!=null && preferences.getGender().toString().trim().length()!=0)
        {
            txtname.setText(preferences.getName());
            txtgender.setText(preferences.getGender());
            llGender.setVisibility(View.VISIBLE);
            llName.setVisibility(View.VISIBLE);
        }
        else
        {
            llGender.setVisibility(View.GONE);
            llName.setVisibility(View.GONE);
        }
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initMultiSelectionDialog();
            }
        });
    }

    private void initMultiSelectionDialog() {
        dialog = new Dialog(mActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit_profile);
        TextView txtName=dialog.findViewById(R.id.txtName);
        txtName.setText(preferences.getName());
        RadioGroup radioGroup=dialog.findViewById(R.id.radioGrp);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
       TextView txtCancel=dialog.findViewById(R.id.txtCancel);
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        TextView txtOK=dialog.findViewById(R.id.txtOK);
        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtName.getText().toString().trim().length()==0)
                {
                    ToastUtils.showToast(mActivity,"Please Enter Name");
                    return;
                }
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) dialog.findViewById(selectedId);
                hitApi(txtName.getText().toString().trim(),radioButton.getText().toString().trim());
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }



    private void hitApi(String name,String gender) {
        try {
            if (!ConnectivityUtils.isNetworkEnabled(mActivity)) {
                Toast.makeText(mActivity, "No internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            this.name=name;
            this.gender=gender;
            JSONObject requestParams = new JSONObject();
            try {
                requestParams.put("first_name", name);
                requestParams.put("last_name", "");
                requestParams.put("gender", gender);
                requestParams.put("employee_card", preferences.getEmpCode());
            } catch (Exception e) {
                e.printStackTrace();
            }
            LoopJRequestHandler.getInstance().hitApiPostMethodByJSON(UPDATE_PROFILE, requestParams, this, Constants.RequestType.UPDATE_PROFILE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResultSuccess(boolean isSuccess, String response, int responseCode) {
        showToast("Profile Updated Successfully");
        preferences.setName(name);
        preferences.setGender(gender);
        txtname.setText(preferences.getName());
        txtgender.setText(preferences.getGender());
        llGender.setVisibility(View.VISIBLE);
        llName.setVisibility(View.VISIBLE);
        dialog.dismiss();
    }
}