package com.example.epidemicsituation.ui.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

public class CustomNP extends NumberPicker {

    private static final String TAG = "CustomNP";

    public CustomNP(Context context) {
        super(context);
    }

    public CustomNP(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNP(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    /**
     * 修改字的大小和颜色
     */
    private void updateView(View view){
        if( view instanceof EditText){
            EditText editText = (EditText) view;
            editText.setTextColor(Color.parseColor("#979797"));
            Log.d(TAG, "调用一次");
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "调用onDraw");
    }

    /**
     * 修改分割线的颜色
     */
    public void setNumberPickerDividerColor(int color){
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields){
            if(pf.getName().equals("mSelectionDivider")){ //找到mSelectionDivider
                pf.setAccessible(true);

                //设置分割线的颜色
                try {
                    pf.set(this, new ColorDrawable(color));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
