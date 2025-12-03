package Controlador;

import javax.swing.table.DefaultTableModel;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Paciente;
import Vista.PacienteListView;

import java.util.*;

/**
 * Controlador encargado de la vista de listado de pacientes.
 * Gestiona búsqueda, eliminación, selección y navegación entre vistas.
 */
public class PacienteListController extends Controller {

    public PacienteListController(String tag) {
        super(tag);
    }

    @Override
    public void handleAnadirPaciente() {
        Map<String, Object> data = new HashMap<>();
        data.put("modo", "AGREGAR");
        data.put("returnViewTag", MainViewLayout.PACIENTES_VIEW);
        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    @Override
    public void handleEliminarPaciente(String clavePaciente) {
        try {
            myModel.eliminarPaciente(clavePaciente);
        } catch (Exception e) {
            System.err.println("Error eliminando paciente: " + e.getMessage());
        }
    }
    
    @Override
    public void handleBuscarPaciente(String criterio) {
        List<Paciente> filtrados = myModel.buscarPacientes(criterio);

        if (!(myView instanceof PacienteListView vista)) return;

        DefaultTableModel model = (DefaultTableModel) vista.getTblPacientes().getModel();
        model.setRowCount(0);

        for (Paciente paciente : filtrados) {
            String nombre = paciente.getNombre() == null ? "" : paciente.getNombre();
            String apellido = paciente.getApellido() == null ? "" : paciente.getApellido();

            model.addRow(new Object[]{
                    paciente.getClavePaciente(),
                    nombre + " " + apellido,
                    "N/A"
            });
        }
    }

    @Override
    public void handlePacienteSeleccionado(String clavePaciente) {
        myModel.seleccionarPaciente(clavePaciente);
        cambiarVista(MainViewLayout.HISTORIAL_VIEW, clavePaciente);
    }

    @Override
    public void handleLogout() {
        myModel.logout();
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }

    @Override
    public void handleActualizarPaciente(Paciente paciente) {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    @Override
    public void handleSalir() {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    
}