package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

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

public class Calificanos extends AppCompatActivity {
    List<String> comentarios;
    List<ListElementComentarios> elements;
    //Autenticación extraer info actual del usuario loggeado
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    //intancia a la base de datos
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    String userID = mAuth.getCurrentUser().getUid();
    EditText comentario;
    EditText temporal;
    RatingBar rating;
    String nombreee;
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calificanos);
        String dato;
        comentario = findViewById(R.id.txtCampoComentario);
        rating = findViewById(R.id.ratingBar);
        temporal = findViewById(R.id.txtNombreTemporal);
        ImageButton btnSend = findViewById(R.id.btnSendOp);
        init();
        ImageButton bck = findViewById(R.id.btnBackRate);
        ImageView bug = findViewById(R.id.goBug);
        bug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Calificanos.this, Reportebug.class));
            }
        });

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Calificanos.this, Principal.class));
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("ID USUARIO NUEVO", mAuth.getUid());
                Map<String, Object> map = new HashMap<>();
                String IDnuevo = mAuth.getCurrentUser().getUid();
                map.put("id_residente", IDnuevo);
                map.put("nombre", temporal.getText().toString());
                map.put("comentario", comentario.getText().toString());
                map.put("estrellas", rating.getRating());
                //insertar datos
                ref.child("comentarios").child(IDnuevo).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Log.i("Todo bien", "Todo bien master");
                            init();
                        }else{
                            Log.w("Error XD 1",task2.getException());
                        }
                    }
                });
            }
        });
    }

    private void init(){
        elements = new ArrayList<>();
        comentarios = new ArrayList<>();
        Query query = ref.child("comentarios").orderByChild("id_residente");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()){
                        String id_residente = citi.child("id_residente").getValue().toString();
                        Log.i("Imprimirrrrr", id_residente);
                        Log.i("jirijirijiri", "Sí entró");
                        nombreee = citi.child("nombre").getValue(String.class);

                        String coment = citi.child("comentario").getValue(String.class);
                        int estrellas = citi.child("estrellas").getValue(Integer.class);

                        Log.i("Valores comentarios",id_residente + " " + nombreee + " " + estrellas);
                        elements.add(new ListElementComentarios(nombreee, coment, estrellas));

                        ListAdapterComentarios listAdapter = new ListAdapterComentarios(elements, Calificanos.this);
                        RecyclerView recyclerView = findViewById(R.id.recicladoraComentarios);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(Calificanos.this));
                        recyclerView.setAdapter(listAdapter);
                        if(id_residente.equals(userID)){
                            temporal.setText(nombreee);
                            comentario.setText(coment);
                            rating.setRating(estrellas);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(Calificanos.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }
}