package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

// Persona es abstracta y por eso sus reglas se prueban con las subclases
class PersonaTest {

    @Test
    void aceptaLosLimitesDeLaEdadYRechazaLosValoresFuera() {
        assertDoesNotThrow(() -> new Actor("Bebe", "Ecuatoriana", 0));
        assertDoesNotThrow(() -> new Actor("Abuelo", "Ecuatoriana", 120));
        assertThrows(IllegalArgumentException.class, () -> new Actor("Nadie", "Ecuatoriana", -1));
        assertThrows(IllegalArgumentException.class, () -> new Actor("Nadie", "Ecuatoriana", 121));
    }

    @Test
    void noPermiteTextosVacios() {
        assertThrows(IllegalArgumentException.class, () -> new Actor(" ", "Ecuatoriana", 30));
        assertThrows(IllegalArgumentException.class, () -> new Actor("Ryan Gosling", "", 30));
        assertThrows(IllegalArgumentException.class, () -> new Investigador("Neil", "Estadounidense", "", 68));
        assertThrows(IllegalArgumentException.class, () -> new Artista("Ramon Ayala", "Puertorriquena", 49, null));
    }

    @Test
    void elSetterRechazaUnaEdadInvalidaYConservaLaAnterior() {
        Actor actor = new Actor("Ryan Gosling", "Canadiense", 30);

        assertThrows(IllegalArgumentException.class, () -> actor.setEdad(150));
        assertEquals(30, actor.getEdad());
    }
}
