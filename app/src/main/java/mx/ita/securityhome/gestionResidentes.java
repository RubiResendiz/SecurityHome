package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class gestionResidentes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_residentes);
        Button btnAgregar = findViewById(R.id.btnAgregarResidente);
        ImageButton btnEditar = findViewById(R.id.btnEditarResidente), back = findViewById(R.id.btnReturnPrincipalAdmin);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(gestionResidentes.this, PrincipalAdmin.class);
                startActivity(intents);
            }
        });
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gestionResidentes.this, agregarResidente.class);
                startActivity(intent);
            }
        });
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gestionResidentes.this, editarResidente.class);
                startActivity(intent);
            }
        });
    }
}