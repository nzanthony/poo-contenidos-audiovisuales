package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DocumentalTest {
    private Documental documental;
    private Investigador investigador;

    @BeforeEach
    void crearDatos() {
        documental = new Documental("Cosmos", 60, "Ciencia", "Universo");
        investigador = new Investigador("Neil deGrasse Tyson", "Estadounidense", "Astrofisico", 68);
    }

    @Test
    void agregaUnInvestigadorNuevo() {
        assertTrue(documental.agregarInvestigador(investigador));
        assertEquals(1, documental.getInvestigadores().size());
    }

    @Test
    void noAgregaElMismoInvestigadorDosVeces() {
        documental.agregarInvestigador(investigador);

        assertFalse(documental.agregarInvestigador(investigador));
    }

    @Test
    void rechazaUnInvestigadorNulo() {
        assertFalse(documental.agregarInvestigador(null));
    }

    @Test
    void elTextoIncluyeTemaEInvestigadores() {
        documental.agregarInvestigador(investigador);
        String texto = documental.toString();

        assertTrue(texto.contains("Universo"));
        assertTrue(texto.contains("Neil deGrasse Tyson"));
    }
}