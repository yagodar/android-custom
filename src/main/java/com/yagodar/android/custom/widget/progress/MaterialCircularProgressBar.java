package com.yagodar.android.custom.widget.progress;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.yagodar.android.custom.R;

/**
 * Created by yagodar on 09.09.2015.
 * Thanks to <a href="http://takeoffandroid.com/dialog/material-progress-dialog-using-v4-support-library/">takeoffandroid.com</a>
 */
public class MaterialCircularProgressBar extends ImageView {

    public MaterialCircularProgressBar(Context context) {
        super(context);
        initProgressBar(context, null, 0);
    }

    public MaterialCircularProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialCircularProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initProgressBar(context, attrs, defStyleAttr);
    }

    private void initProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {

        //TODO USE MATERIALISH instead https://github.com/pnikosis/materialish-progress
        //TODO JUST USE IT!

        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialCircularProgressBar, defStyleAttr, 0);
        mProgress = a.getInt(R.styleable.MaterialCircularProgressBar_progress, 0);
        mProgressMax = a.getInt(R.styleable.MaterialCircularProgressBar_progress_max, 100);
        mProgressColor = a.getColor(R.styleable.MaterialCircularProgressBar_progress_color, DEF_PROGRESS_COLOR);
        a.recycle();

        setImageDrawable(new MaterialCircularProgressDrawable(getContext(), this));
    }

    private int mProgress;
    private int mProgressMax;
    private int mProgressColor;

    private static final int DEF_PROGRESS_COLOR = 0xFFFAFAFA;
}
