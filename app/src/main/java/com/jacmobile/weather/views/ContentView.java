package com.jacmobile.weather.views;

import android.view.ViewGroup;
import com.jacmobile.weather.activities.AActivity;

/** An indirection which allows controlling the root container used for each activity. */
public interface ContentView {
    /** The root {@link android.view.ViewGroup} into which the activity should place its contents. **/
    ViewGroup get(AActivity activity);

    /** An {@link com.jacmobile.weather.views.ContentView} which returns the normal activity content view. **/
    ContentView DEFAULT = new ContentView() {
        @Override public ViewGroup get(AActivity activity) {
            return (ViewGroup) activity.findViewById(android.R.id.content);
        }
    };
}