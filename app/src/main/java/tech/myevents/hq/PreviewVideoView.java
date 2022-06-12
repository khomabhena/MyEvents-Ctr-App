package tech.myevents.hq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

public class PreviewVideoView extends AppCompatActivity {

    VideoView vvVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_video_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        vvVideoView = (VideoView) findViewById(R.id.vvVideoView);
        File videoFile = new File("sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4");
        String videoPath = "sdcard/MyEvents/PreBroadcast/pre_broadcast.mp4";

        if(videoFile.exists()){
            vvVideoView.setVideoPath(videoPath);
            vvVideoView.setMediaController(new MediaController(this));
            vvVideoView.requestFocus();
            vvVideoView.start();
        }
    }

}
