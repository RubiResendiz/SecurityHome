package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class editarResidente extends AppCompatActivity {
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String id, id_invitado;
    String url=null, access="RES",correo = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_residente);
        ImageButton btnBack = findViewById(R.id.btnBackAdmin2);
        Bundle bundle = getIntent().getExtras();
        String dato=bundle.getString("id_residente");
        Log.i("Dato recibido", dato);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button confirm = findViewById(R.id.btnFinalizarEditar);
        TextView txtNombre = findViewById(R.id.txtEditNombreRes);
        TextView txtCalle = findViewById(R.id.txtEditCalleRes);
        TextView txtNum = findViewById(R.id.txtEditNumRes);
        TextView pass = findViewById(R.id.txtEditPassRes);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", dato);
                map.put("url", url);
                map.put("access", access);
                map.put("nombre", txtNombre.getText().toString());
                map.put("calle", txtCalle.getText().toString());
                map.put("numero", txtNum.getText().toString());
                map.put("correo", correo);
                map.put("pass",  pass.getText().toString());



                mDatabase.child("residentes").child(dato).
                        setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Log.i("Todo bien", "Todo bien master");
                        }else{
                            Log.w("Error XD 1",task2.getException());
                        }
                    }
                });
                startActivity(new Intent(editarResidente.this, gestionResidentes.class));
            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("residentes").child(dato).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String calle = dataSnapshot.child("calle").getValue(String.class);
                    String numero = dataSnapshot.child("numero").getValue(String.class);
                    String ur = dataSnapshot.child("url").getValue(String.class);
                    String psw = dataSnapshot.child("pass").getValue(String.class);
                    String cor = dataSnapshot.child("correo").getValue(String.class);
                    String acc = dataSnapshot.child("access").getValue(String.class);


                    txtNombre.setText(nombre);
                    txtCalle.setText(calle);
                    txtNum.setText(numero);
                    pass.setText(psw);
                    url = ur;
                    access = acc;
                    correo = cor;


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(editarResidente.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarResidente.this, gestionResidentes.class);
                startActivity(intent);

            }
        });
    }
}