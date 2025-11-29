package Controlador;

import Modelo.Core.Controller;
import Modelo.Core.MainViewLayout;
import Modelo.POJOs.Consulta;
import java.util.HashMap;
import java.util.Map;

/**
 * Controlador para manejar la lógica de la vista del historial de consultas de un paciente.
 * Gestiona la navegación hacia el formulario de consulta (agregar/editar) y el retorno a la lista de pacientes.
 */

public class HistorialController extends Controller {
    
    public HistorialController(String tag) {
        super(tag);
    }
    
    @Override
    public void handleAgregarConsulta(String clavePaciente) {

    }
    
    @Override
    public void handleEliminarConsulta(String claveConsulta) {
        // Lógica para eliminar una consulta.
    }
    
    @Override
    public void handleEditarConsulta(String claveConsulta) {

        
        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, null);
    }
    
    @Override
    public void handleSalir() {
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }
    
    @Override
    public void handleLogout() {
        // Cierra sesión y regresa a la vista de Login.
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }
    
    @Override
    public void update() {
    }
}