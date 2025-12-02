package Modelo.Core;


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
    
    public void handleGuardarConsulta(String clavePaciente, String claveConsulta, String nombre, String apellido,
            String correo, int sexo, String telefono, String fechaNacimiento, double altura,
            String condicionesMedicas, String medicacion, String historialCirugias, String alergias,
            String preferenciaComida, String horarioSueno, int nivelEstres, String habitosAlimenticios,
            String tipoLiquidosConsumidos, double cantidadLiquidoConsumido, String barreraAlimenticia,
            double peso, int nivelActividadFisica, int razonConsulta, double calorias, double carbohidratos,
            double proteinas, double lipidos, String modo) {
        
    }
    public void handleSalir() {
    	
    }
    

    @Override
    public abstract void update();
}