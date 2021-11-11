package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import mx.ita.securityhome.ui.login.LoginActivity;

public class editarPerfil extends AppCompatActivity {
    ImageButton regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        regresar = findViewById(R.id.btnReturnEditarPerfil);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(editarPerfil.this, Principal.class);
                startActivity(i);
            }
        });
    }
}