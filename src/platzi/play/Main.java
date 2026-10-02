package platzi.play;

import platzi.play.contenido.*;
import platzi.play.excepcion.PeliculaExistenteException;
import platzi.play.plataforma.Plataforma;
import platzi.play.util.FileUtils;
import platzi.play.util.ScannerUtils;

import java.util.List;

public class Main {
    /**** Clase 11 - Inmutabilidad - static final *****/
    // final - utilizada para la inmutabilidad - constante
    public static final String NOMBRE_PLATAFORMA = "PLATZI PLAY";
    public static final String VERSION = "1.0.0";
    public static final int AGREGAR = 1;
    public static final int MOSTRAR_TODO = 2;
    public static final int BUSCAR_POR_TITULO = 3;
    public static final int BUSCAR_POR_GENERO = 4;
    public static final int VER_POPULARES = 5;
    public static final int REPRODUCIR = 6;
    public static final int BUSCAR_POR_TIPO = 7;
    public static final int ELIMINAR = 8;
    public static final int SALIR = 9;


    // El metodo Main debe tener esta estructura siempre
    public static void main(String[] args) {
        System.out.println("******* "+ NOMBRE_PLATAFORMA + " v" + VERSION + "*******");
        Plataforma plataforma = new Plataforma(NOMBRE_PLATAFORMA);

        /** Clase 17 - Switch **/
        cargarPeliculas(plataforma);

        /** clase 20 - Ordenar y transformar listas con streams **/
        System.out.println("Mas de " + plataforma.getDuracionTotal() + " minutos de contenido! \n");

        /* clase 30 - Interfaces */
        plataforma.getContenidoPromocionable().forEach(promocionable -> System.out.println(promocionable.promocionar()));

        while (true) {
            int opcionElegida = ScannerUtils.capturarNumero("""
                    1. Agregar contenido
                    2. Mostrar todo
                    3. Buscar por titulo
                    4. Buscar por genero
                    5. Ver populares
                    6. Reproducir
                    7. Buscar por tipo
                    8. Eliminar
                    9. Salir
                    """);
            System.out.println("Opción elegida: " + opcionElegida);

            switch (opcionElegida) {
                case AGREGAR -> {
                    int tipoContenido = ScannerUtils.capturarNumero("Que tipo de contenido quieres agregar?\n 1. Pelicua\n 2. Documental");

                    String titulo = ScannerUtils.capturarTexto("Nombre del contenido");
                    int duracion = ScannerUtils.capturarNumero("Duración del contenido");
                    // Genero genero = Genero.valueOf(ScannerUtils.capturarTexto("Genero del contenido"));
                    Genero genero = ScannerUtils.capturarGenero("Genero del contenido");
                    double calificacion = ScannerUtils.capturarDecimal("Calificación del contenido");

                    /* clase 27 - Herencia */
                    try {
                        if (tipoContenido == 1) {
                            plataforma.agregar(new Pelicula(titulo, duracion, genero, calificacion));
                        } else {
                            String narrador = ScannerUtils.capturarTexto("Narrador del documental: ");
                            plataforma.agregar(new Documental(titulo, duracion, genero, calificacion, narrador));
                        }

                    } catch (PeliculaExistenteException e) {
                        System.out.println(e.getMessage());
                    }
                }
                case MOSTRAR_TODO -> {
                    // List<String> titulos = plataforma.getTitulos();
                    List<ResumenContenido> contenidosResumidos = plataforma.getResumenes();
                    // titulos.forEach(System.out::println);
                    contenidosResumidos.forEach(resumen -> System.out.println(resumen.toString()));
                }
                case BUSCAR_POR_TITULO -> {
                    String nombreBuscado = ScannerUtils.capturarTexto("Nombre del contenido a buscar: ");
                    Contenido contenido = plataforma.buscarPorTitulo(nombreBuscado);

                    if (contenido != null) {
                        System.out.println(contenido.obtenerFichaTecnica());
                    } else {
                        System.out.println(nombreBuscado + " no existe dentro de " + plataforma.getNombre());
                    }
                }
                case BUSCAR_POR_GENERO -> {
                    // Genero generoBuscado = ScannerUtils.capturarTexto("Genero del contenido a buscar: ");
                    // Genero generoBuscado = Genero.valueOf(ScannerUtils.capturarTexto("Genero del contenido a buscar: "));
                    Genero generoBuscado = ScannerUtils.capturarGenero("Genero del contenido a buscar");

                    List<Contenido> contenidoPorGenero = plataforma.buscarPorGenero(generoBuscado);
                    System.out.println(contenidoPorGenero.size() + " resultados encontrados para " + generoBuscado);

                    contenidoPorGenero.forEach(contenido -> System.out.println(contenido.obtenerFichaTecnica() + "\n"));
                }
                case VER_POPULARES -> {
                    int cantidad = ScannerUtils.capturarNumero("Cantidad de resultados a mostrar: ");
                    List<Contenido> contenidoPopulares = plataforma.getPopulares(cantidad);
                    contenidoPopulares.forEach(contenido -> System.out.println(contenido.obtenerFichaTecnica() + "\n"));
                }
                case REPRODUCIR -> {
                    String nombre = ScannerUtils.capturarTexto("Nombre del contenido a reproducir: ");
                    Contenido contenido = plataforma.buscarPorTitulo(nombre);

                    if (contenido != null) {
                        plataforma.reproducir(contenido);
                    } else {
                        System.out.println(nombre + " no existe.");
                    }
                }
                case BUSCAR_POR_TIPO -> {
                    int tipoDeContenido = ScannerUtils.capturarNumero("Qué tipo de contenido quieres agregar?\n 1. Pelicula\n2.Documental");

                    if (tipoDeContenido == 1) {
                       List<Pelicula> peliculas = plataforma.getPeliculas();
                       peliculas.forEach(pelicula -> System.out.println(pelicula.obtenerFichaTecnica() + "\n"));
                    } else {
                        List<Documental> documentales = plataforma.getDocumental();
                        documentales.forEach(documental -> System.out.println(documental.obtenerFichaTecnica() + "\n"));
                    }
                }
                case ELIMINAR -> {
                    String nombreAEliminar = ScannerUtils.capturarTexto("Nombre del contenido a eliminar: ");
                    Contenido contenido = plataforma.buscarPorTitulo(nombreAEliminar);

                    if (contenido != null) {
                        plataforma.eliminar(contenido);
                        System.out.println(nombreAEliminar + " eliminada!");
                    } else {
                        System.out.println(nombreAEliminar + " no existe dentro de " + plataforma.getNombre());
                    }
                }
                case SALIR -> System.exit(0);
            }

        }

        /* Clases 6,7 */
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Cuál es tu nombre?");
        // String nombre = scanner.nextLine();

        // System.out.println(nombre + "Cuantos años tienes?");
        // int edad = scanner.nextInt();

        // System.out.println("Hola " + nombre + ", esto es Platzi Play");
        // System.out.println(nombre + "Puedes ver contenido +" + edad);

        /* ** clase 10 - Atributos y Metodos estaticos ***/
        // String titulo = ScannerUtils.capturarTexto("Nombre del contenido");
        // String genero = ScannerUtils.capturarTexto("Genero del contenido");
        // int duracion = ScannerUtils.capturarNumero("Duración del contenido");
        // double calificacion = ScannerUtils.capturarDecimal("Calificación del contenido");

        /* ** clase 8 - Datos primitivos y por referencia ***/
        // Contenido pelicula = new Contenido(nombre, duracion, genero);
        // Contenido pelicula = new Contenido(titulo, duracion, genero, calificacion);
        // Contenido pelicula2 = new Contenido("F1 The Movie",220,"Accion", 4.5);

        // String titulo = pelicula.getNombre();
        // pelicula.fechaEstreno = LocalDate.of(2018,10, 21);
        // String genero = pelicula.getGenero();
        // pelicula.calificar(calificacion);

        /* *** Clase 9 - Casting de tipo de datos *****/
        // Casting implicito y explicito
        // impicito: lo hace java
        // explicito: nosotros lo hacemos
        // pelicula.duracion = duracion;
        // duracion = pelicula.getDuracion();
        // calificacion = pelicula.getCalificacion();
        // long duracionLong = duracion;
        // int calificacionInt = (int)calificacion;

        // capturar long a traves de un String
        // long numeroDePremios = Long.parseLong("25");

        // System.out.println("Long a String: " + numeroDePremios);

        // System.out.println(pelicula.obtenerFichaTecnica());

        // Usuario usuario = new Usuario("Juan", "juan@mail.com");
        // usuario.nombre = "Juan";
        // usuario.fechaRegistro = LocalDateTime.now();

        // System.out.println(usuario.fechaRegistro);
        // usuario.ver(pelicula);

        /* * clase15 - Listas **/
        // Plataforma plataforma = new Plataforma(NOMBRE_PLATAFORMA);

        // plataforma.agregar(pelicula);
        // plataforma.agregar(pelicula2);
        // System.out.println("Numero de elementos en la plataforma: " + plataforma.getContenido().size());
        // plataforma.eliminar(pelicula2);

        // plataforma.mostrarTitulos();

        /* * clase16 - Asociacion | Agregacion | Composicion **/

        // usuario.ver(pelicula);
    }

    private static void cargarPeliculas(Plataforma plataforma) {
        plataforma.getContenido().addAll(FileUtils.leerContenido());

        // plataforma.agregar(new Contenido("Shrek", 90, Genero.ANIMADA, 0));
        // plataforma.agregar(new Contenido("Inception", 148, Genero.CIENCIA_FICCION, 0));
        // plataforma.agregar(new Contenido("Titanic", 195, Genero.DRAMA, 4.6));
        // plataforma.agregar(new Contenido("John Wick", 101, Genero.ACCION, 0));
        // plataforma.agregar(new Contenido("El Conjuro", 112, Genero.TERROR, 3.0));
        // plataforma.agregar(new Contenido("Coco", 105, Genero.ANIMADA, 4.7));
        // plataforma.agregar(new Contenido("Interstellar", 169, Genero.CIENCIA_FICCION, 5));
        // plataforma.agregar(new Contenido("Joker", 122, Genero.DRAMA, 0));
        // plataforma.agregar(new Contenido("Toy Story", 81, Genero.ANIMADA, 4.5));
        // plataforma.agregar(new Contenido("Avengers: Endgame", 181, Genero.ACCION, 3.9));
    }
}
