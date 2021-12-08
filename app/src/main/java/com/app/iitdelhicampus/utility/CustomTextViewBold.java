package com.app.iitdelhicampus.utility;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.app.iitdelhicampus.application.BaseApplication;


public class CustomTextViewBold extends TextView {

    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextViewBold(Context context) {
        super(context);
        init();
    }

    public void init() {
        Typeface tf = Typeface.createFromAsset(BaseApplication.getInstance().getAssets(), "Roboto-Bold.ttf");
        setTypeface(tf);

    }
}