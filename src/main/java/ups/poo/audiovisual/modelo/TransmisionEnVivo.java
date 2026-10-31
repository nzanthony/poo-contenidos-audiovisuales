package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;


public class TransmisionEnVivo extends ContenidoAudiovisual{
    private String plataforma;
    private List<Segmento> segmentos;

    public TransmisionEnVivo(String titulo, int duracionEnMinutos, String genero, String plataforma){
        super(titulo, duracionEnMinutos, genero);
        this.plataforma = plataforma;
        segmentos = new ArrayList<Segmento>();
    }

    public void agregarSegmento(String titulo, int duracionEnMinutos){
        Segmento segmento = new Segmento(titulo, duracionEnMinutos);
        segmentos.add(segmento);
    }
    
    public List<Segmento> getSegmentos() {
        return List.copyOf(segmentos);
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public int calcularDuracion(){
        int total = 0;
        for (Segmento segmento : segmentos) {
            total = total + segmento.getDuracionEnMinutos();
        }
        return total;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles de la transmision en vivo:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Género: " + getGenero());
        System.out.println("Plataforma: " + getPlataforma());
        System.out.println("Duracion en minutos: " + getDuracionEnMinutos());
        System.out.println("Agenda:");
        for (Segmento segmento : segmentos) {
            System.out.println("  - " + segmento.getTitulo() + " (" + segmento.getDuracionEnMinutos() + " min)");
        }
        System.out.println("Duracion total de la agenda: " + calcularDuracion() + " min");
        System.out.println();
    }
}