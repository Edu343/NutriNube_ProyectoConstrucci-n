package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Paciente;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para manejar la lógica de la lista principal de pacientes.
 * Gestiona la adición, eliminación y selección de pacientes, y el cierre de sesión.
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
        
    }
    
    @Override
    public void handleBuscarPaciente(String nombre) {
    }
    
    @Override
    public void handlePacienteSeleccionado(String clavePaciente) {
        cambiarVista(MainViewLayout.HISTORIAL_VIEW, clavePaciente);
    }
    
    @Override
    public void handleLogout() {
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
    }
}