package com.uclouddemo.play.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.uclouddemo.R;

/**
 *
 * Created by lw.tan on 2015/10/10.
 *
 */
public class UTopView extends RelativeLayout {
	private Callback mCallabck;
	
//	@Bind(R.id.topview_title_txtv)
	TextView mTitleTxtv;
	
//	@Bind(R.id.topview_left_button)
	ImageButton mLeftImgBtn;
	
//	@Bind(R.id.topview_right_button)
	ImageButton mRightImgBtn;

	public interface Callback {
		boolean onLeftButtonClick(View view);
		boolean onRightButtonClick(View view);
	}
	
	public UTopView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public UTopView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UTopView(Context context) {
		this(context, null);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
//		ButterKnife.bind(this);

		mTitleTxtv = (TextView)findViewById(R.id.topview_title_txtv);
		mLeftImgBtn = (ImageButton)findViewById(R.id.topview_left_button);
		mRightImgBtn = (ImageButton)findViewById(R.id.topview_right_button);

		mLeftImgBtn.setOnClickListener(mLeftButtonClickListener);
		mRightImgBtn.setOnClickListener(mRightButtonClickListener);
	}

	OnClickListener mLeftButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mCallabck != null) {
				mCallabck.onLeftButtonClick(v);
			}
		}
	};

	OnClickListener mRightButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mCallabck != null) {
				mCallabck.onRightButtonClick(v);
			}
		}
	};

	public void registerCallback(Callback callback) {
		mCallabck = callback;
	}

	public void setTitle(int resid) {
		if (mTitleTxtv != null) {
			mTitleTxtv.setText(resid);
		}
	}
}
