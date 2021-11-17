package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * archivo java de contactanos (parte funcional)
 *  
 *
 */

public class Calificanos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificanos);
        ImageButton btnSend = findViewById(R.id.btnSendOp);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com")));
            }
        });
    }
}
