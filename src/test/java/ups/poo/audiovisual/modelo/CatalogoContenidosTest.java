package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogoContenidosTest {
    private CatalogoContenidos catalogo;

    @BeforeEach
    void crearCatalogo() {
        catalogo = new CatalogoContenidos();
    }

    @Test
    void asignaIdsConsecutivosDesdeCero() {
        Pelicula primera = new Pelicula("La La Land", 128, "Musical", "Summit");
        Pelicula segunda = new Pelicula("Crazy, Stupid, Love", 118, "Comedia", "Warner");
        catalogo.agregarContenido(primera);
        catalogo.agregarContenido(segunda);

        assertEquals(0, primera.getId());
        assertEquals(1, segunda.getId());
    }

    @Test
    void noReutilizaElIdDeUnContenidoEliminado() {
        Pelicula primera = new Pelicula("A", 100, "Drama", "Estudio");
        catalogo.agregarContenido(primera);
        catalogo.eliminarContenido(primera);
        Pelicula segunda = new Pelicula("B", 100, "Drama", "Estudio");
        catalogo.agregarContenido(segunda);

        assertEquals(1, segunda.getId());
    }

    @Test
    void asignaIdsALasPersonas() {
        Actor actor = new Actor("Ryan Gosling", "Canadiense", 30);
        Artista artista = new Artista("Ramon Ayala", "Puertorriqueña", 49, "Daddy Yankee");
        catalogo.registrarPersona(actor);
        catalogo.registrarPersona(artista);

        assertEquals(0, actor.getId());
        assertEquals(1, artista.getId());
    }

    @Test
    void buscaUnContenidoPorSuId() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");
        catalogo.agregarContenido(pelicula);

        assertSame(pelicula, catalogo.buscarContenidoPorId(0));
    }

    @Test
    void devuelveNullSiElIdNoExiste() {
        assertNull(catalogo.buscarContenidoPorId(99));
    }

    @Test
    void eliminaUnContenido() {
        Pelicula pelicula = new Pelicula("La La Land", 128, "Musical", "Summit");
        catalogo.agregarContenido(pelicula);

        assertTrue(catalogo.eliminarContenido(pelicula));
        assertTrue(catalogo.getContenidos().isEmpty());
    }

    @Test
    void filtraLasPersonasPorTipo() {
        catalogo.registrarPersona(new Actor("Ryan Gosling", "Canadiense", 30));
        catalogo.registrarPersona(new Investigador("Neil deGrasse Tyson", "Estadounidense", "Astrofisico", 68));
        catalogo.registrarPersona(new Actor("Emma Stone", "Estadounidense", 28));

        assertEquals(2, catalogo.getPersonas(Actor.class).size());
        assertEquals(1, catalogo.getPersonas(Investigador.class).size());
        assertTrue(catalogo.getPersonas(Artista.class).isEmpty());
    }

    @Test
    void noPermiteModificarLosContenidosDesdeFuera() {
        assertThrows(UnsupportedOperationException.class,
                () -> catalogo.getContenidos().add(new SerieDeTV("X", 60, "Drama")));
    }
}