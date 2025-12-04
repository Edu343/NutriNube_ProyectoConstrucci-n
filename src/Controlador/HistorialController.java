package Controlador;

import Modelo.Core.Controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Consulta;

/**
* Controlador del historial de consultas de un paciente.
* Coordina navegación, edición, filtrado y eliminación de consultas.
*/
public class HistorialController extends Controller {

    public HistorialController(String tag) {
        super(tag);
    }

    /**
    * Prepara la vista para registrar una nueva consulta
    * asociada al paciente indicado.
    *
    * @param clavePaciente Identificador del paciente seleccionado.
    */
    @Override
    public void handleAgregarConsulta(String clavePaciente) {
        if (clavePaciente == null) return;

        myModel.seleccionarPaciente(clavePaciente);

        var data = new java.util.HashMap<String, Object>();
        data.put("modo", "AGREGAR");
        data.put("clavePaciente", clavePaciente);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    /**
    * Prepara la vista para editar una consulta existente.
    *
    * @param claveConsulta Identificador de la consulta a editar.
    */
    @Override
    public void handleEditarConsulta(String claveConsulta) {
        if (claveConsulta == null) return;

        myModel.seleccionarConsulta(claveConsulta);

        var data = new java.util.HashMap<String, Object>();
        data.put("modo", "EDITAR");
        data.put("claveConsulta", claveConsulta);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    @Override
    public void handleEliminarConsulta(String claveConsulta) {
        if (claveConsulta == null) return;

        try {
            myModel.eliminarConsulta(claveConsulta);
        } catch (Exception e) {
            System.err.println("Error eliminando consulta: " + e.getMessage());
        }
    }

    @Override
    public void handleSalir() {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    @Override
    public void handleLogout() {
        myModel.logout();
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }

    
    
    /**
    * Filtra las consultas de un paciente según una fecha exacta.
    * Valida la fecha antes de consultar al modelo.
    *
    * @param clavePaciente Identificador del paciente.
    * @param fecha Fecha a buscar en formato YYYY-MM-DD.
    * @return Lista filtrada de consultas o lista vacía si no hay coincidencias.
    */
    public List<Consulta> buscarConsultasPorFecha(String clavePaciente, String fecha) {   
        if (clavePaciente == null || clavePaciente.isEmpty()) return Collections.emptyList();

        if (fecha == null || fecha.trim().isEmpty()) {
            return Collections.emptyList();
        }

        String FORMATO_FECHA = "\\d{4}-\\d{2}-\\d{2}"; 
        if (!fecha.matches(FORMATO_FECHA)) {
            return Collections.emptyList();
        }

        try {
            return myModel.obtenerConsultasPorPacienteYFecha(clavePaciente, fecha);
        } catch (SQLException _) {
            return Collections.emptyList();
        }
    }
}