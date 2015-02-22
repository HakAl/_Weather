package com.jacmobile.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LockedVerticalViewPager extends VerticalViewPager
{
    private boolean isSwipeEnabled;

    public LockedVerticalViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.isSwipeEnabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return this.isSwipeEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event)
    {
        if (this.isSwipeEnabled) {
            return super.onInterceptTouchEvent(event);
        }
        return false;
    }

    public int back()
    {
        int currentIndex = this.getCurrentItem();
        if (currentIndex != 0) {
            this.setCurrentItem(--currentIndex, false);
        }
        return currentIndex;
    }

    public int next()
    {
        int currentIndex = this.getCurrentItem();
        if (currentIndex != (this.getChildCount() - 1)) {
            this.setCurrentItem(++currentIndex, false);
        }
        return currentIndex;
    }

    /**
     * Enable or disable swipe
     *
     * @param isSwipeEnabled true to enable swipe, false otherwise
     */
    public void setPagingEnabled(boolean isSwipeEnabled)
    {
        this.isSwipeEnabled = isSwipeEnabled;
    }

    /**
     * Flip on or off.
     */
    public void toggle()
    {
        this.isSwipeEnabled = !isSwipeEnabled;
    }
}
