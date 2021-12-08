package com.app.iitdelhicampus.activity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.adapter.WeeklyOffAdapter;
import com.app.iitdelhicampus.model.TimeModel;
import com.app.iitdelhicampus.model.WeeklyOffModel;
import com.app.iitdelhicampus.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class WeeklyOffActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView txtHeader;
    private TextView txtDone;
    private RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    WeeklyOffAdapter weeklyOffAdapter;
    private ArrayList<WeeklyOffModel> weekdays;
    private ArrayList<WeeklyOffModel> selectedList;

    WeeklyOffModel weeklyOffModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_off);
        init();
    }

    private void init() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtDone = (TextView) findViewById(R.id.txtDone);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        weeklyOffAdapter = new WeeklyOffAdapter(this);
        recyclerview.setAdapter(weeklyOffAdapter);
        recyclerview.setLayoutManager(linearLayoutManager);
        weekdays = new ArrayList<>();
        selectedList = new ArrayList<>();
        weeklyOffModel = new WeeklyOffModel("Monday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Tuesday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Wednesday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Thursday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Friday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Saturday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("Sunday", new TimeModel());
        weekdays.add(weeklyOffModel);
        weeklyOffModel = new WeeklyOffModel("All 7 days Working", new TimeModel());
        weekdays.add(weeklyOffModel);

        weeklyOffAdapter.updateList(weekdays);

        txtDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDone:

                Intent intent = new Intent();
                selectedList.clear();
                selectedList.addAll(getSelectedList(weeklyOffAdapter.getList()));
//                selectedList.addAll(weeklyOffAdapter.getList());
                intent.putExtra("list", selectedList);
                setResult(RESULT_OK, intent);
                finish();
                break;

            case R.id.imgBack:
                finish();
                break;
        }
    }

    private ArrayList<WeeklyOffModel> getSelectedList(ArrayList<WeeklyOffModel> list) {
        ArrayList<WeeklyOffModel> selectedList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isSelected()) {
                selectedList.add(list.get(i));
            }
        }
        return selectedList;
    }

//    public void list(String option, String id) {
//        Intent i = new Intent();
//        i.putExtra(AppConstants.EXTRA_MULTIPLE_IMAGES, option);
//        i.putExtra("pos", pos);
//        i.putExtra("id", id);
//        setResult(RESULT_OK, i);
//        finish();
//    }
//
//      case "servingType":
//              selectedList.clear();
//                        i.putExtra("type","servingType");
//                        selectedList.addAll(menuServingTypeAdapter.getList());
//                        i.putExtra("list", selectedList);
//                        break;

    public void timepickerFrom(final TextView textView, final int pos) {
        final Calendar c1 = Calendar.getInstance();
        final int hour = c1.get(Calendar.HOUR_OF_DAY);
        final int minute = c1.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                textView.setText(hourOfDay + ":" + minute);
//                weekdays.get(pos).getTimeModel().setFromTime(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
                weekdays.get(pos).getTimeModel().setFromTime(textView.getText().toString());

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void timepickerTo(final TextView textView, final int pos) {
        final Calendar c1 = Calendar.getInstance();
        final int hour = c1.get(Calendar.HOUR_OF_DAY);
        final int minute = c1.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                textView.setText(hourOfDay + ":" + minute);
//                weekdays.get(pos).getTimeModel().setToTime(hourOfDay + ":" + minute);
                weekdays.get(pos).getTimeModel().setToTime(textView.getText().toString());

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }


//    public void updateList(ArrayList<WeeklyOffModel> list, int position) {
//        if (list != null && list.size() != 0) {
//            for (int i = 0; i < list.size(); i++) {
//                list.get(i).setSelected(false);
//            }
//            list.get(position).setSelected(true);
//            weeklyOffAdapter.updateList(list);
//        }
//
//    }
}
