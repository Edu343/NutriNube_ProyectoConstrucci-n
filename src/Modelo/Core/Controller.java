package Core;

import POJOs.CaloriasCalculo;
import POJOs.Consulta;
import Servicios.NutriNubeModelo;

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
    	
    public void handleAnadirPaciente() {
    	
    }
    public void handleEliminarPaciente() {
    	
    }
    public void handleBuscarPaciente() {
    	
    }
    public void handlePacienteSeleccionado(String clavePaciente) {
    	
    }
    public void handleLogout() {
    	
    }
    public void handleAgregarConsulta() {
    	
    }
    public void handleEliminarConsulta() {
    	
    }
    public void handleEditarConsulta(String id) {
    	
    }
    public void handleCalcularCalorias(CaloriasCalculo calculoData) {
    	
    }
    public void handleGuardarConsulta(Consulta consultaAGuardar) {
    	
    }
    public void handleSalir() {}

    @Override
    public void update() {
    }
}