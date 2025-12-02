package Controlador;

import Modelo.Core.Controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Consulta;

public class HistorialController extends Controller {

    public HistorialController(String tag) {
        super(tag);
    }

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

    @Override
    public void update() {
    }
    
    public List<Consulta> buscarConsultasPorFecha(String clavePaciente, String fecha) {
        if (clavePaciente == null || clavePaciente.isEmpty()) return Collections.emptyList();

        // Validar formato YYYY-MM-DD (simple)
        if (fecha == null || fecha.trim().isEmpty()) {
            // Si piden lista vacía en blanco, la vista puede pedir recargar todo
            return Collections.emptyList();
        }
        if (!fecha.matches("\\d{4}-\\d{2}-\\d{2}")) {
            // formato inválido -> devolver vacío (la vista puede ignorar y no filtrar)
            return Collections.emptyList();
        }

        try {
            return myModel.obtenerConsultasPorPacienteYFecha(clavePaciente, fecha);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}