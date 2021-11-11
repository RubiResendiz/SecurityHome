package mx.ita.securityhome;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Locale;

public class agregarReunion extends AppCompatActivity {
    TextView pickdate1, pickdate2;
    final Calendar calendario = Calendar.getInstance();
    int anio = calendario.get(Calendar.YEAR);
    int mes = calendario.get(Calendar.MONTH);
    int diaDelMes = calendario.get(Calendar.DAY_OF_MONTH);
    private int ultimoAnio, ultimoMes, ultimoDiaDelMes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_reunion);
        ImageButton btnReturn = findViewById(R.id.btnReturnLista);
        pickdate1 = findViewById(R.id.pickDate1);
        pickdate2 = findViewById(R.id.pickDate2);
        refrescarFechaEnEditText();
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(agregarReunion.this, invitadoslista.class);
                startActivity(intent);
            }
        });
        pickdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialogoFecha = new DatePickerDialog(agregarReunion.this, listenerDeDatePicker, ultimoAnio, ultimoMes, ultimoDiaDelMes);
                dialogoFecha.show();
            }
        });
    }
    public void refrescarFechaEnEditText() {
        // Formateamos la fecha pero podríamos hacer cualquier otra cosa ;)
        String fecha = String.format(Locale.getDefault(), "%02d-%02d-%02d", ultimoAnio, ultimoMes+1, ultimoDiaDelMes);

        // La ponemos en el editText
        pickdate1.setText(fecha);
    }
    // Crear un listener del datepicker;
    private DatePickerDialog.OnDateSetListener listenerDeDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int anio, int mes, int diaDelMes) {
            // Esto se llama cuando seleccionan una fecha. Nos pasa la vista, pero más importante, nos pasa:
            // El año, el mes y el día del mes. Es lo que necesitamos para saber la fecha completa
        }
    };
}