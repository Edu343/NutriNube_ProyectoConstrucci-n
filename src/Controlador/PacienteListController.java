package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import POJOs.Paciente;
import java.util.HashMap;
import java.util.Map;

public class PacienteListController extends Controller {
    
    public PacienteListController(String tag) {
        super(tag);
    }
    
    public void handleAnadirPacienteRequest() {
        Map<String, Object> data = new HashMap<>();
        data.put("modo", "AGREGAR"); 
        data.put("returnViewTag", MainViewLayout.PACIENTES_VIEW); 

        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data); 
    }
    
    public void handleEliminarPacienteRequest(String clavePaciente) {
        // Simulación: No hace nada
    }
    
    public void handleBuscarPacienteRequest(String criterio) {
        // Simulación: No hace nada
    }
    
    @Override
    public void handlePacienteSeleccionado(String clavePaciente) {
        // Navega a Historial pasando una clave de prueba
        cambiarVista(MainViewLayout.HISTORIAL_VIEW, clavePaciente);
    }
    
    public void handleLogoutRequest() {
        // Navega a LOGIN
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }
    
    public void handleGuardarPacienteRequest(Paciente paciente) {
        // Vuelve a la lista de pacientes
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }
    
    public void handleCancelarFormularioRequest() {
        // Vuelve a la lista de pacientes
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }

    @Override
    public void update() {
    }
}