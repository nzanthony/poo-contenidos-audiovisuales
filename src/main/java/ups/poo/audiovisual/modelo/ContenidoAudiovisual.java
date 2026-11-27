package ups.poo.audiovisual.modelo;

// Clase base con los datos comunes a todos los tipos de contenido
public abstract class ContenidoAudiovisual {
    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private int id;

    // Metodo sin public para que solo el catalogo asigne el id al registrar el contenido
    void asignarId(int id) {
        this.id = id;
    }

    // El id queda en 0 hasta que el catalogo lo asigne al registrar el contenido
    public ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.titulo = Validacion.exigirTexto(titulo, "El titulo");
        // No usa esDuracionValida porque las subclases aun no terminan de construirse
        this.duracionEnMinutos = Validacion.exigirPositivo(duracionEnMinutos, "La duracion");
        this.genero = Validacion.exigirTexto(genero, "El genero");
    }

    // Getter y Setter del titulo
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Validacion.exigirTexto(titulo, "El titulo");
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
        this.genero = Validacion.exigirTexto(genero, "El genero");
    }

    // Getter del id sin Setter publico porque solo el catalogo puede asignarlo
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