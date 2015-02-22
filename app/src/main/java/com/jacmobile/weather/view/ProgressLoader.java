// GiftCards Android App
//
// Copyright (c) 2007-2015 GiftCards.com.  All rights reserved.

package com.jacmobile.weather.view;

import android.app.FragmentManager;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import javax.inject.Inject;
import javax.inject.Singleton;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import jacmobile.com.weather.R;

@Singleton public class ProgressLoader
{
    private static final String TAG = "progress";

    public static final int SHORT_DURATION = 1666;
    public static final int MED_DURATION = 2500;
    public static final int LONG_DURATION = 5000;

    @Inject Handler handler;

    private LoadingFragment temp;

    public void showProgress(FragmentManager fm)
    {
        temp = LoadingFragment.newInstance();
        temp.setCancelable(false);
        temp.show(fm, TAG);
    }

    public void hideProgress()
    {
        if (temp != null) temp.dismiss();
    }

    public void transientProgress(FragmentManager fm, final int duration)
    {
        temp = LoadingFragment.newInstance();
        temp.setCancelable(false);
        temp.show(fm, TAG);
        handler.postDelayed(getProgressRunnable(), duration);
    }

    public Runnable getProgressRunnable()
    {
        return new Runnable()
        {
            @Override
            public void run()
            {
                hideProgress();
            }
        };
    }

    public void showProgress(FragmentManager fm, String description)
    {
        temp = LoadingFragment.newInstance(description);
        temp.setCancelable(false);
        temp.show(fm, TAG);
    }

//  Use this to close progress when image(s) finish loading
    public GlideDrawableImageViewTarget getCallbackWrappedView(ImageView imageView, final Runnable todo)
    {
        return new GlideDrawableImageViewTarget(imageView)
        {
            @Override public void onResourceReady(GlideDrawable drawable, GlideAnimation anim)
            {
                super.onResourceReady(drawable, anim);
                handler.post(todo);
            }

            @Override public void onLoadFailed(Exception e, Drawable errorDrawable)
            {
                super.onLoadFailed(e, errorDrawable);
                handler.post(todo);
            }
        };
    }


    public static class LoadingFragment extends DialogFragment
    {
        private static final String TITLE = "description";

        public static LoadingFragment newInstance()
        {
            return new LoadingFragment();
        }

        public static LoadingFragment newInstance(String description)
        {
            LoadingFragment fragment = new LoadingFragment();
            Bundle args = new Bundle();
            args.putString(TITLE, description);
            fragment.setArguments(args);
            return fragment;
        }

        @Override public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            Dialog dialog = new Dialog(getActivity(), R.style.Dialog_No_Border);
            View view = LayoutInflater.from(getActivity()).inflate(
                    R.layout.loading_progress, new LinearLayout(getActivity()), false);
            dialog.setTitle(null);
            dialog.setContentView(view);

            //adjust if more args are added
            if (getArguments() != null) {
                ((TextView) view.findViewById(R.id.txt_progress))
                        .setText(getArguments().getString(TITLE));
            }

            dialog.setContentView(view);
            return dialog;
        }
    }
}