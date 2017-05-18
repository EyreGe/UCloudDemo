package com.uclouddemo.publish;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import com.ucloud.ulive.UFilterProfile;
import com.ucloud.ulive.UVideoProfile;
import com.uclouddemo.MainActivity;
import com.uclouddemo.R;

/**
 * Created by Flynn on 17/4/6.
 */

public class PublishActivity extends Activity {

    private AVOption mAVOption;
    private LiveRoomView liveRoomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.live_room_view);
        liveRoomView = ((LiveRoomView) findViewById(R.id.liveroom));
        initConfig();
        liveRoomView.startPreview(mAVOption);
    }

    private void initConfig() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        Intent i = getIntent();
        mAVOption = new AVOption();
        mAVOption.streamUrl = i.getStringExtra(MainActivity.KEY_STREAMING_ADDRESS);
        if (TextUtils.isEmpty(mAVOption.streamUrl)) {
            Toast.makeText(this, "streaming url is null.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        mAVOption.videoFilterMode = i.getIntExtra(MainActivity.KEY_FILTER, UFilterProfile.FilterMode.GPU);
        mAVOption.videoCodecType = i.getIntExtra(MainActivity.KEY_CODEC, UVideoProfile.CODEC_MODE_HARD);
        mAVOption.videoCaptureOrientation = i.getIntExtra(MainActivity.KEY_CAPTURE_ORIENTATION, UVideoProfile.ORIENTATION_PORTRAIT);
        mAVOption.videoFramerate = i.getIntExtra(MainActivity.KEY_FPS, 20);
        mAVOption.videoBitrate = i.getIntExtra(MainActivity.KEY_VIDEO_BITRATE, UVideoProfile.VIDEO_BITRATE_NORMAL);
        mAVOption.videoResolution = i.getIntExtra(MainActivity.KEY_VIDEO_RESOLUTION, UVideoProfile.Resolution.RATIO_AUTO.ordinal());
    }

    @Override
    public void onPause() {
        super.onPause();
        liveRoomView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        liveRoomView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        liveRoomView.onDestroy();
    }
}
