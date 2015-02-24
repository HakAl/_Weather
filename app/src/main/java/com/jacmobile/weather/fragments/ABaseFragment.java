package com.jacmobile.weather.fragments;

import android.app.Activity;
import android.app.Fragment;

import com.jacmobile.weather.activities.AActivity;

/** Base fragment which performs injection using the activity-scoped object graph **/
public abstract class ABaseFragment extends Fragment
{
    private boolean firstAttach = true;

    @Override public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        if (firstAttach) {
            firstAttach = false;
            ((AActivity) activity).inject(this);
        }
    }

    @Override public void setUserVisibleHint(boolean visible)
    {
        super.setUserVisibleHint(visible);
        if (visible && isResumed())
        {
            //Only manually call onResume if fragment is already visible
            //Otherwise allow natural fragment lifecycle to call onResume
            onResume();
        }
    }
}