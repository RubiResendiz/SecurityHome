package mx.ita.securityhome;

public class ListElementBitacora {
    public String name;
    public String tipo;
    public String id;
    public String direccion;
    public String fecha;

    public ListElementBitacora(String name, String tipo, String direccion, String id, String fecha){
        this.name= name;
        this.tipo = tipo;
        this.id = id;
        this.direccion = direccion;
        this.fecha = fecha;
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

    public void setName(String name) { this.name = name; }

    public String getDireccion() { return direccion; }

    public void setDireccion(String telefono) {
        this.direccion = direccion;
    }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFecha() { return fecha; }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
