package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PrincipalAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_admin);
        Button btnInvitados, btnResidentes, btnVisitar;
        btnResidentes = findViewById(R.id.btnGestionarResidentes);
        btnInvitados = findViewById(R.id.btnGestionInv);
        btnVisitar = findViewById(R.id.btnGestionVis);
        Button btnIot = findViewById(R.id.btnIOT);
        btnResidentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(PrincipalAdmin.this, gestionResidentes.class);
                startActivity(intent1);
            }
        });
        btnInvitados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(PrincipalAdmin.this, gestionInvitados.class);
                startActivity(intent1);
            }
        });
        btnVisitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(PrincipalAdmin.this, gestionVisitas.class);
                startActivity(intent1);
            }
        });
        btnIot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrincipalAdmin.this, Dispositivos.class));
            }
        });
    }
}