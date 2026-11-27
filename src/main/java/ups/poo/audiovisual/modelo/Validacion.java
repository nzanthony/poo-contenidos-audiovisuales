package ups.poo.audiovisual.modelo;

// Reglas comunes que impiden crear objetos del modelo con datos invalidos
final class Validacion {

    // Constructor privado porque la clase solo agrupa metodos estaticos
    private Validacion() {
    }

    // Cada metodo devuelve el valor recibido para validar y asignar en una sola linea
    static String exigirTexto(String valor, String campo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio");
        }
        return valor;
    }

    static int exigirPositivo(int valor, String campo) {
        if (valor <= 0) {
            throw new IllegalArgumentException(campo + " debe ser mayor que cero");
        }
        return valor;
    }

    static int exigirRango(int valor, int minimo, int maximo, String campo) {
        if (valor < minimo || valor > maximo) {
            throw new IllegalArgumentException(campo + " debe estar entre " + minimo + " y " + maximo);
        }
        return valor;
    }
}
