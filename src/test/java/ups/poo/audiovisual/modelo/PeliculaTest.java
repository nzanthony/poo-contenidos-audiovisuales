package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PeliculaTest {
    private Pelicula pelicula;
    private Actor actor;

    @BeforeEach
    void crearDatos() {
        pelicula = new Pelicula("La La Land", 128, "Musical", "Summit Entertainment");
        actor = new Actor("Ryan Gosling", "Canadiense", 30);
    }

    @Test
    void agregaUnActorNuevo() {
        assertTrue(pelicula.agregarActor(actor));
        assertEquals(1, pelicula.getActores().size());
    }

    @Test
    void noAgregaElMismoActorDosVeces() {
        pelicula.agregarActor(actor);

        assertFalse(pelicula.agregarActor(actor));
        assertEquals(1, pelicula.getActores().size());
    }

    @Test
    void rechazaUnActorNulo() {
        assertFalse(pelicula.agregarActor(null));
    }

    @Test
    void noPermiteModificarLosActoresDesdeFuera() {
        assertThrows(UnsupportedOperationException.class,
                () -> pelicula.getActores().add(actor));
    }

    @Test
    void elTextoIncluyeEstudioYActores() {
        pelicula.agregarActor(actor);
        String texto = pelicula.toString();

        assertTrue(texto.contains("Summit Entertainment"));
        assertTrue(texto.contains("Ryan Gosling"));
    }
}