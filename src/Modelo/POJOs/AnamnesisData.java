package Modelo.POJOs;


/**
 * Representa la información proporcionada por el paciente
 * durante la anamnesis: hábitos, antecedentes, condiciones médicas,
 * medicación, alergias y otros datos relevantes.
 */
public class AnamnesisData {

    private String condicionesMedicas;
    private String medicacion;
    private String historialCirugias;
    private String alergias;
    private String preferenciaComida;
    private String horarioSueno;
    private int nivelEstres;
    private String habitosAlimenticios;
    private String tipoLiquidosConsumidos;
    private double cantidadLiquidoConsumido;
    private String barreraAlimenticia;

    public AnamnesisData() {
    }

    public String getCondicionesMedicas() {
        return condicionesMedicas;
    }

    public String getMedicacion() {
        return medicacion;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getPreferenciaComida() {
        return preferenciaComida;
    }

    public String getHorarioSueno() {
        return horarioSueno;
    }

    public String getHistorialCirugias() {
        return historialCirugias;
    }

    public int getNivelEstres() {
        return nivelEstres;
    }

    public String getHabitoAlimenticio() {
        return habitosAlimenticios;
    }

    public String getTipoLiquidoConsumido() {
        return tipoLiquidosConsumidos;
    }

    public double getCantidadLiquidoConsumido() {
        return cantidadLiquidoConsumido;
    }

    public String getBarreraAlimenticia() {
        return barreraAlimenticia;
    }

    public void setCondicionesMedicas(String condicionesMedicas) {
        this.condicionesMedicas = condicionesMedicas;
    }

    public void setMedicacion(String medicacion) {
        this.medicacion = medicacion;
    }

    public void setAlergias(String alergias) {
        this.alergias = alergias;
    }

    public void setPreferenciaComida(String preferenciaComida) {
        this.preferenciaComida = preferenciaComida;
    }

    public void setHorarioSueno(String horarioSueno) {
        this.horarioSueno = horarioSueno;
    }

    public void setHistorialCirugias(String historialCirugias) {
        this.historialCirugias = historialCirugias;
    }

    public void setNivelEstres(int nivelEstres) {
        this.nivelEstres = nivelEstres;
    }

    public void setHabitoAlimenticio(String habitoAlimenticio) {
        this.habitosAlimenticios = habitoAlimenticio;
    }

    public void setTipoLiquidoConsumido(String tipoLiquidoConsumido) {
        this.tipoLiquidosConsumidos = tipoLiquidoConsumido;
    }

    public void setCantidadLiquidoConsumido(double cantidadLiquidoConsumido) {
        this.cantidadLiquidoConsumido = cantidadLiquidoConsumido;
    }

    public void setBarreraAlimenticia(String barreraAlimenticia) {
        this.barreraAlimenticia = barreraAlimenticia;
    }
}
