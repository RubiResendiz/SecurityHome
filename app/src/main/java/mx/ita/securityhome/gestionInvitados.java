package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import mx.ita.securityhome.R;

public class gestionInvitados extends AppCompatActivity {
    List<String> invitados;
    List<ListElementIR> elements;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    String dato;
    Spinner spinner;
    List<String[]> residentes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_invitados);
        ImageButton back = findViewById(R.id.imageButton6);
        spinner = findViewById(R.id.spinBuscar);
        Button todos = findViewById(R.id.btnTodos);
        init();
        todos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                init();
            }
        });
        Query AXD = ref.child("residentes").orderByChild("id");
        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                        String id_residente = citi.child("id").getValue().toString();
                        Log.i("Imprimirrrrr", id_residente);
                        String nombre = citi.child("nombre").getValue().toString();
                        String[] por ={nombre,id_residente};
                        residentes.add(por);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(gestionInvitados.this,
                            android.R.layout.simple_spinner_item);
                    for(String[] res : residentes){
                        adapter.add(res[0]+"-"+res[1]);
                    }
                    spinner.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(gestionInvitados.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                init(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gestionInvitados.this, PrincipalAdmin.class);
                startActivity(intent);
            }
        });
    }
    public void init(){
        RecyclerView recyclerView = findViewById(R.id.recicladoraIR);
        elements = new ArrayList<>();
        Query AXD = ref.child("invitados").orderByChild("id_invitado");
        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                        String id_invitado = citi.child("id_invitado").getValue().toString();
                        Log.i("Imprimirrrrr", id_invitado);
                        String telefono = citi.child("telefono").getValue().toString();
                        String nombre = citi.child("nombre").getValue().toString();
                        String id_anfitrion =  citi.child("id_anfitrion").getValue().toString();
                        Query found = ref.child("residentes").orderByChild("id");
                        found.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    for(DataSnapshot jerjes : snapshot.getChildren()){
                                        String nombreres = jerjes.child("nombre").getValue().toString();
                                        String idres = jerjes.child("id").getValue().toString();
                                        if(id_anfitrion.equals(idres)){
                                            elements.add(new ListElementIR(nombre,telefono,id_invitado,"Anfitrión: "+(nombreres+"-"+idres),"invitados"));
                                            ListAdapterIR listAdapter = new ListAdapterIR(elements, gestionInvitados.this);
                                            recyclerView.setHasFixedSize(true);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(gestionInvitados.this));
                                            recyclerView.setAdapter(listAdapter);
                                        }
                                    }
                                }else{

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(gestionInvitados.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }


    public void init(String where){
        RecyclerView recyclerView = findViewById(R.id.recicladoraIR);
        elements = new ArrayList<>();
        Query AXD = ref.child("invitados").orderByChild("id_invitado");
        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                        String id_invitado = citi.child("id_invitado").getValue().toString();
                        Log.i("Imprimirrrrr", id_invitado);
                        String telefono = citi.child("telefono").getValue().toString();
                        String nombre = citi.child("nombre").getValue().toString();
                        String id_anfitrion =  citi.child("id_anfitrion").getValue().toString();
                        Query found = ref.child("residentes").orderByChild("id");
                        found.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){

                                    for(DataSnapshot jerjes : snapshot.getChildren()){
                                        String nombreres = jerjes.child("nombre").getValue().toString();
                                        String idres = jerjes.child("id").getValue().toString();
                                        if(id_anfitrion.equals(idres)){
                                            if((nombreres+"-"+idres).equals(where)){
                                                elements.add(new ListElementIR(nombre,telefono,id_invitado,"Anfitrión: "+where,"invitados"));
                                                ListAdapterIR listAdapter = new ListAdapterIR(elements, gestionInvitados.this);
                                                recyclerView.setHasFixedSize(true);
                                                recyclerView.setLayoutManager(new LinearLayoutManager(gestionInvitados.this));
                                                recyclerView.setAdapter(listAdapter);
                                            }else{
                                                Log.i("nada que compartir XD", "NADA");
                                                recyclerView.removeAllViewsInLayout();
                                            }
                                        }
                                    }
                                }else{
                                    Log.i("nada","NADA!");

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(gestionInvitados.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }

}