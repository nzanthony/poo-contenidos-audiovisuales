package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class PeliculaTest {

    @Test
    void agregaUnActorNuevo() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit Entertainment");
        Actor actor = new Actor("Ryan Gosling", "Canadiense", 30);

        assertTrue(pelicula.agregarActor(actor));
    }

    @Test
    void noAgregaElMismoActorDosVeces() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit Entertainment");
        Actor actor = new Actor("Ryan Gosling", "Canadiense", 30);
        pelicula.agregarActor(actor);

        assertFalse(pelicula.agregarActor(actor));
    }
}