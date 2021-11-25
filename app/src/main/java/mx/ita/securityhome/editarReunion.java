package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class editarReunion extends AppCompatActivity {
    ImageButton regresar;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    Button confirm;
    String id, id_residente;
    final Calendar calendario = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reunion);
        TextView txtFecha1 = findViewById(R.id.pickDate1E);
        TextView txtFecha2 = findViewById(R.id.pickDate2E);
        TextView txtHora1 = findViewById(R.id.pickHour1E);
        TextView txtHora2 = findViewById(R.id.pickHour2E);

        txtFecha1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(txtFecha1);
            }
        });
        txtFecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(txtFecha2);
            }
        });
        txtHora1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(txtHora1);
            }
        });
        txtHora2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(txtHora2);
            }
        });


        Bundle bundle = getIntent().getExtras();
        String dato=bundle.getString("id_reunion");
        Log.i("Dato recibido", dato);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Button confirmar = findViewById(R.id.btnGuardarEditReunion);
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("id_residente", id_residente);
                map.put("id_reunion", dato);
                map.put("fechainicio", txtFecha1.getText().toString());
                map.put("fechafin", txtFecha2.getText().toString());
                map.put("horainicio", txtHora1.getText().toString());
                map.put("horafin", txtHora2.getText().toString());



                mDatabase.child("reuniones").child(dato).
                        setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Log.i("Todo bien", "Todo bien master");
                            startActivity(new Intent(editarReunion.this, invitadoslista.class));
                        }else{
                            Log.w("Error XD 1",task2.getException());
                        }
                    }
                });

            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        regresar = findViewById(R.id.btnReturnListaEditReu);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(editarReunion.this, invitadoslista.class);
                startActivity(intent);
            }
        });
        ref.child("reuniones").child(dato).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String fechainicio = dataSnapshot.child("fechainicio").getValue(String.class);
                    String fechafin = dataSnapshot.child("fechafin").getValue(String.class);
                    String horainicio = dataSnapshot.child("horainicio").getValue(String.class);
                    String horafin = dataSnapshot.child("horafin").getValue(String.class);
                    id_residente = dataSnapshot.child("id_residente").getValue().toString();

                    txtFecha1.setText(fechainicio);
                    txtFecha2.setText(fechafin);
                    txtHora1.setText(horainicio);
                    txtHora2.setText(horafin);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(editarReunion.this,"Fallo la lectura: " + databaseError.getCode(),Toast.LENGTH_LONG);
            }
        });
    }
    private void chooseTime(TextView date_field) {

        final Calendar calendar = Calendar.getInstance();

        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        final int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                calendario.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendario.set(Calendar.MINUTE, minute);

                String dateString = String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
                date_field.setText(dateString); // set the date
            }
        },hour,minute,true);

        timePicker.show();

        timePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }

    private void chooseDate(TextView date_field) {
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePicker =
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(final DatePicker view, final int year, final int month,
                                          final int dayOfMonth) {

                        @SuppressLint("SimpleDateFormat")
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        calendar.set(year, month, dayOfMonth);
                        String dateString = sdf.format(calendar.getTime());

                        date_field.setText(dateString); // set the date
                    }
                }, year, month, day); // set date picker to current date

        datePicker.show();

        datePicker.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(final DialogInterface dialog) {
                dialog.dismiss();
            }
        });
    }
}