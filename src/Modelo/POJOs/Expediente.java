package POJOs;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa el conjunto de consultas pertenecientes a un paciente.
 * Se utiliza para construir una vista completa del historial clínico.
 */
public class Expediente {

    private String clavePaciente;
    private List<Consulta> listaConsultas;

    public Expediente() {
        this.listaConsultas = new ArrayList<>();
    }

    public String getClavePaciente() {
        return clavePaciente;
    }

    public List<Consulta> getListaConsultas() {
        return listaConsultas;
    }

    public void setClavePaciente(String clavePaciente) {
        this.clavePaciente = clavePaciente;
    }

    public void agregarConsulta(Consulta consulta) {
        listaConsultas.add(consulta);
    }
}
