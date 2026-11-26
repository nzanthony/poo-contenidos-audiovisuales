package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SerieDeTVTest {
    private SerieDeTV serie;

    @BeforeEach
    void crearSerie() {
        serie = new SerieDeTV("Game of Thrones", 60, "Fantasia");
    }

    @Test
    void agregaUnaTemporadaNueva() {
        assertTrue(serie.agregarTemporada(1, 10));
        assertEquals(1, serie.getNumeroTemporadas());
    }

    @Test
    void noRepiteElNumeroDeTemporada() {
        serie.agregarTemporada(1, 10);

        assertFalse(serie.agregarTemporada(1, 8));
        assertEquals(1, serie.getNumeroTemporadas());
    }

    @Test
    void cuentaVariasTemporadas() {
        serie.agregarTemporada(1, 10);
        serie.agregarTemporada(2, 10);
        serie.agregarTemporada(3, 10);

        assertEquals(3, serie.getNumeroTemporadas());
    }

    @Test
    void elTextoIncluyeLasTemporadas() {
        serie.agregarTemporada(1, 10);

        assertTrue(serie.toString().contains("Temporada 1: 10 episodios"));
    }

    @Test
    void noPermiteModificarLasTemporadasDesdeFuera() {
        assertThrows(UnsupportedOperationException.class,
                () -> serie.getTemporadas().clear());
    }
}