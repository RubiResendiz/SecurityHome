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

        Switch sw = root.findViewById(R.id.switch1),
            sw2 = root.findViewById(R.id.switch2);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //sacmos configuración previa
        String val1 = preferences.getString("splash",null);
        sw.setChecked(val1.equals("sonido") ? true:false);
        String val2 = preferences.getString("anim",null);
        sw2.setChecked(val2.equals("true")?true:false);

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
                if(sw2.isChecked()){
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

    }
}