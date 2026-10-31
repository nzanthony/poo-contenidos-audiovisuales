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
    public String getTipo() {
        return "Transmision en vivo";
    }

    @Override
    public String toString() {
        String texto = super.toString() + "\nPlataforma: " + plataforma + "\nAgenda:";
        for (Segmento segmento : segmentos) {
            texto += "\n  - " + segmento.getTitulo() + " (" + segmento.getDuracionEnMinutos() + " min)";
        }
        texto += "\nDuracion total de la agenda: " + calcularDuracion() + " min";
        return texto;
    }
}