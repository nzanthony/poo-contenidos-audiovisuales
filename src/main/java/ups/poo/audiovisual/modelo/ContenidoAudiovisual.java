package ups.poo.audiovisual.modelo;

// Clase base con los datos comunes a todos los tipos de contenido
public abstract class ContenidoAudiovisual {
    // Contador compartido por todas las instancias para generar ids unicos
    private static int contar = 0;
    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private int id;

    // Asigna el siguiente id disponible y luego aumenta el contador
    public ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.id = contar++;
        this.titulo = titulo;
        this.duracionEnMinutos = duracionEnMinutos;
        this.genero = genero;
    }

    // Getter y Setter del titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Getter de la duracion sin Setter porque se cambia con cambiarDuracion para validarla
    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    // Devuelve false si la duracion no es valida para este tipo de contenido
    public boolean cambiarDuracion(int nuevaDuracion) {
        if (!esDuracionValida(nuevaDuracion)) {
            return false;
        }
        this.duracionEnMinutos = nuevaDuracion;
        return true;
    }

    // Regla base que las subclases pueden ampliar con restricciones propias
    protected boolean esDuracionValida(int duracion) {
        return duracion > 0;
    }

    // Getter y Setter del genero
    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    // Getter del id sin Setter porque el id no debe cambiar despues de crearse
    public int getId() {
        return id;
    }

    // Cada subclase devuelve el nombre de su tipo para mostrarlo en pantalla
    public abstract String getTipo();

    // Texto base que las subclases amplian con sus propios datos
    @Override
    public String toString() {
        return "=== " + getTipo() + " ===\n"
                + "ID: " + id + "\n"
                + "Titulo: " + titulo + "\n"
                + "Duracion en minutos: " + duracionEnMinutos + "\n"
                + "Genero: " + genero;
    }
}