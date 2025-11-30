package Modelo.Core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Modelo.Servicios.NutriNubeModelo;
import Vista.*;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;


/**
 * Contenedor principal de la aplicación que gestiona la navegación entre vistas usando CardLayout.
 * Es un Observer del Modelo y se encarga de la Inyección de Dependencias a las vistas y controladores.
 * Define las constantes (TAGs) para todas las vistas.
 */

public class MainViewLayout  {
    
    private JFrame ventanaPrincipal;
    private JPanel panelTarjetas;
    private CardLayout gestorTarjetas;
    private Map<String, View> vistasLogicas;
    private NutriNubeModelo miModelo;
    
    public static final String LOGIN_VIEW = "LOGIN";
    public static final String PACIENTES_VIEW = "PACIENTES";
    public static final String HISTORIAL_VIEW = "HISTORIAL";
    public static final String CONSULTA_FORM_VIEW = "CONSULTA_FORM";

    public MainViewLayout(NutriNubeModelo modelo) {
        this.vistasLogicas = new HashMap<>();
        inicializar(modelo);
    }

   
    private void inicializar(NutriNubeModelo modelo) {
        this.miModelo = modelo;
        crearVistasLogicas(); 
        crearDisenoVista();
    }
    
    private void crearVistasLogicas() {

        LoginView loginView = new LoginView(LOGIN_VIEW);
        loginView.inicializar(miModelo, this); 
        vistasLogicas.put(LOGIN_VIEW, loginView);
        
        PacienteListView pacienteListView = new PacienteListView(PACIENTES_VIEW);
        pacienteListView.inicializar(miModelo, this); 
        vistasLogicas.put(PACIENTES_VIEW, pacienteListView);
        
        
        HistorialConsultasView historialView = new HistorialConsultasView(HISTORIAL_VIEW);
        historialView.inicializar(miModelo, this);
        vistasLogicas.put(HISTORIAL_VIEW, historialView);
        
        ConsultaFormularioView consultaFormView = new ConsultaFormularioView(CONSULTA_FORM_VIEW);
        consultaFormView.inicializar(miModelo, this); 
        vistasLogicas.put(CONSULTA_FORM_VIEW, consultaFormView);
    }


  
    private void crearDisenoVista() {

        ventanaPrincipal = new JFrame("NutriNube - Sistema de Gestión Nutricional");
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaPrincipal.setSize(1000, 700);
        
        gestorTarjetas = new CardLayout();
        panelTarjetas = new JPanel(gestorTarjetas);
        
        for (Map.Entry<String, View> entry : vistasLogicas.entrySet()) {
            View v = entry.getValue();
            panelTarjetas.add(v.getViewLayout(), entry.getKey());
        }
        
        ventanaPrincipal.add(panelTarjetas);
        ventanaPrincipal.setLocationRelativeTo(null);
        ventanaPrincipal.setVisible(true);
        
        gestorTarjetas.show(panelTarjetas, LOGIN_VIEW);
    }

   


    public void cambiarAVista(String vistaNombre, Object data) {
        gestorTarjetas.show(panelTarjetas, vistaNombre);
        
        View vistaDestino = vistasLogicas.get(vistaNombre);
        if (vistaDestino != null) {
            vistaDestino.cargarDatos(data);
            vistaDestino.update();
        }
    }


}