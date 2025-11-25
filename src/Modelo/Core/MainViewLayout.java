package Core;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.util.HashMap;
import java.util.Map;
import Servicios.NutriNubeModelo;
import Vistas.*;

public class MainViewLayout implements Observer  {
    
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
        this.miModelo.attach(this);
        crearVistasLogicas(); 
        crearDisenoVista();
    }
    
    private void crearVistasLogicas() {
        // 🚨 MODIFICADO: Ahora pasamos 'this' (la instancia de MainViewLayout) a la inicialización

        LoginView loginView = new LoginView(LOGIN_VIEW);
        loginView.inicializar(miModelo, this); // <-- Inyección
        vistasLogicas.put(LOGIN_VIEW, loginView);
        
        PacienteListView pacienteListView = new PacienteListView(PACIENTES_VIEW);
        pacienteListView.inicializar(miModelo, this); // <-- Inyección
        vistasLogicas.put(PACIENTES_VIEW, pacienteListView);
        
        
        HistorialConsultasView historialView = new HistorialConsultasView(HISTORIAL_VIEW);
        historialView.inicializar(miModelo, this); // <-- Inyección
        vistasLogicas.put(HISTORIAL_VIEW, historialView);
        
        ConsultaFormularioView consultaFormView = new ConsultaFormularioView(CONSULTA_FORM_VIEW);
        consultaFormView.inicializar(miModelo, this); // <-- Inyección
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
            vistaDestino.loadData(data);
            vistaDestino.update();
        }
    }


	@Override
	public void update() {
	}
}