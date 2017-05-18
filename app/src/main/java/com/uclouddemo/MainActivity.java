package com.uclouddemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ucloud.ulive.UFilterProfile;
import com.ucloud.ulive.UVideoProfile;
import com.uclouddemo.R;
import com.uclouddemo.play.VideoActivity;
import com.uclouddemo.publish.PublishActivity;
import com.uclouddemo.publish.permission.PermissionsActivity;
import com.uclouddemo.publish.permission.PermissionsChecker;

public class MainActivity extends Activity implements View.OnClickListener {

    public static final String KEY_STREAMING_ADDRESS = "streaming-address";

    public static final String KEY_PLAY_ADDRESS = "play-address";

    public static final String KEY_CAPTURE_ORIENTATION = "capture-orientation";

    public static final String KEY_FILTER = "capture-filter";

    public static final String KEY_CODEC = "capture-codec";

    public static final String KEY_FPS = "capture-fps" ;

    public static final String KEY_VIDEO_BITRATE = "video-bitrate" ;

    public static final String KEY_VIDEO_RESOLUTION = "video-resolution" ;

    private static final int REQUEST_CODE = 200;

    public static final String KEY_MEDIACODEC = "mediacodec";

    public static final String KEY_START_ON_PREPARED = "start-on-prepared";

    public static final String KEY_LIVE_STREMAING = "live-streaming";

    public static final String KEY_ENABLE_BACKGROUND_PLAY = "enable-background-play";

    public static final String KEY_SHOW_DEBUG_INFO = "show-debug-info";

    public static final String KEY_URI = "uri";

    private Button publish,play;

    private Intent intent;

    private PermissionsChecker mPermissionsChecker; //for android target version >=23

    String[] permissions = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        publish = ((Button) findViewById(R.id.publish));
        play = ((Button) findViewById(R.id.play));

        mPermissionsChecker = new PermissionsChecker(this);

        publish.setOnClickListener(this);
        play.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.publish:
                if (mPermissionsChecker.lacksPermissions(permissions)) {
                    PermissionsActivity.startActivityForResult(this, REQUEST_CODE, permissions);
                    return;
                } else {
                    startActivity(0,R.id.publish);
                }
                break;
            case R.id.play:
                startActivityPlay(1,R.id.play);
                break;
        }
    }

    private void startActivity(int index,int id) {
//        if ( index == 0){
            intent = new Intent(this,PublishActivity.class);
//        } else {
//            intent = new Intent(this,VideoActivity.class);
//        }
        intent.putExtra(KEY_FILTER, UFilterProfile.FilterMode.GPU);
        intent.putExtra(KEY_CODEC, UVideoProfile.CODEC_MODE_HARD);
        intent.putExtra(KEY_VIDEO_BITRATE, UVideoProfile.VIDEO_BITRATE_MEDIUM);
        intent.putExtra(KEY_VIDEO_RESOLUTION, UVideoProfile.Resolution.RATIO_4x3.ordinal());
        intent.putExtra(KEY_FPS, 20);
        intent.putExtra(KEY_CAPTURE_ORIENTATION, UVideoProfile.ORIENTATION_PORTRAIT);
        line(intent, id);
//        intent.putExtra(KEY_FILTER, filterType(filterRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_CODEC, codecType(codecRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_VIDEO_BITRATE, videoBitrate(videoBitrateRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_VIDEO_RESOLUTION, videoResolution(resolutionRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_FPS, framerate(framerateEdtxt));
//        intent.putExtra(KEY_CAPTURE_ORIENTATION, captureOrientation(captureOrientationRg.getCheckedRadioButtonId()));
//        line(intent, lineRg.getCheckedRadioButtonId(), streamIdEdtxt);
        startActivity(intent);
    }

    private void startActivityPlay(int index,int id) {
        intent = new Intent(this,VideoActivity.class);
        intent.putExtra(KEY_LIVE_STREMAING, 1);
        intent.putExtra(KEY_MEDIACODEC, 0);
        intent.putExtra(KEY_START_ON_PREPARED, 1);
        intent.putExtra(KEY_ENABLE_BACKGROUND_PLAY, 0);
        intent.putExtra(KEY_SHOW_DEBUG_INFO, 1);
        intent.putExtra(KEY_URI, String.format("http://vlive3.rtmp.cdn.ucloud.com.cn/ucloud/%s.flv", "1111"));

//        intent.putExtra(KEY_LIVE_STREMAING, streamingTypeRg.getCheckedRadioButtonId() == R.id.rb_live_streaming ? 1: 0);
//        intent.putExtra(KEY_MEDIACODEC, videoCodecRg.getCheckedRadioButtonId() == R.id.rb_mediacodec ? 1: 0);
//        intent.putExtra(KEY_START_ON_PREPARED, startOnPreparedRg.getCheckedRadioButtonId() == R.id.rb_auto ? 1: 0);
//        intent.putExtra(KEY_ENABLE_BACKGROUND_PLAY, backgroundPlayCb.isChecked() ? 1: 0);
//        intent.putExtra(KEY_SHOW_DEBUG_INFO, showDebugInfoCb.isChecked() ? 1: 0);
//        intent.putExtra(KEY_URI, uri);


//        intent.putExtra(KEY_FILTER, filterType(filterRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_CODEC, codecType(codecRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_VIDEO_BITRATE, videoBitrate(videoBitrateRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_VIDEO_RESOLUTION, videoResolution(resolutionRg.getCheckedRadioButtonId()));
//        intent.putExtra(KEY_FPS, framerate(framerateEdtxt));
//        intent.putExtra(KEY_CAPTURE_ORIENTATION, captureOrientation(captureOrientationRg.getCheckedRadioButtonId()));
//        line(intent, lineRg.getCheckedRadioButtonId(), streamIdEdtxt);
        startActivity(intent);
    }

//    private int filterType(int id) {
//        return id == R.id.rb_cpufilter ? UFilterProfile.FilterMode.CPU : UFilterProfile.FilterMode.GPU;
//    }
//
//    private int codecType(int id) {
//        return id == R.id.rb_hwcodec ? UVideoProfile.CODEC_MODE_HARD : UVideoProfile.CODEC_MODE_SOFT;
//    }
//
//    private int videoBitrate(int id) {
//        switch (id) {
//            case R.id.rb_videobitrate_low:
//                return UVideoProfile.VIDEO_BITRATE_LOW;
//            case R.id.rb_videobitrate_normal:
//                return UVideoProfile.VIDEO_BITRATE_NORMAL;
//            case R.id.rb_videobitrate_medium:
//                return UVideoProfile.VIDEO_BITRATE_MEDIUM;
//            case R.id.rb_videobitrate_high:
//                return UVideoProfile.VIDEO_BITRATE_HIGH;
//            default:
//                return UVideoProfile.VIDEO_BITRATE_LOW;
//        }
//    }
//
//    private int videoResolution(int id) {
//        switch (id) {
//            case R.id.rb_videoaspect_auto:
//                return UVideoProfile.Resolution.RATIO_AUTO.ordinal();
//            case R.id.rb_videoaspect_4x3:
//                return UVideoProfile.Resolution.RATIO_4x3.ordinal();
//            case R.id.rb_videoaspect_16x9:
//                return UVideoProfile.Resolution.RATIO_16x9.ordinal();
//            default:
//                return UVideoProfile.Resolution.RATIO_AUTO.ordinal();
//        }
//    }
//
//    private int framerate(EditText editText) {
//        if (editText == null) {
//            return 20;
//        }
//        String framerate = editText.getText().toString();
//
//        if (TextUtils.isEmpty(framerate) || !TextUtils.isDigitsOnly(framerate)) {
//            return 20;
//        } else {
//            return Integer.parseInt(editText.getText().toString().trim());
//        }
//    }
//
//    private int captureOrientation(int id) {
//        return id == R.id.rb_capture_orientation_landspace ? UVideoProfile.ORIENTATION_LANDSCAPE : UVideoProfile.ORIENTATION_PORTRAIT;
//    }

    private void line(Intent intent, int id) {
        if (id == R.id.publish) {
            intent.putExtra(KEY_STREAMING_ADDRESS, String.format("rtmp://publish3.cdn.ucloud.com.cn/ucloud/%s", "1111"));
            intent.putExtra(KEY_PLAY_ADDRESS, String.format("http://vlive3.rtmp.cdn.ucloud.com.cn/ucloud/%s.flv", "1111"));
        } else {
            intent.putExtra(KEY_STREAMING_ADDRESS, String.format("rtmp://publish3.usmtd.ucloud.com.cn/live/%s", "1111"));
            intent.putExtra(KEY_PLAY_ADDRESS, String.format("http://rtmp3.usmtd.ucloud.com.cn/live/%s.flv", "1111"));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (!mPermissionsChecker.lacksPermissions(permissions)) {
                startActivity(0,R.id.publish);
            }
        }
    }
}
