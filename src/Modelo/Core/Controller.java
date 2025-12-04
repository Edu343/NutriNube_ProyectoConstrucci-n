package Modelo.Core;


import Modelo.POJOs.Paciente;
import Modelo.POJOs.Consulta;
import Modelo.Servicios.NutriNubeModelo;
import java.util.Collections;
import java.util.List;

/**
 * Clase base abstracta para todos los controladores del sistema.
 * Implementa la interfaz Observer y maneja la comunicación entre el Modelo y la Vista.
 * Centraliza la Inyección de Dependencia para la navegación (cambiarVista).
 */
public abstract class Controller {
    protected NutriNubeModelo myModel;
    protected View myView;
    protected String tag;
    

    private MainViewLayout mainLayout; 

    public Controller(String tag) {
        this.tag = tag;
    }

    public void initialize(NutriNubeModelo model, View view, MainViewLayout layout) {
        myModel = model;
        myView = view;
        this.mainLayout = layout; 
    }
    

    public void cambiarVista(String vistaNombre, Object data) {
        if (mainLayout != null) {
            mainLayout.cambiarAVista(vistaNombre, data);
        }
    }
    
    

    public void handleLogin(String usuario, String contrasena) {
    	
    }
    	
    public void handleAnadirPaciente() {
    	
    }
    public void handleEliminarPaciente(String clavePaciente) {
    	
    }
    public void handleActualizarPaciente(Paciente paciente) {
        
    }
    public void handleBuscarPaciente(String nombre) {
    	
    }
    public void handlePacienteSeleccionado(String clavePaciente) {
    	
    }
    public void handleLogout() {
    	
    }
    public void handleAgregarConsulta(String clavePaciente) {
    	
    }
    public void handleEliminarConsulta(String claveConsulta) {
    	
    }
    public void handleEditarConsulta(String claveConsulta) {
    	
    }
 
    public void handleCalcularCalorias(String clavePaciente, double peso, int nivelActividadFisica, int razonConsulta, 
                                       int sexo, double altura, String fechaNacimiento) {
        
    }
    
    public void handleGuardarConsulta(Paciente paciente, Consulta consulta, String modo) {
        
    }

    public void handleSalir() {

    }

    public List<Consulta> buscarConsultasPorFecha(String clavePaciente, String fecha) {
        return Collections.emptyList();
    }

}