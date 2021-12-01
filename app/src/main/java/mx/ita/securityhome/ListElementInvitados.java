package mx.ita.securityhome;

import android.graphics.Bitmap;

public class ListElementInvitados {
    public String name;
    public String telefono;
    public String id;

    public ListElementInvitados(String name, String telefono, String id){
        this.name= name;
        this.telefono = telefono;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
