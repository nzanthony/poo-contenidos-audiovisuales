package ups.poo.audiovisual.modelo;

public class Investigador {
    private String nombre;
    private String nacionalidad;
    private String especialidad;
    private int edad;

    public Investigador(String nombre, String nacionalidad, String especialidad, int edad){
        this.nombre = nombre;
        this.nacionalidad = nacionalidad;
        this.especialidad = especialidad;
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

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
}
