package ups.poo.audiovisual.vista;
import java.util.Scanner;
import java.util.List;

import ups.poo.audiovisual.modelo.ContenidoAudiovisual;
import ups.poo.audiovisual.modelo.Persona;

public class VistaConsola {
    private Scanner scanner = new Scanner(System.in);

    public void menuPrincipal(){
        System.out.println("=== Catalogo de Contenidos Audiovisuales ===");
        System.out.println("1. Listar contenidos");
        System.out.println("2. Crear contenidos");
        System.out.println("3. Registrar persona");
        System.out.println("4. Agregar a un contenido");
        System.out.println("5. Modificar contenido");
        System.out.println("6. Eliminar contenido");
        System.out.println("0. Salir");
    }

    public void mostrarContenidos(List<ContenidoAudiovisual> contenidos){
        for (ContenidoAudiovisual contenido : contenidos){
            System.out.println(contenido);
            System.out.println();
        }
    }

    public void menuCrearContenidos(){
        System.out.println("=== Crear Contenidos ===");
        System.out.println("1. Pelicula");
        System.out.println("2. Serie de TV");
        System.out.println("3. Documental");
        System.out.println("4. Video musical");
        System.out.println("5. Transmision en vivo");
        System.out.println("0. Volver");
    }

    public void menuRegistrarPersona(){
        System.out.println("=== Registrar Persona ===");
        System.out.println("1. Actor");
        System.out.println("2. Investigador");
        System.out.println("3. Artista");
        System.out.println("0. Volver");
    }

    public void mostrarPersonas(List<? extends Persona> personas){
        for (int i = 0; i < personas.size(); i++) {
            System.out.println((i + 1) + ". " + personas.get(i).getNombre());
        }
    }

    public void menuModificarContenido(){
        System.out.println("=== Modificar contenido ===");
        System.out.println("1. Titulo");
        System.out.println("2. Duracion");
        System.out.println("3. Genero");
        System.out.println("0. Volver");
    }
    
    public int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine();
            try {
                return Integer.parseInt(linea.trim());
            } catch (NumberFormatException e) {
                System.out.println("Error: debe ingresar un numero entero.");
            }
        }
    }

    public int leerEntero(String mensaje, int minimo, int maximo) {
        while (true) {
            int numero = leerEntero(mensaje);
            if (numero >= minimo && numero <= maximo) {
                return numero;
            }
            System.out.println("Error: ingrese un numero entre " + minimo + " y " + maximo + ".");
        }
    }

    public String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                return linea;
            }
            System.out.println("Error: el texto no puede estar vacio.");
        }
    }

    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }

    public void mostrarError(String mensaje){
       System.out.println("Error: " + mensaje);
    }
}