package mx.ita.securityhome;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

public class agregarinvitado extends AppCompatActivity {
    ImageButton regresar;
    Button add;
    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    String SiteKey= "6LeGZh4dAAAAACIWfgFKSz03KzBktT0RxUPjtQUk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregarinvitado);
        Button captcha = findViewById(R.id.btnCaptcha);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        add = findViewById(R.id.btnAddConfirm);
        add.setEnabled(false);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView nombre = findViewById(R.id.txtNombreInvitado),
                        telefono = findViewById(R.id.txtTelefonoInvitado);
                Spinner spinnerGen = findViewById(R.id.spinnerGen),
                        spinnerRel = findViewById(R.id.spinnerRel);

                Map<String, Object> map = new HashMap<>();
                String IDnuevo = mAuth.getCurrentUser().getUid();
                //mapear información para insertar en firebase
                //sql = insert into nombre, genero, re
                map.put("id_invitado",IDnuevo + telefono.getText().toString());
                map.put("nombre", nombre.getText().toString());
                map.put("telefono", telefono.getText().toString());
                map.put("genero", spinnerGen.getSelectedItem().toString());
                map.put("relacion",spinnerRel.getSelectedItem().toString());
                map.put("id_anfitrion", IDnuevo);

                mDatabase.child("invitados").child(IDnuevo + telefono.getText().toString()).
                        setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if(task2.isSuccessful()){
                            Log.i("Todo bien", "Todo bien master");
                        }else{
                            Log.w("Error XD 1",task2.getException());
                        }
                    }
                });
                startActivity(new Intent(agregarinvitado.this, invitadoslista.class));
            }
        });
        captcha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SafetyNet.getClient(agregarinvitado.this).verifyWithRecaptcha(SiteKey)
                        .addOnSuccessListener(new OnSuccessListener<SafetyNetApi.RecaptchaTokenResponse>() {
                            @Override
                            public void onSuccess(SafetyNetApi.RecaptchaTokenResponse recaptchaTokenResponse) {
                                // Indicates communication with reCAPTCHA service was
                                // successful.

                                //verificamos que el usuario ingrese la información solicitada forma correcta
                                String userResponseToken = recaptchaTokenResponse.getTokenResult();
                                if (!userResponseToken.isEmpty()) {
                                    // Validate the user response token using the
                                    // reCAPTCHA siteverify API.
                                    Log.e("captchabelazlo", userResponseToken);
                                    captcha.setBackgroundColor(Color.GREEN);
                                    add.setEnabled(true);
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (e instanceof ApiException) {
                                    // An error occurred when communicating with the
                                    // reCAPTCHA service. Refer to the status code to
                                    // handle the error appropriately.
                                    ApiException apiException = (ApiException) e;
                                    int statusCode = apiException.getStatusCode();
                                    Log.e("Captchabela", "Error: " + CommonStatusCodes
                                            .getStatusCodeString(statusCode));
                                } else {
                                    // A different, unknown type of error occurred.
                                    Log.e("Captchabela", "Error: " + e.getMessage());
                                }
                            }
                        });
            }
        });

        regresar = findViewById(R.id.btnReturnInvitadoA);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(agregarinvitado.this, invitadoslista.class);
                startActivity(intent);
            }
        });
    }
}