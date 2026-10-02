package platzi.play.contenido;

import java.time.LocalDate;

public abstract class Contenido {
    // Definiendo atributos
    private String titulo;
    private String descripcion;
    private int duracion;
    private Genero genero;
    // public int anioEstreno;
    private double calificacion;
    private boolean disponible;
    private LocalDate fechaEstreno;

    /**** Clase 12 - Constructor *****/
    public Contenido(String titulo, int duracion, Genero genero) {
        this.titulo = titulo;
        this.duracion = duracion;
        this.genero = genero;
        this.fechaEstreno = LocalDate.now();
        this.disponible = true;
    }

    public Contenido(String titulo, int duracion, Genero genero, double calificacion) {
        this(titulo, duracion, genero);
        this.calificar(calificacion);
    }

    // clase 9: casting
    // public int duracion;

    /* Clase 29 - Clases y Metodos Abstractos */
    /* public void reproducir () {
        System.out.println("Reproduciendo " + titulo);
    } */
    public abstract void reproducir();

    public String obtenerFichaTecnica () {
        return titulo + " (" + fechaEstreno.getYear() + ")\n" +
                "Genero: " + genero + "\n" +
                "Calificacion: " + calificacion + "/5";
    }

    public void calificar (double calificacion) {
        if (calificacion >= 0 && calificacion <= 5) {
            this.calificacion = calificacion;
        }
    }

    public boolean esPopular () {
        return calificacion >= 4;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracion() {
        return duracion;
    }

    public Genero getGenero() {
        return genero;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public LocalDate getFechaEstreno() { return fechaEstreno; }

    public void setFechaEstreno(LocalDate fechaEstreno) {
        this.fechaEstreno = fechaEstreno;
    }
}
