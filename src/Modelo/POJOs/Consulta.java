package POJOs;

/**
 * Representa una consulta realizada por un paciente.
 * Contiene datos clínicos básicos, información antropométrica,
 * datos de anamnesis, cálculo de calorías y macronutrientes.
 */
public class Consulta {

    private String claveConsulta;
    private String clavePaciente;
    private String claveNutriologo;
    private String fechaVisita;
    
    private int edad;
    private double altura;
    
    private AnamnesisData anamnesisData;

    private CaloriasCalculo totalCalorias;

    private Macronutrientes macronutrientes;

    public Consulta() {
    }

    public String getClaveConsulta() {
        return claveConsulta;
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public String getFechaVisita() {
        return fechaVisita;
    }

    public String getClaveNutriologo() {
        return claveNutriologo;
    }

    public CaloriasCalculo getTotalCalorias() {
        return totalCalorias;
    }

    public AnamnesisData getAnamnesisData() {
        return anamnesisData;
    }

    public int getEdad() {
        return edad;
    }

    public double getAltura() {
        return altura;
    }

    public Macronutrientes getMacronutrientes() {
        return macronutrientes;
    }

    public void setClaveConsulta(String claveConsulta) {
        this.claveConsulta = claveConsulta;
    }

    public void setClavePaciente(String clavePaciente) {
        this.clavePaciente = clavePaciente;
    }

    public void setFechaVisita(String fechaVisita) {
        this.fechaVisita = fechaVisita;
    }

    public void setClaveNutriologo(String claveNutriologo) {
        this.claveNutriologo = claveNutriologo;
    }

    public void setTotalCalorias(CaloriasCalculo totalCalorias) {
        this.totalCalorias = totalCalorias;
    }

    public void setAnamnesisData(AnamnesisData anamnesisData) {
        this.anamnesisData = anamnesisData;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public void setMacronutrientes(Macronutrientes macronutrientes) {
        this.macronutrientes = macronutrientes;
    }
}
