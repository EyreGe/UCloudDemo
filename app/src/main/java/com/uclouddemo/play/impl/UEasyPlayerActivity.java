package com.uclouddemo.play.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TableLayout;
import android.widget.Toast;

import com.ucloud.uvod.UMediaProfile;
import com.ucloud.uvod.UPlayerStateListener;
import com.ucloud.uvod.widget.UVideoView;
import com.uclouddemo.MainActivity;
import com.uclouddemo.R;
import com.uclouddemo.play.ui.UEasyPlayer;
import com.uclouddemo.play.ui.USettingMenuView;
import com.uclouddemo.play.ui.base.UMenuItem;

/**
 * Created by lw.tan on 2015/10/10.
 */
public class UEasyPlayerActivity extends FragmentActivity implements USettingMenuView.Callback, UPlayerStateListener {

	public static final String TAG = "EasyPlayer";

//	@Bind(R.id.video_main_view)
	UEasyPlayer mEasyPlayer;

//	@Bind(R.id.hud_view)
	TableLayout mHudView;

	private String mUri;

	@Override
	protected void onCreate(Bundle bundles) {
		super.onCreate(bundles);
		setContentView(R.layout.activity_video_demo1);
//		ButterKnife.bind(this);
		mEasyPlayer = (UEasyPlayer)findViewById(R.id.video_main_view);
		mHudView = (TableLayout)findViewById(R.id.hud_view);

		mUri = getIntent().getStringExtra(MainActivity.KEY_URI);

		String intentAction = getIntent().getAction();
		if (!TextUtils.isEmpty(intentAction) && intentAction.equals(Intent.ACTION_VIEW)) {
			mUri = getIntent().getDataString();
			mUri = Uri.decode(mUri);
		}

		mEasyPlayer.init(this);

		UMediaProfile profile = new UMediaProfile();
		profile.setInteger(UMediaProfile.KEY_START_ON_PREPARED, getIntent().getIntExtra(MainActivity.KEY_START_ON_PREPARED, 1));
		profile.setInteger(UMediaProfile.KEY_LIVE_STREAMING, getIntent().getIntExtra(MainActivity.KEY_LIVE_STREMAING, 0));  //  1 live streaming 0 vod streaming
		profile.setInteger(UMediaProfile.KEY_MEDIACODEC, getIntent().getIntExtra(MainActivity.KEY_MEDIACODEC, 0));
		profile.setInteger(UMediaProfile.KEY_ENABLE_BACKGROUND_PLAY, getIntent().getIntExtra(MainActivity.KEY_ENABLE_BACKGROUND_PLAY, 0));

		profile.setInteger(UMediaProfile.KEY_PREPARE_TIMEOUT, 1000 * 5);
		profile.setInteger(UMediaProfile.KEY_MIN_READ_FRAME_TIMEOUT_RECONNECT_INTERVAL, 3);

		profile.setInteger(UMediaProfile.KEY_READ_FRAME_TIMEOUT, 1000 * 5);
		profile.setInteger(UMediaProfile.KEY_MIN_PREPARE_TIMEOUT_RECONNECT_INTERVAL, 3);

		if (mUri != null && mUri.endsWith("m3u8")) {
			profile.setInteger(UMediaProfile.KEY_MAX_CACHED_DURATION, 0);// m3u8 默认不开启延时丢帧策略
		}

		mEasyPlayer.setMediaProfile(profile);
		mEasyPlayer.setScreenOriention(UEasyPlayer.SCREEN_ORIENTATION_SENSOR);
		mEasyPlayer.setPlayerStateLisnter(this);
		mEasyPlayer.setOnSettingMenuItemSelectedListener(this);

		if(getIntent().getIntExtra(MainActivity.KEY_SHOW_DEBUG_INFO, 1) == 1) {
			mEasyPlayer.setHudView(mHudView);
		}
		mEasyPlayer.initAspectRatio(UVideoView.VIDEO_RATIO_FIT_PARENT);
		//open before setVideoPath VIDEO_RATIO_FILL_PARENT or VIDEO_RATIO_16_9_FIT_PARENT VIDEO_RATIO_4_3_FIT_PARENT VIDEO_RATIO_WRAP_CONTENT ...

		mEasyPlayer.setVideoPath(mUri);

		IntentFilter filter = new IntentFilter();
		filter.setPriority(1000);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(mNetworkStateListener, filter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		mEasyPlayer.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mEasyPlayer.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mEasyPlayer.onDestroy();
		unregisterReceiver(mNetworkStateListener);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mEasyPlayer.isFullscreen()) {
				mEasyPlayer.toggleScreenOrientation();
				return true;
			} else {
				finish();
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onSettingMenuSelected(UMenuItem item) {
		return false;
	}

	private BroadcastReceiver mNetworkStateListener = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo activeInfo = manager.getActiveNetworkInfo();
				if (activeInfo == null) {
					Toast.makeText(context, getString(R.string.error_current_network_disconnected), Toast.LENGTH_LONG).show();
				}
			}
		}
	};

	@Override
	public void onPlayerStateChanged(State state, int extra1, Object extra2) {
		Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerStateChanged " + state.name());
		switch (state) {
			case PREPARING:
				break;
			case PREPARED:
				break;
			case START:
				break;
			case PAUSE:
				break;
			case STOP:
				break;
			case VIDEO_SIZE_CHANGED:
				break;
			case COMPLETED:
				break;
			case RECONNECT:
				break;
		}
	}

	@Override
	public void onPlayerInfo(Info info, int extra1, Object extra2) {
		switch (info) {
			case BUFFERING_START:
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerInfo BUFFERING_START.");
				break;
			case BUFFERING_END:
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerInfo BUFFERING_END.");
				break;
			case BUFFERING_UPDATE:
				break;
		}
	}

	@Override
	public void onPlayerError(Error error, int extra1, Object extra2) {
		switch (error) {
			case IOERROR:
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerError IOERROR.");
				break;
			case PREPARE_TIMEOUT://just a warn
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerError PREPARE_TIMEOUT.");
				break;
			case READ_FRAME_TIMEOUT://just a warn
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerError READ_FRAME_TIMEOUT.");
				break;
			case UNKNOWN:
				Log.i(TAG, "lifecycle->EasyPlayer->demo-> onPlayerError UNKNOWN.");
				break;
		}
	}
}
