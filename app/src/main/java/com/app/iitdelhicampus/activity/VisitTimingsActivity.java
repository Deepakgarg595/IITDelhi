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
import com.app.iitdelhicampus.adapter.VisitTimingsAdapter;
import com.app.iitdelhicampus.model.TimeModel;
import com.app.iitdelhicampus.model.VisitTimingModel;
import com.app.iitdelhicampus.ui.BaseActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class VisitTimingsActivity extends BaseActivity implements View.OnClickListener {
    private ImageView imgBack;
    private TextView txtHeader;
    private TextView txtDone;
    private RecyclerView recyclerview;
    LinearLayoutManager linearLayoutManager;
    VisitTimingsAdapter visitTimingsAdapter;
    private ArrayList<VisitTimingModel> weekdays;
    private ArrayList<VisitTimingModel> selectedList;
    VisitTimingModel visitTimingModel;

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
        visitTimingsAdapter = new VisitTimingsAdapter(this);
        recyclerview.setAdapter(visitTimingsAdapter);
        recyclerview.setLayoutManager(linearLayoutManager);
        weekdays = new ArrayList<>();
        selectedList = new ArrayList<>();
        visitTimingModel = new VisitTimingModel("Mon-Sat", new TimeModel());
        weekdays.add(visitTimingModel);
        visitTimingModel = new VisitTimingModel("Sunday", new TimeModel());
        weekdays.add(visitTimingModel);
        visitTimingModel = new VisitTimingModel("Preferred Time Slot", new TimeModel());
        weekdays.add(visitTimingModel);

        visitTimingsAdapter.updateList(weekdays);

        txtDone.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtDone:
                Intent intent = new Intent();
                selectedList.clear();
                selectedList.addAll(getSelectedList(visitTimingsAdapter.getList()));
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

    private ArrayList<VisitTimingModel> getSelectedList(ArrayList<VisitTimingModel> list) {
        ArrayList<VisitTimingModel> selectedList = new ArrayList<>();
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


    public void timepickerFrom(final TextView textView, final int pos, final String type) {
        final Calendar c1 = Calendar.getInstance();
        final int hour = c1.get(Calendar.HOUR_OF_DAY);
        final int minute = c1.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                textView.setText(hourOfDay + ":" + minute);
                if (type.equalsIgnoreCase("MorningFrom")) {
                    weekdays.get(pos).getTimeModel().setMorningFrom(textView.getText().toString());

                } else if (type.equalsIgnoreCase("EveningFrom")) {
                    weekdays.get(pos).getTimeModel().setEveningFrom(textView.getText().toString());
                }

            }
        }, hour, minute, false);
        timePickerDialog.show();
    }

    public void timepickerTo(final TextView textView, final int pos, final String type) {
        final Calendar c1 = Calendar.getInstance();
        final int hour = c1.get(Calendar.HOUR_OF_DAY);
        final int minute = c1.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                textView.setText(hourOfDay + ":" + minute);
                if (type.equalsIgnoreCase("MorningTo")) {
                    weekdays.get(pos).getTimeModel().setMorningTo(textView.getText().toString());

                } else if (type.equalsIgnoreCase("EveningTo")) {
                    weekdays.get(pos).getTimeModel().setEveningTo(textView.getText().toString());
                }


            }
        }, hour, minute, false);
        timePickerDialog.show();
    }


//    public void updateList(ArrayList<VisitTimingModel> list, int position) {
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
