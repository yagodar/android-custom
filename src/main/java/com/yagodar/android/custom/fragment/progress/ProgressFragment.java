package com.yagodar.android.custom.fragment.progress;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.yagodar.android.custom.R;


/**
 * Created by yagodar on 06.03.2015.
 */
public class ProgressFragment extends Fragment implements IProgressContext {

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
        setContentShown(shown, true);
    }

    public View getContentView() {
        return mContentView;
    }

    public void setContentView(int layoutResId) {
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View contentView = layoutInflater.inflate(layoutResId, null);
        setContentView(contentView);
    }

    public void setContentView(View view) {
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
    }

    public void setContentShownNoAnimation(boolean shown) {
        setContentShown(shown, false);
    }

    protected void setContentShown(boolean shown, boolean animate) {
        if (mContentShown == shown) {
            return;
        }

        ensureContainers();

        mContentShown = shown;
        if (shown) {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
            } else {
                mProgressContainer.clearAnimation();
                mContentContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.GONE);
            mContentContainer.setVisibility(View.VISIBLE);
        } else {
            if (animate) {
                mProgressContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_in));
                mContentContainer.startAnimation(AnimationUtils.loadAnimation(getActivity(), android.R.anim.fade_out));
            } else {
                mProgressContainer.clearAnimation();
                mContentContainer.clearAnimation();
            }
            mProgressContainer.setVisibility(View.VISIBLE);
            mContentContainer.setVisibility(View.GONE);
        }
    }

    protected void ensure() {
        ensureContainers();
        ensureContent();
    }

    protected void ensureContent() {
        if(mContentView != null) {
            return;
        }

        setContentShown(false, false);
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

    protected View mContentView;
    protected View mProgressContainer;
    protected View mContentContainer;
    protected boolean mContentShown;
}
