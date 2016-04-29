package com.yagodar.android.custom.fragment.progress.recycler_view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * Created by yagodar on 29.04.2016.
 */
public class EmptyRecyclerView extends RecyclerView {
    public EmptyRecyclerView(Context context) {
        super(context);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //TODO empty_view
    //http://stackoverflow.com/a/27801394/2678746
    //https://github.com/googlesamples/android-XYZTouristAttractions/blob/master/Application/src/main/java/com/example/android/xyztouristattractions/ui/AttractionsRecyclerView.java
}
