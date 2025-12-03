package Modelo.POJOs;

import java.util.ArrayList;


/**
 * Representa el conjunto de consultas pertenecientes a un paciente.
 * Se utiliza para construir una vista completa del historial clínico.
 */
public class Expediente {

    private Paciente paciente;             
    private ArrayList<Consulta> consultas;    

    public Expediente() {
        this.consultas = new ArrayList<>();
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public ArrayList<Consulta> getConsultas() {
        return consultas;
    }

    public void setConsultas(ArrayList<Consulta> consultas) {
        this.consultas = consultas;
    }

    /**
     * Agrega una consulta al expediente.
     */
    public void agregarConsulta(Consulta consulta) {
        this.consultas.add(consulta);
    }

    /**
     * Devuelve la cantidad total de consultas que tiene el paciente.
     */
    public int getTotalConsultas() {
        return consultas.size();
    }
}
