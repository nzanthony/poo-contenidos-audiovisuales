package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// ContenidoAudiovisual es abstracta y por eso se prueba con Pelicula que no cambia las reglas de duracion
class ContenidoAudiovisualTest {

    @Test
    void cambiaLaDuracionAUnValorPositivo() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");

        assertTrue(pelicula.cambiarDuracion(140));
        assertEquals(140, pelicula.getDuracionEnMinutos());
    }

    @Test
    void rechazaUnaDuracionCeroONegativa() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");

        assertFalse(pelicula.cambiarDuracion(0));
        assertFalse(pelicula.cambiarDuracion(-5));
        assertEquals(128, pelicula.getDuracionEnMinutos());
    }

    @Test
    void noPermiteCrearConDuracionCeroONegativa() {
        assertThrows(IllegalArgumentException.class, () -> new Pelicula("La La Land", 0, "Musical", "Summit"));
        assertThrows(IllegalArgumentException.class, () -> new Pelicula("La La Land", -5, "Musical", "Summit"));
    }

    @Test
    void noPermiteTextosVaciosOSoloConEspacios() {
        assertThrows(IllegalArgumentException.class, () -> new Pelicula("", 128, "Musical", "Summit"));
        assertThrows(IllegalArgumentException.class, () -> new Pelicula("La La Land", 128, "   ", "Summit"));
        assertThrows(IllegalArgumentException.class, () -> new Pelicula("La La Land", 128, "Musical", null));
    }

    @Test
    void elSetterRechazaUnTituloVacioYConservaElAnterior() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");

        assertThrows(IllegalArgumentException.class, () -> pelicula.setTitulo(" "));
        assertEquals("La La Land", pelicula.getTitulo());
    }
}