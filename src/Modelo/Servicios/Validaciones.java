package Modelo.Servicios;


import Modelo.POJOs.Nutriologo;
import Modelo.POJOs.Paciente;
import Modelo.POJOs.Consulta;

/**
 * Servicio encargado de validar reglas de negocio para las
 * entidades del sistema antes de ser procesadas o almacenadas.
 */
public class Validaciones {

    /**
     * Valida que los datos esenciales de un paciente estén completos.
     */
    public boolean isValidPaciente(Paciente paciente) {

        if (paciente.getNombre() == null || paciente.getNombre().trim().isEmpty()) return false;
        if (paciente.getApellido() == null || paciente.getApellido().trim().isEmpty()) return false;
        if (paciente.getAltura() <= 0) return false;

        return true;
    }

    /**
     * Valida los datos principales del nutriólogo, excluyendo la contraseña
     * ya que ésta se procesa mediante hashing.
     */
    public boolean isValidNutriologo(Nutriologo nutriologo) {

        if (nutriologo.getClaveNutriologo() == null ||
            nutriologo.getClaveNutriologo().length() != 6) return false;

        if (nutriologo.getCorreo() == null ||
            nutriologo.getCorreo().trim().isEmpty()) return false;

        if (nutriologo.getNombre() == null ||
            nutriologo.getNombre().trim().isEmpty()) return false;

        return true;
    }

    /**
     * Valida los campos principales de una consulta.
     */
    public boolean isValidConsulta(Consulta consulta) {

        if (consulta.getCaloriasCalculo().getPeso() <= 0) return false;

        if (consulta.getClavePaciente() == null ||
            consulta.getClavePaciente().trim().isEmpty()) return false;

        return true;
    }
}