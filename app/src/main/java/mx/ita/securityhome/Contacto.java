package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class Contacto extends AppCompatActivity {
    ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        ImageButton face = findViewById(R.id.btnFace),
                whats = findViewById(R.id.btnWhats),
                insta = findViewById(R.id.btnInsta),
                gmail = findViewById(R.id.btngmail);
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/Security-Home-101649935673331")));
            }
        });
        whats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://api.whatsapp.com/send?phone=+524494596585")));
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://instagram.com/_u/securityhome21")));
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_VIEW , Uri.parse("mailto:" + "securityhome21@gmail.com"));
                intent.putExtra(Intent.EXTRA_SUBJECT, "Contact");
                intent.putExtra(Intent.EXTRA_TEXT, "♥");
                startActivity(intent);
            }
        });

        btn = findViewById(R.id.btnAtrasPrincipal);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Contacto.this, Principal.class);
                startActivity(intent);
            }
        });
    }
}