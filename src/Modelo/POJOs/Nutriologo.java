package Modelo.POJOs;

/**
 * Representa a un nutriólogo registrado en el sistema.
 * Hereda nombre, apellido y correo desde Persona.
 */
public class Nutriologo extends Persona {

    private String claveNutriologo;
    private String hashContrasena;
    private String saltContrasena;

    public Nutriologo() {
        super();
    }

    public String getClaveNutriologo() {
        return claveNutriologo;
    }

    public String getHashContrasena() {
        return hashContrasena;
    }

    public String getSaltContrasena() {
        return saltContrasena;
    }

    public void setClaveNutriologo(String claveNutriologo) {
        this.claveNutriologo = claveNutriologo;
    }

    public void setHashContrasena(String hashContrasena) {
        this.hashContrasena = hashContrasena;
    }

    public void setSaltContrasena(String saltContrasena) {
        this.saltContrasena = saltContrasena;
    }
}
