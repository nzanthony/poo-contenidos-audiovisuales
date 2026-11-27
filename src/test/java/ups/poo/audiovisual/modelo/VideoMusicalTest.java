package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VideoMusicalTest {
    private VideoMusical video;
    private Artista artista;

    @BeforeEach
    void crearDatos() {
        video = new VideoMusical("Despacito", 5, "Reggaeton", "Vida", 2017);
        artista = new Artista("Luis Alfonso Rodriguez", "Puertorriqueña", 48, "Luis Fonsi");
    }

    @Test
    void agregaUnArtistaNuevo() {
        assertTrue(video.agregarArtista(artista));
        assertEquals(1, video.getArtistas().size());
    }

    @Test
    void noAgregaElMismoArtistaDosVeces() {
        video.agregarArtista(artista);

        assertFalse(video.agregarArtista(artista));
    }

    @Test
    void rechazaUnArtistaNulo() {
        assertFalse(video.agregarArtista(null));
    }

    @Test
    void muestraAlArtistaConSuNombreArtistico() {
        video.agregarArtista(artista);

        assertTrue(video.toString().contains("Luis Fonsi (Luis Alfonso Rodriguez)"));
    }

    @Test
    void aceptaLosLimitesDelAnioYRechazaLosValoresFuera() {
        assertDoesNotThrow(() -> new VideoMusical("Antiguo", 3, "Jazz", "Album", 1900));
        assertDoesNotThrow(() -> new VideoMusical("Futuro", 3, "Pop", "Album", 2100));
        assertThrows(IllegalArgumentException.class, () -> new VideoMusical("Antiguo", 3, "Jazz", "Album", 1899));
        assertThrows(IllegalArgumentException.class, () -> new VideoMusical("Futuro", 3, "Pop", "Album", 2101));
    }
}