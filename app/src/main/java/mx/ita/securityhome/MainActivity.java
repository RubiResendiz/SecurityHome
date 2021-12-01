package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.VideoView;

import java.util.Locale;

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
        Log.i("shared", preferences.getString("anim",null)+ " " + preferences.getString("splash",null));
        if(preferences.getString("anim",null).equals("true")){
            path = preferences.getString("splash",null).equals("sonido") ?  "android.resource://" + getPackageName() + "/" + R.raw.splash:"android.resource://" + getPackageName() + "/" + R.raw.splashmuted;
        }else{
            path = "android.resource://" + getPackageName() + "/" + R.raw.statico;
        }
        if(preferences.getString("idioma",null).equals("es")){
            Locale localizacion = new Locale("es", "ES");
            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            Log.i("Cocacola","espuma1");
        }
        if(preferences.getString("idioma",null).equals("en")){
            Locale localizacion = new Locale("en", "US");
            Locale.setDefault(localizacion);
            Configuration config = new Configuration();
            config.locale = localizacion;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
            Log.i("Cocacola","espuma2");
        }

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