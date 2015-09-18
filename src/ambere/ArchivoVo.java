package ambere;

public class ArchivoVo {
    private int id;
    private String nombre;
    private String direccion;
    private String completo;

    public ArchivoVo(int id, String nombre, String direccion, String completo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.completo = completo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCompleto() {
        return completo;
    }

    public void setCompleto(String completo) {
        this.completo = completo;
    }
    
}
