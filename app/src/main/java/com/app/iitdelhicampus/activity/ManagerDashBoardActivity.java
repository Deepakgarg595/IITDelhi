package com.app.iitdelhicampus.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.app.iitdelhicampus.R;
import com.app.iitdelhicampus.constants.Constants;
import com.app.iitdelhicampus.ui.BaseActivity;
import com.app.iitdelhicampus.utility.BizzyBackgroundTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManagerDashBoardActivity extends BaseActivity {

    Calendar minDate;
    Calendar maxDate;
    boolean chkAll = false;
    ProgressBar loading_spinner;
    LinearLayout main_layout;

    // region Lifecycle methods
    //FiftyShadesOf fiftyShadesOf;
    private int mShortAnimationDuration = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_manager_dash_board, frameLayout);
        setSupportActionBar(toolbar);
        loading_spinner = findViewById(R.id.loading_spinner);
        main_layout = findViewById(R.id.main_layout);
        LoadTask();
    }

    public static Calendar toCalendarDateTime(String s) {
        Calendar calendar = Calendar.getInstance();
        try {
            if (s.contains("T")) {
                calendar.setTime(new SimpleDateFormat(Constants.DateSaveFormat).parse(s));
            } else {
                //SimpleDateFormat oldformat = ;
                calendar.setTime(new SimpleDateFormat(Constants.YYYY_MM_DD_HH_MM_SS).parse(s));
            }
        } catch (Exception e) {

        }
        return calendar;
    }


//    private void mockList(List<CalendarEvent> eventList, String startDate, String endDate) {
//        List<ViewReminder> alertList = SqlUtil.getRemindersPeriod(this, chkAll, startDate, endDate);
//        for (ViewReminder reminder : alertList) {
//            Calendar startTime = toCalendarDateTime(reminder.getDueDate());
//            eventList.add(new ReminderCalendarEvent(reminder.getReminderType(), reminder.getReminderId(), reminder.getClientName(), reminder.getActivity(), reminder.getDesc(), reminder.getStatus(), reminder.getClosingDate(), reminder.getClientName(), ColorUtil.GetRandomColorByName(reminder.getActivity()), startTime, startTime, true));
//        }
//    }


    public void LoadTask() {
        new BizzyBackgroundTask<Object>(this) {
            @Override
            protected Object onRun() {
                // minimum and maximum date of our calendar
                // 2 month behind, one year ahead, example: March 2015 <-> May 2015 <-> May 2016
                minDate = Calendar.getInstance();
                maxDate = Calendar.getInstance();

                minDate.add(Calendar.MONTH, -12);
                minDate.set(Calendar.DAY_OF_MONTH, 1);
                maxDate.add(Calendar.YEAR, 1);

//                mockList(eventList, BizzyDateUtils.getDateInYYYYMmAndDd(minDate), BizzyDateUtils.getDateInYYYYMmAndDd(maxDate));

                ////////
                return null;
            }

            @SuppressLint("StaticFieldLeak")
            @Override
            protected void onSuccess(Object result) {
                crossFade();

            }
        }
                //.execute();

                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_agenda_patch, menu);
        MenuItem item = menu.findItem(R.id.action_check);
        item.setChecked(chkAll);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
//                mAgendaCalendarView.resetAgendaList();
                return true;
            case R.id.action_add:
//                //Intent intent = new Intent(mContext, SearchViewActivity.class);
//                //startActivity(intent);
//                addAlert = new AddAlert(mContext).show().withListener(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            //Tbl_CRM_ReminderMasterDao dao2= BizzyApplication.getInstance().daoSession.getTbl_CRM_ReminderMasterDao();
//                            //EventBus.getDefault().post(new ApplicationEvents.R.ReminderPostpone(reminder.getReminderId()));
//                            //parent.notifyEventUpdated(-1,null);
//                            reloadData();
//                        } catch (Exception ex) {
//                            XLog.e(ex.getMessage());
//                        }
//                    }
//                });
                return true;
            case R.id.action_check:
//                item.setChecked(!item.isChecked());
//                chkAll = item.isChecked();
//                try {
//                    getSupportActionBar().setSubtitle(item.isChecked() ? "All Reminders" : "Open Reminders");
//                } catch (Exception ex) {
//                    XLog.e(ex.getMessage());
//                }
//                reloadData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void crossFade() {
        main_layout.setAlpha(0.0F);
        main_layout.setVisibility(View.VISIBLE);
        main_layout.animate().alpha(1.0F).setDuration(this.mShortAnimationDuration).setListener(null);
        loading_spinner.animate().alpha(0.0F).setDuration(this.mShortAnimationDuration).setListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator paramAnonymousAnimator) {
                ManagerDashBoardActivity.this.loading_spinner.setVisibility(View.INVISIBLE);
            }
        });
    }

}