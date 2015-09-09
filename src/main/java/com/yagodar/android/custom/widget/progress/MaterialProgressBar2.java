package com.yagodar.android.custom.widget.progress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by yagodar on 09.09.2015.
 * Thanks to <a href="http://stackoverflow.com/a/29032403">Dmide</a>
 */
public class MaterialProgressBar2 extends View {

    private MaterialCircularProgressDrawable mDrawable;

    public MaterialProgressBar2(Context context) {
        this(context, null);
    }

    public MaterialProgressBar2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialProgressBar2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mDrawable = new MaterialCircularProgressDrawable(context, this);
        mDrawable.setCallback(this);
        if (getVisibility() == VISIBLE) {
            mDrawable.start();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mDrawable.draw(canvas);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (mDrawable != null) {
            if (visibility == VISIBLE) {
                mDrawable.start();
            } else {
                mDrawable.stop();
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(0, 0, w, h);
    }

    @Override
    protected boolean verifyDrawable(Drawable who) {
        return who == mDrawable || super.verifyDrawable(who);
    }
}