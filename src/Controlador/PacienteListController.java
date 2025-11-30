package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Paciente;
import Vista.PacienteListView;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Controlador corregido para PacienteListView.
 */
public class PacienteListController extends Controller {

    public PacienteListController(String tag) {
        super(tag);
    }

    @Override
    public void handleAnadirPaciente(Paciente paciente) {
        Map<String, Object> data = new HashMap<>();
        data.put("modo", "AGREGAR");
        data.put("returnViewTag", MainViewLayout.PACIENTES_VIEW);
        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }

    @Override
    public void handleEliminarPaciente(String clavePaciente) {
        try {
            myModel.eliminarPaciente(clavePaciente);
            cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
        } catch (Exception e) {
            System.err.println("Error eliminando paciente: " + e.getMessage());
        }
    }
    
    @Override
    public void handleBuscarPaciente(String criterio) {

        List<Paciente> filtrados = myModel.buscarPacientes(criterio);

        // verificar vista correcta
        if (!(myView instanceof PacienteListView vista)) return;

        DefaultTableModel model =
            (DefaultTableModel) vista.getTblPacientes().getModel();

        model.setRowCount(0);

        for (Paciente p : filtrados) {
            String nombre = p.getNombre() == null ? "" : p.getNombre();
            String apellido = p.getApellido() == null ? "" : p.getApellido();

            model.addRow(new Object[]{
                    p.getClavePaciente(),
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

    @Override
    public void update() {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }
}
