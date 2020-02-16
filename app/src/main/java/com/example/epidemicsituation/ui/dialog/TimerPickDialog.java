package com.example.epidemicsituation.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.epidemicsituation.R;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimerPickDialog extends Dialog {

    private static final String TAG = "TimerPickDialog";

    private static final String DATE_STRING = "{0} 年 {1} 月 {2} 日 {3} 时";

    @BindView(R.id.tv_time_pick_start)
    TextView tvTimePickStart;
    @BindView(R.id.v_time_pick_start)
    View vTimePickStart;
    @BindView(R.id.tv_time_pick_end)
    TextView tvTimePickEnd;
    @BindView(R.id.v_time_pick_end)
    View vTimePickEnd;
    @BindView(R.id.np_year)
    CustomNP npYear;
    @BindView(R.id.np_month)
    CustomNP npMonth;
    @BindView(R.id.np_day)
    CustomNP npDay;
    @BindView(R.id.np_hour)
    CustomNP npHour;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_config)
    TextView tvConfig;

    private int[] start = new int[4];
    private int[] end = new int[4];

    private int[] bigMonth = new int[] {1, 3, 5, 7, 8, 10 ,12};

    private boolean isChooseStart = true;

    public TimerPickDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_timer_pick);
        ButterKnife.bind(this);

        //隐藏分割线
        npYear.setNumberPickerDividerColor(android.R.color.transparent);
        npDay.setNumberPickerDividerColor(android.R.color.transparent);
        npMonth.setNumberPickerDividerColor(android.R.color.transparent);
        npHour.setNumberPickerDividerColor(android.R.color.transparent);
        npYear.setMinValue(0);
        npYear.setMaxValue(9999);
        npMonth.setMinValue(1);
        npMonth.setMaxValue(12);
        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npDay.setMinValue(1);
        npDay.setMaxValue(monthHasDay(start[0],start[1]));

        //初始化默认时间
        Calendar calendar = Calendar.getInstance();
        start[0] = end[0] = calendar.get(Calendar.YEAR);
        start[1] = end[1] = calendar.get(Calendar.MONTH) + 1;
        start[2] = end[2] = calendar.get(Calendar.DAY_OF_MONTH);
        start[3] = end[3] = calendar.get(Calendar.HOUR_OF_DAY);

        tvTimePickStart.setText(MessageFormat.format(DATE_STRING,start[0],start[1],start[2],start[3]));
        tvTimePickEnd.setText(MessageFormat.format(DATE_STRING,start[0],start[1],start[2],start[3]));
        tpGotoTime(start);

        Log.d(TAG, start[0] + ":" + start[1] +":" + start[2] + ":" + start[3]);

        npYear.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setTimeEd(0, npYear);
            }
        });

        npMonth.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                npDay.setMaxValue(monthHasDay(npYear.getValue(), npMonth.getValue()));
                setTimeEd(1, npMonth);
            }
        });

        npDay.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setTimeEd(2, npDay);
            }
        });

        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                setTimeEd(3, npHour);
            }
        });

        npYear.setValue(start[0]);
    }

    private int monthHasDay(int year, int month) {
        int dayNum = 0;
        boolean isLeapYear;
        if (year % 100 == 0 && year % 400 == 0 || year % 100 != 0 && year % 4 == 0) {
            //闰年
            isLeapYear = true;
        }else {
            isLeapYear = false;
        }

        if (AContainB(bigMonth,month)) {
            dayNum = 31;
        } else if (month == 2) {
            if (isLeapYear) {
                dayNum = 29;
            }else {
                dayNum = 28;
            }
        }else {
            dayNum = 30;
        }

        return dayNum;
    }

    private void setTimeEd(int num, NumberPicker picker) {
        if (isChooseStart) {
            start[num] = picker.getValue();
            tvTimePickStart.setText(MessageFormat.format(DATE_STRING, start[0], start[1], start[2], start[3]));
        }else {
            end[num] = picker.getValue();
            tvTimePickEnd.setText(MessageFormat.format(DATE_STRING, end[0], end[1], end[2], end[3]));
        }
    }

    private void tpGotoTime(int[] time) {
        npYear.setValue(time[0]);
        npMonth.setValue(time[1]);
        npDay.setValue(time[2]);
        npHour.setValue(time[3]);
    }

    @OnClick({R.id.tv_time_pick_start, R.id.tv_time_pick_end, R.id.tv_cancel, R.id.tv_config})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_pick_start:
                isChooseStart = true;
                tpGotoTime(start);
                vTimePickStart.setBackgroundResource(R.color.line_selected);
                vTimePickEnd.setBackgroundResource(R.color.line_not_selected);
                break;
            case R.id.tv_time_pick_end:
                isChooseStart = false;
                vTimePickEnd.setBackgroundResource(R.color.line_selected);
                vTimePickStart.setBackgroundResource(R.color.line_not_selected);
                tpGotoTime(end);
                break;
            case R.id.tv_cancel:
                break;
            case R.id.tv_config:
                break;
        }
    }

    public static boolean AContainB(int[] A, int B) {
        for (int temp: A) {
            if (temp == B) {
                return true;
            }
        }
        return false;
    }
}
