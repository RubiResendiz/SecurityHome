package mx.ita.securityhome.ui.home;

import android.animation.Animator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import net.glxn.qrgen.android.QRCode;

import java.util.Calendar;

import mx.ita.securityhome.Contacto;
import mx.ita.securityhome.Notificaciones;
import mx.ita.securityhome.R;
import mx.ita.securityhome.invitadoslista;

public class HomeFragment extends Fragment implements View.OnClickListener{
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    String userID = mAuth.getCurrentUser().getUid();
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        Button btnAgregarUsuario = root.findViewById(R.id.btnAgregarInvitado);
        btnAgregarUsuario.setOnClickListener(this);
        ref.keepSynced(true);
        ImageView fondo = root.findViewById(R.id.fondodia);
        TextView nombreUser = root.findViewById(R.id.txtNombreHome);
        TextView dia = root.findViewById(R.id.txtBuendia);
        ImageButton qr = root.findViewById(R.id.btnQR);

        Calendar rightNow = Calendar.getInstance();
        int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);


        if(currentHourIn24Format >=6 && currentHourIn24Format <12)
            fondo.setImageResource(R.drawable.dia);
        if(currentHourIn24Format >=12 && currentHourIn24Format <18)
            fondo.setImageResource(R.drawable.tarde);
        if(currentHourIn24Format >=18 && currentHourIn24Format <21)
            fondo.setImageResource(R.drawable.atardecer);
        if(currentHourIn24Format >=21 && currentHourIn24Format <6)
            fondo.setImageResource(R.drawable.noche);

        //generar QR
        Query query = ref.child("residentes");
        Log.i("id",userID);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot citi : dataSnapshot.getChildren()) {
                        String id = citi.child("id").getValue().toString();
                        if(id.equals(userID)){
                            Log.i("veamos log: " , citi.getChildrenCount()+"");
                            String nombre = citi.child("nombre").getValue().toString();
                            String calle = citi.child("calle").getValue().toString();
                            String numEXT = citi.child("numero").getValue().toString();
                            String URL = citi.child("url").getValue().toString();

                            Bitmap bitmap = QRCode.from(id).withSize(400,400).bitmap();
                            qr.setImageBitmap(bitmap);
                            nombreUser.setText(nombre);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return root;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnAgregarInvitado:
                Intent intent = new Intent(getActivity(), invitadoslista.class);
                startActivity(intent);
                break;
            case R.id.btnContacto:
                Intent contacto = new Intent(getActivity(), Contacto.class);
                startActivity(contacto);
                break;
        }
    }
}