package ups.poo.audiovisual.modelo.persistencia;

import java.io.IOException;
import java.util.List;

import ups.poo.audiovisual.modelo.CatalogoContenidos;

// Abstraccion del almacenamiento para que el controlador no dependa de CSV ni de otro medio concreto
public interface RepositorioContenidos {
    CatalogoContenidos cargar() throws IOException;

    void guardar(CatalogoContenidos catalogo) throws IOException;

    // Problemas de la ultima carga que no impidieron terminarla
    List<String> getAdvertencias();

    // Indica si se descartaron filas porque guardar despues las borraria de los archivos
    boolean hayFilasOmitidas();
}