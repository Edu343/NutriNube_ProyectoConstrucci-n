package POJOs;


/**
 * Contiene los datos necesarios para calcular el total de calorías
 * y la razón de consulta asociada.
 */
public class CaloriasCalculo {

    private String sexo;
    private double peso;
    private int nivelActividadFisica;
    private double calorias;
    private int razonConsulta;

    public CaloriasCalculo() {
    }

    public double getPeso() {
        return peso;
    }

    public int getNivelActividadFisica() {
        return nivelActividadFisica;
    }

    public double getTotalCalorias() {
        return calorias;
    }

    public int getRazonConsulta() {
        return razonConsulta;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setNivelActividadFisica(int nivelActividadFisica) {
        this.nivelActividadFisica = nivelActividadFisica;
    }

    public void setTotalCalorias(double totalCalorias) {
        this.calorias = totalCalorias;
    }

    public void setRazonConsulta(int razonConsulta) {
        this.razonConsulta = razonConsulta;
    }
}
