package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;

// Transmision con una agenda de segmentos que no puede superar su duracion
public class TransmisionEnVivo extends ContenidoAudiovisual{
    private String plataforma;
    private List<Segmento> segmentos;

    public TransmisionEnVivo(String titulo, int duracionEnMinutos, String genero, String plataforma){
        super(titulo, duracionEnMinutos, genero);
        this.plataforma = plataforma;
        segmentos = new ArrayList<Segmento>();
    }

    // Devuelve false si el segmento no cabe en los minutos que quedan libres
    public boolean agregarSegmento(String titulo, int duracionEnMinutos) {
        if (duracionEnMinutos > getMinutosDisponibles()) {
            return false;
        }
        segmentos.add(new Segmento(titulo, duracionEnMinutos));
        return true;
    }

    // Impide reducir la duracion por debajo de lo que ya ocupa la agenda
    @Override
    protected boolean esDuracionValida(int duracion) {
        return super.esDuracionValida(duracion) && duracion >= calcularDuracionAgenda();
    }

    // Minutos de la transmision que aun no ocupa ningun segmento
    public int getMinutosDisponibles() {
        return getDuracionEnMinutos() - calcularDuracionAgenda();
    }

    // Devuelve una copia de solo lectura para que no se modifique la agenda desde afuera
    public List<Segmento> getSegmentos() {
        return List.copyOf(segmentos);
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    // Suma la duracion de todos los segmentos de la agenda
    public int calcularDuracionAgenda(){
        int total = 0;
        for (Segmento segmento : segmentos) {
            total = total + segmento.getDuracionEnMinutos();
        }
        return total;
    }

    @Override
    public String getTipo() {
        return "Transmision en vivo";
    }

    // Agrega la plataforma y la agenda al texto base del contenido
    @Override
    public String toString() {
        StringBuilder texto = new StringBuilder(super.toString());
        texto.append("\nPlataforma: ").append(plataforma).append("\nAgenda:");
        for (Segmento segmento : segmentos) {
            texto.append("\n  - ").append(segmento);
        }
        texto.append("\nDuracion total de la agenda: ").append(calcularDuracionAgenda()).append(" min");
        return texto.toString();
    }
}