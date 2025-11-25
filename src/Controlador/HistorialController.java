package Controladores;

import Core.Controller;
import Core.MainViewLayout;
import POJOs.Consulta;
import java.util.HashMap;
import java.util.Map;

public class HistorialController extends Controller {
    
    public HistorialController(String tag) {
        super(tag);
    }
    
    public void handleAgregarConsultaRequest(String clavePaciente) {
        Map<String, Object> data = new HashMap<>();
        // MODO: AGREGAR, Retorno: HISTORIAL
        data.put("modo", ConsultaController.MODO_AGREGAR);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);
        // Clave de paciente (simulada)
        data.put("clavePacienteActual", clavePaciente);
        
        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }
    
    public void handleEliminarConsultaRequest(String claveConsulta) {
        // Simulación: No hace nada
    }
    
    public void handleEditarConsultaRequest(String claveConsulta) {
        // Simulación: Crea una consulta ficticia para modo EDITAR
        Consulta consultaMock = new Consulta(); 
        consultaMock.setClaveConsulta(claveConsulta);
        consultaMock.setClavePaciente("CLAVE-PACIENTE-MOCK");

        Map<String, Object> data = new HashMap<>();
        // MODO: EDITAR, Retorno: HISTORIAL
        data.put("modo", ConsultaController.MODO_EDITAR);
        data.put("returnViewTag", MainViewLayout.HISTORIAL_VIEW);
        data.put("consultaActual", consultaMock);
        
        cambiarVista(MainViewLayout.CONSULTA_FORM_VIEW, data);
    }
    
    public void handleVolverPacientesRequest() {
        // Vuelve a la lista de pacientes
        cambiarVista(MainViewLayout.PACIENTES_VIEW, null);
    }
    
    public void handleLogoutRequest() {
        // Vuelve a LOGIN
        cambiarVista(MainViewLayout.LOGIN_VIEW, null);
    }
    
    @Override
    public void update() {
    }
}