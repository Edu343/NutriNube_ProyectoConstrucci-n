package Modelo.POJOs;

/**
 * Clase base que representa a una persona dentro del sistema.
 * Contiene atributos comunes para pacientes y nutriólogos.
 */
public class Persona {

    protected String nombre;
    protected String apellido;
    protected String correo;

    public Persona() {
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
