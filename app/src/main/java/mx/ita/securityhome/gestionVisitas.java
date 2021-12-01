package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

public class gestionVisitas extends AppCompatActivity {
    List<String[]> visitas;
    List<ListElementBitacora> elements;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_visitas);
        ImageButton btn = findViewById(R.id.btnBackBitacora);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(gestionVisitas.this, PrincipalAdmin.class);
                startActivity(intent);
            }
        });
        init();
    }
    public void init(){
        RecyclerView recyclerView = findViewById(R.id.recicladoraBitacora);
        visitas = new ArrayList<>();
        Query query = ref.child("visitas").orderByChild("id_visitante");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()){
                        String id_visita = citi.child("id_visitante").getValue().toString();
                        String tiempo = citi.child("tiempo").getValue().toString();
                        Log.i("String PRUEBA", id_visita + " " + tiempo);
                        String[] datos = {id_visita,tiempo};
                        visitas.add(datos);
                    }
                }
                Log.i("Visitas ", visitas.size() + " ");
                elements = new ArrayList<>();
                for(String[] visita : visitas){
                    Log.i(visita[0].length()+" E ","longitud");
                    if(visita[0].length() == 28){
                        Query AXD = ref.child("residentes").orderByChild("id");
                        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                                        String id_residente= citi.child("id").getValue().toString();
                                        if(id_residente.equals(visita[0])){
                                            Log.i("Imprimirrrrr", id_residente);
                                            String nombre = citi.child("nombre").getValue(String.class);
                                            String direccion = citi.child("calle").getValue(String.class);
                                            String numero = citi.child("numero").getValue(String.class);
                                            String tipo = "res";
                                            elements.add(new ListElementBitacora(nombre, tipo,(direccion + " #" +numero),id_residente,visita[1]));
                                            ListAdapterBitacora listAdapter = new ListAdapterBitacora(elements, gestionVisitas.this);
                                            recyclerView.setHasFixedSize(true);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(gestionVisitas.this));
                                            recyclerView.setAdapter(listAdapter);
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    if(visita[0].length() == 38){
                        Query AXD = ref.child("invitados").orderByChild("id_invitado");
                        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                                        String id_invitado= citi.child("id_invitado").getValue().toString();
                                        if(id_invitado.equals(visita[0])){
                                            Log.i("Imprimirrrrr", id_invitado);
                                            String nombre = citi.child("nombre").getValue(String.class);
                                            String anfitrion = citi.child("id_anfitrion").getValue(String.class);
                                            String telefono = citi.child("telefono").getValue(String.class);
                                            String tipo = "inv";
                                            Query getAnfitrion = ref.child("residentes").child(anfitrion).orderByChild("id");
                                            getAnfitrion.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(dataSnapshot.exists()){
                                                        String calle = dataSnapshot.child("calle").getValue(String.class);
                                                        String numero = dataSnapshot.child("numero").getValue(String.class);
                                                        elements.add(new ListElementBitacora(nombre, tipo,telefono,id_invitado,visita[1]+ " rumbo a " + (calle + " #"+numero)));
                                                        ListAdapterBitacora listAdapter = new ListAdapterBitacora(elements, gestionVisitas.this);
                                                        recyclerView.setHasFixedSize(true);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(gestionVisitas.this));
                                                        recyclerView.setAdapter(listAdapter);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    if(visita[0].length() == 15){
                        Query AXD = ref.child("reuniones").orderByChild("id_reunion");
                        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot
                            ) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                                        String id_reunion= citi.child("id_reunion").getValue().toString();
                                        if(id_reunion.equals(visita[0])){
                                            Log.i("Imprimirrrrr", id_reunion);
                                            String fechainicio= citi.child("fechainicio").getValue(String.class);
                                            String horainicio = citi.child("horainicio").getValue(String.class);
                                            String anfitrion = citi.child("id_residente").getValue(String.class);
                                            String fechafin= citi.child("fechafin").getValue(String.class);
                                            String horafin= citi.child("horafin").getValue(String.class);
                                            String tipo = "reu";
                                            Query getAnfitrion = ref.child("residentes").child(anfitrion).orderByChild("id");
                                            getAnfitrion.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if(dataSnapshot.exists()){
                                                        String calle = dataSnapshot.child("calle").getValue(String.class);
                                                        String numero = dataSnapshot.child("numero").getValue(String.class);
                                                        elements.add(new ListElementBitacora((fechainicio + " " + horainicio), tipo,(fechafin+ " " +horafin),id_reunion,visita[1]+ " en " + (calle + " #"+numero)));
                                                        ListAdapterBitacora listAdapter = new ListAdapterBitacora(elements, gestionVisitas.this);
                                                        recyclerView.setHasFixedSize(true);
                                                        recyclerView.setLayoutManager(new LinearLayoutManager(gestionVisitas.this));
                                                        recyclerView.setAdapter(listAdapter);
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });
                                        }
                                    }
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
                Toast.makeText(gestionVisitas.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }

}