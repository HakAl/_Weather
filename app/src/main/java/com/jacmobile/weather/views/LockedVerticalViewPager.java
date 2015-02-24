package com.jacmobile.weather.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class LockedVerticalViewPager extends VerticalViewPager
{
    private boolean locked;
    LockableLayoutListener listener;

    public LockedVerticalViewPager(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.locked = false;
    }

    @Override public boolean onTouchEvent(MotionEvent event)
    {
        return this.locked && super.onTouchEvent(event);
    }

    @Override public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return this.listener.isLocked();
    }

    public void setListener(LockableLayoutListener listener)
    {
        this.listener = listener;
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

    public void lock(boolean isSwipeEnabled)
    {
        this.locked = isSwipeEnabled;
    }

    public void toggleLock()
    {
        this.locked = !locked;
    }

    public interface LockableLayoutListener
    {
        public boolean isLocked();
    }
}
