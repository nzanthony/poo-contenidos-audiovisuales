package ups.poo.audiovisual.app;

import java.nio.file.Path;

import ups.poo.audiovisual.controlador.ControladorContenidos;
import ups.poo.audiovisual.modelo.persistencia.RepositorioContenidos;
import ups.poo.audiovisual.modelo.persistencia.RepositorioCsv;
import ups.poo.audiovisual.vista.VistaConsola;

// Punto de entrada que crea las dependencias y se las entrega al controlador
public class Main {
    public static void main(String[] args) {
        VistaConsola vista = new VistaConsola();
        // La ruta es relativa a la carpeta desde donde se ejecuta el programa
        RepositorioContenidos repositorio = new RepositorioCsv(Path.of("datos"));
        ControladorContenidos controlador = new ControladorContenidos(vista, repositorio);
        controlador.iniciar();
    }
}