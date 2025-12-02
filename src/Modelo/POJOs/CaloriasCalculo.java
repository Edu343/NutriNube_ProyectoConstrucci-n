package Modelo.POJOs;

/**
 * Contiene los datos necesarios para calcular el total de calorías
 * y la razón de consulta asociada.
 */
public class CaloriasCalculo {

    private double peso;
    private int nivelActividadFisica;
    private double calorias;
    private int razonConsulta;

    public CaloriasCalculo() {
    }

    public CaloriasCalculo(double peso, int actividad, int razon, double calorias) {
        this.peso = peso;
        this.nivelActividadFisica = actividad;
        this.razonConsulta = razon;
        this.calorias = calorias;
    }

    public double getPeso() {
        return peso;
    }

    public int getNivelActividadFisica() {
        return nivelActividadFisica;
    }

    public int getRazonConsulta() {
        return razonConsulta;
    } 

    public double getCalorias() {
        return calorias;
    }
    
    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setNivelActividadFisica(int nivelActividadFisica) {
        this.nivelActividadFisica = nivelActividadFisica;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public void setRazonConsulta(int razonConsulta) {
        this.razonConsulta = razonConsulta;
    }
}
