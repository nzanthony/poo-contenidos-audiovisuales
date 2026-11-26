package ups.poo.audiovisual.modelo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TransmisionEnVivoTest {
    private TransmisionEnVivo transmision;

    @BeforeEach
    void crearTransmision() {
        transmision = new TransmisionEnVivo("The Game Awards 2026", 180, "Videojuegos", "YouTube");
    }

    @Test
    void agregaUnSegmentoQueCabe() {
        assertTrue(transmision.agregarSegmento("Pre-show", 30));
        assertEquals(1, transmision.getSegmentos().size());
    }

    @Test
    void agregaUnSegmentoQueLlenaElTiempoExacto() {
        transmision.agregarSegmento("Entrega de premios", 170);

        assertTrue(transmision.agregarSegmento("Cierre", 10));
    }

    @Test
    void rechazaUnSegmentoQueExcedeLaDuracion() {
        transmision.agregarSegmento("Entrega de premios", 170);

        assertFalse(transmision.agregarSegmento("Concierto final", 15));
        assertEquals(1, transmision.getSegmentos().size());
    }

    @Test
    void rechazaUnSegmentoQueProvocariaDesbordamiento() {
        transmision.agregarSegmento("Pre-show", 30);

        assertFalse(transmision.agregarSegmento("Segmento enorme", Integer.MAX_VALUE));
    }

    @Test
    void calculaLaDuracionDeLaAgenda() {
        transmision.agregarSegmento("Pre-show", 30);
        transmision.agregarSegmento("Entrega de premios", 120);

        assertEquals(150, transmision.calcularDuracionAgenda());
        assertEquals(30, transmision.getMinutosDisponibles());
    }

    @Test
    void laAgendaVaciaDuraCero() {
        assertEquals(0, transmision.calcularDuracionAgenda());
    }

    @Test
    void rechazaUnaDuracionMenorQueLaAgenda() {
        transmision.agregarSegmento("Entrega de premios", 150);

        assertFalse(transmision.cambiarDuracion(100));
        assertEquals(180, transmision.getDuracionEnMinutos());
    }

    @Test
    void aceptaUnaDuracionIgualALaAgenda() {
        transmision.agregarSegmento("Entrega de premios", 150);

        assertTrue(transmision.cambiarDuracion(150));
        assertEquals(150, transmision.getDuracionEnMinutos());
    }

    @Test
    void noPermiteModificarLaAgendaDesdeFuera() {
        assertThrows(UnsupportedOperationException.class,
                () -> transmision.getSegmentos().clear());
    }
}