package com.yagodar.android.custom.fragment.progress.common_view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.yagodar.android.custom.R;
import com.yagodar.android.custom.fragment.progress.IProgressContext;


/**
 * Created by yagodar on 06.03.2015.
 */
public class ProgressFragment<T extends View> extends Fragment implements IProgressContext {

    public ProgressFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.progress_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensure();
    }

    @Override
    public void onDestroyView() {
        mContentView = null;
        mContentShown = false;
        mProgressContainer = mContentContainer = null;
        super.onDestroyView();
    }

    @Override
    public void setContentShown(boolean shown) {
        if (mContentShown == shown) {
            return;
        }

        ensureContainers();

        mContentShown = shown;
        if (mContentShown) {
            mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            mProgressContainer.setVisibility(View.GONE);
            mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            mContentContainer.setVisibility(View.VISIBLE);
        } else {
            mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            mProgressContainer.setVisibility(View.VISIBLE);
            mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            mContentContainer.setVisibility(View.GONE);
        }
    }

    public T getContentView() {
        return mContentView;
    }

    public void setContentView(int layoutResId) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        T contentView;
        try {
            contentView = (T) layoutInflater.inflate(layoutResId, null);
        } catch(ClassCastException e) {
            throw new IllegalArgumentException("Content view must be of proper type!");
        }
        setContentView(contentView);
    }

    public void setContentView(T view) {
        if(view == null) {
            throw new IllegalArgumentException("Content view must not be null!");
        }
        ensureContainers();
        if (mContentContainer instanceof ViewGroup) {
            ViewGroup contentContainer = (ViewGroup) mContentContainer;
            if (mContentView == null) {
                contentContainer.addView(view);
            } else {
                int index = contentContainer.indexOfChild(mContentView);
                contentContainer.removeView(mContentView);
                contentContainer.addView(view, index);
            }
            mContentView = view;
        } else {
            throw new IllegalStateException("Content Container must be ViewGroup!");
        }
        invalidateContent();
        ensureContent();
    }

    protected boolean isContentShown() {
        return mContentShown;
    }

    protected boolean isContentEnsured() {
        return mContentEnsured;
    }

    protected void invalidateContent() {
        mContentEnsured = false;
    }

    protected void ensure() {
        ensureContainers();
        ensureContent();
    }

    protected void ensureContent() {
        if(isContentEnsured()) {
            return;
        }
        setContentShown(false);
        mContentEnsured = true;
    }

    private void ensureContainers() {
        if (mContentContainer != null && mProgressContainer != null) {
            return;
        }

        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created!");
        }

        mProgressContainer = root.findViewById(R.id.progress_container);
        mContentContainer = root.findViewById(R.id.content_container);
        if (mContentContainer == null || mProgressContainer == null) {
            throw new IllegalStateException("Can't be used with a custom content view!");
        }
    }

    private T mContentView;
    private boolean mContentEnsured;
    private View mProgressContainer;
    private View mContentContainer;
    private boolean mContentShown;
}
