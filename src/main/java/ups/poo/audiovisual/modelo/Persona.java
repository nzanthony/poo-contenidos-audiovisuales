package ups.poo.audiovisual.modelo;

// Clase base con los datos comunes de actores investigadores y artistas
public abstract class Persona {
    private String nombre;
    private String nacionalidad;
    private int edad;
    private int id;

    // Getter del id sin Setter publico porque solo el catalogo puede asignarlo
    public int getId() {
        return id;
    }

    // Metodo sin public para que solo el catalogo asigne el id al registrar la persona
    void asignarId(int id) {
        this.id = id;
    }

    // Constructor protected porque solo las subclases pueden crear personas
    protected Persona(String nombre,String nacionalidad, int edad){
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
