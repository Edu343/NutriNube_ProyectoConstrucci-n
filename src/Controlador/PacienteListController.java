package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import POJOs.Paciente;
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
        // Prepara los datos para abrir el formulario en modo AGREGAR.
        Map<String, Object> data = new HashMap<>();
        data.put("modo", "AGREGAR"); 
        data.put("returnViewTag", MainViewLayout.PACIENTES_VIEW); 

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data); 
    }
    
    @Override
    public void handleEliminarPaciente(String clavePaciente) {
        // Lógica para eliminar paciente.
    }
    
    @Override
    public void handleBuscarPaciente(String nombre) {
        // Lógica para filtrar la lista de pacientes.
    }
    
    @Override
    public void handlePacienteSeleccionado(String clavePaciente) {
        // Navega a la vista de historial del paciente seleccionado.
        cambiarVista(MainViewLayout.HISTORIAL_VIEW, clavePaciente);
    }
    
    @Override
    public void handleLogout() {
        // Cierra sesión y regresa a la vista de Login.
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }
    
    @Override
    public void handleActualizarPaciente(Paciente paciente) {
        // Lógica de guardado/actualización y retorno a la lista.
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }
    
    @Override
    public void handleSalir() {
        // Regresa a la lista de pacientes (usado típicamente en formularios de paciente).
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    @Override
    public void update() {
    }
}