package platzi.play.util;

import platzi.play.contenido.Genero;

import java.util.Scanner;

public class ScannerUtils {
    // static: permite definir que este atributo no depende de ningun objeto
    // si no de la clase en general
    public static final Scanner SCANNER = new Scanner(System.in);

    public static String capturarTexto(String nombre) {
        System.out.println(nombre + ": ");
        return SCANNER.nextLine();
    }

    public static int capturarNumero(String mensaje) {
        System.out.println(mensaje + ": ");

        while (!SCANNER.hasNextInt()) {
            System.out.println("Dato no aceptado. " + mensaje + ": ");
            SCANNER.next();
        }

        int dato = SCANNER.nextInt();

        // sirve para descartar el enter que da el usuario
        SCANNER.nextLine();
        return dato;
    }

    public static double capturarDecimal(String mensaje) {
        System.out.println(mensaje + ": ");

        while (!SCANNER.hasNextDouble()) {
            System.out.println("Dato no aceptado. " + mensaje + ": ");
            SCANNER.next();
        }

        double dato = SCANNER.nextDouble();
        SCANNER.nextLine();
        return dato;
    }

    public static Genero capturarGenero(String mensaje) {
        while (true) {
            System.out.println(mensaje + "Opciones:");

            for (Genero genero : Genero.values()) {
                System.out.println("- " + genero.name());
            }

            System.out.println("Cuál quieres: ");
            // String entrada = capturarTexto(mensaje);
            String entrada = SCANNER.nextLine();

            try {
                return Genero.valueOf(entrada.toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Genero no aceptado. " + mensaje + ": ");
            }
        }
    }
}
