package com.yagodar.android.custom.fragment.progress.list_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

import com.yagodar.android.custom.fragment.progress.emptyable_view.IEmptyableView;

/**
 * Created by yagodar on 11.06.2016.
 */
public class EmptyableListView extends ListView implements IEmptyableView {
    public EmptyableListView(Context context) {
        super(context);
    }

    public EmptyableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyableListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
