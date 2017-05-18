package com.uclouddemo.play.ui.base;

import android.app.Activity;
import android.content.Context;
import android.view.WindowManager.LayoutParams;

/**
 * 
 * Created by lw.tan on 2015/10/10.
 *
 */
public class UBrightnessHelper extends UBaseHelper {

	public static int DEFAULT_MAX_BRIGHTNESS_VALUE = 100;
	public static int DEFAULT_MIN_CHANAGE_LEVEL = 10;
	public static int DEFAULT_BRIGHTNESS_VALUE = 50;

	public UBrightnessHelper(Context context) {
		super(context);
	}

	@Override
	public void init(Context context) {
		setLevel(DEFAULT_MIN_CHANAGE_LEVEL);
		setMaxLevel(DEFAULT_MAX_BRIGHTNESS_VALUE);
	}

	@Override
	public void setValue(int level, boolean isTouch) {
		if (isZero() && isTouch) {
			level = mHistoryLevel;
		}
		if (level < 0) {
			level = 0;
		} else if (level > mMaxLevel) {
			level = mMaxLevel;
		}
		float tempValue = level;
		if (mContext != null && mContext instanceof Activity) {
			LayoutParams lp = ((Activity)(mContext)).getWindow().getAttributes();
			lp.screenBrightness = tempValue / mMaxLevel;
			((Activity)(mContext)).getWindow().setAttributes(lp);
			updateValue();
			if (!isZero()) {
				mHistoryLevel = mCurrentLevel;
			}
			if (mListener != null) {
				mListener.onUpdateUI();
			}
		}
	}

	@Override
	public int getSystemValueLevel() {
		if (mContext != null && mContext instanceof Activity) {
			LayoutParams lp = ((Activity)(mContext)).getWindow().getAttributes();
			return lp.screenBrightness == -1 ? DEFAULT_MAX_BRIGHTNESS_VALUE : (int)(lp.screenBrightness * DEFAULT_MAX_BRIGHTNESS_VALUE);
		}
		return DEFAULT_MAX_BRIGHTNESS_VALUE;
	}
}
