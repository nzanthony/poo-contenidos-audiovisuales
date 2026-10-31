package ups.poo.audiovisual.modelo;

public class Investigador extends Persona {
    private String especialidad;

    public Investigador(String nombre, String nacionalidad, String especialidad, int edad){
        super(nombre, nacionalidad, edad);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
}