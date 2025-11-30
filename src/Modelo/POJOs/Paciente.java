package Modelo.POJOs;

/**
 * Representa a un paciente dentro del sistema NutriNube.
 * Hereda los atributos básicos desde Persona.
 */
public class Paciente extends Persona {

    private String clavePaciente;
    private int sexo;
    private int telefono;
    private String claveNutriologo;
    private String fechaNacimiento;
    private double altura;

    public Paciente() {
        super();
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public int getSexo() {
        return sexo;
    }
    
    public int getTelefono() {
        return telefono;
    }

    public String getClaveNutriologo() {
        return claveNutriologo;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public double getAltura() {
        return altura;
    }

    public void setClavePaciente(String clavePaciente) {
        this.clavePaciente = clavePaciente;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setClaveNutriologo(String claveNutriologo) {
        this.claveNutriologo = claveNutriologo;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }
}
