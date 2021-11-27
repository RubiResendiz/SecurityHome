package mx.ita.securityhome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragmentReunion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragmentReunion extends Fragment {
    List<String> reuniones;
    List<ListElementReuniones> elements;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    String dato;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragmentReunion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentReunion.
     */
    // TODO: Rename and change types and number of parameters
    public static fragmentReunion newInstance(String param1, String param2) {
        fragmentReunion fragment = new fragmentReunion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View root = inflater.inflate(R.layout.fragment_reunion, container, false);
      init(root);
      return root;
    }
    public void init(View root){
        reuniones = new ArrayList<>();
        Query query = ref.child("reuniones").orderByChild("id_reunion");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()){
                        String id_reunion = citi.child("id_reunion").getValue().toString();
                        String id_residente = citi.child("id_residente").getValue().toString();
                        Log.i("String PRUEBA", id_residente+"");
                        if(id_residente.equals(userID))
                            reuniones.add(id_reunion);
                    }
                }
                Log.i("número de reuniones", reuniones.size()+"");
                Log.i("INIT","OK_________________-");
                elements = new ArrayList<>();

                for(String reunion : reuniones){
                    Query AXD = ref.child("reuniones").orderByChild("id_reuniones");
                    Log.i("Valores reuniones","No me quieren!");
                    AXD.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot citi : dataSnapshot.getChildren()) {
                                    String id_reunion = citi.child("id_reunion").getValue().toString();
                                    Log.i("Imprimirrrrr", id_reunion);
                                    if(id_reunion.equals(reunion)){
                                        Log.i("jirijirijiri", "Sí entró");
                                        String fechainicio = citi.child("fechainicio").getValue(String.class);
                                        String horainicio = citi.child("horainicio").getValue(String.class);
                                        Log.i("Valores reunion",id_reunion + " " + fechainicio);
                                        elements.add(new ListElementReuniones(fechainicio, horainicio, id_reunion));
                                        Log.i("número dereuniones :D", reuniones.size()+"");
                                        ListAdapterReuniones listAdapter = new ListAdapterReuniones(elements, getContext());
                                        RecyclerView recyclerView = root.findViewById(R.id.recicladoraReunion);
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