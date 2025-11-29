package Modelo.POJOs;


/**
 * Define la meta nutricional (porcentajes o valores objetivo)
 * que se utilizará para distribuir las calorías.
 */
public class MetaNutricional {

    private double carbohidratosObjetivo;
    private double lipidosObjetivo;
    private double proteinasObjetivo;

    public MetaNutricional(double carbohidratosObjetivo, double lipidosObjetivo, double proteinasObjetivo) {
        this.carbohidratosObjetivo = carbohidratosObjetivo;
        this.lipidosObjetivo = lipidosObjetivo;
        this.proteinasObjetivo = proteinasObjetivo;
    }

    

    public double getCarbohidratosObjetivo() {
        return carbohidratosObjetivo;
    }

    public double getLipidosObjetivo() {
        return lipidosObjetivo;
    }

    public double getProteinasObjetivo() {
        return proteinasObjetivo;
    }

    public void setCarbohidratosObjetivo(double carbohidratosObjetivo) {
        this.carbohidratosObjetivo = carbohidratosObjetivo;
    }

    public void setLipidosObjetivo(double lipidosObjetivo) {
        this.lipidosObjetivo = lipidosObjetivo;
    }

    public void setProteinasObjetivo(double proteinasObjetivo) {
        this.proteinasObjetivo = proteinasObjetivo;
    }
}
