package Modelo.POJOs;

/**
 * Representa a un paciente dentro del sistema NutriNube.
 * Hereda los atributos básicos desde Persona.
 */
public class Paciente extends Persona {

    private String clavePaciente;
    private int sexo;
    private String telefono;
    private String claveNutriologo;
    private String fechaNacimiento;
    private double altura;

    public Paciente() {
        super();
    }

    public Paciente(String clavePaciente, String claveNutriologo, String nombre, String apellido, 
                    String correo, int sexo, String telefono, String fechaNacimiento, double altura) {
        super();
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        
        this.clavePaciente = clavePaciente;
        this.claveNutriologo = claveNutriologo;
        this.sexo = sexo;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
        this.altura = altura;
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public int getSexo() {
        return sexo;
    }
    
    public String getTelefono() {
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

    public void setTelefono(String telefono) {
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
