package Modelo.Core;

import Modelo.POJOs.CaloriasCalculo;
import Modelo.POJOs.Consulta;
import Modelo.POJOs.Paciente;
import Modelo.Servicios.NutriNubeModelo;

/**
 * Clase base abstracta para todos los controladores del sistema.
 * Implementa la interfaz Observer y maneja la comunicación entre el Modelo y la Vista.
 * Centraliza la Inyección de Dependencia para la navegación (cambiarVista).
 */


public abstract class Controller implements Observer {
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
        myModel.attach(this);
    }
    

    protected void cambiarVista(String vistaNombre, Object data) {
        if (mainLayout != null) {
            mainLayout.cambiarAVista(vistaNombre, data);
        }
    }
    
    

    public void handleLogin(String usuario, String contrasena) {
    	
    }
    	
    public void handleAnadirPaciente(Paciente paciente) {
    	
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
    public void handleEditarConsulta(String id) {
    	
    }
    public void handleCalcularCalorias(CaloriasCalculo calculoData, Paciente clavePaciente) {
    	
    }
    public void handleGuardarConsulta(Consulta consultaAGuardar) {
    	
    }
    public void handleSalir() {}

    @Override
    public void update() {
    }
}