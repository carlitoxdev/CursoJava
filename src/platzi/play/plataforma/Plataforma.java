package platzi.play.plataforma;

import platzi.play.contenido.*;
import platzi.play.excepcion.PeliculaExistenteException;
import platzi.play.util.FileUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Plataforma {
    /** clase15 - Listas **/
    private String nombre;
    private List<Contenido> contenido;
    /*** clase 24 Map - permite almacer datos clave/valor */
    private Map<Contenido, Integer> visualizaciones;

    public Plataforma(String nombre) {
        this.nombre = nombre;
        this.contenido = new ArrayList<>();
        this.visualizaciones = new HashMap<>();
    }

    public void agregar(Contenido elemento) {
        Contenido contenido = this.buscarPorTitulo(elemento.getTitulo());

        if (contenido != null) {
            throw new PeliculaExistenteException(elemento.getTitulo());
        }
        FileUtils.escribirContenido(elemento);
        this.contenido.add(elemento);
    }

    public void reproducir(Contenido contenido) {
        int conteoActual = visualizaciones.getOrDefault(contenido, 0);

        System.out.println(contenido.getTitulo() + " ha sido reproducido " + conteoActual + " veces.");

        // visualizaciones.put(contenido, conteoActual + 1);
        this.contarVisualizaciones(contenido);
        contenido.reproducir();
    }

    private void contarVisualizaciones(Contenido contenido) {
        int conteoActual = visualizaciones.getOrDefault(contenido, 0);
        visualizaciones.put(contenido, conteoActual + 1);
    }

    /* clase19 - streams y lambdas */
    // lambdas: forma corta de escribir un metodo
    public List<String> getTitulos () {
        //for (Contenido pelicula : contenido) {
        //    System.out.println(pelicula.getTitulo());
        //}

        // contenido.forEach(pelicula -> System.out.println(pelicula.getTitulo()));

        // usando streams
        // return contenido.stream().map(contenido -> contenido.getTitulo());
        // otra forma de hacerlo - metodo de referencia: s llama directamente de la clase
        return contenido.stream().map(Contenido::getTitulo).toList();
    }

    public List<ResumenContenido> getResumenes() {
        return contenido.stream()
                .map(c -> new ResumenContenido(c.getTitulo(), c.getDuracion(), c.getGenero()))
                .toList();
    }

    public void eliminar(Contenido elemento) {
        this.contenido.remove(elemento);
    }

    public Contenido buscarPorTitulo(String titulo) {
        /* for (Contenido pelicula: contenido) {
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

    public List<Contenido> buscarPorGenero(Genero genero) {
        return contenido.stream()
                .filter(contenido -> contenido.getGenero().equals(genero))
                .toList();
    }

    public List<Contenido> getPopulares(int cantidad) {
        return contenido.stream()
                .sorted(Comparator.comparingDouble(Contenido::getCalificacion).reversed())
                .limit(cantidad)
                .toList();
    }

    public List<Pelicula> getPeliculas() {
        return contenido.stream()
                .filter(contenido -> contenido instanceof Pelicula)
                .map(contenidoFiltrado -> (Pelicula) contenidoFiltrado)
                .toList();
    }

    public List<Documental> getDocumental() {
        return contenido.stream()
                .filter(contenido -> contenido instanceof Documental)
                .map(contenidoFiltrado -> (Documental) contenidoFiltrado)
                .toList();
    }

    public List<Promocionable> getContenidoPromocionable() {
        return contenido.stream()
                .filter(contenido -> contenido instanceof Promocionable)
                .map(contenidoProm -> (Promocionable) contenidoProm)
                .toList();
    }

    public int getDuracionTotal () {
        return contenido.stream()
                .mapToInt(Contenido::getDuracion)
                .sum();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Contenido> getContenido() {
        return contenido;
    }
}
