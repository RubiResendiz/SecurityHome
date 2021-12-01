package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class gestionResidentes extends AppCompatActivity {
    List<String> residentes;
    List<ListElementResidentes> elements;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser getUser;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    String dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_residentes);
        Button btnAgregar = findViewById(R.id.btnAgregarResidente);
        ImageButton back = findViewById(R.id.btnReturnPrincipalAdmin);

        init();

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
    }
    public void init(){
        elements = new ArrayList<>();
        Query AXD = ref.child("residentes").orderByChild("id");
        AXD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                        String id_residente = citi.child("id").getValue().toString();
                        Log.i("Imprimirrrrr", id_residente);
                        String correo = citi.child("correo").getValue().toString();
                        String nombre = citi.child("nombre").getValue().toString();
                        String url = citi.child("url").getValue().toString();
                        Bitmap pic = null;

                        if(url.equals("lol")){
                            pic = BitmapFactory.decodeResource(getResources(),
                                    R.drawable.house_png);
                        }
                        Log.i("Valores invitado",id_residente + " " + nombre + " " + url +" " +pic);
                        elements.add(new ListElementResidentes(id_residente,nombre,pic,correo));


                        ListAdapterResidentes listAdapter = new ListAdapterResidentes(elements, gestionResidentes.this);
                        RecyclerView recyclerView = findViewById(R.id.recicladoraresidentes);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(gestionResidentes.this));
                        recyclerView.setAdapter(listAdapter);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(gestionResidentes.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }
}