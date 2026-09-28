package platzi.play.plataforma;

import platzi.play.contenido.Genero;
import platzi.play.contenido.Pelicula;
import platzi.play.contenido.ResumenContenido;
import platzi.play.excepcion.PeliculaExistenteException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plataforma {
    /** clase15 - Listas **/
    private String nombre;
    private List<Pelicula> contenido;
    /*** clase 24 Map - permite almacer datos clave/valor */
    private Map<Pelicula, Integer> visualizaciones; 

    public Plataforma(String nombre) {
        this.nombre = nombre;
        this.contenido = new ArrayList<>();
        this.visualizaciones = new HashMap<>();
    }

    public void agregar(Pelicula elemento) {
        Pelicula contenido = this.buscarPorTitulo(elemento.getTitulo());

        if (contenido != null) {
            throw new PeliculaExistenteException(elemento.getTitulo());
        }
        this.contenido.add(elemento);
    }

    public void reproducir(Pelicula contenido) {
        int conteoActual = visualizaciones.getOrDefault(contenido, 0);

        System.out.println(contenido.getTitulo() + "ha sido reproducido " + conteoActual + " veces.");

        // visualizaciones.put(contenido, conteoActual + 1);
        this.contarVisualizaciones(contenido);
        contenido.reproducir();
    }

    private void contarVisualizaciones(Pelicula contenido) {
        int conteoActual = visualizaciones.getOrDefault(contenido, 0);
        visualizaciones.put(contenido, conteoActual + 1);
    }

    /* clase19 - streams y lambdas */
    // lambdas: forma corta de escribir un metodo
    public List<String> getTitulos () {
        //for (Pelicula pelicula : contenido) {
        //    System.out.println(pelicula.getTitulo());
        //}

        // contenido.forEach(pelicula -> System.out.println(pelicula.getTitulo()));

        // usando streams
        // return contenido.stream().map(contenido -> contenido.getTitulo());
        // otra forma de hacerlo - metodo de referencia: s llama directamente de la clase
        return contenido.stream().map(Pelicula::getTitulo).toList();
    }

    public List<ResumenContenido> getResumenes() {
        return contenido.stream()
                .map(c -> new ResumenContenido(c.getTitulo(), c.getDuracion(), c.getGenero()))
                .toList();
    }

    public void eliminar(Pelicula elemento) {
        this.contenido.remove(elemento);
    }

    public Pelicula buscarPorTitulo(String titulo) {
        /* for (Pelicula pelicula: contenido) {
            if (pelicula.getTitulo().equalsIgnoreCase(titulo)) {
                return pelicula;
            }
        } */

        return contenido.stream()
                .filter(contenido -> contenido.getTitulo().equalsIgnoreCase(titulo))
                .findFirst()
                .orElse(null);

        // return null;
    }

    public List<Pelicula> buscarPorGenero(Genero genero) {
        return contenido.stream()
                .filter(contenido -> contenido.getGenero().equals(genero))
                .toList();
    }

    public List<Pelicula> getPopulares(int cantidad) {
        return contenido.stream()
                .sorted(Comparator.comparingDouble(Pelicula::getCalificacion).reversed())
                .limit(cantidad)
                .toList();
    }

    public int getDuracionTotal () {
        return contenido.stream()
                .mapToInt(Pelicula::getDuracion)
                .sum();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Pelicula> getContenido() {
        return contenido;
    }
}
