package mx.ita.securityhome;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.Socket;

public class devices2 extends AppCompatActivity {
    static private String IP_HOST = "192.168.100.20";
    static private int PORT = 9999;
    private byte[] buf;
    Bitmap bp;
    String buff;
    private DatagramSocket reveSocket;

    private ProgressDialog pDialog;
    static private ImageView imgShow;
    static private TextView buffer;
    static private WebView webView;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);
        ImageButton settings = findViewById(R.id.btnSettings);
        ImageButton back = findViewById(R.id.btnBackDevice);
        imgShow = findViewById(R.id.salidaVideo);
        buffer = findViewById(R.id.txtEstado);

        new actualizar().execute();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(devices2.this, PrincipalAdmin.class));
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }


    public class actualizar extends AsyncTask<String, Bitmap, String> {

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected String doInBackground(String... params) {

            Log.i("ola","corriendo");
            try {
                boolean XD = true;
                byte conteo = 0;
                socket = new Socket(IP_HOST,PORT);
                BufferedReader mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while(socket.isConnected()){
                    String next = mBufferIn.readLine();
                    InputStream targetStream = new ByteArrayInputStream(next.getBytes());
                    Log.v("Printing image",next.length()+" ::: " + " ::: " + targetStream.available());
                    int maxLogSize = 1000;
                    for(int i = 0; i <= next.length() / maxLogSize; i++) {
                        int start = i * maxLogSize;
                        int end = (i+1) * maxLogSize;
                        end = end > next.length() ? next.length() : end;
                        Log.v("OOOOOO", next.substring(start, end));
                    }
                    byte[] decodedString = Base64.decode(next, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    next = "";
                    this.publishProgress(decodedByte);
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            imgShow.setImageBitmap(values[0]);
        }

    }
}
