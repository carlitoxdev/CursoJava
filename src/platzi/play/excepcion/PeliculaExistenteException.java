package platzi.play.excepcion;

public class PeliculaExistenteException extends RuntimeException {
    public PeliculaExistenteException(String titulo) {
        super("La pelicula " + titulo + " ya existe!");
    }
}
