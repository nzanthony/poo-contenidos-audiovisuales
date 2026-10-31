package ups.poo.audiovisual.modelo;
import java.util.ArrayList;
import java.util.List;

public class VideoMusical extends ContenidoAudiovisual{
    private String album;
    private int anioLanzamiento;
    private List<Artista> artistas;

    public VideoMusical(String titulo, int duracionEnMinutos, String genero, String album, int anioLanzamiento){
        super(titulo, duracionEnMinutos, genero);
        this.album = album;
        this.anioLanzamiento = anioLanzamiento;
        this.artistas = new ArrayList<>();
    }

    public void agregarArtista(Artista artista){
        artistas.add(artista);
    }

    public List<Artista> getArtistas(){
        return List.copyOf(artistas);
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = anioLanzamiento;
    }

    @Override
    public void mostrarDetalles() {
        System.out.println("Detalles del video musical:");
        System.out.println("ID: " + getId());
        System.out.println("Título: " + getTitulo());
        System.out.println("Duración en minutos: " + getDuracionEnMinutos());
        System.out.println("Género: " + getGenero());
        System.out.println("Album: " + getAlbum());
        System.out.println("Año de lanzamiento: " + getAnioLanzamiento());
        System.out.println("Artistas:");
        for (Artista artista : artistas) {
            System.out.println("  - " + artista.getNombreArtistico() + " (" + artista.getNombre() + ")");
        }
        System.out.println();
    }
}
