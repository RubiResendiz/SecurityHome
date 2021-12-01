package mx.ita.securityhome;

public class ListElementIR {
    public String name;
    public String telefono;
    public String id;
    public String anfitrion;
    public String tabla;

    public ListElementIR(String name, String telefono, String id, String anfitrion, String tabla){
        this.name= name;
        this.telefono = telefono;
        this.id = id;
        this.anfitrion = anfitrion;
        this.tabla = tabla;
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

    public String getAnfitrion() {
        return anfitrion;
    }

    public void setAnfitrion (String anfitrion) { this.anfitrion = anfitrion; }

    public String getTabla() {
        return tabla;
    }

    public void setTabla (String tabla) { this.tabla = tabla; }
}
