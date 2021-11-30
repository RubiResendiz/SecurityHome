package mx.ita.securityhome;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Dispositivos extends AppCompatActivity {
    static private String IP_HOST = "192.168.100.202"; //posible cambio para meter la IP manualmente
    static private int PORT = 9999;
    private byte[] buf;
    Bitmap bp;
    String buff;
    private DatagramSocket reveSocket;

    private ProgressDialog pDialog;
    static private ImageView imgShow; //aquí vamos a mostrar el video
    static private TextView estado; //si está online u opffline
    static private EditText capturar_ip;
    static private Button conectar;
    Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);
        ImageButton settings = findViewById(R.id.btnSettings);
        ImageButton back = findViewById(R.id.btnBackDevice);
        imgShow = findViewById(R.id.salidaVideo);
        estado = findViewById(R.id.txtEstado);
        capturar_ip = findViewById(R.id.txtIPHost);
        conectar = findViewById(R.id.btnConectar);
        conectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!capturar_ip.getText().toString().equals("")){
                    try{
                        new actualizar().execute(); //tarea asíncrona
                    }catch (Exception e){
                        Toast.makeText(Dispositivos.this,"OFFLINE", Toast.LENGTH_SHORT);
                    }

                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dispositivos.this, PrincipalAdmin.class));
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
            IP_HOST = capturar_ip.getText().toString();
            Log.i("ola","corriendo");
            try {
                boolean XD = true;
                socket = new Socket(IP_HOST,PORT); //creamos socket con los datos del server
                int conteo=0;
                BufferedReader mBufferIn = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                //buffer para contener los packetes con 3 secciones

                while(socket.isConnected()){
                    String xxx = mBufferIn.readLine(); //encabezado del formato utf-8
                    Log.i("CONTEO: " , (conteo++)+"-----------------");
                    int size = Integer.parseInt(xxx);
                    String next = mBufferIn.readLine(); //segunda línea es la imagen en base64 (string)
                    InputStream targetStream = new ByteArrayInputStream(next.getBytes()); //guardar base 64 en InputStream
                    Log.i("Printing image",next.length()+" ::: " + size + " ::: " + targetStream.available());

                    Log.i("substring", next.substring(size,next.length()));

                   /* int maxLogSize = 1000;
                    for(int i = 0; i <= next.length() / maxLogSize; i++) {
                        int start = i * maxLogSize;
                        int end = (i+1) * maxLogSize;
                        end = end > next.length() ? next.length() : end;
                        Log.v("OOOOOO2", next.substring(start, end));
                    }*/

                    byte[] decodedString = Base64.decode(next, Base64.DEFAULT); //decodificamos base64
                    //
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    this.publishProgress(decodedByte); //publicar la imagen porque es una tarea asíncrona
                    String facherito = mBufferIn.readLine(); //separador entre paquetes
                }

            } catch (Exception e) {
                estado.setText("OFFLINE");
                estado.setTextColor(Color.RED);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Bitmap... values) {
            super.onProgressUpdate(values);
            imgShow.setImageBitmap(values[0]);
            estado.setTextColor(Color.GREEN);
            estado.setText("ONLINE");
            conectar.setEnabled(false);
        }

    }
    private static int MAX_CONNECTION = 10;
    private int reconnections = 0;
}
