package com.yagodar.android.custom.fragment.progress.emptyable_view;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yagodar.android.custom.R;
import com.yagodar.android.custom.fragment.progress.common_view.ProgressFragment;

/**
 * Created by yagodar on 18.06.2015.
 */
public class ProgressEmptyableFragment<T extends View> extends ProgressFragment<T> {

    public ProgressEmptyableFragment() {
        super();
    }

    @Override
    public void onDestroyView() {
        mEmptyView = null;
        super.onDestroyView();
    }

    @Override
    protected void ensure() {
        super.ensure();
        ensureEmpty();
    }

    @Override
    protected void ensureContent() {
        if(isContentEnsured()) {
            return;
        }
        T contentView = getContentView();
        if(contentView != null && !(contentView instanceof IEmptyableView)) {
            throw new IllegalArgumentException("Content view must be IEmptyableView!");
        }
        super.ensureContent();
    }

    public void setEmptyImage(int resId) {
        ensure();
        mEmptyImageView.setImageResource(resId);
    }

    public void setEmptyImage(Bitmap bitmap) {
        ensure();
        mEmptyImageView.setImageBitmap(bitmap);
    }

    public void setEmptyImage(Drawable drawable) {
        ensure();
        mEmptyImageView.setImageDrawable(drawable);
    }

    public void setEmptyText(int resId) {
        ensure();
        mEmptyTextView.setText(resId);
    }

    public void setEmptyText(CharSequence text) {
        ensure();
        mEmptyTextView.setText(text);
    }

    protected View getEmptyView() {
        return mEmptyView;
    }

    protected void ensureEmpty() {
        if(mEmptyView != null) {
            return;
        }

        View root = getView();
        mEmptyView = root.findViewById(android.R.id.empty);
        mEmptyImageView = (ImageView) root.findViewById(R.id.empty_image);
        mEmptyTextView = (TextView) root.findViewById(R.id.empty_text);

        if(mEmptyView == null || mEmptyImageView == null || mEmptyTextView == null) {
            throw new IllegalStateException("Can't be used with a custom content view!");
        }

        IEmptyableView emptyableContentView = (IEmptyableView) getContentView();
        emptyableContentView.setEmptyView(mEmptyView);
    }

    private View mEmptyView;
    private ImageView mEmptyImageView;
    private TextView mEmptyTextView;
}
