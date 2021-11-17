package mx.ita.securityhome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;


public class fragmentInvitado extends Fragment{
    List<String> invitados;
    List<ListElement> elements;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    String dato;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_invitado, container, false);
        //dato = this.getArguments().getString("id_invitado");
        init(root);
        return root;
    }
    public void init(View root){
        invitados = new ArrayList<>();
        Query query = ref.child("invitados").orderByChild("id_invitado");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()){
                        String id_invitado = citi.child("id_invitado").getValue().toString();
                        String id_anfitrion = citi.child("id_anfitrion").getValue().toString();
                        Log.i("String PRUEBA", id_anfitrion+"");
                        if(id_anfitrion.equals(userID))
                            invitados.add(id_invitado);
                    }
                }
                Log.i("número de invitados", invitados.size()+"");
                Log.i("INIT","OK_________________-");
                elements = new ArrayList<>();

                for(String invitado : invitados){
                    Query AXD = ref.child("invitados").orderByChild("id_invitado");
                    Log.i("Valores invitado","No me quieren!");
                    AXD.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot citi : dataSnapshot.getChildren()) {
                                    String id_invitado = citi.child("id_invitado").getValue().toString();
                                    Log.i("Imprimirrrrr", id_invitado);
                                    if(id_invitado.equals(invitado)){
                                        Log.i("jirijirijiri", "Sí entró");
                                        String nombre = citi.child("nombre").getValue(String.class);
                                        String telefono = citi.child("telefono").getValue(String.class);
                                        Log.i("Valores invitado",id_invitado + " " + nombre);
                                        elements.add(new ListElement(nombre, telefono, id_invitado));
                                        Log.i("número de productos :D", invitados.size()+"");
                                        ListAdapter listAdapter = new ListAdapter(elements, getContext());
                                        RecyclerView recyclerView = root.findViewById(R.id.recicladoraXD);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                        recyclerView.setAdapter(listAdapter);

                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Toast.makeText(getContext(),"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(),"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });

    }

}