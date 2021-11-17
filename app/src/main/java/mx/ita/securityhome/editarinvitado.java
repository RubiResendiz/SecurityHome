package mx.ita.securityhome;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class editarinvitado extends AppCompatActivity {
    ImageButton regresar;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Button confirm;
    String id, id_invitado;
    String[] rel = {"No responderé","Amistosa","Sentimental","Familiar","Laboral","Otro"};
    String[] gen = {"Hombre", "Mujer", "Otro"};
    private byte getPosition(String[] array, String dat){
        byte pos =0;
        for(byte x = 0; x < array.length; x++){
            if(array[x].equals(dat))
                pos = x;
            Log.i(array[x]+" : ",dat+" : " + pos +"_ _ _ "+x);
        }
        return pos;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editarinvitado);
        Bundle bundle = getIntent().getExtras();
        String dato=bundle.getString("id_invitado");
        Log.i("Dato recibido", dato);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        TextView txtNombre = findViewById(R.id.txtEditarNombreInvitado);
        TextView txtTelefono = findViewById(R.id.txtEditarTelefonoInvitado);
        Spinner relacions = findViewById(R.id.spinnerEditRel);
        Spinner generos = findViewById(R.id.spinnerEditGen);
        String IDnuevo = mAuth.getCurrentUser().getUid();
        confirm = findViewById(R.id.btnEditConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("id_anfitrion", id);
                map.put("id_invitado", id_invitado);
                map.put("nombre", txtNombre.getText().toString());
                map.put("telefono", txtTelefono.getText().toString());
                map.put("genero", generos.getSelectedItem().toString());
                map.put("relacion", relacions.getSelectedItem().toString());



                mDatabase.child("invitados").child(dato).
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
                startActivity(new Intent(editarinvitado.this, invitadoslista.class));
            }
        });
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        regresar = findViewById(R.id.btnReturnInvitadoA);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarinvitado.this, invitadoslista.class);
                startActivity(intent);
            }
        });
        ref.child("invitados").child(dato).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue(String.class);
                    String telefono = dataSnapshot.child("telefono").getValue(String.class);
                    String genero = dataSnapshot.child("genero").getValue(String.class);
                    String relacion = dataSnapshot.child("relacion").getValue(String.class);
                    id = dataSnapshot.child("id_anfitrion").getValue().toString();
                    id_invitado = dataSnapshot.child("id_invitado").getValue().toString();

                    txtNombre.setText(nombre);
                    txtTelefono.setText(telefono);
                    Log.i("RELAAA", getPosition(rel,relacion)+"");
                    Log.i("GEBBB", getPosition(gen,genero)+"");
                    relacions.setSelection((int)getPosition(rel,relacion));
                    generos.setSelection((int)getPosition(gen,genero));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(editarinvitado.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }
}