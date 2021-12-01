package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Reportebug extends AppCompatActivity {

    TextView para, asunto, mensaje;
    ImageButton enviar, back;
    ImageView access;
    String sPara, sAsunto, sMensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportebug);
        ImageButton bck = findViewById(R.id.btnBackBug);
        access = findViewById(R.id.btnAccessRating);
        access.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Reportebug.this, Calificanos.class));
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Reportebug.this, Calificanos.class));
            }
        });

        back = findViewById(R.id.btnBackBug);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Reportebug.this, Principal.class));
            }
        });
        asunto = findViewById(R.id.txtMotivoBug);
        enviar = findViewById(R.id.btnSendBug);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sPara = "securyhome21@gmail.com";
                sMensaje = asunto.getText().toString();

                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{sPara});
                email.putExtra(Intent.EXTRA_SUBJECT, "Reporte de Bug");
                email.putExtra(Intent.EXTRA_TEXT, sMensaje);

                //necesita esto para solicitar solo al cliente de correo electrónico
                email.setType("message/rfc822");

                startActivity(Intent.createChooser(email, "Elija el cliente de correo electrónico :"));
            }
        });
    }
}