
package com.uclouddemo.play.ui.widget;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.uclouddemo.R;


/**
 * 
 * Created by lw.tan on 2015/10/10.
 *
 */
public class UVerticalProgressBar extends ProgressBar {
    private int mBarWidth;
    private int mBarHeight;

    private boolean mHorizontal = false;

    public UVerticalProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public UVerticalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UVerticalProgressBar(Context context) {
        this(context, null);
    }

   
    private void init() {
        mBarWidth = getResources().getDimensionPixelSize(R.dimen.volume_vertical_progress_width);
        mBarHeight = getResources().getDimensionPixelSize(R.dimen.vs_progressbar_height);
    }

    public void setOrientation(boolean horizontal) {
        mHorizontal = horizontal;
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        try {
            Rect rec = getProgressDrawable().getBounds();
            if (!mHorizontal) {
                if (getWidth() > mBarWidth) {
                    rec.left = (getWidth() - mBarWidth) / 2;
                    getProgressDrawable().setBounds(rec.left, rec.top,
                            rec.left + mBarWidth, rec.bottom);
                }
            } else {
                if (getHeight() > mBarHeight) {
                    rec.top = (getHeight() - mBarWidth) / 2;
                    getProgressDrawable().setBounds(rec.left, rec.top,
                            rec.right, rec.top + mBarHeight);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDraw(canvas);
    }
}