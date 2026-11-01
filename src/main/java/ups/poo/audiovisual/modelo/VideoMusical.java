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

    public boolean agregarArtista(Artista artista){
        if (artista == null || artistas.contains(artista)) {
            return false;
        }
        artistas.add(artista);
        return true;
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
    public String getTipo() {
        return "Video Musical";
    }

    @Override
    public String toString() {
        String texto = super.toString() + "\nAlbum: " + album + "\nAño: " + anioLanzamiento + "\nArtistas: ";
        for (Artista artista : artistas) {
            texto += "\n  - " + artista.getNombreArtistico() + " (" + artista.getNombre() + ")";
        }
        return texto;
    }
}