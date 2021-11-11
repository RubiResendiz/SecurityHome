package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import mx.ita.securityhome.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private VideoView mVideoView;
    private static int TIME_OUT = 3050;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String path = preferences.getString("splash",null) =="sonido" ?  "android.resource://" + getPackageName() + "/" + R.raw.splashmuted:"android.resource://" + getPackageName() + "/" + R.raw.splash;
        Log.i("shareeeed",preferences.getString("splash",null));
        mVideoView =(VideoView)findViewById(R.id.videoView);
        mVideoView.setVideoURI(Uri.parse(path));
        mVideoView.start();
        mVideoView.requestFocus();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, TIME_OUT);
    }
}