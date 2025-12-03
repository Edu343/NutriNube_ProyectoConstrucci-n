package Modelo.Core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import Modelo.Servicios.NutriNubeModelo;
import Vista.*;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;


/**
 * Administrador principal de vistas de la aplicación.
 * Controla la navegación entre pantallas mediante un CardLayout,
 * inicializa vistas, asigna controladores y coordina su interacción
 * con el modelo.
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

    /**
     * Configura el administrador construyendo las vistas y el diseño gráfico.
     *
     * @param modelo Modelo que será inyectado en cada vista.
     */
    private void inicializar(NutriNubeModelo modelo) {
        this.miModelo = modelo;
        crearVistasLogicas(); 
        crearDisenoVista();
    }
    
    /**
     * Crea cada vista lógica de la aplicación, instancia su controlador,
     * realiza la inyección de dependencias y registra las vistas por su TAG.
     */
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


    /**
     * Construye la ventana principal, configura el CardLayout
     * y registra cada vista dentro del contenedor gráfico.
     */
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

   
    /**
     * Cambia la vista activa según su nombre lógico (TAG).
     * Envía datos opcionales a la vista destino y fuerza la actualización.
     *
     * @param vistaNombre Identificador de la vista destino.
     * @param data Información adicional para la vista.
     */
    public void cambiarAVista(String vistaNombre, Object data) {
        gestorTarjetas.show(panelTarjetas, vistaNombre);
        
        View vistaDestino = vistasLogicas.get(vistaNombre);
        if (vistaDestino != null) {
            vistaDestino.cargarDatos(data);
            vistaDestino.update();
        }
    }


}