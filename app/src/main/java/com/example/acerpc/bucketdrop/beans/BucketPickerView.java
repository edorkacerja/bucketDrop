package com.example.acerpc.bucketdrop.beans;


import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.acerpc.bucketdrop.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by AcerPC on 11/26/2016.
 */

public class BucketPickerView extends LinearLayout implements View.OnTouchListener {

    private Calendar mCalendar;
    private TextView mTextDate;
    private TextView mTextMonth;
    private TextView mTextYear;
    private SimpleDateFormat myDateFormat;
    public static final int LEFT = 0;
    public static final int TOP = 1;
    public static final int RIGHT = 2;
    public static final int BOTTOM = 3;

    public BucketPickerView(Context context) {
        super(context);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BucketPickerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.bucket_picker_view, this);
        mCalendar = Calendar.getInstance();
        myDateFormat = new SimpleDateFormat("MMM");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextDate = (TextView) this.findViewById(R.id.tv_date);
        mTextMonth = (TextView) this.findViewById(R.id.tv_month);
        mTextYear = (TextView) this.findViewById(R.id.tv_year);
        mTextDate.setOnTouchListener(this);
        mTextMonth.setOnTouchListener(this);
        mTextYear.setOnTouchListener(this);
        int date = mCalendar.get(Calendar.DATE);
        int month = mCalendar.get(Calendar.MONTH);
        int year = mCalendar.get(Calendar.YEAR);
        update(date, month, year, 0, 0, 0);
    }

    private void update(int date, int month, int year, int hour, int minute, int second) {
        mCalendar.set(Calendar.DATE, date);
        mCalendar.set(Calendar.MONTH, month);
        mCalendar.set(Calendar.YEAR, year);
        mCalendar.set(Calendar.HOUR, hour);
        mCalendar.set(Calendar.MINUTE, minute);
        mCalendar.set(Calendar.SECOND, second);
        mTextDate.setText(date + "");
        mTextYear.setText(year + "");
        mTextMonth.setText(myDateFormat.format(mCalendar.getTime()));
    }

    public long getTime() {
        return mCalendar.getTimeInMillis();
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (view.getId()) {
            case R.id.tv_date:
                processEventsFor(mTextDate, event);
                break;
            case R.id.tv_month:
                processEventsFor(mTextMonth, event);
                break;
            case R.id.tv_year:
                processEventsFor(mTextYear, event);
                break;
        }
        return false;
    }

    private void processEventsFor(TextView textView, MotionEvent event) {
        Drawable[] drawable = textView.getCompoundDrawables();
        if (hasDrawablesTop(drawable) && hasDrawablesBottom(drawable)) {
            Rect topBounds = drawable[TOP].getBounds();
            Rect bottomBounds = drawable[BOTTOM].getBounds();
            float coordinateX = event.getX();
            float coordinateY = event.getY();

            if (drawableTopClicked(textView, topBounds.height(), coordinateX, coordinateY)) {
                if (isActionDown(event)) {
                    increment (textView.getId());
                }
            } else if (drawableBottomClicked(textView, bottomBounds.height(), coordinateX, coordinateY)) {
                if (isActionDown(event)) {
                    decrement (textView.getId());
                }
            } else {

            }

        }
    }
    private void increment (int id) {
        switch (id) {
            case R.id.tv_date:
                mCalendar.add(Calendar.DATE, 1);
                break;
            case R.id.tv_month:
                mCalendar.add(Calendar.MONTH, 1);
                break;
            case R.id.tv_year:
                mCalendar.add(Calendar.YEAR, 1);
                break;
        }
        refresh(mCalendar);
    }

    private void decrement (int id) {
        switch (id) {
            case R.id.tv_date:
                mCalendar.add(Calendar.DATE, -1);
                break;
            case R.id.tv_month:
                mCalendar.add(Calendar.MONTH, -1);
                break;
            case R.id.tv_year:
                mCalendar.add(Calendar.YEAR, -1);
                break;
        }
        refresh(mCalendar);
    }

    private void refresh(Calendar calendar) {
        int date = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        mTextDate.setText(date+"");
        mTextYear.setText(year+"");
        mTextMonth.setText(myDateFormat.format(mCalendar.getTime()));
    }

    private boolean isActionDown(MotionEvent event) {
        return event.getAction() == MotionEvent.ACTION_DOWN;
    }

    private boolean drawableTopClicked(TextView textView, int drawableHeight, float X, float Y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymin = textView.getPaddingTop();
        int ymax = ymin + drawableHeight;

        return X > xmin && X < xmax && Y > ymin && Y < ymax;
    }

    private boolean drawableBottomClicked(TextView textView, int drawableHeight, float X, float Y) {
        int xmin = textView.getPaddingLeft();
        int xmax = textView.getWidth() - textView.getPaddingRight();
        int ymin = textView.getHeight() - textView.getPaddingBottom() - drawableHeight;
        int ymax = ymin + drawableHeight;

        return X > xmin && X < xmax && Y > ymin && Y < ymax;
    }

    private boolean hasDrawablesTop(Drawable[] drawables) {
        return drawables[TOP] != null;
    }

    private boolean hasDrawablesBottom(Drawable[] drawables) {
        return drawables[BOTTOM] != null;
    }

}
