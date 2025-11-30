package Modelo.POJOs;


/**
 * Representa la distribución de macronutrientes para una consulta.
 * Modelo simple utilizado para cálculos nutricionales.
 */
public class Macronutrientes {

    private double carbohidratos;
    private double proteinas;
    private double lipidos;

    public Macronutrientes() {
    }

    public double getCarbohidratos() {
        return carbohidratos;
    }

    public double getProteinas() {
        return proteinas;
    }

    public double getLipidos() {
        return lipidos;
    }

    public void setCarbohidratos(double carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public void setProteinas(double proteinas) {
        this.proteinas = proteinas;
    }

    public void setLipidos(double lipidos) {
        this.lipidos = lipidos;
    }
}
