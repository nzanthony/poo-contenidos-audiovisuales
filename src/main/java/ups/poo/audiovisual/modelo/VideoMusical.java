package ups.poo.audiovisual.modelo;

import java.util.List;

public class VideoMusical extends ContenidoAudiovisual {
    private String album;
    private int anioLanzamiento;
    private final Participantes<Artista> artistas = new Participantes<>();

    public VideoMusical(String titulo, int duracionEnMinutos, String genero, String album, int anioLanzamiento) {
        super(titulo, duracionEnMinutos, genero);
        this.album = Validacion.exigirTexto(album, "El album");
        this.anioLanzamiento = Validacion.exigirRango(anioLanzamiento, 1900, 2100, "El anio de lanzamiento");
    }

    public boolean agregarArtista(Artista artista) {
        return artistas.agregar(artista);
    }

    public List<Artista> getArtistas() {
        return artistas.comoLista();
    }
    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = Validacion.exigirTexto(album, "El album");
    }

    public int getAnioLanzamiento() {
        return anioLanzamiento;
    }

    public void setAnioLanzamiento(int anioLanzamiento) {
        this.anioLanzamiento = Validacion.exigirRango(anioLanzamiento, 1900, 2100, "El anio de lanzamiento");
    }

    @Override
    public String getTipo() {
        return "Video Musical";
    }

    @Override
    public String toString() {
        return super.toString() + "\nAlbum: " + album + "\nAño: " + anioLanzamiento + "\nArtistas: " + artistas.listar();
    }
}