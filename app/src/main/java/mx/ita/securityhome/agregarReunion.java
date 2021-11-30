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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class agregarReunion extends AppCompatActivity {
    TextView pickdate1, pickdate2, pickhour1, pickhour2;
    Button btn;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    final Calendar calendario = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reunion);
        ImageButton btnReturn = findViewById(R.id.btnReturnLista);

        pickdate1 = findViewById(R.id.pickDate1);
        pickdate2 = findViewById(R.id.pickDate2);
        pickhour1 = findViewById(R.id.pickHour1);
        pickhour2 = findViewById(R.id.pickHour2);
        btn = findViewById(R.id.btnGuardarReunion);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth = FirebaseAuth.getInstance();
                mDatabase = FirebaseDatabase.getInstance().getReference();
                //objeto para guardar valores que serán subidos a firebase
                Map<String, Object> map = new HashMap<>();
                String IDnuevo = mAuth.getCurrentUser().getUid();
                //mapear información para insertar en firebase
                //sql = insert into nombre, genero, re
                String id_reunion = agregarResidente.generateRandomPassword(15);
                map.put("id_reunion",id_reunion);
                map.put("fechainicio", pickdate1.getText().toString());
                map.put("fechafin", pickdate2.getText().toString());
                map.put("horainicio", pickhour1.getText().toString());
                map.put("horafin",pickhour2.getText().toString());
                map.put("id_residente", IDnuevo);
                //nos vamos a la tabla reuniones e insertamos el mapa de datos anterior
                mDatabase.child("reuniones").child(id_reunion).
                        setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Log.i("Todo bien", "Todo bien master");
                            startActivity(new Intent(agregarReunion.this, invitadoslista.class));
                        }else{
                            Log.w("Error XD 1",task2.getException());
                        }
                    }
                });

            }
        });
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(agregarReunion.this, invitadoslista.class);
                startActivity(intent);
            }
        });
        pickhour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(pickhour1);
            }
        });
        pickhour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseTime(pickhour2);
            }
        });
        pickdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(pickdate1);
            }
        });
        pickdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseDate(pickdate2);
            }
        });

    }
    //programación del calendario
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