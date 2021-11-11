package mx.ita.securityhome.ui.gallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import mx.ita.securityhome.MainActivity;
import mx.ita.securityhome.R;
import mx.ita.securityhome.invitadoslista;

public class GalleryFragment extends Fragment implements View.OnClickListener{

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        Button idioma = root.findViewById(R.id.btnBug);
        idioma.setOnClickListener(this);
        Switch sw = root.findViewById(R.id.switch1),
            sw2 = root.findViewById(R.id.switch2);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String val1 = preferences.getString("splash",null);
        sw.setChecked(val1 == "sonido" ? false:true);
        String val2 = preferences.getString("anim",null);
        sw2.setChecked(val2 =="true"?false:true);

        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw.isChecked()){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("splash", "sonido");
                    editor.commit();
                    Log.i("shared", preferences.getString("splash",null));
                }else{
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("splash", "nosonido");
                    editor.commit();
                    Log.i("shared", preferences.getString("splash",null));
                }
            }
        });
        sw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sw.isChecked()){
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("anim", "true");
                    editor.commit();
                    Log.i("anim", preferences.getString("anim",null));
                }else{
                    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("anim", "false");
                    editor.commit();
                    Log.i("anim", preferences.getString("anim",null));
                }
            }
        });
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBug:
                Intent email= new Intent(Intent.ACTION_SENDTO);
                email.setData(Uri.parse("mailto:findmybusinesscorp.com"));
                email.putExtra(Intent.EXTRA_SUBJECT, "Reporte de bug");
                email.putExtra(Intent.EXTRA_TEXT, "Descripción del suceso");
                startActivity(email);
                break;
        }
    }
}